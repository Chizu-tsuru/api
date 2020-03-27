package com.chizu.tsuru.api.repositories;

import com.chizu.tsuru.api.Entities.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Integer> {
}
