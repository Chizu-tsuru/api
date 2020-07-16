package com.chizu.tsuru.map_clustering.clusters.services;

import com.chizu.tsuru.map_clustering.clusters.dto.GetLocationDTO;
import com.chizu.tsuru.map_clustering.clusters.dto.GetTravelDTO;
import com.chizu.tsuru.map_clustering.clusters.entities.Cluster;
import com.chizu.tsuru.map_clustering.clusters.entities.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TravelServiceTests {
    private final TravelService travelService;

    @Autowired
    public TravelServiceTests(TravelService travelService) {
        this.travelService = travelService;
    }

    @Test
    void should_return_good_path_and_distance() {

        var cluster = Cluster.builder().area("shinzukyo").clusterId(1).latitude(48.8047708).longitude(2.1863857).build();
        List<Location> locations = new ArrayList<>();
        locations.add(Location.builder().locationId(1).cluster(cluster).latitude(48.8047708).longitude(2.1863857).build());
        locations.add(Location.builder().locationId(2).cluster(cluster).latitude(48.8048684).longitude(2.1181667).build());
        locations.add(Location.builder().locationId(2).cluster(cluster).latitude(48.8001348).longitude(2.1846854).build());

        var expected = GetTravelDTO.builder().distance(5.620408964998223).locations(new ArrayList<>() {
            {
                add(GetLocationDTO.builder().latitude(48.8047708).longitude(2.1863857).build());
                add(GetLocationDTO.builder().latitude(48.8001348).longitude(2.1846854).build());
                add(GetLocationDTO.builder().latitude(48.8048684).longitude(2.1181667).build());
            }
        }).build();

        cluster.setLocations(locations);
        var startPoint = Location.builder()
                .locationId(2).cluster(cluster).latitude(48.805266).longitude(2.188868).build();

        var travel = this.travelService.findPath(cluster, startPoint);
        assertThat(travel).isEqualTo(expected);
    }
}
