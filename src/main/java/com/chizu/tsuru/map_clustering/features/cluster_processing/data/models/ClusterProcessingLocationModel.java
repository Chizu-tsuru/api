package com.chizu.tsuru.map_clustering.features.cluster_processing.data.models;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Location;
import com.chizu.tsuru.map_clustering.features.clustering.data.models.ClusterModel;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "locations")
public class ClusterProcessingLocationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer locationId;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cluster_id")
    private ClusteringModel cluster;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClusterProcessingTagModel> tags;

    public ClusterProcessingLocationModel() {}

    public ClusterProcessingLocationModel(Integer locationId, Double latitude, Double longitude, ClusteringModel clusterModel) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cluster = clusterModel;
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

    public ClusteringModel getCluster() {
        return cluster;
    }

    public void setCluster(ClusteringModel cluster) {
        this.cluster = cluster;
    }

    public List<ClusterProcessingTagModel> getTags() {
        return tags;
    }

    public void setTags(List<ClusterProcessingTagModel> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterProcessingLocationModel that = (ClusterProcessingLocationModel) o;
        return Objects.equals(locationId, that.locationId) &&
                Objects.equals(latitude, that.latitude) &&
                Objects.equals(longitude, that.longitude) &&
                Objects.equals(cluster, that.cluster) &&
                Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationId, latitude, longitude, cluster, tags);
    }

    public Location toLocation() {
        return new Location(locationId, latitude, longitude, tags.stream().map(ClusterProcessingTagModel::toTag).collect(Collectors.toList()));
    }

    public static ClusterProcessingLocationModel fromLocation(Location location, ClusteringModel clusterModel) {
        var locationModel = new ClusterProcessingLocationModel(location.getLocationId(), location.getLatitude(), location.getLongitude(), clusterModel);
        var tags = location.getTags().stream().map(tag -> ClusterProcessingTagModel.fromTag(tag, locationModel)).collect(Collectors.toList());
        locationModel.setTags(tags);
        return locationModel;
    }
}
