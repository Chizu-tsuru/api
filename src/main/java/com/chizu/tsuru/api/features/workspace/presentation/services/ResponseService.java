package com.chizu.tsuru.api.features.workspace.presentation.services;

import com.chizu.tsuru.api.core.services.URIService;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.api.features.workspace.presentation.dto.GetWorkspaceDTO;
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
        var uri = uriService.getClusters(workspace.getWorkspaceId()).toString();
        return new GetWorkspaceDTO(workspace.getName(), uri);
    }

    public List<GetWorkspaceDTO> workspacesToDTO(List<Workspace> workspaces) {
        return workspaces.stream().map(this::workspaceToDTO).collect(Collectors.toList());
    }

    public URI getLocationWorkspace(Integer workspaceId) {
        return uriService.fromParent(workspaceId);
    }
}
