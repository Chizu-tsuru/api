package com.chizu.tsuru.api.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class GeoCodingServiceTests {

    @Autowired
    private GeocodingService geocodingService = new GeocodingService();

    @Test
    void getDataFromCoordonate_should_return_a_string_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(result).isExactlyInstanceOf(String.class);
    }

    @Test
    void getFormattedAddressFromCoordonate_should_return_a_string_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getFormattedAddressFromCoordonate(result)).isExactlyInstanceOf(String.class);
    }

    @Test
    void getFormattedAddressFromCoordonate_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getFormattedAddressFromCoordonate(result)).isEqualTo("36 Avenue du Midi, 94100 Saint-Maur-des-Fossés, France");
    }

    @Test
    void getCityFromCoordonate_should_return_a_string_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getCityFromCoordonate(result)).isExactlyInstanceOf(String.class);
    }

    @Test
    void getCityFromCoordonate_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getCityFromCoordonate(result)).isEqualTo("Saint-Maur-des-Fossés");
    }

    @Test
    void getAdministrativeAreaLevel2FromCoordonate_should_return_a_string_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getAdministrativeAreaLevel2FromCoordonate(result)).isExactlyInstanceOf(String.class);
    }

    @Test
    void getAdministrativeAreaLevel2FromCoordonate_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getAdministrativeAreaLevel2FromCoordonate(result)).isEqualTo("Val-de-Marne");
    }

    @Test
    void getAdministrativeAreaLevel1FromCoordonate_should_return_a_string_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getAdministrativeAreaLevel1FromCoordonate(result)).isExactlyInstanceOf(String.class);
    }

    @Test
    void getAdministrativeAreaLevel1FromCoordonate_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getAdministrativeAreaLevel1FromCoordonate(result)).isEqualTo("Île-de-France");
    }

    @Test
    void getCountryFromCoordonate_should_return_a_string_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getCountryFromCoordonate(result)).isExactlyInstanceOf(String.class);
    }

    @Test
    void getCountryFromCoordonate_test(){
        String result = geocodingService.getDataFromCoordonate(48.8, 2.5);
        assertThat(geocodingService.getCountryFromCoordonate(result)).isEqualTo("France");
    }

    @Test
    void getCountryFromCoordonate_should_return_null_test(){
        String result = geocodingService.getDataFromCoordonate(5.125,5.235);
        assertThat(geocodingService.getCountryFromCoordonate(result)).isNull();
    }
}
