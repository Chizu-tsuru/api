package com.chizu.tsuru.api.features.workspace.domain.entities;
import java.util.List;
import java.util.Objects;

public class Location {
    private Integer locationId;
    private Double latitude;
    private Double longitude;
    private List<Tag> tags;

    public Location(Integer locationId, Double latitude, Double longitude, List<Tag> tags) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tags = tags;
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(locationId, location.locationId) &&
                Objects.equals(latitude, location.latitude) &&
                Objects.equals(longitude, location.longitude) &&
                Objects.equals(tags, location.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, latitude, longitude, tags);
    }
}
