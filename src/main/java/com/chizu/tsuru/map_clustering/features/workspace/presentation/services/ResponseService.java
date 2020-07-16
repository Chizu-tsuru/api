package com.chizu.tsuru.map_clustering.features.workspace.presentation.services;

import com.chizu.tsuru.map_clustering.core.services.URIService;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.map_clustering.features.workspace.presentation.dto.GetClusterDTO;
import com.chizu.tsuru.map_clustering.features.workspace.presentation.dto.GetWorkspaceDTO;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    final URIService uriService;

    public ResponseService(URIService uriService) {
        this.uriService = uriService;
    }

    public GetWorkspaceDTO workspaceToDTO(Workspace workspace) {
        var clustersUri = uriService.getClusters(workspace.getWorkspaceId()).toString();
        return new GetWorkspaceDTO(workspace.getName(), clustersUri);
    }

    public List<GetWorkspaceDTO> workspacesToDTO(List<Workspace> workspaces) {
        return workspaces.stream().map(this::workspaceToDTO).collect(Collectors.toList());
    }

    public GetClusterDTO clusterToDTO(Cluster cluster) {
        var locationsUri = uriService.getLocations(cluster.getClusterId()).toString();
        var addressUri = uriService
                .getAddress(cluster.getAddress().getAddressId())
                .toString();
        return new GetClusterDTO(cluster.getLongitude(), cluster.getLatitude(), cluster.getArea(), locationsUri,addressUri);
    }

    public List<GetClusterDTO> clustersToDTO(List<Cluster> clusters) {
        return clusters.stream().map(this::clusterToDTO).collect(Collectors.toList());
    }

    public URI getLocationWorkspace(Integer workspaceId) {
        return uriService.fromParent(workspaceId);
    }
}
