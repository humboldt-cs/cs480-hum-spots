package com.example.humspots.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Venue {
    String venueID;
    String capacity;
    String venueName;
    String venueLatitude;
    String venueLongitude;

    public Venue(JSONObject jsonObject) throws JSONException {
        venueID = jsonObject.getString("id");
        capacity = jsonObject.getString("capacity");
        venueName = jsonObject.getString("name");
        venueLatitude = jsonObject.getString("latitude");
        venueLongitude = jsonObject.getString("longitude");
    }

    public static List<Venue> fromJsonArray(JSONArray venueJsonArray) throws JSONException {
        List<Venue> venues = new ArrayList<>();

        for(int i = 0; i < venueJsonArray.length(); i++) {
            venues.add(new Venue(venueJsonArray.getJSONObject(i)));
        }
        return venues;
    }

    public static List<Venue> fromJsonObject(JSONObject venueJsonObjectStr) throws JSONException {
        List<Venue> venues = new ArrayList<>();

        //JSONObject object = new JSONObject(venueJsonObjectStr);
        JSONArray Jarray  = venueJsonObjectStr.getJSONArray("address");

        for(int i = 0; i < Jarray.length(); i++) {
            venues.add(new Venue(Jarray.getJSONObject(i)));
        }

        return venues;
    }

    public String getVenueID() {
        return venueID;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getVenueName() {
        return venueName;
    }

    public String getVenueLatitude() {
        return venueLatitude;
    }

    public String getVenueLongitude() {
        return venueLongitude;
    }
}
