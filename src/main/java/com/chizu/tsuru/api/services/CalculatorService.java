package com.chizu.tsuru.api.services;

import com.chizu.tsuru.api.Entities.Location;

import java.util.ArrayList;

public class CalculatorService {

    public double minAvgDist(ArrayList<Location> LocationList) {
        ArrayList<Double> dist = listDist(LocationList);
        return dist
                .stream()
                .reduce(0.0, Double::sum)
                / dist.size();
    }

    private ArrayList<Double> listDist(ArrayList<Location> p) {
        ArrayList<Double> dist = new ArrayList<>();
        double d, c;
        for (int i = 0; i < p.size(); i++) {
            d = -1;
            for (int j = 0; j < p.size(); j++) {
                if (i != j) {
                    c = convertLocationsToKm(p.get(i), p.get(j));
                    if (c < d || d == -1)
                        d = c;
                }
            }
            dist.add(Math.sqrt(d));
        }
        return dist;
    }

    private double convertLocationsToKm(Location a, Location b) {
        double p = 0.017453292519943295;    // Math.PI / 180
        double res = 0.5 - Math.cos((b.getLatitude() - a.getLatitude()) * p) / 2 +
                Math.cos(a.getLatitude() * p) * Math.cos(b.getLatitude() * p)
                        * (1 - Math.cos((b.getLongitude() - a.getLongitude()) * p)) / 2;

        return 12742 * Math.asin(Math.sqrt(res)); // 2 * R; R = 6371 km
    }
}
