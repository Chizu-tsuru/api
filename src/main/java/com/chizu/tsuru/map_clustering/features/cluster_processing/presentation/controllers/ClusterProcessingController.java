package com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.controllers;

import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.MinimumDistanceAverage;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases.GetMinimumDistanceAverage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clusters")
public class ClusterProcessingController {
    private final GetMinimumDistanceAverage getMinimumDistanceAverage;

    public ClusterProcessingController(GetMinimumDistanceAverage getMinimumDistanceAverage) {
        this.getMinimumDistanceAverage = getMinimumDistanceAverage;
    }

    @GetMapping("/{clusterId}/minMaxAvg")
    public MinimumDistanceAverage getMinimumDistanceAverage(@PathVariable Integer clusterId) {
        return getMinimumDistanceAverage.execute(clusterId);
    }
}
