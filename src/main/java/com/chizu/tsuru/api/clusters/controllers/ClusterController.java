package com.chizu.tsuru.api.clusters.controllers;

import com.chizu.tsuru.api.clusters.dto.GetClusterDTO;
import com.chizu.tsuru.api.clusters.dto.GetMinMaxAvgDTO;
import com.chizu.tsuru.api.clusters.dto.GetTravelDTO;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.clusters.services.ClusterService;
import com.chizu.tsuru.api.clusters.services.LuceneService;
import com.chizu.tsuru.api.clusters.services.MinDistAvgService;
import com.chizu.tsuru.api.clusters.services.TravelService;
import com.chizu.tsuru.api.shared.services.ResponseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clusters")
public class ClusterController {

    private final ResponseService responseService;
    private final ClusterService clusterService;
    private final TravelService travelService;
    private final MinDistAvgService minDistAvgService;
    private final LuceneService luceneService;

    public ClusterController(ResponseService responseService, ClusterService clusterService,
                             TravelService travelService, MinDistAvgService minDistAvgService,
                             LuceneService luceneService) {
        this.responseService = responseService;
        this.clusterService = clusterService;
        this.travelService = travelService;
        this.minDistAvgService = minDistAvgService;
        this.luceneService = luceneService;
    }

    @GetMapping("/{idCluster}")
    public GetClusterDTO getCluster(@PathVariable("idCluster") Integer idCluster) {
        return this.responseService.getClusterDTO(this.clusterService.getCluster(idCluster)) ;
    }

    @GetMapping("/{idCluster}/travel")
    public GetTravelDTO getPath(@PathVariable("idCluster") Integer idCluster,
                                @RequestParam() Double longitude,
                                @RequestParam() Double latitude) {

        return this.travelService.getTravel(idCluster, longitude, latitude);
    }

    @GetMapping("/{idCluster}/minMaxAvg")
    public GetMinMaxAvgDTO getMinMaxAvg(@PathVariable("idCluster") Integer idCluster) {
        var cluster = this.clusterService.getCluster(idCluster);
        return this.minDistAvgService.minAvgDist(cluster.getLocations());
    }

    @GetMapping("/lucene")
    public void getLucene() {
        luceneService.searchWorkspace();
    }

}