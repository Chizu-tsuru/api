package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.clusters.repositories.LocationRepository;
import com.chizu.tsuru.api.shared.exceptions.NotFoundException;
import com.chizu.tsuru.api.shared.services.ResponseService;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {
    private final LocationRepository locationRepository;
    private final ResponseService responseService;
    private final ClusterRepository clusterRepository;


    @Autowired
    public LocationService(LocationRepository locationRepository,
                           ResponseService responseService,
                           ClusterRepository clusterRepository) {
        this.locationRepository = locationRepository;
        this.responseService = responseService;
        this.clusterRepository = clusterRepository;
    }

    @Transactional(readOnly = true)
    public Location getLocation(Integer id) {
        return locationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Location not found"));
    }

    // Par rapport un cluster / workspace
    @Transactional(readOnly = true)
    public List<GetLocationDTO> getLocations() {
        return locationRepository
                .findAll()
                .stream()
                .map(this.responseService::getLocationDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetLocationDTO> getLocationsByCluster(Cluster cluster) {
        return  locationRepository.findAllByCluster(cluster)
                .stream()
                .map(this.responseService::getLocationDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetLocationDTO> getLocationsByWorkspace(Workspace workspace) {
        return  locationRepository.findAllByWorkspace(workspace)
                .stream()
                .map(this.responseService::getLocationDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public Integer createLocation(@Validated Location l) {
        clusterRepository.findById(l.getCluster().getClusterId()).orElseThrow(() -> new NotFoundException("Cluster not found"));
        Location created = locationRepository.save(l);
        return created.getLocationId();
    }
}
