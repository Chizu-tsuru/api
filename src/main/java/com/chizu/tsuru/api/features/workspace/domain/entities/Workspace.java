package com.chizu.tsuru.api.features.workspace.domain.entities;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Workspace {
    private Integer workspaceId;
    private String name;
    private List<Cluster> clusters;

    public Workspace(Integer workspaceId, String name, List<Cluster> clusters) {
        this.workspaceId = workspaceId;
        this.name = name;
        this.clusters = clusters;
    }

    public <E> Workspace(String name, ArrayList<Cluster> clusters) {
        this.name = name;
        this.clusters = clusters;
    }

    public Integer getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Integer workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workspace workspace = (Workspace) o;
        return Objects.equals(workspaceId, workspace.workspaceId) &&
                Objects.equals(name, workspace.name) &&
                Objects.equals(clusters, workspace.clusters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, name, clusters);
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "workspaceId=" + workspaceId +
                ", name='" + name + '\'' +
                ", clusters=" + clusters +
                '}';
    }
}
