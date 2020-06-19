package com.chizu.tsuru.api.features.workspace.data.repository;

import com.chizu.tsuru.api.features.workspace.data.datasources.WorkspaceDataSource;
import com.chizu.tsuru.api.features.workspace.data.models.WorkspaceModel;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.api.features.workspace.domain.repository.WorkspaceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkspaceMysqlRepository implements WorkspaceRepository {
    final WorkspaceDataSource workspaceDataSource;

    public WorkspaceMysqlRepository(WorkspaceDataSource workspaceDataSource) {
        this.workspaceDataSource = workspaceDataSource;
    }

    @Override
    public List<Workspace> getWorkspaces() {
        return workspaceDataSource.findAll().stream().map(WorkspaceModel::toWorkspace).collect(Collectors.toList());
    }

    @Override
    public Optional<Workspace> getWorkspace(Integer workspaceId) {
        return workspaceDataSource.findById(workspaceId).map(WorkspaceModel::toWorkspace);
    }

    @Override
    public Workspace createWorkspace(Workspace workspace) {
        return workspaceDataSource.save(WorkspaceModel.fromWorkspace(workspace)).toWorkspace();
    }
}
