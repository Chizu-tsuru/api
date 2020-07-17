package com.chizu.tsuru.map_clustering.features.cluster_processing.domain.use_cases;


import com.chizu.tsuru.map_clustering.core.useCases.UseCase;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.Location;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.entities.MinimumDistanceAverage;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.repository.ClusterProcessingRepository;
import com.chizu.tsuru.map_clustering.features.cluster_processing.domain.services.MapService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GetMinimumDistanceAverage implements UseCase<MinimumDistanceAverage, Integer> {
    private final ClusterProcessingRepository clusterProcessingRepository;
    private final MapService mapService;

    public GetMinimumDistanceAverage(ClusterProcessingRepository clusterProcessingRepository, MapService mapService) {
        this.clusterProcessingRepository = clusterProcessingRepository;
        this.mapService = mapService;
    }

    @Override
    public MinimumDistanceAverage execute(Integer integer) {
        var cluster = clusterProcessingRepository.getClusterById(integer);
        return calculateMinimumDistanceAverage(cluster.getLocations());
    }

    public MinimumDistanceAverage calculateMinimumDistanceAverage(List<Location> locations) {
        if (locations.size() < 2) {
            return new MinimumDistanceAverage(locations.size(), 0.0);
        }
        List<Local> locals = locationToLocal(locations);

        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < locals.size(); i++) {
            for (int j = i + 1; j < locals.size(); j++) {
                edges.add(new Edge(locals.get(i), locals.get(j)));
            }
        }
        edges.sort(Comparator.comparingDouble(Edge::getWeight)); // Sort list by weight

        // Kruskal algorithm
        List<Edge> graph = new ArrayList<>();
        int i = 0;
        while (graph.size() < locals.size() - 1) {
            Edge edge = edges.get(i++);
            int id1 = edge.l1.clusterId;
            int id2 = edge.l2.clusterId;
            if (id1 != id2) {
                graph.add(edge);
                for (Local local : locals)
                    if (local.clusterId == id2) {
                        local.clusterId = id1;
                    }
            }
        }

        return new MinimumDistanceAverage(locations.size(), kruskalToDist(graph));
    }

    private double kruskalToDist(List<Edge> edges) {
        List<Double> res = new ArrayList<>();
        for (Edge edge : edges) {
            res.add(this.mapService.getDistance(edge.l1.toLocation(), edge.l2.toLocation()));
        }
        return res
                .stream()
                .reduce(0.0, Double::sum)
                / res.size();
    }

    private List<Local> locationToLocal(List<Location> locations) {
        List<Local> locals = new ArrayList<>();
        for (Location location : locations)
            locals.add(new GetMinimumDistanceAverage.Local(location.getLongitude(), location.getLatitude()));
        return locals;
    }

    static class Local {
        static int nex_id = 0;
        private final Double latitude;
        private final Double longitude;
        int clusterId = nex_id++;

        Local(double Longitude, double Latitude) {
            this.longitude = Longitude;
            this.latitude = Latitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        Location toLocation() {
            return new Location(this.latitude, this.longitude);
        }
    }

    static class Edge {

        private final Local l1;

        private final Local l2;

        private final double weight;

        Edge(Local l1, Local l2) {
            this.l1 = l1;
            this.l2 = l2;
            this.weight = Math.hypot(Math.abs(l1.getLongitude() - l2.getLongitude()), Math.abs(l1.getLatitude() - l2.getLatitude()));
        }

        double getWeight() {
            return weight;
        }
    }
}
