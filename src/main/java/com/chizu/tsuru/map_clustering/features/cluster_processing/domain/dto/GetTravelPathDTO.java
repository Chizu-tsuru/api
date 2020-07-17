package com.chizu.tsuru.map_clustering.features.cluster_processing.domain.dto;

public class GetTravelPathDTO {
    public double latitude;
    public double longitude;
    public Integer clusterId;

    public GetTravelPathDTO(double latitude, double longitude, Integer clusterId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.clusterId = clusterId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
