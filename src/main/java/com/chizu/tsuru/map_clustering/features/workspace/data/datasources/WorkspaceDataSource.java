package com.chizu.tsuru.map_clustering.features.workspace.data.datasources;

import com.chizu.tsuru.map_clustering.features.workspace.data.models.WorkspaceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceDataSource extends JpaRepository<WorkspaceModel, Integer> {
}
