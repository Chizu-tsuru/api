package com.chizu.tsuru.map_clustering.features.workspace.domain.useCases;

import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.workspace.domain.repository.WorkspaceRepository;

// INJECTION DEPENDENCIES (CHECK OF REMOVE THEM)
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetClustersByWorkspace implements UseCase<List<Cluster>, Integer> {
    private final WorkspaceRepository workspaceRepository;

    public GetClustersByWorkspace(@Qualifier("workspaceDataSourceRepository") WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public List<Cluster> execute(Integer workspaceId) {
        return this.workspaceRepository.getClusters(workspaceId);
    }
}
