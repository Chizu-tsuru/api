package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.clusters.repositories.AddressRepository;
import com.chizu.tsuru.api.clusters.repositories.ClusterRepository;
import com.chizu.tsuru.api.workspaces.services.GeocodingService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressServiceTests {


    private static AddressRepository addressRepository;
    private static ClusterRepository clusterRepository;
    private static GeocodingService geocodingService;

    private static AddressService addressService;

    @BeforeClass
    public static void setup(){
        addressRepository = mock(AddressRepository.class);
        clusterRepository = mock(ClusterRepository.class);
        geocodingService = mock(GeocodingService.class);
        addressService = new AddressService(addressRepository, clusterRepository, geocodingService);

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
                .cluster(cluster)
                .country("country")
                .build();
        when(addressRepository.save(any())).thenReturn(address);
    }

    @Test
    public void createAddress_should_return_1_test(){
        assertThat(addressService.createAddress(1)).isEqualTo(1);
    }
}
