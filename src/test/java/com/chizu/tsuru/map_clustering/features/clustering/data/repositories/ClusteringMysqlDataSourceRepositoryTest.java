package com.chizu.tsuru.map_clustering.features.clustering.data.repositories;

import com.chizu.tsuru.map_clustering.features.clustering.data.datasources.ClusteringMysqlDataSource;
import com.chizu.tsuru.map_clustering.features.clustering.data.datasources.ClusteringGoogleApiDataSource;
import com.chizu.tsuru.map_clustering.features.clustering.data.models.AddressModel;
import com.chizu.tsuru.map_clustering.features.clustering.data.models.WorkspaceModel;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Workspace;
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
public class ClusteringMysqlDataSourceRepositoryTest {
    ClusteringDataSourceRepository clusteringDataSourceRepository;
    @Mock
    ClusteringMysqlDataSource clusteringMysqlDataSource;
    @Mock
    ClusteringGoogleApiDataSource clusteringGoogleApiDataSource;

    final List<WorkspaceModel> workspaceModels = new ArrayList<>();

    final List<Workspace> workspaces = new ArrayList<>();

    final AddressModel addressModel = new AddressModel("toto", "tata", "titi", "tutu", "tete");

    @Before
    public void setUp() {
        workspaceModels.add(new WorkspaceModel(1, "testing"));
        workspaceModels.add(new WorkspaceModel(2, "testing2"));
        workspaces.add(new Workspace(1, "testing", new ArrayList<>()));
        workspaces.add(new Workspace(2, "testing2", new ArrayList<>()));
        clusteringDataSourceRepository = new ClusteringDataSourceRepository(clusteringMysqlDataSource, clusteringGoogleApiDataSource);
    }

    @Test
    public void get_workspaces() {
        when(clusteringMysqlDataSource.findAll()).thenReturn(workspaceModels);
        lenient()
                .when(clusteringGoogleApiDataSource.getLocalisationFromCoordinates(anyDouble(), anyDouble()))
                .thenReturn(addressModel);
        var result = clusteringDataSourceRepository.getWorkspaces();
        verify(clusteringMysqlDataSource).findAll();
        verify(clusteringGoogleApiDataSource, never()).getLocalisationFromCoordinates(anyDouble(), anyDouble());
        assertEquals(result, workspaces);
    }
}
