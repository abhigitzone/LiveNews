package com.example.jsonparsing;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<DataNews>> {
    private String mUrl;

    NewsLoader(@NonNull Context context, String requestUrl) {
        super(context);
        mUrl = requestUrl;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<DataNews> loadInBackground() {
        if (mUrl == null) {
            return null;
        } else
            // Perform the network request, parse the response, and extract a list of news.
            return QueryUtils.fetchNewsData(mUrl);
    }
}
