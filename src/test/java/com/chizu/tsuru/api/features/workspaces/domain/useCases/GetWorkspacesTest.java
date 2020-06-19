package com.chizu.tsuru.api.features.workspaces.domain.useCases;

import com.chizu.tsuru.api.core.useCases.NoParams;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.api.features.workspace.domain.repository.WorkspaceRepository;
import com.chizu.tsuru.api.features.workspace.domain.useCases.GetWorkspaces;
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
    WorkspaceRepository workspaceRepository;

    GetWorkspaces getWorkspaces;
    final List<Workspace> workspace = new ArrayList<>();
    @Before
    public void setUp() {
        getWorkspaces = new GetWorkspaces(workspaceRepository);
        workspace.add(new Workspace(2, "testing", new ArrayList<>()));
        workspace.add(new Workspace(3, "testing2", new ArrayList<>()));
    }

    @Test
    public void should_return_workspace_list() {
        Mockito.when(workspaceRepository.getWorkspaces())
                .thenReturn(workspace);

        final var result = getWorkspaces.execute(new NoParams());

        assertThat(result).isEqualTo(workspace);
        verify(workspaceRepository).getWorkspaces();
        verifyNoMoreInteractions(workspaceRepository);
    }
}
