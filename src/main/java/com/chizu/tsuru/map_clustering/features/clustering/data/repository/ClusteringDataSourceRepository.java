package com.chizu.tsuru.map_clustering.features.clustering.data.repository;

import com.chizu.tsuru.map_clustering.core.errors.NotFoundException;
import com.chizu.tsuru.map_clustering.features.clustering.data.datasources.ClusteringMysqlDataSource;
import com.chizu.tsuru.map_clustering.features.clustering.data.datasources.ClusteringGoogleApiDataSource;
import com.chizu.tsuru.map_clustering.features.clustering.data.models.WorkspaceModel;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Address;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.repository.ClusteringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ClusteringDataSourceRepository implements ClusteringRepository {
    final ClusteringMysqlDataSource clusteringMysqlDataSource;
    final ClusteringGoogleApiDataSource clusteringGoogleApiDataSource;

    @Autowired
    public ClusteringDataSourceRepository(ClusteringMysqlDataSource clusteringMysqlDataSource, ClusteringGoogleApiDataSource clusteringGoogleApiDataSource) {
        this.clusteringMysqlDataSource = clusteringMysqlDataSource;
        this.clusteringGoogleApiDataSource = clusteringGoogleApiDataSource;
    }

    @Override
    public List<Workspace> getWorkspaces() {
        return clusteringMysqlDataSource.findAll().stream().map(WorkspaceModel::toWorkspace).collect(Collectors.toList());
    }

    @Override
    public Optional<Workspace> getWorkspace(Integer workspaceId) {
        return clusteringMysqlDataSource.findById(workspaceId).map(WorkspaceModel::toWorkspace);
    }

    @Override
    public Workspace createWorkspace(Workspace workspace) {
        return clusteringMysqlDataSource.save(WorkspaceModel.fromWorkspace(workspace)).toWorkspace();
    }

    @Override
    public Address getAddressByCoordinates(double longitude, double latitude) {
        return clusteringGoogleApiDataSource
                .getLocalisationFromCoordinates(latitude, longitude)
                .toAddress();
    }

    @Override
    public List<Cluster> getClusters(Integer workspaceId) {
        var workspace = getWorkspace(workspaceId).orElseThrow(() -> new NotFoundException("Workspace not found"));
        return workspace.getClusters();
    }
}
