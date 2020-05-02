package com.chizu.tsuru.api.services;

import com.chizu.tsuru.api.Entities.Address;
import com.chizu.tsuru.api.Entities.Cluster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class GeoCodingServiceTests {

    @TestConfiguration
    static class GeoCodingServiceImplTestContextConfiguration {

        @Bean
        public GeocodingService geocodingService() {
            return new GeocodingService();
        }
    }

    @Autowired
    private GeocodingService geocodingService;

    @Test
    public void getDataFromCoordonate_should_return_a_string_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(result).isExactlyInstanceOf(String.class);
    }

    @Test
    public void getAddressFromCoordonate_should_return_an_address_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        Address address = geocodingService.convertResponseStringToAddressObject(result, new Cluster());
        assertThat(address).isExactlyInstanceOf(Address.class);
    }

    @Test
    public void getAddressFromCoordonate_should_return_an_address_and_addressId_should_be_null_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        Address address = geocodingService.convertResponseStringToAddressObject(result, new Cluster());
        assertThat(address.getAddressId()).isNull();
    }

    @Test
    public void getAddressFromCoordonate_should_return_an_address_and_administrative_area_1_should_be_null_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        Address address = geocodingService.convertResponseStringToAddressObject(result, new Cluster());
        assertThat(address.getAdministrative_area_1()).isEqualTo("Île-de-France");
    }

    @Test
    public void getAddressFromCoordonate_should_return_an_address_and_administrative_area_2_should_be_a_string_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        Address address = geocodingService.convertResponseStringToAddressObject(result, new Cluster());
        assertThat(address.getAdministrative_area_2()).isEqualTo("Val-de-Marne");
    }

    @Test
    public void getAddressFromCoordonate_should_return_an_address_and_area_should_be_a_string_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        Address address = geocodingService.convertResponseStringToAddressObject(result, new Cluster());
        assertThat(address.getArea()).isEqualTo("94100");
    }

    @Test
    public void getAddressFromCoordonate_should_return_an_address_and_city_should_be_a_string_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        Address address = geocodingService.convertResponseStringToAddressObject(result, new Cluster());
        assertThat(address.getCity()).isEqualTo("Saint-Maur-des-Fossés");
    }

    @Test
    public void getAddressFromCoordonate_should_return_an_address_and_country_should_be_a_string_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        Address address = geocodingService.convertResponseStringToAddressObject(result, new Cluster());
        assertThat(address.getCountry()).isEqualTo("France");
    }

    @Test
    public void getAddressFromCoordonate_should_return_an_address_and_area_should_be_a_cluster_test() {
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        Address address = geocodingService.convertResponseStringToAddressObject(result, new Cluster());
        assertThat(address.getCluster()).isInstanceOf(Cluster.class);
    }
}