package com.chizu.tsuru.api.features.workspace.domain.useCases;

import com.chizu.tsuru.api.core.errors.NotFoundException;
import com.chizu.tsuru.api.core.useCases.UseCase;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.api.features.workspace.domain.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GetWorkspaceById implements UseCase<Workspace, Integer> {
    private final WorkspaceRepository workspaceRepository;

    public GetWorkspaceById(@Qualifier("workspaceMysqlRepository") WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Workspace execute(Integer workspaceId) {
        return workspaceRepository.getWorkspace(workspaceId)
                .orElseThrow(() -> new NotFoundException("Workspace not found"));
    }
}
