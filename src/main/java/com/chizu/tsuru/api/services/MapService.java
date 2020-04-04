package com.chizu.tsuru.api.services;

import org.springframework.stereotype.Service;

@Service
public class MapService {
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public Double getDistance(Double longA, Double latA, Double longB, Double latB) {
        double latitude = Math.toRadians(latB - latA);
        double longitude = Math.toRadians(longB -longA);
        double latBRadian =  Math.toRadians(latB);
        double latARadian =  Math.toRadians(latA);

        double a = haversin(latitude) + Math.cos(latARadian) * Math.cos(latBRadian) * haversin(longitude);
        double b = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * b;
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }
}
