package com.chizu.tsuru.api.clusters.controllers;

import com.chizu.tsuru.api.clusters.dto.GetClusterDTO;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.shared.exceptions.NotFoundException;
import com.chizu.tsuru.api.shared.services.ResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clusters")
public class ClusterController {

    private final ClusterRepository clusterRepository;
    private final ResponseService responseService;

    public ClusterController(ClusterRepository clusterRepository, ResponseService responseService) {
        this.clusterRepository = clusterRepository;
        this.responseService = responseService;
    }

    @GetMapping("/{idCluster}")
    public GetClusterDTO getCluster(@PathVariable("idCluster") Integer idCluster) {
        var cluster = this.clusterRepository.findById(idCluster)
                .orElseThrow(() -> new NotFoundException(idCluster + ": this cluster has not been found"));
        return this.responseService.getClusterDTO(cluster) ;
    }

}
