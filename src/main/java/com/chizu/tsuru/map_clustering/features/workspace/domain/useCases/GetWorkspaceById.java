package com.chizu.tsuru.map_clustering.features.workspace.domain.useCases;

import com.chizu.tsuru.map_clustering.core.errors.NotFoundException;
import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.workspace.domain.repository.WorkspaceRepository;

// INJECTION DEPENDENCIES (CHECK OF REMOVE THEM)
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GetWorkspaceById implements UseCase<Workspace, Integer> {
    private final WorkspaceRepository workspaceRepository;

    public GetWorkspaceById(@Qualifier("workspaceDataSourceRepository") WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Workspace execute(Integer workspaceId) {
        return workspaceRepository.getWorkspace(workspaceId)
                .orElseThrow(() -> new NotFoundException("Workspace not found"));
    }
}
