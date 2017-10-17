/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<List<Earthquake>> {

    private EarthquakeAdapter earthquakeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView lvEarthquakeListView = (ListView) findViewById(R.id.lvList);

        earthquakeAdapter = new EarthquakeAdapter(this);
        lvEarthquakeListView.setAdapter(earthquakeAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, this).forceLoad();


        lvEarthquakeListView.setOnItemClickListener((adapterView, view, i, l) -> {

            Earthquake currentEarthquake = earthquakeAdapter.getItem(i);

            try {
                Uri parse = Uri.parse(currentEarthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, parse);
                startActivity(intent);
            } catch (NullPointerException npe) {
                Log.e("TAG", npe.getMessage());
            }
        });
    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, Constants.USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        if (data != null && !data.isEmpty()) {
            earthquakeAdapter.clear();
            earthquakeAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        earthquakeAdapter.clear();
    }

}
