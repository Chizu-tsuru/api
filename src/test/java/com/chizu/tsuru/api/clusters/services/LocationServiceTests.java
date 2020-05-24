package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.entities.Location;
import com.chizu.tsuru.api.clusters.entities.Tag;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.clusters.repositories.LocationRepository;
import com.chizu.tsuru.api.clusters.repositories.TagRepository;
import com.chizu.tsuru.api.shared.services.ResponseService;
import com.chizu.tsuru.api.workspaces.dto.CreateLocationDTO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class LocationServiceTests {

    private static LocationRepository locationRepository;
    private static ResponseService responseService;
    private static ClusterRepository clusterRepository;
    private static TagRepository tagRepository;

    private static LocationService locationService;

    private static Cluster cluster;
    private static List<Tag> tagList;
    private static Location location;


    @BeforeClass
    public static void setup(){
        locationRepository = mock(LocationRepository.class);
        responseService = mock(ResponseService.class);
        clusterRepository = mock(ClusterRepository.class);
        tagRepository = mock(TagRepository.class);

        locationService = new LocationService(locationRepository, responseService, clusterRepository,tagRepository);

        cluster = Cluster.builder()
                .latitude(2.18)
                .longitude(2.18)
                .area("Paris, France")
                .locations(new ArrayList<>())
                .workspace(null)
                .build();

        tagList = new ArrayList<Tag>();
        Tag t = Tag.builder()
                .name("Tag Test")
                .tagId(5)
                .build();
        tagList.add(t);

        location = Location.builder()
                .tags(tagList)
                .latitude(63.2)
                .longitude(15.2)
                .cluster(cluster)
                .locationId(3)
                .build();

        when(clusterRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(cluster));
        when(locationRepository.save(any())).thenReturn(location);
    }

    @Test
    public void createLocation_should_return_3_test(){
        CreateLocationDTO createLocationDTO = new CreateLocationDTO();
        createLocationDTO.setLatitude(location.getLatitude());
        createLocationDTO.setLongitude(location.getLongitude());
        createLocationDTO.setTags(Arrays.asList("Tag Test"));

        assertThat(locationService.createLocation(createLocationDTO, cluster.getClusterId()).getLocationId()).isEqualTo(3);

    }
}
