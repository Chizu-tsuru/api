package com.chizu.tsuru.api.features.workspace.presentation.controllers;

import com.chizu.tsuru.api.core.useCases.NoParams;
import com.chizu.tsuru.api.features.workspace.domain.useCases.GetWorkspaces;
import com.chizu.tsuru.api.features.workspace.presentation.ResponseService;
import com.chizu.tsuru.api.features.workspace.presentation.dto.GetWorkspaceDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {
    final ResponseService responseService;
    final GetWorkspaces getWorkspaces;

    public WorkspaceController(ResponseService responseService, GetWorkspaces getWorkspaces) {
        this.responseService = responseService;
        this.getWorkspaces = getWorkspaces;
    }

    @GetMapping
    public List<GetWorkspaceDTO> getGetWorkspaces() {
        var workspaces = getWorkspaces.execute(new NoParams());
        return responseService.workspacesToDTO(workspaces);
    }
}
