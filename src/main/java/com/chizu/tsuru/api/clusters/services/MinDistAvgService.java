package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.dto.GetMinMaxAvgDTO;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.shared.services.MapService;
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

    public double anyClosestDist(@NotNull List<Location> p) {
        List<Double> dist = new ArrayList<>();
        double d, c;
        for (int i = 0; i < p.size(); i++) {
            d = -1;
            for (int j = 0; j < p.size(); j++) {
                if (i != j) {
                    c = this.mapService.getDistance(p.get(i), p.get(j));
                    if (c < d || d == -1)
                        d = c;
                }
            }
            dist.add(d);
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
        List<Local> p = locationToLocal(locations);

        List<Edge> allEdges = new ArrayList<>();
        for (int i = 0; i < p.size(); i++) {
            for (int j = i + 1; j < p.size(); j++) {
                allEdges.add(new Edge(p.get(i), p.get(j)));
            }
        }
        allEdges.sort(Comparator.comparingDouble(Edge::getWeight)); // Sort list by weight

        // Kruskal algorithm
        List<Edge> graph = new ArrayList<>();
        int i = 0;
        while (graph.size() < p.size() - 1) {
            Edge edge = allEdges.get(i++);
            int id1 = edge.u.clusterId;
            int id2 = edge.v.clusterId;
            if (id1 != id2) {
                graph.add(edge);
                for (Local l : p)
                    if (l.clusterId == id2) {
                        l.clusterId = id1;
                    }
            }
        }
        return GetMinMaxAvgDTO.builder()
                .minMaxAvgDistance(kruskalToDist(graph))
                .numberLocations(locations.size())
                .build();
    }

    private double kruskalToDist(List<Edge> p) {
        List<Double> res = new ArrayList<>();
        for (Edge e : p) {
            res.add(this.mapService.getDistance(e.u.toLocation(), e.v.toLocation()));
        }
        return res
                .stream()
                .reduce(0.0, Double::sum)
                / res.size();
    }

    private List<Local> locationToLocal(List<Location> l) {
        List<Local> p = new ArrayList<>();
        for (Location i : l)
            p.add(new Local(i.getLongitude(), i.getLatitude()));
        return p;
    }

    static class Local {
        static int nex_id = 0;
        int clusterId = nex_id++;
        private Double latitude;
        private Double longitude;

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

        private final Local u;

        private final Local v;

        private final double weight;

        Edge(Local l1, Local l2) {
            this.u = l1;
            this.v = l2;
            this.weight = Math.hypot(Math.abs(l1.getLongitude() - l2.getLongitude()), Math.abs(l1.getLatitude() - l2.getLatitude()));
        }

        double getWeight() {
            return weight;
        }
    }

}
