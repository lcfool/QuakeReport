package com.example.android.quakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class EarthquakeAdapter extends android.widget.ArrayAdapter<Earthquake> {

    private static final SimpleDateFormat dayFormatter = new SimpleDateFormat("dd.MM.yyyy");
    private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm:ss a");

    EarthquakeAdapter(Activity context) {
        super(context, 0, new ArrayList<>());
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView tvMagnitude = (TextView) listItemView.findViewById(R.id.magnitude);
        TextView tvWhere = (TextView) listItemView.findViewById(R.id.where);
        TextView tvCity = (TextView) listItemView.findViewById(R.id.city);
        TextView tvDate = (TextView) listItemView.findViewById(R.id.dateOfEarthquake);
        TextView tvTime = (TextView) listItemView.findViewById(R.id.timeOfEarthquake);

        String place = currentEarthquake.getCity();
        if (place.matches(".+ of .+")) {
            String[] parts = place.split("(?<=of )");
            tvWhere.setText(parts[0]);
            tvCity.setText(parts[1]);
        } else {
            tvWhere.setText("Near the");
            tvCity.setText(place);
        }

        tvDate.setText(dayFormatter.format(new Date(currentEarthquake.getDate())));
        tvTime.setText(timeFormatter.format(new Date(currentEarthquake.getDate())));

        tvMagnitude.setText(toDecimalFormatter(currentEarthquake.getMag()));

        GradientDrawable gradientCircle = (GradientDrawable) tvMagnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMag());
        gradientCircle.setColor(magnitudeColor);

        return listItemView;
    }

    private String toDecimalFormatter(Double notFormatted) {
        return String.format("%.1f", notFormatted);
    }

    @ColorInt
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
