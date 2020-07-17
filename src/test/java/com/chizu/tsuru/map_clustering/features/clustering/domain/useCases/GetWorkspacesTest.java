package com.chizu.tsuru.map_clustering.features.clustering.domain.useCases;

import com.chizu.tsuru.map_clustering.core.useCases.NoParams;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.repositories.ClusteringRepository;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.GetWorkspaces;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetWorkspacesTest {
    @Mock
    ClusteringRepository clusteringRepository;

    GetWorkspaces getWorkspaces;
    final List<Workspace> workspace = new ArrayList<>();
    @Before
    public void setUp() {
        getWorkspaces = new GetWorkspaces(clusteringRepository);
        workspace.add(new Workspace(2, "testing", new ArrayList<>()));
        workspace.add(new Workspace(3, "testing2", new ArrayList<>()));
    }

    @Test
    public void should_return_workspace_list() {
        Mockito.when(clusteringRepository.getWorkspaces())
                .thenReturn(workspace);

        final var result = getWorkspaces.execute(new NoParams());

        assertThat(result).isEqualTo(workspace);
        verify(clusteringRepository).getWorkspaces();
        verifyNoMoreInteractions(clusteringRepository);
    }
}
