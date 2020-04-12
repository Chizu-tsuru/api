package com.chizu.tsuru.api.services;

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

import java.io.IOException;

public class GeocodingService {
    private static String API_KEY = System.getenv("API_KEY");
    private static String API_URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private String extractDataFromCoordonate(int index, String jsonResponse){
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONArray res = obj.getJSONArray("results");
            if(res.length() != 0){
                JSONObject result = res.getJSONObject(0).getJSONArray("address_components").getJSONObject(index);
                return  result.getString("long_name");
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

    public String getDataFromCoordonate(double latitude, double longitude){
        HttpGet request = new HttpGet(API_URL + "?latlng=" + latitude + "," + longitude + "&key=" + API_KEY);
        return getRequest(request);

    }

    public String getFormattedAddressFromCoordonate(String jsonResponse){
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONObject res = obj.getJSONArray("results").getJSONObject(0);
            return  res.getString("formatted_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getCityFromCoordonate(String jsonResponse){
        return extractDataFromCoordonate(2, jsonResponse);
    }

    public String getAdministrativeAreaLevel2FromCoordonate(String jsonResponse){
        return extractDataFromCoordonate(3, jsonResponse);
    }

    public String getAdministrativeAreaLevel1FromCoordonate(String jsonResponse){
        return extractDataFromCoordonate(4, jsonResponse);
    }

    public String getCountryFromCoordonate(String jsonResponse){
        return extractDataFromCoordonate(5, jsonResponse);
    }

}
