package com.chizu.tsuru.map_clustering.features.cluster_processing.data.datasources;

import com.chizu.tsuru.map_clustering.features.cluster_processing.data.models.ClusteringModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClusterProcessingMysqlDataSource extends JpaRepository<ClusteringModel, Integer> {
}
