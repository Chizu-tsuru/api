package com.chizu.tsuru.api.shared.services;

import com.chizu.tsuru.api.clusters.dto.GetClusterDTO;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.workspaces.dto.GetWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    private final URIService uriService;
    public ResponseService(URIService uriService) {
        this.uriService = uriService;
    }

    public GetWorkspaceDTO getWorkspaceDTO(Workspace w) {
        return GetWorkspaceDTO.builder()
                .name(w.getName())
                .clusters(this.uriService.getClusters(w.getWorkspace_id()).toString())
                .build();
    }

    public GetClusterDTO getClusterDTO(Cluster c) {
        return GetClusterDTO.builder()
                .area(c.getArea())
                .latitude(c.getLatitude())
                .longitude(c.getLongitude())
                .locations(this.uriService.getLocations(c.getClusterId()).toString())
                .workspace(this.uriService.getWorkspace(c.getWorkspace().getWorkspace_id()).toString())
                .build();
    }

}
