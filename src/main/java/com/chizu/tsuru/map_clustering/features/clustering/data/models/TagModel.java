package com.chizu.tsuru.map_clustering.features.clustering.data.models;

import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Tag;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "tags")
public class TagModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tagId;

    @NotNull
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private LocationModel location;

    public TagModel() {}

    public TagModel(Integer tagId, String name, LocationModel location) {
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

    public LocationModel getLocations() {
        return location;
    }

    public void setLocations(LocationModel location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagModel tagModel = (TagModel) o;
        return Objects.equals(tagId, tagModel.tagId) &&
                Objects.equals(name, tagModel.name) &&
                Objects.equals(location, tagModel.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, name, location);
    }

    public Tag toTag() {
        return new Tag(tagId, name);
    }

    public static TagModel fromTag(Tag tag, LocationModel location) {
        return new TagModel(tag.getTagId(), tag.getName(), location);
    }
}
