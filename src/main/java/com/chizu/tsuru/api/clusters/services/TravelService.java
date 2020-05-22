package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.dto.GetTravelDTO;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.shared.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelService {

    private final ClusterService clusterService;
    private final MapService mapService;

    @Autowired
    public TravelService(ClusterService clusterService, MapService mapService) {
        this.clusterService = clusterService;
        this.mapService = mapService;
    }

    public GetTravelDTO getTravel(Integer clusterId, Double longitude, Double latitude) {
        Cluster cluster = this.clusterService.getCluster(clusterId);
        Location reference = Location.builder().latitude(latitude).longitude(longitude).build();

        return findPath(cluster, reference);
    }

    public  GetTravelDTO findPath(Cluster cluster, Location reference) {
        List<Location> locations = new ArrayList<>();
        double distanceTotal = 0;

        while (cluster.getLocations().size() > 0) {
            Optional<Location> minLocation = cluster.getLocations()
                    .stream()
                    .min((Comparator.comparing(location -> mapService.getDistance(reference, location))));

            if (minLocation.isPresent()) {
                Location min = minLocation.get();
                locations.add(min);
                distanceTotal += mapService.getDistance(reference, min);
                reference.setLatitude(min.getLatitude());
                reference.setLongitude(min.getLongitude());
                cluster.getLocations().remove(min);
            }
        }

        return toResponse(locations, distanceTotal);
    }

    public GetTravelDTO toResponse(List<Location> locations, Double distance) {
        return GetTravelDTO.builder().distance(distance)
                .locations(locations.stream().map(Location::toResponse).collect(Collectors.toList()))
                .build();
    }

}
