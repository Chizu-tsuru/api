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
                String result = EntityUtils.toString(entity);
                return result;
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getCityFromCoordonate(String jsonResponse){
        return extractDataFromCoordinate(2, jsonResponse);
    }

    private String getAdministrativeAreaLevel2FromCoordonate(String jsonResponse){
        return extractDataFromCoordinate(3, jsonResponse);
    }

    private String getAdministrativeAreaLevel1FromCoordonate(String jsonResponse){
        return extractDataFromCoordinate(4, jsonResponse);
    }

    private String getCountryFromCoordonate(String jsonResponse){
        return extractDataFromCoordinate(5, jsonResponse);
    }

    private String getAreaFromCoordonate(String jsonResponse){
        return extractDataFromCoordinate(6, jsonResponse);
    }

    public String getDataFromCoordonate(double latitude, double longitude){
        HttpGet request = new HttpGet(this.configuration.getApiUrl() + "?latlng=" + latitude + ","
                + longitude + "&key=" + this.configuration.getApiKey());
        return getRequest(request);
    }

    public Address convertResponseStringToAddressObject(String response, Cluster cluster){
        Address add = Address.builder()
                .administrative_area_1(getAdministrativeAreaLevel1FromCoordonate(response))
                .administrative_area_2(getAdministrativeAreaLevel2FromCoordonate(response))
                .area(getAreaFromCoordonate(response))
                .city(getCityFromCoordonate(response))
                .cluster(cluster)
                .country(getCountryFromCoordonate(response)).build();
        return add;
    }
}
