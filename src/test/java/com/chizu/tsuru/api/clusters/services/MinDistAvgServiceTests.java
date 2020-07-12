package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MinDistAvgServiceTests {

    private final MinDistAvgService cs;

    @Autowired
    public MinDistAvgServiceTests(MinDistAvgService minDistAvgService) {
        this.cs = minDistAvgService;
    }

    @Test
    void should_return_a_double() {
        ArrayList<Location> data = new ArrayList<>();
        data.add(Location.builder().latitude(48.837335).longitude(2.570596).build());
        data.add(Location.builder().latitude(48.843701).longitude(2.579262).build());
        double wanted = 0.9504200517727334;
        double result = cs.anyClosestDist(data);
        assertThat(result).isEqualTo(wanted);
    }

    @Test
    void should_calculate_right() {
        ArrayList<Location> p = new ArrayList<>();
        p.add(Location.builder().latitude(48.837335).longitude(2.570596).build());
        p.add(Location.builder().latitude(48.843701).longitude(2.579262).build());
        double result = cs.minAvgDist(p).getMinMaxAvgDistance();
        double wanted = 0.9504200517727334;
        assertThat(result).isEqualTo(wanted);
    }

    @Test
    void should_do_a_valid_kruskal() {
        ArrayList<Location> p = new ArrayList<>();
        p.add(Location.builder().latitude(12.0).longitude(13.0).build());
        p.add(Location.builder().latitude(5.0).longitude(7.0).build());
        p.add(Location.builder().latitude(0.0).longitude(3.0).build());
        p.add(Location.builder().latitude(2.0).longitude(7.0).build());
        p.add(Location.builder().latitude(8.0).longitude(13.0).build());
        p.add(Location.builder().latitude(1.0).longitude(4.0).build());
        p.add(Location.builder().latitude(2.0).longitude(13.0).build());
        p.add(Location.builder().latitude(3.0).longitude(1.0).build());
        p.add(Location.builder().latitude(7.0).longitude(3.0).build());
        double result = cs.minAvgDist(p).getMinMaxAvgDistance();
        double wanted = 439.6255827831875;
        assertThat(result).isEqualTo(wanted);
    }
}
