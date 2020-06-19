package com.chizu.tsuru.api.features.workspace.domain.useCases;

import com.chizu.tsuru.api.core.useCases.NoParams;
import com.chizu.tsuru.api.core.useCases.UseCase;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.api.features.workspace.domain.repository.WorkspaceRepository;

import java.util.List;

public class GetWorkspaces implements UseCase<List<Workspace>, NoParams> {
    private final WorkspaceRepository workspaceRepository;

    public GetWorkspaces(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public List<Workspace> execute(NoParams noParams) {
        return workspaceRepository.getWorkspaces();
    }
}
