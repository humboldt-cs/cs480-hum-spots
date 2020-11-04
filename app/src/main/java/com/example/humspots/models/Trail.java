package com.example.humspots.models;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import okhttp3.Headers;

import static com.parse.Parse.getApplicationContext;

@Parcel
public class Trail {
//    String summary;
//    String length;
//    String difficulty;
//    String type;

   String TAG = "TrailDetails";
   String business_status;
   String formatted_address;
   String photo_reference;
   String place_name;
   String place_id;
   Bitmap place_photo;
   //String top_review;

    double place_lat;
    double place_long;

    //double place_rating;
    //int user_ratings_total;

    //empty constructor needed by the parceler library.
    public Trail() {
    }

    public Trail(final JSONObject jsonObject) throws JSONException {
        /*
        summary = jsonObject.getString("summary");
        length = jsonObject.getString("length");
        difficulty = jsonObject.getString("difficulty");
        type = jsonObject.getString("type");*/

        //google places
        //THESE ARE FREE
        //business_status = jsonObject.getString("business_status");
        formatted_address = jsonObject.getString("formatted_address");
        if(jsonObject.has("name")) {
            place_name = jsonObject.getString("name");
        }
        else {
            place_name = jsonObject.getString("place_name");
        }

        place_id = jsonObject.getString("place_id");

        place_lat = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        place_long = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        ///THESE ARE NOT FREE
        //place_rating = jsonObject.getDouble("rating");
        //user_ratings_total = jsonObject.getInt("user_ratings_total");
        
    }

    public static List<Trail> fromJsonArray(JSONArray trailJsonArray) throws JSONException {
        List<Trail> trails = new ArrayList<>();

        for(int i = 0; i < trailJsonArray.length(); i++) {
            trails.add(new Trail(trailJsonArray.getJSONObject(i)));
        }
        return trails;
    }

    /*public String getBusiness_status() {
        return business_status;
    }

    public String getFormatted_address(){
        return formatted_address;
    }*/

    public Bitmap getIcon() {
        return place_photo;
    }

    public String getName() {
        return place_name;
    }

    public String getPlace_id(){
        return place_id;
    }

    public double getPlace_lat(){
        return place_lat;
    }

    public double getPlace_long() {
        return place_long;
    }

    //public String getReview() {return top_review;}

    /*

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
    }*/

    public void setName(String argument) {
        place_name = argument;
    }

    public void setLat(double argument) {
        place_lat = argument;
    }

    public void setLong(double argument) {
        place_lat = argument;
    }

    public void setIcon(Bitmap argument) {
        place_photo = argument;
    }

    public void setPlaceId(String argument) {
        place_id = argument;
    }
}
