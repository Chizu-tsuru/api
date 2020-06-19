package com.chizu.tsuru.api.features.workspaces.data.repository;

import com.chizu.tsuru.api.features.workspace.data.datasources.WorkspaceDataSource;
import com.chizu.tsuru.api.features.workspace.data.models.WorkspaceModel;
import com.chizu.tsuru.api.features.workspace.data.repository.WorkspaceMysqlRepository;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WorkspaceMysqlRepositoryTest {
    WorkspaceMysqlRepository workspaceMysqlRepository;
    @Mock
    WorkspaceDataSource workspaceDataSource;

    final List<WorkspaceModel> workspaceModels = new ArrayList<>();

    final List<Workspace> workspaces = new ArrayList<>();

    @Before
    public void setUp() {
        workspaceModels.add(new WorkspaceModel(1, "testing"));
        workspaceModels.add(new WorkspaceModel(2, "testing2"));
        workspaces.add(new Workspace(1, "testing", new ArrayList<>()));
        workspaces.add(new Workspace(2, "testing2", new ArrayList<>()));
        workspaceMysqlRepository = new WorkspaceMysqlRepository(workspaceDataSource);
    }

    @Test
    public void get_workspaces() {
        when(workspaceDataSource.findAll()).thenReturn(workspaceModels);
        var result = workspaceMysqlRepository.getWorkspaces();
        verify(workspaceDataSource).findAll();
        assertEquals(result, workspaces);
    }
}
