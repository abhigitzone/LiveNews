package com.example.jsonparsing;

import android.content.Context;
import android.net.Uri;

class NewsPreferences {
    private static final String NEWS_REQUEST_URL = "http://newsapi.org/v2/top-headlines?country=in&apiKey=30ae643ca7f44acd95d7b9481f11a363";
    private static final String API = "30ae643ca7f44acd95d7b9481f11a363";

    static Uri.Builder getUri(Context context) {
        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("apiKey", API);
        return uriBuilder;
    }
}
