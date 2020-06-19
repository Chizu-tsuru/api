package com.chizu.tsuru.api.features.workspaces.data.models;

import com.chizu.tsuru.api.features.workspace.data.models.ClusterModel;
import com.chizu.tsuru.api.features.workspace.data.models.WorkspaceModel;
import com.chizu.tsuru.api.features.workspace.domain.entities.Cluster;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class WorkspaceModelTest {
    final WorkspaceModel workspaceModel = new WorkspaceModel(1, "testing");

    final List<ClusterModel> clusters = new ArrayList<>();

    final Workspace workspace = new Workspace(1, "testing", new ArrayList<>());

    @Before
    public void setUp() {
        workspaceModel.setClusters(clusters);
        clusters.add(new ClusterModel(2, 2.0, 2.0, "area", workspaceModel));
        clusters.add(new ClusterModel(3, 2.0, 3.0, "area", workspaceModel));
        workspace.getClusters().add(new Cluster(2, 2.0, 2.0, "area", new ArrayList<>()));
        workspace.getClusters().add(new Cluster(3, 2.0, 3.0, "area", new ArrayList<>()));
    }

    @Test
    public void workspace_model_should_return_workspace() {
        workspaceModel.setClusters(clusters);
        var result = workspaceModel.toWorkspace();

        assertThat(result).isEqualTo(workspace);
    }

    @Test
    public void workspace_model_from_workspace() {
        var createWorkspaceModel = WorkspaceModel.fromWorkspace(workspace);

        assertThat(createWorkspaceModel).isEqualTo(workspaceModel);
    }
}
