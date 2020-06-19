package com.chizu.tsuru.api.features.workspace.domain.repository;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository {
    List<Workspace> getWorkspaces();
    Optional<Workspace> getWorkspace(Integer workspaceId);
    Workspace createWorkspace(Workspace workspace);
}
