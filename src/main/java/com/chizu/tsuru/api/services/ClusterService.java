package com.chizu.tsuru.api.services;

import com.chizu.tsuru.api.Entities.Cluster;
import com.chizu.tsuru.api.repositories.ClusterRepository;
import com.chizu.tsuru.api.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClusterService {

    private final ClusterRepository clusterRepository;

    @Autowired
    public ClusterService(ClusterRepository clusterRepository) {
        this.clusterRepository = clusterRepository;
    }

    @Transactional(readOnly = true)
    public Cluster getCluster(Integer id) {
        return clusterRepository.getOne(id);
    }
}
