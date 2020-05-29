package com.example.jsonparsing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<DataNews>> {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int LOADER_TAG = 1;
    NewsAdapter newsAdapter;
    Context context = this;
    ProgressBar progressBar;
    TextView notify;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_circular);
        notify = findViewById(R.id.text);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newsAdapter = new NewsAdapter(this, new ArrayList<DataNews>());
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(newsAdapter);
        listView.setEmptyView(notify);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                DataNews news = newsAdapter.getItem(position);
                assert news != null;
                String url = news.getNewsUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        loaderManager();
    }

    public void loaderManager() {
        //Checking for internet Connectivity..
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(LOADER_TAG, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            notify.setVisibility(View.VISIBLE);
            notify.setText(R.string.networkErr);
            notify.setTextColor(Color.RED);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.refresh: {
                loaderManager();
                Toast.makeText(this, getString(R.string.updated_just_now),
                        Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.exit: {
                finish();
                Toast.makeText(context, R.string.vist, Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<List<DataNews>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri.Builder uriBuilder = NewsPreferences.getUri(MainActivity.this);
        Log.e(LOG_TAG, uriBuilder.toString());
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<DataNews>> loader, List<DataNews> data) {
        progressBar.setVisibility(View.GONE);
        newsAdapter.clear();
        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<DataNews>> loader) {
        newsAdapter.clear();
    }

}


