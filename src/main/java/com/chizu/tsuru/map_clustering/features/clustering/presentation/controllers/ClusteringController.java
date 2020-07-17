package com.chizu.tsuru.map_clustering.features.clustering.presentation.controllers;

import com.chizu.tsuru.map_clustering.core.useCases.NoParams;
import com.chizu.tsuru.map_clustering.features.clustering.domain.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.map_clustering.features.clustering.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.CreateWorkspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.GetClustersByWorkspace;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.GetWorkspaceById;
import com.chizu.tsuru.map_clustering.features.clustering.domain.use_cases.GetWorkspaces;
import com.chizu.tsuru.map_clustering.features.clustering.presentation.dto.GetClusterDTO;
import com.chizu.tsuru.map_clustering.features.clustering.presentation.services.ClusteringResponseService;
import com.chizu.tsuru.map_clustering.features.clustering.presentation.dto.GetWorkspaceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class ClusteringController {
    final ClusteringResponseService clusteringResponseService;
    final GetWorkspaces getWorkspaces;
    final GetWorkspaceById getWorkspaceById;
    final CreateWorkspace createWorkspace;
    final GetClustersByWorkspace getClustersByWorkspace;

    public ClusteringController(ClusteringResponseService clusteringResponseService, GetWorkspaces getWorkspaces,
                                GetWorkspaceById getWorkspaceById, CreateWorkspace createWorkspace, GetClustersByWorkspace getClustersByWorkspace) {
        this.clusteringResponseService = clusteringResponseService;
        this.getWorkspaces = getWorkspaces;
        this.getWorkspaceById = getWorkspaceById;
        this.createWorkspace = createWorkspace;
        this.getClustersByWorkspace = getClustersByWorkspace;
    }

    @GetMapping
    public List<GetWorkspaceDTO> getGetWorkspaces() {
        var workspaces = getWorkspaces.execute(new NoParams());
        return clusteringResponseService.workspacesToDTO(workspaces);
    }

    @GetMapping("/{workspaceId}")
    public GetWorkspaceDTO getWorkspace(@PathVariable Integer workspaceId) {
        var workspace = getWorkspaceById.execute(workspaceId);
        return clusteringResponseService.workspaceToDTO(workspace);
    }

    @GetMapping("/{idWorkspace}/clusters")
    public List<GetClusterDTO> getClustersByWorkspace(@PathVariable("idWorkspace") Integer workspaceId) {
        var clusters = getClustersByWorkspace.execute(workspaceId);
        return clusteringResponseService.clustersToDTO(clusters);

    }

    @PostMapping
    public ResponseEntity<Workspace> CreateWorkspace(@Validated @RequestBody CreateWorkspaceDTO workspaceDTO) {
        var workspace = createWorkspace.execute(workspaceDTO);
        URI location = clusteringResponseService.getLocationWorkspace(workspace.getWorkspaceId());
        return ResponseEntity.created(location).body(workspace);
    }
}
