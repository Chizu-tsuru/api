package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.core.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
public class ClusterService {

    private final ClusterRepository clusterRepository;

    @Autowired
    public ClusterService(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Transactional(readOnly = true)
    public Cluster getCluster(Integer clusterId) {
        return clusterRepository.findById(clusterId)
                .orElseThrow(() -> new NotFoundException(clusterId + ": this cluster has not been found"));
    }

    @Transactional
    public Cluster createCluster(@Validated Cluster c) {
        return clusterRepository.save(c);
    }
}
