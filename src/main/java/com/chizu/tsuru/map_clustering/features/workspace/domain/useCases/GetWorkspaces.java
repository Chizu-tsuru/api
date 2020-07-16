package com.chizu.tsuru.map_clustering.features.workspace.domain.useCases;

import com.chizu.tsuru.map_clustering.core.useCases.NoParams;
import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.workspace.domain.repository.WorkspaceRepository;

// INJECTION DEPENDENCIES (CHECK OF REMOVE THEM)
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetWorkspaces implements UseCase<List<Workspace>, NoParams> {

    private final WorkspaceRepository workspaceRepository;

    public GetWorkspaces(@Qualifier("workspaceDataSourceRepository") WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public List<Workspace> execute(NoParams noParams) {
        return workspaceRepository.getWorkspaces();
    }
}
