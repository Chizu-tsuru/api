package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import({
        MinDistAvgService.class,
        Location.class
})

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CalculatorServiceTests {

    @Autowired
    private MinDistAvgService cs;

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
