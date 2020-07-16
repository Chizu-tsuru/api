package com.chizu.tsuru.map_clustering.features.workspace.domain.entities;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cluster {
    private Integer clusterId;
    private double longitude;
    private double latitude;
    private String area;
    private List<Location> locations = new ArrayList<>();
    private Address address;

    public Cluster(Integer clusterId, double longitude, double latitude,
                   String area, List<Location> locations, Address address) {
        this.clusterId = clusterId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
        this.locations = locations;
        this.address = address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cluster cluster = (Cluster) o;
        return Double.compare(cluster.longitude, longitude) == 0 &&
                Double.compare(cluster.latitude, latitude) == 0 &&
                Objects.equals(clusterId, cluster.clusterId) &&
                Objects.equals(area, cluster.area) &&
                Objects.equals(locations, cluster.locations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusterId, longitude, latitude, area, locations);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
