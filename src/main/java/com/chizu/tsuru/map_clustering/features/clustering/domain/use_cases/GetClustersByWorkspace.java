package com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases;

import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.clustering.domain.repository.ClusteringRepository;

import java.util.List;

public class GetClustersByWorkspace implements UseCase<List<Cluster>, Integer> {
    private final ClusteringRepository clusteringRepository;

    public GetClustersByWorkspace(ClusteringRepository clusteringRepository) {
        this.clusteringRepository = clusteringRepository;
    }

    @Override
    public List<Cluster> execute(Integer workspaceId) {
        return this.clusteringRepository.getClusters(workspaceId);
    }
}
