package com.chizu.tsuru.api.features.workspace.domain.useCases;

import com.chizu.tsuru.api.core.errors.BadRequestException;
import com.chizu.tsuru.api.core.useCases.UseCase;
import com.chizu.tsuru.api.features.workspace.domain.dto.CreateWorkspaceDTO;
import com.chizu.tsuru.api.features.workspace.domain.entities.Address;
import com.chizu.tsuru.api.features.workspace.domain.entities.Location;
import com.chizu.tsuru.api.features.workspace.domain.entities.Workspace;
import com.chizu.tsuru.api.features.workspace.domain.entities.Cluster;
import com.chizu.tsuru.api.features.workspace.domain.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

enum CoordinateType {
    LATITUDE, LONGITUDE
}

@Component
public class CreateWorkspace implements UseCase<Workspace, CreateWorkspaceDTO> {
    final WorkspaceRepository workspaceRepository;


    public CreateWorkspace(@Qualifier("workspaceMysqlRepository") WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Workspace execute(CreateWorkspaceDTO workspaceDTO) {
        if (!workspaceDTO.isValid()) throw new BadRequestException("limitation point are invalid");
        var squareSize = getSquareSize(workspaceDTO.getMinLat(), workspaceDTO.getMaxLat(),
                workspaceDTO.getMinLong(), workspaceDTO.getMaxLong());
        return workspaceRepository.createWorkspace(buildWorkspace(workspaceDTO, squareSize));
    }

    public Workspace buildWorkspace(CreateWorkspaceDTO workspaceDTO, double squareSize) {
        double clusterMinLat;
        double clusterMaxLat;
        double clusterMinLong;
        double clusterMaxLong;

        var workspace = new Workspace(workspaceDTO.getName(), new ArrayList<>());

        for (double i = workspaceDTO.getMinLat(); i < (workspaceDTO.getMaxLat()); i += squareSize) {
            for (double j = workspaceDTO.getMinLong(); j < workspaceDTO.getMaxLong(); j += squareSize) {

                clusterMinLat = i;
                clusterMaxLat = (Math.min(i + squareSize, workspaceDTO.getMaxLat()));
                clusterMinLong = j;
                clusterMaxLong = (Math.min(j + squareSize, workspaceDTO.getMaxLat()));

                Cluster cluster = null;

                for (var locationDTO : workspaceDTO.getLocations()) {
                    if (between(locationDTO.getLatitude(), clusterMinLat, clusterMaxLat)
                            && between(locationDTO.getLongitude(), clusterMinLong, clusterMaxLong)) {
                        if (cluster == null) cluster = new Cluster();

                        Location location = new Location(locationDTO.getLatitude(), locationDTO.getLongitude());

                        cluster.getLocations().add(location);

                        cluster.setLatitude(cluster.getLatitude() + locationDTO.getLatitude());
                        cluster.setLongitude(cluster.getLongitude() + locationDTO.getLongitude());
                    }
                }

                if(cluster != null) {
                    cluster.setLatitude(cluster.getLatitude() / cluster.getLocations().size());
                    cluster.setLongitude(cluster.getLongitude() / cluster.getLocations().size());

                    Address address = this.addressService.createAddress(cluster.getClusterId());

                    cluster.setArea(address.getCity() + ", " + address.getCountry());

                    workspace.getClusters().add(cluster);
                }

            }
        }
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
