package com.example.android.quakereport;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

abstract class Utils {

    private static final String LOG_TAG = Utils.class.getSimpleName();

    static List<Earthquake> fetchEarthquakesData(String url) {

        URL createdUrl = createUrl(url);
        String jsonResponse = null;
        try {
            jsonResponse = makeHTTPRequest(createdUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "IOException occurred", e);
        }

        return parseJSONtoEarthquakes(jsonResponse);
    }

    @NonNull
    private static List<Earthquake> parseJSONtoEarthquakes(String jsonObject) {

        List<Earthquake> earthquakes = new ArrayList<>();
        if (TextUtils.isEmpty(jsonObject)) {
            return earthquakes;
        }


        JSONObject baseJSONResponse;
        JSONArray earthquakeArray;
        try {
            baseJSONResponse = new JSONObject(jsonObject);
            earthquakeArray = baseJSONResponse.optJSONArray("features");
            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i).getJSONObject("properties");
                earthquakes.add(new Earthquake(currentEarthquake.getString("place"), currentEarthquake.getDouble("mag"), currentEarthquake.getLong("time"), currentEarthquake.getString("url")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return earthquakes;
    }

    private static URL createUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException exception) {
            return null;
        }
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String ln = "";
            do {
                ln = reader.readLine();
                stringBuilder.append(ln);
            } while (ln != null);
        }
        return stringBuilder.toString();
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        } else {

            HttpURLConnection connection = null;
            InputStream inputStream = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    inputStream = connection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "makeHTTPRequest: exception occurred", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        return jsonResponse;
    }
}
