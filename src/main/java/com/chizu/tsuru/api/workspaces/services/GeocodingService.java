package com.chizu.tsuru.api.workspaces.services;

import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.clusters.entities.Cluster;
import com.chizu.tsuru.api.config.Configuration;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeocodingService {
    private final Configuration configuration;
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public GeocodingService(Configuration configuration) {
        this.configuration = configuration;
    }

    private String extractDataFromCoordinate(int index, String jsonResponse) {
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONArray res = obj.getJSONArray("results");
            if (res.length() != 0) {
                JSONObject result = res.getJSONObject(0).getJSONArray("address_components").getJSONObject(index);
                return result.getString("long_name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRequest(HttpGet request){
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCityFromCoordinate(String jsonResponse){
        return extractDataFromCoordinate(2, jsonResponse);
    }

    private String getAdministrativeAreaLevel2FromCoordinate(String jsonResponse){
        return extractDataFromCoordinate(3, jsonResponse);
    }

    private String getAdministrativeAreaLevel1FromCoordinate(String jsonResponse){
        return extractDataFromCoordinate(4, jsonResponse);
    }

    private String getCountryFromCoordinate(String jsonResponse){
        return extractDataFromCoordinate(5, jsonResponse);
    }

    private String getAreaFromCoordinate(String jsonResponse){
        return extractDataFromCoordinate(6, jsonResponse);
    }

    public String getDataFromCoordinate(double latitude, double longitude){
        HttpGet request = new HttpGet(this.configuration.getApiUrl() + "?latlng=" + latitude + ","
                + longitude + "&key=" + this.configuration.getApiKey());
        return getRequest(request);
    }

    public Address convertResponseStringToAddressObject(String response, Cluster cluster){
        return Address.builder()
                .administrative_area_1(getAdministrativeAreaLevel1FromCoordinate(response))
                .administrative_area_2(getAdministrativeAreaLevel2FromCoordinate(response))
                .area(getAreaFromCoordinate(response))
                .city(getCityFromCoordinate(response))
                .cluster(cluster)
                .country(getCountryFromCoordinate(response)).build();
    }
}
