package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.entities.Tag;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.clusters.repositories.LocationRepository;
import com.chizu.tsuru.api.clusters.repositories.TagRepository;
import com.chizu.tsuru.api.config.Configuration;
import com.chizu.tsuru.api.shared.exceptions.NotFoundException;
import com.chizu.tsuru.api.shared.services.ResponseService;
import com.chizu.tsuru.api.workspaces.dto.CreateLocationDTO;

import java.net.URI;
import java.text.SimpleDateFormat;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final ResponseService responseService;
    private final ClusterRepository clusterRepository;
    private final TagRepository tagRepository;


    @Autowired
    public LocationService(LocationRepository locationRepository,
                           ResponseService responseService,
                           ClusterRepository clusterRepository,
                           TagRepository tagRepository) {
        this.locationRepository = locationRepository;
        this.responseService = responseService;
        this.clusterRepository = clusterRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    public Location getLocation(Integer id) {
        return locationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Location not found"));
    }

    @Transactional(readOnly = true)
    public List<GetLocationDTO> getLocations() {
        return locationRepository
                .findAll()
                .stream()
                .map(this.responseService::getLocationDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetLocationDTO> getLocationsByCluster(Cluster cluster) {
        return  locationRepository.findAllByCluster(cluster)
                .stream()
                .map(this.responseService::getLocationDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetLocationDTO> getLocationsByCluster(Integer clusterId) {
        return  locationRepository.findAllByClusterId(clusterId)
                .stream()
                .map(this.responseService::getLocationDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetLocationDTO> getLocationsByWorkspace(Workspace workspace) {
        return  locationRepository.findAllByWorkspace(workspace)
                .stream()
                .map(this.responseService::getLocationDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Location createLocation(CreateLocationDTO createLocationDTO,Integer clusterId) {
        Cluster c = clusterRepository.findById(clusterId).orElseThrow(() -> new NotFoundException("Cluster not found"));
        Location location = Location.builder()
                .cluster(c)
                .latitude(createLocationDTO.getLatitude())
                .longitude(createLocationDTO.getLongitude())
                .tags(new ArrayList<Tag>())
                .build();

        if( createLocationDTO.getTags() != null ){
            for( String tag :createLocationDTO.getTags()){
                Tag t = Tag.builder()
                        .name(tag)
                        .build();
                location.getTags().add(t);
            }
        }

        Location created = locationRepository.save(location);

        for(Tag t: location.getTags()){
            tagRepository.save(t);
        }
        return created;
    }

    public int saveRequest(String origin, Date date, Double executionTime, int totalResult, String requestUrl){
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            URI url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/stats").build().toUri();
            HttpPost httpPost = new HttpPost(url);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            String json = "{\"origin\":\"" + origin +"\","
                + "\"date\":\"" + format.format(date) +"\","
                + "\"executionTime\":" + executionTime +","
                + "\"totalResult\":" + totalResult +","
                + "\"request\":\"" + requestUrl +"\"}";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            client.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
