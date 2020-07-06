package com.chizu.tsuru.api.workspaces.services;

import com.chizu.tsuru.api.clusters.entities.Address;
import com.chizu.tsuru.api.config.Configuration;
import org.apache.http.HttpEntity;
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

    private String extractDataFromCoordinate(String index, String jsonResponse) {
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONArray res = obj.getJSONArray("results");
            if (res.length() != 0) {
                JSONArray result = res.getJSONObject(0).getJSONArray("address_components");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonObject = result.getJSONObject(i);
                    JSONArray types = jsonObject.getJSONArray("types");
                    for (int y = 0; y < types.length(); y++) {
                        if (types.getString(y).equals(index)) {
                            return jsonObject.getString("long_name");
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getRequest(double latitude, double longitude) {
        HttpGet request = new HttpGet(this.configuration.getApiUrl() + "?latlng=" + latitude + ","
                + longitude + "&key=" + this.configuration.getApiKey());
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

    private String getCityFromCoordinate(String jsonResponse) {
        return extractDataFromCoordinate("locality", jsonResponse);
    }

    private String getAdministrativeAreaLevel2FromCoordinate(String jsonResponse) {
        return extractDataFromCoordinate("administrative_area_level_2", jsonResponse);
    }

    private String getAdministrativeAreaLevel1FromCoordinate(String jsonResponse) {
        return extractDataFromCoordinate("administrative_area_level_1", jsonResponse);
    }

    private String getCountryFromCoordinate(String jsonResponse) {
        return extractDataFromCoordinate("country", jsonResponse);
    }

    private String getAreaFromCoordinate(String jsonResponse) {
        return extractDataFromCoordinate("postal_code", jsonResponse);
    }

    public String getDataFromCoordinate(double latitude, double longitude) {
        return getRequest(latitude, longitude);
    }

    public Address convertResponseStringToAddressObject(String response) {
        return Address.builder()
                .administrative_area_1(getAdministrativeAreaLevel1FromCoordinate(response))
                .administrative_area_2(getAdministrativeAreaLevel2FromCoordinate(response))
                .area(getAreaFromCoordinate(response))
                .city(getCityFromCoordinate(response))
                .country(getCountryFromCoordinate(response)).build();
    }
}
