package com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.controllers;

import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.dto.GetTravelPathDTO;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.MinimumDistanceAverage;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.TravelPath;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetClusterById;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetMinimumDistanceAverage;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetTravelPath;
import com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.dto.GetClusterDTO;
import com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.services.ClusterProcessingResponseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clusters")
public class ClusterProcessingController {
    final private ClusterProcessingResponseService clusterProcessingResponseService;
    private final GetMinimumDistanceAverage getMinimumDistanceAverage;
    private final GetTravelPath getTravelPath;
    private final GetClusterById getClusterById;

    public ClusterProcessingController(ClusterProcessingResponseService clusterProcessingResponseService, GetMinimumDistanceAverage getMinimumDistanceAverage, GetTravelPath getTravelPath, GetClusterById getClusterById) {
        this.clusterProcessingResponseService = clusterProcessingResponseService;
        this.getMinimumDistanceAverage = getMinimumDistanceAverage;
        this.getTravelPath = getTravelPath;
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

    @GetMapping("/{clusterId}/travel")
    public TravelPath getTravelPath(@PathVariable Integer clusterId,
                                    @RequestParam() Double longitude,
                                    @RequestParam() Double latitude) {
        var travelPathDto = new GetTravelPathDTO(latitude, longitude, clusterId);
        return getTravelPath.execute(travelPathDto);
    }
}
