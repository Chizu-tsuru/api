package com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases;

import com.chizu.tsuru.map_clustering.core.errors.NotFoundException;
import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.repository.ClusteringRepository;

public class GetWorkspaceById implements UseCase<Workspace, Integer> {
    private final ClusteringRepository clusteringRepository;

    public GetWorkspaceById(ClusteringRepository clusteringRepository) {
        this.clusteringRepository = clusteringRepository;
    }

    @Override
    public Workspace execute(Integer workspaceId) {
        return clusteringRepository.getWorkspace(workspaceId)
                .orElseThrow(() -> new NotFoundException("Workspace not found"));
    }
}
