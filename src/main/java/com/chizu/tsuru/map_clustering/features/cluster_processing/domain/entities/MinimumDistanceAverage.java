package com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities;

public class MinimumDistanceAverage {
    public Integer numberLocations;
    public Double minMaxAvgDistance;

    public MinimumDistanceAverage(Integer numberLocations, Double minMaxAvgDistance) {
        this.numberLocations = numberLocations;
        this.minMaxAvgDistance = minMaxAvgDistance;
    }
}
