package com.chizu.tsuru.map_clustering.features.cluster_processing.data.models;

import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Cluster;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "clusters")
public class ClusteringModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clusterId;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;

    private String area;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClusterProcessingLocationModel> locations = new ArrayList<>();

    public ClusteringModel() {
    }

    public ClusteringModel(Integer clusterId, @NotNull double longitude, @NotNull double latitude,
                        String area) {
        this.clusterId = clusterId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
    }

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
    public List<ClusterProcessingLocationModel> getLocations() {
        return locations;
    }

    public void setLocations(List<ClusterProcessingLocationModel> locations) {
        this.locations = locations;
    }

    public Cluster toCluster() {
        var locations = this.locations.stream().map(ClusterProcessingLocationModel::toLocation).collect(Collectors.toList());
        return new Cluster(clusterId, longitude, latitude, area, locations);
    }
}
