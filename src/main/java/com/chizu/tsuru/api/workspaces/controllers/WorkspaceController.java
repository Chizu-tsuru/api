package com.chizu.tsuru.api.workspaces.controllers;

import com.chizu.tsuru.api.shared.services.ResponseService;
import com.chizu.tsuru.api.workspaces.dto.CreateLocationDTO;
import com.chizu.tsuru.api.workspaces.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.dto.GetWorkspaceDTO;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import com.chizu.tsuru.api.shared.exceptions.BadRequestException;
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
import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final double ACCURACY = 1000000;
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
        double latDiff;
        double longDiff;
        double squareSize;
        double clusterMinLat;
        double clusterMaxLat;
        double clusterMinLong;
        double clusterMaxLong;
        double averageClusterLat;
        double averageClusterLong;

        if (!workspace.isValid()) {
            throw new BadRequestException("limitation point are invalid");
        }

        System.out.println(workspace);
        Workspace w = Workspace.builder()
                .name(workspace.getName())
                .clusters(new ArrayList<>())
                .build();

        latDiff = coordinatesRound(getDiff(workspace.getMinLat(), workspace.getMaxLat()));
        longDiff = coordinatesRound(getDiff(workspace.getMinLong(), workspace.getMaxLong()));

        squareSize = getGridSquareSize(latDiff, longDiff);

        System.out.println("Square size :" +squareSize);

        for (double i = workspace.getMinLat() ; i < (workspace.getMaxLat()); i+= squareSize){
            for (double j = workspace.getMinLong() ; j < workspace.getMaxLong(); j+= squareSize){
                ArrayList<Location> locations = new ArrayList<>();

                clusterMinLat = i;
                clusterMaxLat = (i + squareSize < workspace.getMaxLat() ? i + squareSize: workspace.getMaxLat());
                clusterMinLong = j;
                clusterMaxLong = (j + squareSize < workspace.getMaxLat() ? j + squareSize: workspace.getMaxLat());

                averageClusterLat = 0;
                averageClusterLong = 0;

                Cluster cluster = Cluster.builder()
                        .latitude(averageClusterLat)
                        .longitude(averageClusterLong)
                        .area("Temp")
                        .locations(locations)
                        .workspace(w)
                        .build();

                for(CreateLocationDTO locationDTO : workspace.getLocations()){
                    if (between(locationDTO.getLatitude(), clusterMinLat, clusterMaxLat)
                        && between(locationDTO.getLongitude(), clusterMinLong, clusterMaxLong)){
                        Location location = Location.builder()
                                .cluster(cluster)
                                .latitude(locationDTO.getLatitude())
                                .longitude(locationDTO.getLongitude())
                                .build();

                        locations.add(location);
                        averageClusterLat += locationDTO.getLatitude();
                        averageClusterLong += locationDTO.getLongitude();
                    }
                }

                averageClusterLat = locations.size() == 0 ? 0 : averageClusterLat /  locations.size();
                averageClusterLong = locations.size() == 0 ? 0 : averageClusterLong /  locations.size();

                cluster.setLatitude(averageClusterLat);
                cluster.setLongitude(averageClusterLong);

                // TODO: Pour chaque cluster, tapper sur l'api de Geocoding pour pouvoir récupérer le nom du clister

                w.getClusters().add(cluster);
            }
        }


        Integer workspaceId = this.workspaceService.createWorkspace(w);
        URI location = this.uriService.fromParent(workspaceId);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{idWorkspace}")
    public GetWorkspaceDTO getWorkspace(@PathVariable("idWorkspace") Integer idWorkspace) {
        return this.responseService
                .getWorkspaceDTO(this.workspaceService.getWorkspace(idWorkspace));
    }



    private boolean between(double value, double min, double max){
        return value >= min && value < max;
    }

    private double getGridSquareSize(double latDiff, double longDiff) {
        double magnitude = Math.log10(Math.max(latDiff, longDiff));

        System.out.println("Magnitude : "+magnitude);

        return Math.pow(10,((int) Math.round(magnitude) )- 1);
    }

    private double greatestCommonDivisor(double num1, double num2){
        BigInteger int1 = BigDecimal.valueOf(num1 * ACCURACY).toBigInteger();
        BigInteger int2 = BigDecimal.valueOf(num2 * ACCURACY).toBigInteger();
        BigInteger gcd = int1.gcd(int2);
        System.out.println("GCD INT : "+ gcd);
        BigDecimal gcdDecimal = new BigDecimal(gcd);
        System.out.println("GCD DEC : "+ gcdDecimal);
        return (gcdDecimal.doubleValue()) / ACCURACY;
    }


    private double coordinatesRound(double coordinates){
        return (double) Math.round(coordinates * 100000d) / 100000d;
    }

    private double getDiff(double min, double max){
        return Math.abs(max - min);
    }
}
