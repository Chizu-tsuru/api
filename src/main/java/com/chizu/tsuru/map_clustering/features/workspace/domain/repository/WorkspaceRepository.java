package com.chizu.tsuru.map_clustering.features.workspace.domain.repository;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Address;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Workspace;
import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository {
    List<Workspace> getWorkspaces();
    Optional<Workspace> getWorkspace(Integer workspaceId);
    Workspace createWorkspace(Workspace workspace);
    Address getAddressByCoordinates(double longitude, double latitude);
    List<Cluster> getClusters(Integer workspaceId);
}
