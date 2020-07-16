package com.chizu.tsuru.map_clustering.features.workspace.data.models;

import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Cluster;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "clusters")
public class ClusterModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer clusterId;

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;

    private String area;

    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "workspace_id")
    private WorkspaceModel workspace;

    @OneToMany(mappedBy = "cluster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LocationModel> locations = new ArrayList<>();

    @OneToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressModel address;

    public ClusterModel() {
    }

    public ClusterModel(Integer clusterId, @NotNull double longitude, @NotNull double latitude,
                        String area, WorkspaceModel workspaceModel) {
        this.clusterId = clusterId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.area = area;
        this.workspace = workspaceModel;
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

    public WorkspaceModel getWorkspace() {
        return workspace;
    }

    public void setWorkspace(WorkspaceModel workspace) {
        this.workspace = workspace;
    }

    public List<LocationModel> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationModel> locations) {
        this.locations = locations;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterModel that = (ClusterModel) o;
        return Double.compare(that.longitude, longitude) == 0 &&
                Double.compare(that.latitude, latitude) == 0 &&
                Objects.equals(clusterId, that.clusterId) &&
                Objects.equals(area, that.area) &&
                Objects.equals(workspace, that.workspace) &&
                Objects.equals(locations.size(), that.locations.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clusterId, longitude, latitude, area, workspace, locations);
    }

    public Cluster toCluster() {
        var locs = locations.stream().map(LocationModel::toLocation).collect(Collectors.toList());
        return new Cluster(clusterId, longitude, latitude, area, locs, address.toAddress());
    }

    public static ClusterModel fromCluster(Cluster cluster, WorkspaceModel workspaceModel) {
        var c = new ClusterModel(cluster.getClusterId(), cluster.getLongitude(), cluster.getLatitude(),
                cluster.getArea(), workspaceModel);
        var address = AddressModel.fromAddress(cluster.getAddress(), c);
        var locations = cluster.getLocations().stream().map(location -> LocationModel.fromLocation(location, c))
                .collect(Collectors.toList());
        c.setLocations(locations);
        c.setAddress(address);
        return c;
    }
}
