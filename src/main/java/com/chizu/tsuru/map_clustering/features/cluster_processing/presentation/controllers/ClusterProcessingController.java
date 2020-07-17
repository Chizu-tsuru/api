package com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.controllers;

import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.MinimumDistanceAverage;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetClusterById;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetMinimumDistanceAverage;
import com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.dto.GetClusterDTO;
import com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.services.ClusterProcessingResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clusters")
public class ClusterProcessingController {
    final private ClusterProcessingResponseService clusterProcessingResponseService;
    private final GetMinimumDistanceAverage getMinimumDistanceAverage;
    private final GetClusterById getClusterById;

    public ClusterProcessingController(ClusterProcessingResponseService clusterProcessingResponseService, GetMinimumDistanceAverage getMinimumDistanceAverage, GetClusterById getClusterById) {
        this.clusterProcessingResponseService = clusterProcessingResponseService;
        this.getMinimumDistanceAverage = getMinimumDistanceAverage;
        this.getClusterById = getClusterById;
    }

    @GetMapping("/{clusterId}")
    public GetClusterDTO getCluster(@PathVariable Integer clusterId) {
        var cluster = getClusterById.execute(clusterId);
        return this.clusterProcessingResponseService.clusterToDTO(cluster);
    }

    @GetMapping("/{clusterId}/minMaxAvg")
    public MinimumDistanceAverage getMinimumDistanceAverage(@PathVariable Integer clusterId) {
        return getMinimumDistanceAverage.execute(clusterId);
    }
}
