package com.example.jsonparsing;

import android.content.Context;
import android.net.Uri;

class NewsPreferences {
    private static final String NEWS_REQUEST_URL = "Put your News Request Url here";
    private static final String API = "Put your News API here";

    static Uri.Builder getUri(Context context) {
        Uri baseUri = Uri.parse(NEWS_REQUEST_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("apiKey", API);
        return uriBuilder;
    }
}
