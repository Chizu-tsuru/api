package com.chizu.tsuru.api.workspaces.services;

import com.chizu.tsuru.api.clusters.dto.GetClusterDTO;
import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.clusters.services.AddressService;
import com.chizu.tsuru.api.clusters.services.ClusterService;
import com.chizu.tsuru.api.clusters.services.LocationService;
import com.chizu.tsuru.api.clusters.services.LuceneService;
import com.chizu.tsuru.api.core.errors.NotFoundException;
import com.chizu.tsuru.api.core.services.ResponseOldService;
import com.chizu.tsuru.api.features.workspace.presentation.services.ResponseService;
import com.chizu.tsuru.api.workspaces.dto.CreateLocationDTO;
import com.chizu.tsuru.api.workspaces.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.dto.GetWorkspaceDTO;
import com.chizu.tsuru.api.workspaces.entities.Workspace;
import com.chizu.tsuru.api.workspaces.repositories.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkspaceService {

    public final int LATITUDE = 0;
    public final int LONGITUDE = 1;
    private final WorkspaceRepository workspaceRepository;
    private final ClusterRepository clusterRepository;
    private final ResponseOldService responseService;
    private final LocationService locationService;
    private final ClusterService clusterService;
    private final AddressService addressService;
    private final LuceneService luceneService;

    @Autowired
    public WorkspaceService(
            WorkspaceRepository workspaceRepository,
            ClusterRepository clusterRepository, ResponseOldService responseService,
            LocationService locationService,
            AddressService addressService,
            ClusterService clusterService,
            LuceneService luceneService) {
        this.workspaceRepository = workspaceRepository;
        this.clusterRepository = clusterRepository;
        this.responseService = responseService;
        this.locationService = locationService;
        this.clusterService = clusterService;
        this.addressService = addressService;
        this.luceneService = luceneService;
    }

    @Transactional(readOnly = true)
    public List<GetWorkspaceDTO> getWorkspaces() {
        return workspaceRepository
                .findAll()
                .stream()
                .map(this.responseService::getWorkspaceDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetClusterDTO> getClustersByWorkspace(Integer workspaceId) {
        return clusterRepository.findAllByWorkspaceId(workspaceId)
                .stream()
                .map(this.responseService::getClusterDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Workspace getWorkspace(Integer id) {
        return workspaceRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found"));
    }

    @Transactional
    public Integer createWorkspace(@Validated Workspace w) {
        Workspace created = workspaceRepository.save(w);
        return created.getWorkspaceId();
    }

    public double getGridSquareSize(double latDiff, double longDiff) {
        double magnitude = Math.log10(Math.max(latDiff, longDiff));

        return Math.pow(10, ((int) Math.round(magnitude)) - 1);
    }

    public double coordinatesRound(double coordinates) {
        return (double) Math.round(coordinates * 100000d) / 100000d;
    }

    public double getAbsDiff(double min, double max, int side) {

        switch (side) {
            case LATITUDE:
                return Math.abs(min - max);

            case LONGITUDE:
                if (min > max) {
                    return 360 - (min - max);
                } else {
                    return max - min;
                }

            default:
                return 0;
        }
    }

    public double getSquareSize(CreateWorkspaceDTO workspace) {
        double latDiff = coordinatesRound(getAbsDiff(workspace.getMinLat(), workspace.getMaxLat(), LATITUDE));
        double longDiff = coordinatesRound(getAbsDiff(workspace.getMinLong(), workspace.getMaxLong(), LONGITUDE));

        return getGridSquareSize(latDiff, longDiff);
    }

    public Workspace newWorkspace(CreateWorkspaceDTO workspaceDTO) {
        double squareSize;
        double clusterMinLat;
        double clusterMaxLat;
        double clusterMinLong;
        double clusterMaxLong;
        double i;
        double j;
        boolean minLongIsGreaterThanMaxLong = workspaceDTO.getMinLong() > workspaceDTO.getMaxLong();

        Workspace workspace = Workspace.builder()
                .name(workspaceDTO.getName())
                .clusters(new ArrayList<>())
                .build();

        squareSize = this.getSquareSize(workspaceDTO);

        for (i = workspaceDTO.getMinLat(); i < workspaceDTO.getMaxLat(); i += squareSize) {
            for (j = workspaceDTO.getMinLong(); minLongIsGreaterThanMaxLong ? !(j < workspaceDTO.getMinLong() && j > workspaceDTO.getMaxLong()) : j < workspaceDTO.getMaxLong(); j += squareSize) {
                clusterMinLat = i;
                clusterMaxLat = (i + squareSize < workspaceDTO.getMaxLat() ? i + squareSize : workspaceDTO.getMaxLat());
                clusterMinLong = j;

                if (minLongIsGreaterThanMaxLong) {
                    clusterMaxLong = !(j + squareSize < workspaceDTO.getMinLong() && j + squareSize > workspaceDTO.getMaxLong()) ? j + squareSize : workspaceDTO.getMaxLong();
                } else {
                    clusterMaxLong = (j + squareSize < workspaceDTO.getMaxLong() ? j + squareSize : workspaceDTO.getMaxLong());
                }
                Cluster cluster = Cluster.builder()
                        .latitude(0)
                        .longitude(0)
                        .area("Temp")
                        .address(null)
                        .locations(new ArrayList<>())
                        .workspace(workspace)
                        .build();

                for (CreateLocationDTO locationDTO : workspaceDTO.getLocations()) {
                    if (between(locationDTO.getLatitude(), clusterMinLat, clusterMaxLat)
                            && between(locationDTO.getLongitude(), clusterMinLong, clusterMaxLong)) {
                        if (cluster.getClusterId() == null) {
                            cluster = this.clusterService.createCluster(cluster);
                        }

                        Location location = this.locationService.createLocation(locationDTO, cluster.getClusterId());

                        cluster.getLocations().add(location);

                        cluster.setLatitude(cluster.getLatitude() + locationDTO.getLatitude());
                        cluster.setLongitude(cluster.getLongitude() + locationDTO.getLongitude());
                    }
                }

                if (cluster.getClusterId() != null) {
                    cluster.setLatitude(cluster.getLatitude() / cluster.getLocations().size());
                    cluster.setLongitude(cluster.getLongitude() / cluster.getLocations().size());

                    Address address = this.addressService.createAddress(cluster);
                    cluster.setAddress(address);
                    cluster.setArea(address.getCity() + ", " + address.getCountry());

                    workspace.getClusters().add(cluster);
                    this.addLocationsInLucene(cluster.getLocations());
                }
                if (j > 180) {
                    j = -180;
                }
            }
        }
        return workspace;
    }

    private void addLocationsInLucene(List<Location> locations) {
        try {
            this.luceneService.addLocations(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean between(double value, double min, double max) {
        return value >= min && value < max;
    }
}
