package com.chizu.tsuru.map_clustering.features.clustering.data.models;

import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Workspace;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "workspaces")
public class WorkspaceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer workspaceId;

    @NotNull
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClusterModel> clusters = new ArrayList<>();

    public WorkspaceModel() {}

    public WorkspaceModel(Integer workspaceId, String name) {
        this.workspaceId = workspaceId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "WorkspaceModel{" +
                "workspace_id=" + workspaceId +
                ", name='" + name + '\'' +
                ", clusters=" + clusters +
                '}';
    }

    public Integer getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Integer workspace_id) {
        this.workspaceId = workspace_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClusterModel> getClusters() {
        return clusters;
    }

    public void setClusters(List<ClusterModel> clusters) {
        this.clusters = clusters;
    }

    public Workspace toWorkspace() {
        return new Workspace(workspaceId, name, clusters.stream().map(ClusterModel::toCluster).collect(Collectors.toList()));
    }

    public static WorkspaceModel fromWorkspace(Workspace workspace) {
        var w =  new WorkspaceModel(workspace.getWorkspaceId(), workspace.getName());
        var clusters = workspace.getClusters().stream()
                .map(cluster -> ClusterModel.fromCluster(cluster, w)).collect(Collectors.toList());
        w.setClusters(clusters);
        return w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceModel that = (WorkspaceModel) o;
        return Objects.equals(workspaceId, that.workspaceId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(clusters.size(), that.clusters.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, name, clusters);
    }
}
