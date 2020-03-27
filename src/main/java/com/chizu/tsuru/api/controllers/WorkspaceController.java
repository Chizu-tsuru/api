package com.chizu.tsuru.api.controllers;

import com.chizu.tsuru.api.Entities.Workspace;
import com.chizu.tsuru.api.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public List<Workspace> getWorkspaces() {
        return workspaceService.getWorkspaces();
    }
}
