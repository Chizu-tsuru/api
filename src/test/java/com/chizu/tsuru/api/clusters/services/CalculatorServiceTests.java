package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Location;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculatorServiceTests {

    private final MinDistAvgService cs;

    @Autowired
    public CalculatorServiceTests(MinDistAvgService minDistAvgService) {
        this.cs = minDistAvgService;
    }

    @Test
    void should_return_a_double() {
        ArrayList<Location> data = new ArrayList<>();
        data.add(Location.builder()
                .latitude(48.837335)
                .longitude(2.570596)
                .build());
        data.add(Location.builder()
                .latitude(48.843701)
                .longitude(2.579262)
                .build());
        double wanted = 0.9748948926795818;
        double result = cs.minAvgDist(data);
        assertThat(result).isEqualTo(wanted);
    }
}
