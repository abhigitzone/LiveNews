package com.example.jsonparsing;

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
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public QueryUtils() {
    }

    //Creating URL
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem getting the URL.", e);
        }
        return url;
    }

    //Fetching Data
    static List<DataNews> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem getting the HTTP request.", e);
        }

        return parseJson(jsonResponse);
    }

    //Make HTTP Request
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("mainActivity", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("QueryUtils", "Error getting HTTP request: ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Parsing JSON
    private static ArrayList<DataNews> parseJson(String response) {

        ArrayList<DataNews> dataNewsItem = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            //TODO: Call here JSON ARRAY and pass "articles" as a string.
            JSONArray arrayNews = jsonObject.getJSONArray("articles");

            for (int i = 0; i < arrayNews.length(); i++) {
                JSONObject newsJson = arrayNews.getJSONObject(i);
                JSONObject source = newsJson.getJSONObject("source");

                //TODO: Have to call JSON attributes.
                String title = newsJson.getString("title");
                String newsDesc = newsJson.getString("description");
                String newsImg = newsJson.getString("urlToImage");
                String newsAuthor = newsJson.getString("author");
                String newsDate = newsJson.getString("publishedAt");
                newsDate = formatDate(newsDate);
                String newsType = source.getString("name");
                String url = newsJson.getString("url");

                //TODO: Assign JSON attributes as parameter to this constructor.
                DataNews dataNews = new DataNews(newsType, title, newsDesc, newsAuthor, newsDate, newsImg, url);
                dataNewsItem.add(dataNews);

            }

        } catch (JSONException e) {
            Log.e("Json Message", "Problem Parsing the Employee JSON");
        }

        return dataNewsItem;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static String formatDate(String rawDate) {
        String jsonDatePattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat jsonFormatter = new SimpleDateFormat(jsonDatePattern, Locale.US);
        try {
            Date parsedJsonDate = jsonFormatter.parse(rawDate);
            String finalDatePattern = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            assert parsedJsonDate != null;
            return finalDateFormatter.format(parsedJsonDate);
        } catch (ParseException e) {
            Log.e("QueryUtils", "Error parsing JSON date: ", e);
            return "";
        }
    }
}
