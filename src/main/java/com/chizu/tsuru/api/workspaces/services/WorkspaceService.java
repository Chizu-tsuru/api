package com.chizu.tsuru.api.workspaces.services;

import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.entities.Tag;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.clusters.services.LocationService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final ClusterRepository clusterRepository;
    private final ResponseService responseService;
    private final GeocodingService geocodingService;
    private final LocationService locationService;

    public final int LATITUDE = 0;
    public final int LONGITUDE = 1;

    @Autowired
    public WorkspaceService(
            WorkspaceRepository workspaceRepository,
            GeocodingService geocodingService,
            ResponseService responseService,
            LocationService locationService,
            ClusterRepository clusterRepository
    ) {
        this.workspaceRepository = workspaceRepository;
        this.responseService = responseService;
        this.geocodingService = geocodingService;
        this.locationService = locationService;
        this.clusterRepository = clusterRepository;
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

    public Workspace newWorkspace(CreateWorkspaceDTO workspace) {
        double squareSize;
        double clusterMinLat;
        double clusterMaxLat;
        double clusterMinLong;
        double clusterMaxLong;
        double averageClusterLat = 0;
        double averageClusterLong = 0;

        Workspace w = Workspace.builder()
                .name(workspace.getName())
                .clusters(new ArrayList<>())
                .build();

        squareSize = this.getSquareSize(workspace);

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
//                        Location location = Location.builder()
//                                .cluster(cluster)
//                                .latitude(locationDTO.getLatitude())
//                                .longitude(locationDTO.getLongitude())
//                                .tags(new ArrayList<Tag>())
//                                .build();
//
//                        locations.add(location);
//
//                        if(locationDTO.getTags() != null){
//                            for(String tag_name : locationDTO.getTags()){
//                                Tag tag = Tag.builder()
//                                        .name(tag_name)
//                                        .build();
//                                location.getTags().add(tag);
//
//                            }
//                        }

                        averageClusterLat += locationDTO.getLatitude();
                        averageClusterLong += locationDTO.getLongitude();
                    }
                }

                if(locations.size() != 0){
                    averageClusterLat = averageClusterLat / locations.size();
                    averageClusterLong = averageClusterLong / locations.size();

                    cluster.setLatitude(averageClusterLat);
                    cluster.setLongitude(averageClusterLong);

                    String result = this.geocodingService.getDataFromCoordinate(averageClusterLat, averageClusterLong);

                    Address address =  this.geocodingService.convertResponseStringToAddressObject(result,cluster);

                    cluster.setArea(address.getArea());

                    cluster = this.clusterRepository.save(cluster);

                    for (Location location : cluster.getLocations()){
                        location.setCluster(cluster);
                        this.locationService.createLocation(location);
                    }

                    w.getClusters().add(cluster);
                }

            }
        }

        return w;
    }

    private boolean between(double value, double min, double max){
        return value >= min && value < max;
    }
}
