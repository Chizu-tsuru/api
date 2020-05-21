package com.chizu.tsuru.api.workspaces.controllers;

import com.chizu.tsuru.api.shared.services.ResponseService;
import com.chizu.tsuru.api.workspaces.dto.CreateLocationDTO;
import com.chizu.tsuru.api.workspaces.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.dto.GetWorkspaceDTO;
import com.chizu.tsuru.api.clusters.dto.TravelDTO;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import com.chizu.tsuru.api.shared.exceptions.BadRequestException;
import com.chizu.tsuru.api.clusters.services.ClusterService;
import com.chizu.tsuru.api.shared.services.MapService;
import com.chizu.tsuru.api.shared.services.URIService;
import com.chizu.tsuru.api.workspaces.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    private final MapService mapService;
    private final ClusterService clusterService;
    private final ResponseService responseService;
    private final URIService uriService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService,
                               MapService mapService,
                               URIService uriService,
                               ResponseService responseService,
                               ClusterService clusterService) {
        this.workspaceService = workspaceService;
        this.mapService = mapService;
        this.clusterService = clusterService;
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

    @GetMapping("/{idWorkspace}/clusters/{idCluster}/travel")
    public TravelDTO getPath(@PathVariable("idWorkspace") Integer idWorkspace,
                          @PathVariable("idCluster") Integer idCluster,
                          @RequestParam() Double longitude,
                          @RequestParam() Double latitude) {

        Cluster cluster = clusterService.getCluster(idCluster);
        Location reference = Location.builder().latitude(latitude).longitude(longitude).build();

        List<Location> locations = new ArrayList<>();
        double distanceTotal = 0;

        while (cluster.getLocations().size() > 0) {
            Optional<Location> minLocation = cluster.getLocations()
                    .stream()
                    .min((Comparator.comparing(location -> mapService.getDistance(reference, location))));

            if (minLocation.isPresent()) {
                Location min = minLocation.get();
                locations.add(min);
                distanceTotal += mapService.getDistance(reference, min);
                reference.setLatitude(min.getLatitude());
                reference.setLongitude(min.getLongitude());
                cluster.getLocations().remove(min);
            }
        }

        return TravelDTO.builder().distance(distanceTotal)
                .locations(locations.stream().map(Location::toResponse).collect(Collectors.toList()))
                .build();
    }

}
