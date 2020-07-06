package com.chizu.tsuru.api.features.workspace.data.datasources;

import com.chizu.tsuru.api.core.config.Configuration;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class WorkspaceGoogleApiDataSource {
    private final Configuration configuration;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public WorkspaceGoogleApiDataSource(Configuration configuration) {
        this.configuration = configuration;
    }

    public Optional<String> getLocalisationFromCoordinates(double latitude, double longitude) {
        HttpGet request = new HttpGet(this.configuration.getApiUrl() + "?latlng=" + latitude + ","
                + longitude + "&key=" + this.configuration.getApiKey());
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return Optional.of(EntityUtils.toString(entity));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
