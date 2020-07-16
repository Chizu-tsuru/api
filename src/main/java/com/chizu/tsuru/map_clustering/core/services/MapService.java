package com.chizu.tsuru.map_clustering.core.services;

import com.chizu.tsuru.map_clustering.clusters.entities.Location;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class MapService {

    public Double getDistance(@NotNull Location a, @NotNull Location b) {
        double p = 0.017453292519943295;    // Math.PI / 180
        double res = 0.5 - Math.cos((b.getLatitude() - a.getLatitude()) * p) / 2 +
                Math.cos(a.getLatitude() * p) * Math.cos(b.getLatitude() * p)
                        * (1 - Math.cos((b.getLongitude() - a.getLongitude()) * p)) / 2;
        return 12742 * Math.asin(Math.sqrt(res)); // 2 * R; R = 6371 km
    }
}
