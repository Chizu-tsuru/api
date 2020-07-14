package com.chizu.tsuru.api.features.workspace.domain.useCases;

import com.chizu.tsuru.api.core.errors.BadRequestException;
import com.chizu.tsuru.api.core.useCases.UseCase;
import com.chizu.tsuru.api.features.workspace.domain.dto.CreateLocationDTO;
import com.chizu.tsuru.api.features.workspace.domain.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.features.workspace.domain.entities.*;
import com.chizu.tsuru.api.features.workspace.domain.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum CoordinateType {
    LATITUDE, LONGITUDE
}

@Component
public class CreateWorkspace implements UseCase<Workspace, CreateWorkspaceDTO> {
    final WorkspaceRepository workspaceRepository;


    public CreateWorkspace(@Qualifier("workspaceDataSourceRepository") WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Workspace execute(CreateWorkspaceDTO workspaceDTO) {
        var squareSize = getSquareSize(workspaceDTO.getMinLat(), workspaceDTO.getMaxLat(),
                workspaceDTO.getMinLong(), workspaceDTO.getMaxLong());
        return workspaceRepository.createWorkspace(buildWorkspace(workspaceDTO, squareSize));
    }

    public Workspace buildWorkspace(CreateWorkspaceDTO workspaceDTO, Double squareSize) {
        double clusterMinLat;
        double clusterMaxLat;
        double clusterMinLong;
        double clusterMaxLong;
        boolean minLongIsGreaterThanMaxLong = workspaceDTO.getMinLong() > workspaceDTO.getMaxLong();

        var workspace = new Workspace(workspaceDTO.getName(), new ArrayList<>());

        for (double i = workspaceDTO.getMinLat(); i < workspaceDTO.getMaxLat(); i += squareSize) {
            for (double j = workspaceDTO.getMinLong(); minLongIsGreaterThanMaxLong ? !(j < workspaceDTO.getMinLong() && j > workspaceDTO.getMaxLong()) : j < workspaceDTO.getMaxLong(); j += squareSize) {
                clusterMinLat = i;
                clusterMaxLat = (Math.min(i + squareSize, workspaceDTO.getMaxLat()));
                clusterMinLong = j;

                if (minLongIsGreaterThanMaxLong) {
                    clusterMaxLong = !(j + squareSize < workspaceDTO.getMinLong() && j + squareSize > workspaceDTO.getMaxLong()) ? j + squareSize : workspaceDTO.getMaxLong();
                } else {
                    clusterMaxLong = (Math.min(j + squareSize, workspaceDTO.getMaxLong()));
                }
                Cluster cluster = clusterWithLocation(workspaceDTO.getLocations(), clusterMaxLat, clusterMinLat, clusterMinLong, clusterMaxLong);

                if(cluster != null) {
                    cluster.setLatitude(cluster.getLatitude() / cluster.getLocations().size());
                    cluster.setLongitude(cluster.getLongitude() / cluster.getLocations().size());

                    Address address = this.workspaceRepository
                            .getAddressByCoordinates(cluster.getLongitude(), cluster.getLatitude());

                    cluster.setAddress(address);
                    cluster.setArea(address.getCity() + ", " + address.getCountry());

                    workspace.getClusters().add(cluster);
                    //this.addLocationsInLucene(cluster.getLocations());
                }
                if (j > 180) {
                    j = -180;
                }
            }
        }
        return workspace;
    }

    private Cluster clusterWithLocation(List<CreateLocationDTO> locations, Double clusterMaxLat, Double clusterMinLat,
                                        Double clusterMinLong, Double clusterMaxLong) {
        Cluster cluster = null;
        for (var locationDTO : locations) {
            if (between(locationDTO.getLatitude(), clusterMinLat, clusterMaxLat)
                    && between(locationDTO.getLongitude(), clusterMinLong, clusterMaxLong)) {
                if (cluster == null) cluster = new Cluster();

                Location location = new Location(locationDTO.getLatitude(), locationDTO.getLongitude());
                location.setTags(locationDTO.getTags().stream().map(Tag::new).collect(Collectors.toList()));

                cluster.getLocations().add(location);

                cluster.setLatitude(cluster.getLatitude() + locationDTO.getLatitude());
                cluster.setLongitude(cluster.getLongitude() + locationDTO.getLongitude());
            }
        }
        return cluster;
    }

    public double getSquareSize(double minLat, double maxLat, double minLong, double maxLong) {
        double latDiff = getCoordinateDifference(minLat, maxLat, CoordinateType.LATITUDE);
        double longDiff = getCoordinateDifference(minLong, maxLong, CoordinateType.LATITUDE);

        return getGridSquareSize(latDiff, longDiff);
    }

    public double getCoordinateDifference(double min, double max, CoordinateType side) {
        var difference = 0.0;
        switch (side) {
            case LATITUDE:
                difference = Math.abs(min - max);
                break;
            case LONGITUDE:
                if (min > max) {
                    difference = 360 - (min - max);
                } else {
                    difference = max - min;
                }
                break;
        }
        return (double) Math.round(difference * 100000d) / 100000d;
    }

    public double getGridSquareSize(double latDiff, double longDiff) {
        double magnitude = Math.log10(Math.max(latDiff, longDiff));

        return Math.pow(10, ((int) Math.round(magnitude)) - 1);
    }

    private boolean between(double value, double min, double max) {
        return value >= min && value < max;
    }
}
