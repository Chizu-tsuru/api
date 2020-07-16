package com.chizu.tsuru.map_clustering.features.workspace.domain.dto;

import java.util.List;

public class CreateLocationDTO {
    private final double latitude;
    private final double longitude;
    private final List<String> tags;

    public CreateLocationDTO(double latitude, double longitude, List<String> tags) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getTags() {
        return tags;
    }
}
