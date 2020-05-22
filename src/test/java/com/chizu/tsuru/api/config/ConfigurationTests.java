package com.chizu.tsuru.api.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ConfigurationTests {

    @Autowired
    private Configuration config;

    @Test
    void apiKeyShouldExist() {
        assertThat(config.getApiKey()).isNotNull();
    }

    @Test
    void apiUrlShouldExist() throws URISyntaxException {
        assertThat(new URI(config.getApiUrl())).hasHost("maps.googleapis.com");
    }


}
