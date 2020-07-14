package com.chizu.tsuru.api.features.workspaces.data.repository;

import com.chizu.tsuru.api.features.workspace.data.datasources.WorkspaceDataSource;
import com.chizu.tsuru.api.features.workspace.data.datasources.WorkspaceGoogleApiDataSource;
import com.chizu.tsuru.api.features.workspace.data.models.AddressModel;
import com.chizu.tsuru.api.features.workspace.data.models.WorkspaceModel;
import com.chizu.tsuru.api.features.workspace.data.repository.WorkspaceDataSourceRepository;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WorkspaceDataSourceRepositoryTest {
    WorkspaceDataSourceRepository workspaceDataSourceRepository;
    @Mock
    WorkspaceDataSource workspaceDataSource;
    @Mock
    WorkspaceGoogleApiDataSource workspaceGoogleApiDataSource;

    final List<WorkspaceModel> workspaceModels = new ArrayList<>();

    final List<Workspace> workspaces = new ArrayList<>();

    final AddressModel addressModel = new AddressModel("toto", "tata", "titi", "tutu", "tete");

    @Before
    public void setUp() {
        workspaceModels.add(new WorkspaceModel(1, "testing"));
        workspaceModels.add(new WorkspaceModel(2, "testing2"));
        workspaces.add(new Workspace(1, "testing", new ArrayList<>()));
        workspaces.add(new Workspace(2, "testing2", new ArrayList<>()));
        workspaceDataSourceRepository = new WorkspaceDataSourceRepository(workspaceDataSource, workspaceGoogleApiDataSource);
    }

    @Test
    public void get_workspaces() {
        when(workspaceDataSource.findAll()).thenReturn(workspaceModels);
        lenient()
                .when(workspaceGoogleApiDataSource.getLocalisationFromCoordinates(anyDouble(), anyDouble()))
                .thenReturn(addressModel);
        var result = workspaceDataSourceRepository.getWorkspaces();
        verify(workspaceDataSource).findAll();
        verify(workspaceGoogleApiDataSource, never()).getLocalisationFromCoordinates(anyDouble(), anyDouble());
        assertEquals(result, workspaces);
    }
}
