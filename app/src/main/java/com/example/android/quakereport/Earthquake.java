package com.example.android.quakereport;


import com.google.gson.annotations.SerializedName;

public class Earthquake {

    @SerializedName("place")
    private String city;

    @SerializedName("mag")
    private Double magnitude;

    @SerializedName("time")
    private Long date;

    private String url;

    Earthquake(String city, Double magnitude, Long date, String url) {
        this.city = city;
        this.magnitude = magnitude;
        this.date = date;
        this.url = url;
    }

    String getCity() {
        return city;
    }

    Double getMag() {
        return magnitude;
    }

    Long getDate() {
        return date;
    }

    String getUrl() {
        return url;
    }

}