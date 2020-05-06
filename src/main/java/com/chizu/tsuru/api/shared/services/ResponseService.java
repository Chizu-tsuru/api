package com.chizu.tsuru.api.shared.services;

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

}
