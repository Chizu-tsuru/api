package com.chizu.tsuru.api;

import com.chizu.tsuru.api.Entities.Location;
import com.chizu.tsuru.api.services.CalculatorService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ApiApplication.class, args);
        ArrayList<Location> locations = new ArrayList<>();
        CalculatorService cs = new CalculatorService();
        locations.add(Location.builder()
                .latitude(0.0)
                .longitude(1.0)
                .build());
        locations.add(Location.builder()
                .latitude(0.0)
                .longitude(2.0)
                .build());
        locations.add(Location.builder()
                .latitude(0.0)
                .longitude(3.0)
                .build());
        locations.add(Location.builder()
                .latitude(0.0)
                .longitude(4.0)
                .build());
        System.out.println("avg: " + cs.minAvgDist(locations));
    }

}
