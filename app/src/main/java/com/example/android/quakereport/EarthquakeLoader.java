package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String inputUrl;

    EarthquakeLoader(Context context, String inputUrl) {
        super(context);
        this.inputUrl = inputUrl;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        return Utils.fetchEarthquakesData(inputUrl);
    }

}
