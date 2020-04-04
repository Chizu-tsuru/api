package com.chizu.tsuru.api.services;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
            JSONObject res = obj.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(index);
            return  res.getString("long_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double extractDataFromLocation(String data, String jsonResponse ){
        try {
            JSONObject obj = new JSONObject(jsonResponse);
            JSONObject res = obj.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            return res.getDouble(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 8;
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

    public String getDataFromLocation(String address){
        String httpEncodedString = address.replace(" ","+");
        HttpGet request = new HttpGet(API_URL + "?address=" + httpEncodedString + "&key=" + API_KEY);

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

    public String getStreetNumberFromCoordonate(String jsonResponse){
        return extractDataFromCoordonate(0, jsonResponse);
    }

    public String getRouteFromCoordonate(String jsonResponse){
        return extractDataFromCoordonate(1, jsonResponse);
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

    public String getPostalCodeFromCoordonate(String jsonResponse){
        return extractDataFromCoordonate(6, jsonResponse);
    }

    public double getLatitudeFromLocation(String jsonResponse){
        return extractDataFromLocation("lat", jsonResponse);
    }
    public double getLongitudeFromLocation(String jsonResponse){
        return extractDataFromLocation("lng", jsonResponse);
    }



}
