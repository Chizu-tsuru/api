package com.chizu.tsuru.map_clustering.features.workspace.presentation.dto;

public class GetClusterDTO {
    public Double longitude;
    public Double latitude;
    public String area;
    public String locations;
    public String address;

    public GetClusterDTO(Double longitude, Double latitude, String area, String locations, String address) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
        this.locations = locations;
        this.address = address;
    }
}
