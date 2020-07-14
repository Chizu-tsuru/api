package com.chizu.tsuru.api.workspaces.controllers;

import com.chizu.tsuru.api.clusters.dto.GetClusterDTO;
import com.chizu.tsuru.api.core.services.URIService;
import com.chizu.tsuru.api.features.workspace.presentation.services.ResponseService;
import com.chizu.tsuru.api.workspaces.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import com.chizu.tsuru.api.core.errors.BadRequestException;
import com.chizu.tsuru.api.workspaces.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceOldController {

    private final WorkspaceService workspaceService;

    private final ResponseService responseService;
    private final URIService uriService;

    @Autowired
    public WorkspaceOldController(WorkspaceService workspaceService, URIService uriService,
                                  ResponseService responseService) {
        this.workspaceService = workspaceService;
        this.responseService = responseService;
        this.uriService = uriService;
    }


}
