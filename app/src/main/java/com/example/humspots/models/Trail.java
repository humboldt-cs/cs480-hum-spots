package com.example.humspots.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Trail {
    String imageURL;
    String name;
    String summary;
    String rating;
    String location;
    String longitude;
    String latitude;
    String length;
    String difficulty;
    String type;

    //empty constructor needed by the parceler library.
    public Trail() {
    }

    public Trail(JSONObject jsonObject) throws JSONException {
        imageURL = jsonObject.getString("imgSmallMed");
        name = jsonObject.getString("name");
        summary = jsonObject.getString("summary");
        rating = jsonObject.getString("stars");
        location = jsonObject.getString("location");
        longitude = jsonObject.getString("longitude");
        latitude = jsonObject.getString("latitude");
        length = jsonObject.getString("length");
        difficulty = jsonObject.getString("difficulty");
        type = jsonObject.getString("type");
    }

    public static List<Trail> fromJsonArray(JSONArray trailJsonArray) throws JSONException {
        List<Trail> trails = new ArrayList<>();

        for(int i = 0; i < trailJsonArray.length(); i++) {
            trails.add(new Trail(trailJsonArray.getJSONObject(i)));
        }
        return trails;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        return summary;
    }

    public String getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLength() {
        return length;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getType() {
        return type;
    }
}
