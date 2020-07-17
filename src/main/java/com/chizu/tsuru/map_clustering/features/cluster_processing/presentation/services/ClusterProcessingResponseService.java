package com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.services;

import com.chizu.tsuru.map_clustering.core.services.URIService;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.dto.GetClusterDTO;
import org.springframework.stereotype.Service;

@Service
public class ClusterProcessingResponseService {
    final URIService uriService;

    public ClusterProcessingResponseService(URIService uriService) {
        this.uriService = uriService;
    }

    public GetClusterDTO clusterToDTO(Cluster cluster) {
        var locationsUri = uriService.getLocations(cluster.getClusterId()).toString();
        return new GetClusterDTO(cluster.getLongitude(), cluster.getLatitude(), cluster.getArea());
    }
}
