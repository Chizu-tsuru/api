package com.chizu.tsuru.map_clustering.features.workspace.data.repository;

import com.chizu.tsuru.map_clustering.core.errors.NotFoundException;
import com.chizu.tsuru.map_clustering.features.workspace.data.datasources.WorkspaceDataSource;
import com.chizu.tsuru.map_clustering.features.workspace.data.datasources.WorkspaceGoogleApiDataSource;
import com.chizu.tsuru.map_clustering.features.workspace.data.models.WorkspaceModel;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Address;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.workspace.domain.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WorkspaceDataSourceRepository implements WorkspaceRepository {
    final WorkspaceDataSource workspaceDataSource;
    final WorkspaceGoogleApiDataSource workspaceGoogleApiDataSource;

    @Autowired
    public WorkspaceDataSourceRepository(WorkspaceDataSource workspaceDataSource, WorkspaceGoogleApiDataSource workspaceGoogleApiDataSource) {
        this.workspaceDataSource = workspaceDataSource;
        this.workspaceGoogleApiDataSource = workspaceGoogleApiDataSource;
    }

    @Override
    public List<Workspace> getWorkspaces() {
        return workspaceDataSource.findAll().stream().map(WorkspaceModel::toWorkspace).collect(Collectors.toList());
    }

    @Override
    public Optional<Workspace> getWorkspace(Integer workspaceId) {
        return workspaceDataSource.findById(workspaceId).map(WorkspaceModel::toWorkspace);
    }

    @Override
    public Workspace createWorkspace(Workspace workspace) {
        return workspaceDataSource.save(WorkspaceModel.fromWorkspace(workspace)).toWorkspace();
    }

    @Override
    public Address getAddressByCoordinates(double longitude, double latitude) {
        return workspaceGoogleApiDataSource
                .getLocalisationFromCoordinates(latitude, longitude)
                .toAddress();
    }

    @Override
    public List<Cluster> getClusters(Integer workspaceId) {
        var workspace = getWorkspace(workspaceId).orElseThrow(() -> new NotFoundException("Workspace not found"));
        return workspace.getClusters();
    }
}
