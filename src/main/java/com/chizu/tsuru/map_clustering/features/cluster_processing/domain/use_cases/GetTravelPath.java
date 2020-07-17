package com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases;

import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.dto.GetTravelPathDTO;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Cluster;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Location;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.TravelPath;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.repositories.ClusterProcessingRepository;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.services.MapService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GetTravelPath implements UseCase<TravelPath, GetTravelPathDTO> {
    private final ClusterProcessingRepository clusterProcessingRepository;
    private final MapService mapService;

    public GetTravelPath(ClusterProcessingRepository clusterProcessingRepository, MapService mapService) {
        this.clusterProcessingRepository = clusterProcessingRepository;
        this.mapService = mapService;
    }

    @Override
    public TravelPath execute(GetTravelPathDTO travelPathDTO) {
        var cluster = clusterProcessingRepository.getClusterById(travelPathDTO.clusterId);
        var initialPoint = new Location(travelPathDTO.latitude, travelPathDTO.longitude);
        return getClusterOptionalPath(cluster, initialPoint);
    }

    private TravelPath getClusterOptionalPath(Cluster cluster, Location initialPoint) {
        List<Location> locations = new ArrayList<>();
        double distanceTotal = 0;

        while (cluster.getLocations().size() > 0) {
            Optional<Location> minLocation = cluster.getLocations()
                    .stream()
                    .min((Comparator.comparing(location -> mapService.getDistance(initialPoint, location))));

            if (minLocation.isPresent()) {
                Location min = minLocation.get();
                locations.add(min);
                distanceTotal += mapService.getDistance(initialPoint, min);
                initialPoint.setLatitude(min.getLatitude());
                initialPoint.setLongitude(min.getLongitude());
                cluster.getLocations().remove(min);
            }
        }

        return new TravelPath(locations, distanceTotal);
    }
}
