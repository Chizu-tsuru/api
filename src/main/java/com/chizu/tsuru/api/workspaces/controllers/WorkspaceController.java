package com.chizu.tsuru.api.workspaces.controllers;

import com.chizu.tsuru.api.shared.exceptions.BadRequestException;
import com.chizu.tsuru.api.shared.services.ResponseService;
import com.chizu.tsuru.api.shared.services.URIService;
import com.chizu.tsuru.api.workspaces.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.dto.GetWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import com.chizu.tsuru.api.workspaces.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    private final ResponseService responseService;
    private final URIService uriService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService, URIService uriService,
                               ResponseService responseService) {
        this.workspaceService = workspaceService;
        this.responseService = responseService;
        this.uriService = uriService;
    }

    @GetMapping
    public List<GetWorkspaceDTO> getWorkspaces() {
        return workspaceService.getWorkspaces();
    }

    @PostMapping
    public ResponseEntity<Void> CreateWorkspace(@Validated @RequestBody CreateWorkspaceDTO workspace) {


        if (!workspace.isValid()) {
            throw new BadRequestException("limitation point are invalid");
        }

        Workspace w = this.workspaceService.newWorkspace(workspace);


        Integer workspaceId = this.workspaceService.createWorkspace(w);
        URI location = this.uriService.fromParent(workspaceId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{idWorkspace}")
    public GetWorkspaceDTO getWorkspace(@PathVariable("idWorkspace") Integer idWorkspace) {
        return this.responseService
                .getWorkspaceDTO(this.workspaceService.getWorkspace(idWorkspace));
    }


}
