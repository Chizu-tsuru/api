package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.shared.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Service
public class MinDistAvgService {

    private final MapService mapService;

    @Autowired
    public MinDistAvgService(MapService mapService){
        this.mapService = mapService;
    }

    public double minAvgDist(ArrayList<Location> LocationList) {
        ArrayList<Double> dist = anyClosestDist(LocationList);
        return dist
                .stream()
                .reduce(0.0, Double::sum)
                / dist.size();
    }

    private ArrayList<Double> anyClosestDist(@NotNull ArrayList<Location> p) {
        ArrayList<Double> dist = new ArrayList<>();
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
            dist.add(Math.sqrt(d));
        }
        return dist;
    }
}
