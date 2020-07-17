package com.chizu.tsuru.map_clustering.features.cluster_processing.presentation.dto;

public class GetClusterDTO {
    public Double longitude;
    public Double latitude;
    public String area;

    public GetClusterDTO(Double longitude, Double latitude, String area) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
    }
}
