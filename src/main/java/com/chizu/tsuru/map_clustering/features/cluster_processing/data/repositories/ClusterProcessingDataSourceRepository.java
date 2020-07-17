package com.chizu.tsuru.map_clustering.features.cluster_processing.data.repositories;

import com.chizu.tsuru.map_clustering.core.errors.NotFoundException;
import com.chizu.tsuru.map_clustering.features.cluster_processing.data.datasources.ClusterProcessingMysqlDataSource;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.repositories.ClusterProcessingRepository;
import org.springframework.stereotype.Service;

@Service
public class ClusterProcessingDataSourceRepository implements ClusterProcessingRepository {
    private final ClusterProcessingMysqlDataSource clusterProcessingMysqlDataSource;

    public ClusterProcessingDataSourceRepository(ClusterProcessingMysqlDataSource clusterProcessingMysqlDataSource) {
        this.clusterProcessingMysqlDataSource = clusterProcessingMysqlDataSource;
    }

    @Override
    public Cluster getClusterById(Integer clusterId) {
        return this.clusterProcessingMysqlDataSource.findById(clusterId)
                .orElseThrow(() -> new NotFoundException("Cluster not found"))
                .toCluster();
    }
}
