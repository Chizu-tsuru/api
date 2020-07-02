package com.chizu.tsuru.api.core.config.injections;

import com.chizu.tsuru.api.features.workspace.data.datasources.WorkspaceDataSource;
import com.chizu.tsuru.api.features.workspace.data.repository.WorkspaceMysqlRepository;
import com.chizu.tsuru.api.features.workspace.domain.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoriesConfig {
    private final WorkspaceDataSource workspaceDataSource;

    @Autowired
    public RepositoriesConfig(WorkspaceDataSource workspaceDataSource) {
        this.workspaceDataSource = workspaceDataSource;
    }

    public @Bean WorkspaceRepository WorkspaceRepository () {
        return new WorkspaceMysqlRepository(workspaceDataSource);
    }
}