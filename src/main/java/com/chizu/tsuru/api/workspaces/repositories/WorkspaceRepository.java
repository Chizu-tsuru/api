package com.chizu.tsuru.api.workspaces.repositories;

import com.chizu.tsuru.api.workspaces.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
}
