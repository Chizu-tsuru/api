package com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases;

import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.repositories.ClusterProcessingRepository;

public class GetClusterById implements UseCase<Cluster, Integer> {
    private final ClusterProcessingRepository clusterProcessingRepository;

    public GetClusterById(ClusterProcessingRepository clusterProcessingRepository) {
        this.clusterProcessingRepository = clusterProcessingRepository;
    }

    @Override
    public Cluster execute(Integer clusterId) {
        return clusterProcessingRepository.getClusterById(clusterId);
    }
}
