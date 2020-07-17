package com.chizu.tsuru.map_clustering.features.clustering.data.datasources;

import com.chizu.tsuru.map_clustering.core.config.Configuration;
import com.chizu.tsuru.map_clustering.core.errors.InternalServerException;
import com.chizu.tsuru.map_clustering.features.clustering.data.models.AddressModel;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClusteringGoogleApiDataSource {
    private final Configuration configuration;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public ClusteringGoogleApiDataSource(Configuration configuration) {
        this.configuration = configuration;
    }

    public AddressModel getLocalisationFromCoordinates(Double latitude, Double longitude) {
        HttpGet request = new HttpGet(this.configuration.getApiUrl() + "?latlng=" + latitude + ","
                + longitude + "&key=" + this.configuration.getApiKey());
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                var responseStr = EntityUtils.toString(entity);
                var jsonResponse = new JSONObject(responseStr);
                return AddressModel.FromJson(jsonResponse);
            }

        } catch (IOException | JSONException e) {
            throw new InternalServerException("unable to fetch google API data");
        }
        throw new InternalServerException("unable to fetch google API data");
    }
}
