package com.chizu.tsuru.map_clustering.features.clustering.data.datasources;

import com.chizu.tsuru.map_clustering.features.clustering.data.models.WorkspaceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusteringMysqlDataSource extends JpaRepository<WorkspaceModel, Integer> {
}
