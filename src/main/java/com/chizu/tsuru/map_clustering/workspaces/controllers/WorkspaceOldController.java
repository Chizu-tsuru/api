package com.chizu.tsuru.map_clustering.workspaces.controllers;

import com.chizu.tsuru.map_clustering.core.services.URIService;
import com.chizu.tsuru.map_clustering.features.workspace.presentation.services.ResponseService;
import com.chizu.tsuru.map_clustering.workspaces.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
