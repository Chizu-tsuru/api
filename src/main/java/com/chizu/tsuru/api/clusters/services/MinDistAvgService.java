package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.dto.GetMinMaxAvgDTO;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.core.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MinDistAvgService {

    private final MapService mapService;

    @Autowired
    public MinDistAvgService(MapService mapService) {
        this.mapService = mapService;
    }

    public double anyClosestDist(@NotNull List<Location> locations) {
        List<Double> dist = new ArrayList<>();
        double minDist, currentDist;
        for (int i = 0; i < locations.size(); i++) {
            minDist = -1;
            for (int j = 0; j < locations.size(); j++) {
                if (i != j) {
                    currentDist = this.mapService.getDistance(locations.get(i), locations.get(j));
                    if (currentDist < minDist || minDist == -1)
                        minDist = currentDist;
                }
            }
            dist.add(minDist);
        }
        return dist
                .stream()
                .reduce(0.0, Double::sum)
                / dist.size();
    }

    public GetMinMaxAvgDTO minAvgDist(List<Location> locations) {
        if (locations.size() < 2) {
            return GetMinMaxAvgDTO.builder()
                    .minMaxAvgDistance(0.0)
                    .numberLocations(locations.size())
                    .build();
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
        return GetMinMaxAvgDTO.builder()
                .minMaxAvgDistance(kruskalToDist(graph))
                .numberLocations(locations.size())
                .build();
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
            locals.add(new Local(location.getLongitude(), location.getLatitude()));
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
            return Location.builder().latitude(this.latitude).longitude(this.longitude).build();
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
