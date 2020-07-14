package com.chizu.tsuru.api.core.services;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class JsonParsingService {

    public static String parseJson(String element, JSONObject jsonResponse) {
        try {
            JSONArray res = jsonResponse.getJSONArray("results");
            if (res.length() != 0) {
                JSONArray result = res.getJSONObject(0).getJSONArray("address_components");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jsonObject = result.getJSONObject(i);
                    JSONArray types = jsonObject.getJSONArray("types");
                    for (int y = 0; y < types.length(); y++) {
                        if (types.getString(y).equals(element)) {
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
}
