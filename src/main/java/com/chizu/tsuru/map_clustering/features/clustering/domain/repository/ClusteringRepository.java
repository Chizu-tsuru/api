package com.chizu.tsuru.map_clustering.features.clustering.domain.repository;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Address;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Workspace;
import java.util.List;
import java.util.Optional;

public interface ClusteringRepository {
    List<Workspace> getWorkspaces();
    Optional<Workspace> getWorkspace(Integer workspaceId);
    Workspace createWorkspace(Workspace workspace);
    Address getAddressByCoordinates(double longitude, double latitude);
    List<Cluster> getClusters(Integer workspaceId);
}
