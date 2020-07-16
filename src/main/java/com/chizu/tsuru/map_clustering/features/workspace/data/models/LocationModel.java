package com.chizu.tsuru.map_clustering.features.workspace.data.models;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Location;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "locations")
public class LocationModel {
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
    private ClusterModel cluster;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TagModel> tags;

    public LocationModel() {}

    public LocationModel(Integer locationId, Double latitude, Double longitude, ClusterModel clusterModel) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cluster = clusterModel;
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

    public ClusterModel getCluster() {
        return cluster;
    }

    public void setCluster(ClusterModel cluster) {
        this.cluster = cluster;
    }

    public List<TagModel> getTags() {
        return tags;
    }

    public void setTags(List<TagModel> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationModel that = (LocationModel) o;
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
        return new Location(locationId, latitude, longitude, tags.stream().map(TagModel::toTag).collect(Collectors.toList()));
    }

    public static LocationModel fromLocation(Location location, ClusterModel clusterModel) {
        var l = new LocationModel(location.getLocationId(), location.getLatitude(), location.getLongitude(), clusterModel);
        var tags = location.getTags().stream().map(tag -> TagModel.fromTag(tag, l)).collect(Collectors.toList());
        l.setTags(tags);
        return l;
    }
}
