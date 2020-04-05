package com.chizu.tsuru.api.services;

import com.chizu.tsuru.api.Entities.Location;

import java.util.ArrayList;

public class CalculatorService {

    public double minAvgDist(ArrayList<Location> p) {
        ArrayList<Double> dist = listDist(p);
        return dist
                .stream()
                .reduce(0.0, Double::sum)
                / dist.size();
    }

    private double calcDist(Location a, Location b) {
        return Math.pow((a.getLatitude() - b.getLatitude()), 2) + Math.pow((a.getLongitude() - b.getLongitude()), 2);
    }

    private ArrayList<Double> listDist(ArrayList<Location> p) {
        ArrayList<Double> dist = new ArrayList<>();
        double d, c;
        for (int i = 0; i < p.size(); i++) {
            d = -1;
            for (int j = 0; j < p.size(); j++) {
                if (i != j) {
                    c = calcDist(p.get(i), p.get(j));
                    if (c < d || d == -1)
                        d = c;
                }
            }
            dist.add(Math.sqrt(d));
        }
        return dist;
    }

    public double LongLatToKm(double lat1, double lon1, double lat2, double lon2) {
        double p = 0.017453292519943295;    // Math.PI / 180
        double a = 0.5 - Math.cos((lat2 - lat1) * p) / 2 +
                Math.cos(lat1 * p) * Math.cos(lat2 * p) *
                        (1 - Math.cos((lon2 - lon1) * p)) / 2;

        return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
    }
}
