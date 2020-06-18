package com.chizu.tsuru.api.clusters.services;

import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.workspaces.services.GeocodingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GeoCodingServiceTests {

    private final GeocodingService geocodingService;

    @Autowired
    public GeoCodingServiceTests(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @Test
    public void getAddressFromCoordinate_should_return_an_address_test() {
        Address address = geocodingService.ClusterToAddress(Cluster.builder().latitude(48.8).longitude(2.5).build());

        assertThat(address).isExactlyInstanceOf(Address.class);
    }

    @Test
    public void getAddressFromCoordinate_should_return_an_address_and_addressId_should_be_null_test() {
        Address address = geocodingService.ClusterToAddress(Cluster.builder().latitude(48.8).longitude(2.5).build());

        assertThat(address.getAddressId()).isNull();
    }

    @Test
    public void getAddressFromCoordinate_should_return_an_address_and_administrative_area_1_should_be_null_test() {
        Address address = geocodingService.ClusterToAddress(Cluster.builder().latitude(48.8).longitude(2.5).build());

        assertThat(address.getAdministrative_area_1()).isEqualTo("Île-de-France");
    }

    @Test
    public void getAddressFromCoordinate_should_return_an_address_and_administrative_area_2_should_be_a_string_test() {
        Address address = geocodingService.ClusterToAddress(Cluster.builder().latitude(48.8).longitude(2.5).build());

        assertThat(address.getAdministrative_area_2()).isEqualTo("Val-de-Marne");
    }

    @Test
    public void getAddressFromCoordinate_should_return_an_address_and_area_should_be_a_string_test() {
        Address address = geocodingService.ClusterToAddress(Cluster.builder().latitude(48.8).longitude(2.5).build());

        assertThat(address.getArea()).isEqualTo("94100");
    }

    @Test
    public void getAddressFromCoordinate_should_return_an_address_and_city_should_be_a_string_test() {
        Address address = geocodingService.ClusterToAddress(Cluster.builder().latitude(48.8).longitude(2.5).build());

        assertThat(address.getCity()).isEqualTo("Saint-Maur-des-Fossés");
    }

    @Test
    public void getAddressFromCoordinate_should_return_an_address_and_country_should_be_a_string_test() {
        Address address = geocodingService.ClusterToAddress(Cluster.builder().latitude(48.8).longitude(2.5).build());

        assertThat(address.getCountry()).isEqualTo("France");
    }

    @Test
    public void getAddressFromCoordinate_should_return_an_address_and_area_should_be_a_cluster_test() {
        Address address = geocodingService.ClusterToAddress(Cluster.builder().latitude(48.8).longitude(2.5).build());

        assertThat(address.getCluster()).isInstanceOf(Cluster.class);
    }
}