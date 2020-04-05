package com.chizu.tsuru.api.services;

import com.chizu.tsuru.api.Entities.Location;
import org.springframework.stereotype.Service;

@Service
public class MapService {
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public Double getDistance(Location start, Location end) {
        double latitude = Math.toRadians(end.getLatitude() - start.getLatitude());
        double longitude = Math.toRadians(end.getLongitude() - start.getLongitude());
        double latBRadian =  Math.toRadians(end.getLatitude());
        double latARadian =  Math.toRadians(start.getLatitude());

        double a = haversin(latitude) + Math.cos(latARadian) * Math.cos(latBRadian) * haversin(longitude);
        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * b;
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
