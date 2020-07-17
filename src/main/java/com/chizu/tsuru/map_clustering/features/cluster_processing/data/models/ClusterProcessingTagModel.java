package com.chizu.tsuru.map_clustering.features.cluster_processing.data.models;

import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Tag;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "tags")
public class ClusterProcessingTagModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @NotNull
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private ClusterProcessingLocationModel location;

    public ClusterProcessingTagModel() {}

    public ClusterProcessingTagModel(Integer tagId, String name, ClusterProcessingLocationModel location) {
        this.tagId = tagId;
        this.name = name;
        this.location = location;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClusterProcessingLocationModel getLocations() {
        return location;
    }

    public void setLocations(ClusterProcessingLocationModel location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterProcessingTagModel clusterProcessingTagModel = (ClusterProcessingTagModel) o;
        return Objects.equals(tagId, clusterProcessingTagModel.tagId) &&
                Objects.equals(name, clusterProcessingTagModel.name) &&
                Objects.equals(location, clusterProcessingTagModel.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, name, location);
    }

    public Tag toTag() {
        return new Tag(tagId, name);
    }

    public static ClusterProcessingTagModel fromTag(Tag tag, ClusterProcessingLocationModel location) {
        return new ClusterProcessingTagModel(tag.getTagId(), tag.getName(), location);
    }
}
