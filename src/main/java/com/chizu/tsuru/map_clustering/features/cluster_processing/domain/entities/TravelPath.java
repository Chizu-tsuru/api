package com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities;

import java.util.List;

public class TravelPath {
    public List<Location> locations;
    public double distance;

    public TravelPath(List<Location> locations, double distance) {
        this.locations = locations;
        this.distance = distance;
    }
}
