package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.repositories.AddressRepository;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.workspaces.services.GeocodingService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AddressServiceTests {


    private static AddressRepository addressRepository;
    private static ClusterRepository clusterRepository;
    private static GeocodingService geocodingService;

    private static AddressService addressService;

    @BeforeClass
    public static void setup() {
        addressRepository = mock(AddressRepository.class);
        clusterRepository = mock(ClusterRepository.class);
        geocodingService = mock(GeocodingService.class);
        addressService = new AddressService(addressRepository, geocodingService);

        Cluster cluster = Cluster.builder()
                .latitude(2.18)
                .longitude(2.18)
                .area("Paris, France")
                .locations(new ArrayList<>())
                .workspace(null)
                .build();
        when(clusterRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(cluster));

        Address address = Address.builder()
                .addressId(1)
                .administrative_area_1("administrative_area_1")
                .administrative_area_2("administrative_area_2")
                .area("area")
                .city("city")
                .country("country")
                .build();
        when(addressRepository.save(any())).thenReturn(address);
    }

    @Test
    public void createAddress_should_return_1_test() {
        Cluster cluster = Cluster.builder()
                .latitude(2.18)
                .longitude(2.18)
                .area("Paris, France")
                .locations(new ArrayList<>())
                .workspace(null)
                .build();
        assertThat(addressService.createAddress(cluster).getAddressId()).isEqualTo(1);
    }
}
