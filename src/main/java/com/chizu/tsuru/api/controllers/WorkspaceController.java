package com.chizu.tsuru.api.controllers;

import com.chizu.tsuru.api.DTO.CreateWorkspaceDTO;
import com.chizu.tsuru.api.DTO.GetWorkspaceDTO;
import com.chizu.tsuru.api.Entities.Cluster;
import com.chizu.tsuru.api.Entities.Location;
import com.chizu.tsuru.api.Entities.Workspace;
import com.chizu.tsuru.api.exceptions.BadRequestException;
import com.chizu.tsuru.api.services.URIService;
import com.chizu.tsuru.api.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
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
    public List<GetWorkspaceDTO> getWorkspaces() {
        return workspaceService.getWorkspaces();
    }

    @PostMapping
    public ResponseEntity<Void> CreateWorkspace(@Validated @RequestBody CreateWorkspaceDTO workspace) {

        if (!workspace.isValid()) {
            throw new BadRequestException("limitation point are invalid");
        }

        Workspace w = Workspace.builder()
                .name(workspace.getName())
                .clusters(new ArrayList<>())
                .build();

        Cluster cluster = Cluster.builder()
                .latitude(2.18)
                .longitude(2.18)
                .area("Paris, France")
                .locations(new ArrayList<>())
                .workspace(w)
                .build();

        w.getClusters().add(cluster);

        workspace.getLocations().forEach(createLocationDTO -> {
            Location location = Location.builder()
                    .cluster(cluster)
                    .latitude(createLocationDTO.getLatitude())
                    .longitude(createLocationDTO.getLongitude())
                    .build();

            cluster.getLocations().add(location);
        });

        Integer workspaceId = this.workspaceService.createWorkspace(w);
        URI location = URIService.fromParent(workspaceId);
        return ResponseEntity.created(location).build();
    }
}
