package com.chizu.tsuru.api.workspaces.services;

import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.services.AddressService;
import com.chizu.tsuru.api.clusters.services.ClusterService;
import com.chizu.tsuru.api.clusters.services.LocationService;
import com.chizu.tsuru.api.clusters.services.LuceneService;
import com.chizu.tsuru.api.workspaces.dto.CreateLocationDTO;
import com.chizu.tsuru.api.workspaces.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.shared.exceptions.NotFoundException;
import com.chizu.tsuru.api.shared.services.ResponseService;
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

    private final WorkspaceRepository workspaceRepository;
    private final ResponseService responseService;
    private final LocationService locationService;
    private final ClusterService clusterService;
    private final AddressService addressService;
    private final LuceneService luceneService;

    public final int LATITUDE = 0;
    public final int LONGITUDE = 1;

    @Autowired
    public WorkspaceService(
            WorkspaceRepository workspaceRepository,
            ResponseService responseService,
            LocationService locationService,
            AddressService addressService,
            ClusterService clusterService,
            LuceneService luceneService) {
        this.workspaceRepository = workspaceRepository;
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
    public Workspace getWorkspace(Integer id) {
        return workspaceRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found"));
    }

    @Transactional
    public Integer createWorkspace(@Validated Workspace w) {
        Workspace created = workspaceRepository.save(w);
        return created.getWorkspace_id();
    }

    public double getGridSquareSize(double latDiff, double longDiff) {
        double magnitude = Math.log10(Math.max(latDiff, longDiff));

        return Math.pow(10,((int) Math.round(magnitude) )- 1);
    }

    public double coordinatesRound(double coordinates){
        return (double) Math.round(coordinates * 100000d) / 100000d;
    }

    public double getAbsDiff(double min, double max, int side){

        switch(side){
            case LATITUDE:
                return Math.abs(min - max);

            case LONGITUDE:
                if (min > max) {
                    return 360 - (min  - max);
                } else {
                    return max - min;
                }

            default:
                return 0;
        }
    }

    public double getSquareSize(CreateWorkspaceDTO workspace){
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

        Workspace workspace = Workspace.builder()
                .name(workspaceDTO.getName())
                .clusters(new ArrayList<>())
                .build();

        squareSize = this.getSquareSize(workspaceDTO);

        for (double i = workspaceDTO.getMinLat() ; i < (workspaceDTO.getMaxLat()); i+= squareSize){
            for (double j = workspaceDTO.getMinLong() ; j < workspaceDTO.getMaxLong(); j+= squareSize){

                clusterMinLat = i;
                clusterMaxLat = (i + squareSize < workspaceDTO.getMaxLat() ? i + squareSize: workspaceDTO.getMaxLat());
                clusterMinLong = j;
                clusterMaxLong = (j + squareSize < workspaceDTO.getMaxLat() ? j + squareSize: workspaceDTO.getMaxLat());

                Cluster cluster = Cluster.builder()
                        .latitude(0)
                        .longitude(0)
                        .area("Temp")
                        .address(null)
                        .locations(new ArrayList<>())
                        .workspace(workspace)
                        .build();

                for(CreateLocationDTO locationDTO : workspaceDTO.getLocations()){
                    if (between(locationDTO.getLatitude(), clusterMinLat, clusterMaxLat)
                            && between(locationDTO.getLongitude(), clusterMinLong, clusterMaxLong)){
                        if( cluster.getClusterId() == null) cluster = this.clusterService.createCluster(cluster);

                        Location location = this.locationService.createLocation(locationDTO, cluster.getClusterId());

                        cluster.getLocations().add(location);

                        cluster.setLatitude(cluster.getLatitude() + locationDTO.getLatitude());
                        cluster.setLongitude(cluster.getLongitude() + locationDTO.getLongitude());
                    }
                }

                if(cluster.getLocations().size() != 0){
                    cluster.setLatitude(cluster.getLatitude() / cluster.getLocations().size());
                    cluster.setLongitude(cluster.getLongitude() / cluster.getLocations().size());

                    Address address = this.addressService.createAddress(cluster);
                    cluster.setAddress(address);
                    cluster.setArea(address.getCity() + ", " + address.getCountry());

                    workspace.getClusters().add(cluster);
                    this.addLocationsInLucene(cluster.getLocations());
                }

            }
        }
        return workspace;
    }

    private void addLocationsInLucene(List<Location> locations){
        try {
            this.luceneService.addLocations(locations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean between(double value, double min, double max){
        return value >= min && value < max;
    }
}
