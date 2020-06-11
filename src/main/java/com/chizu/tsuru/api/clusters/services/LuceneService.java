package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.clusters.repositories.LocationRepository;
import com.chizu.tsuru.api.config.Configuration;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;


@Service
public class LuceneService {
    private final Configuration configuration;
    private final LocationRepository locationRepository;
    private final LuceneManager luceneManager ;

    @Autowired
    public LuceneService(LocationRepository locationRepository,Configuration configuration) {
        this.locationRepository = locationRepository;
        this.luceneManager = new LuceneManager(locationRepository, configuration.getLuceneFolder());
        this.configuration = configuration;
    }

    public void searchLocationByLatitude(String query, int count){
        try {
            luceneManager.searchQuery("latitude", query, count);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void searchLocationByLongitude(String query, int count){
        try {
            luceneManager.searchQuery("longitude", query, count);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void searchLocationByRange(String field, String query, int count){
        try {
            luceneManager.searchQuery(field, query, count);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}

class LuceneManager{
    private Directory index;
    private StandardAnalyzer analyzer;

    public LuceneManager(LocationRepository locationRepository, String luceneFolder) {
        try {
            analyzer = new StandardAnalyzer();
            index = MMapDirectory.open(Paths.get(luceneFolder));

            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            IndexWriter w = new IndexWriter(index, config);

            List<Location> locationList = getLocations(locationRepository);

            for(Location location : locationList){
                addDoc(w, location);
            }

            w.close();
        }catch (IOException  e) {
            e.printStackTrace();
        }

    }

    private static void addDoc(IndexWriter w, Location location) throws IOException {
        Document doc = new Document();
        doc.add(new StoredField("locationId", location.getLocationId()));
        doc.add(new TextField("latitude", location.getLatitude().toString(), Field.Store.YES));
        doc.add(new TextField("longitude", location.getLongitude().toString(), Field.Store.YES));
        doc.add(new StoredField("cluster", location.getCluster().getClusterId().toString()));
//        doc.add(new TextField("Tag", location.getTags().toString(), Field.Store.YES));
        w.addDocument(doc);
    }

    private List<Location> getLocations(LocationRepository locationRepository){
        return locationRepository.findAll();
    }

    public void searchQuery(String field, String query, int count) throws ParseException, IOException {


        Query q = new QueryParser(field, analyzer).parse(query);

        IndexReader reader = DirectoryReader.open(index);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs docs = searcher.search(q,count);
        ScoreDoc[] hits = docs.scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for(int i=0;i<hits.length;++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get("locationId") + "\t" + d.get("latitude")  + "\t" + d.get("longitude")  + "\t" + d.get("cluster"));
        }
    }


}
