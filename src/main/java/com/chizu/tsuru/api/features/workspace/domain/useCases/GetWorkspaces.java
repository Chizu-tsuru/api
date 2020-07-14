package com.chizu.tsuru.api.features.workspace.domain.useCases;

import com.chizu.tsuru.api.core.useCases.NoParams;
import com.chizu.tsuru.api.core.useCases.UseCase;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.api.features.workspace.domain.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetWorkspaces implements UseCase<List<Workspace>, NoParams> {

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public GetWorkspaces(@Qualifier("workspaceDataSourceRepository") WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public List<Workspace> execute(NoParams noParams) {
        return workspaceRepository.getWorkspaces();
    }
}
