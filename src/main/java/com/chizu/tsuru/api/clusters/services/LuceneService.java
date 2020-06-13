package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.dto.GetLocationLuceneDTO;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.entities.Tag;
import com.chizu.tsuru.api.clusters.repositories.LocationRepository;
import com.chizu.tsuru.api.config.Configuration;
import com.chizu.tsuru.api.shared.services.ResponseService;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class LuceneService {
    private final LocationRepository locationRepository;
    private final Configuration configuration;

    private Directory index;
    private StandardAnalyzer analyzer;

    @Autowired
    public LuceneService(LocationRepository locationRepository, Configuration configuration) {
        this.locationRepository = locationRepository;
        this.configuration = configuration;
    }

    @PostConstruct
    public void setup(){
        try {
            analyzer = new StandardAnalyzer();
            index = MMapDirectory.open(Paths.get(this.configuration.getLuceneFolder()));

            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            IndexWriter w = new IndexWriter(index, config);

            List<Location> locationList = getLocations(this.locationRepository);

            for(Location location : locationList){
                addDoc(w, location);
            }

            w.close();
        }catch (IOException  e) {
            e.printStackTrace();
        }
    }

    public List<GetLocationLuceneDTO> searchLocationWithCustom(String field, String query, int count){
        try {
            return this.searchQuery(field, query, count);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<GetLocationLuceneDTO> searchLocationWithMultipleValue(
            String q_latitude,
            String q_longitude,
            String q_city,
            String q_area,
            String q_administrative_area_1,
            String q_administrative_area_2,
            String q_country,
            String q_tags,
            int count){
        try {
            return this.SearchMultipleQuery(q_latitude, q_longitude, q_city, q_area, q_administrative_area_1, q_administrative_area_2, q_country, q_tags, count);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void addDoc(IndexWriter w, Location location) throws IOException {
        Document doc = new Document();
        //Location
        doc.add(new StoredField("locationId", location.getLocationId()));
        doc.add(new TextField("latitude", location.getLatitude().toString(), Field.Store.YES));
        doc.add(new TextField("longitude", location.getLongitude().toString(), Field.Store.YES));

        //Cluster
        doc.add(new StoredField("clusterId", location.getCluster().getClusterId().toString()));

        // Address
        doc.add(new TextField("city", location.getCluster().getAddress().getCity(), Field.Store.YES));
        doc.add(new TextField("area", location.getCluster().getAddress().getArea(), Field.Store.YES));
        doc.add(new TextField("administrative_area_1", location.getCluster().getAddress().getAdministrative_area_1(), Field.Store.YES));
        doc.add(new TextField("administrative_area_2", location.getCluster().getAddress().getAdministrative_area_2(), Field.Store.YES));
        doc.add(new TextField("country", location.getCluster().getAddress().getCountry(), Field.Store.YES));

        //Tag
        String tagToJoin = "";
        for( Tag tag :location.getTags()){
            tagToJoin = tagToJoin.concat(tag.getName()+";");
        }
        doc.add(new TextField("tags", tagToJoin, Field.Store.YES));

        w.addDocument(doc);
    }

    private List<Location> getLocations(LocationRepository locationRepository){
        return locationRepository.findAll();
    }

    private List<GetLocationLuceneDTO> searchQuery(String field, String query, int count) throws ParseException, IOException {
        Query q = new QueryParser(field, analyzer).parse(query);

        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q,count);
        ScoreDoc[] hits = docs.scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        return parseAndReturnResponse(hits);
    }

    private List<GetLocationLuceneDTO> parseAndReturnResponse(ScoreDoc[] hits) throws IOException {
        List<GetLocationLuceneDTO> getLocationDTOList = new ArrayList<>();
        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        for (ScoreDoc hit : hits) {
            int docId = hit.doc;
            Document d = searcher.doc(docId);

            getLocationDTOList.add(GetLocationLuceneDTO.builder()
                    .locationId(Integer.parseInt(d.get("locationId")))
                    .latitude(Double.parseDouble(d.get("latitude")))
                    .longitude(Double.parseDouble(d.get("longitude")))
                    .tags(Arrays.asList(d.get("tags").split(";")))
                    .cluster(d.get("clusterId"))
                    .build());
        }

        return getLocationDTOList;
    }

    private List<GetLocationLuceneDTO> SearchMultipleQuery(
            String q_latitude,
            String q_longitude,
            String q_city,
            String q_area,
            String q_administrative_area_1,
            String q_administrative_area_2,
            String q_country,
            String q_tags,
            int count) throws ParseException, IOException {
        var booleanQuerryBuilder = new BooleanQuery.Builder();

        if(q_latitude != null)
            booleanQuerryBuilder.add(new QueryParser("latitude", analyzer).parse(q_latitude), BooleanClause.Occur.MUST);
        if(q_longitude != null)
            booleanQuerryBuilder.add(new QueryParser("longitude", analyzer).parse(q_longitude), BooleanClause.Occur.MUST);
        if(q_city != null)
            booleanQuerryBuilder.add(new QueryParser("city", analyzer).parse(q_city), BooleanClause.Occur.MUST);
        if(q_area != null)
            booleanQuerryBuilder.add(new QueryParser("area", analyzer).parse(q_area), BooleanClause.Occur.MUST);
        if(q_administrative_area_1 != null)
            booleanQuerryBuilder.add(new QueryParser("administrative_area_1", analyzer).parse(q_administrative_area_1), BooleanClause.Occur.MUST);
        if(q_administrative_area_2 != null)
            booleanQuerryBuilder.add(new QueryParser("administrative_area_2", analyzer).parse(q_administrative_area_2), BooleanClause.Occur.MUST);
        if(q_country != null)
            booleanQuerryBuilder.add(new QueryParser("country", analyzer).parse(q_country), BooleanClause.Occur.MUST);
        if(q_tags != null)
            booleanQuerryBuilder.add(new QueryParser("tags", analyzer).parse(q_tags), BooleanClause.Occur.MUST);

        BooleanQuery bq = booleanQuerryBuilder.build();

        System.out.println(bq.toString());

        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(bq,count);
        ScoreDoc[] hits = docs.scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        return parseAndReturnResponse(hits);
    }
}
