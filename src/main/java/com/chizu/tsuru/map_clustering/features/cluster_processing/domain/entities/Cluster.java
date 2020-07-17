package com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Cluster {
    private Integer clusterId;
    private double longitude;
    private double latitude;
    private String area;
    private List<Location> locations = new ArrayList<>();

    public Cluster(Integer clusterId, double longitude, double latitude,
                   String area, List<Location> locations) {
        this.clusterId = clusterId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
        this.locations = locations;
    }

    public Cluster() {}

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
