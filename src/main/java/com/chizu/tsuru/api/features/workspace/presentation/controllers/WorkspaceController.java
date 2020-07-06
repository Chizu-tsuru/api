package com.chizu.tsuru.api.features.workspace.presentation.controllers;

import com.chizu.tsuru.api.core.useCases.NoParams;
import com.chizu.tsuru.api.features.workspace.domain.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.features.workspace.domain.useCases.CreateWorkspace;
import com.chizu.tsuru.api.features.workspace.domain.useCases.GetWorkspaceById;
import com.chizu.tsuru.api.features.workspace.domain.useCases.GetWorkspaces;
import com.chizu.tsuru.api.features.workspace.presentation.services.ResponseService;
import com.chizu.tsuru.api.features.workspace.presentation.dto.GetWorkspaceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {
    final ResponseService responseService;
    final GetWorkspaces getWorkspaces;
    final GetWorkspaceById getWorkspaceById;
    final CreateWorkspace createWorkspace;

    public WorkspaceController(ResponseService responseService, GetWorkspaces getWorkspaces,
                               GetWorkspaceById getWorkspaceById, CreateWorkspace createWorkspace) {
        this.responseService = responseService;
        this.getWorkspaces = getWorkspaces;
        this.getWorkspaceById = getWorkspaceById;
        this.createWorkspace = createWorkspace;
    }

    @GetMapping
    public List<GetWorkspaceDTO> getGetWorkspaces() {
        var workspaces = getWorkspaces.execute(new NoParams());
        return responseService.workspacesToDTO(workspaces);
    }

    @GetMapping("/{workspaceId}")
    public GetWorkspaceDTO getWorkspace(@PathVariable Integer workspaceId) {
        var workspace = getWorkspaceById.execute(workspaceId);
        return responseService.workspaceToDTO(workspace);
    }

    @PostMapping
    public ResponseEntity<Void> CreateWorkspace(@Validated @RequestBody CreateWorkspaceDTO workspaceDTO) {
        var workspace = createWorkspace.execute(workspaceDTO);
        URI location = responseService.getLocationWorkspace(workspace.getWorkspaceId());
        return ResponseEntity.created(location).build();
    }
}
