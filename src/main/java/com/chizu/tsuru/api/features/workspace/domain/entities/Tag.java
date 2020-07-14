package com.chizu.tsuru.api.features.workspace.domain.entities;
import java.util.Objects;

public class Tag {

    private Integer tagId;
    private String name;

    public Tag(Integer tagId, String name) {
        this.tagId = tagId;
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getTagId() {
        return tagId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(tagId, tag.tagId) &&
                Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, name);
    }
}
