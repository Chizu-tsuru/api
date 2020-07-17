package com.chizu.tsuru.map_clustering.features.cluster_processing.domain.repositories;

import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Cluster;

public interface ClusterProcessingRepository {
   Cluster getClusterById(Integer clusterId);
}
