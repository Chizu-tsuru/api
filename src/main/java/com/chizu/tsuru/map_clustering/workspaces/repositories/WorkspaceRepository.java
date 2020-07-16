package com.chizu.tsuru.map_clustering.workspaces.repositories;

import com.chizu.tsuru.map_clustering.workspaces.entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
}
