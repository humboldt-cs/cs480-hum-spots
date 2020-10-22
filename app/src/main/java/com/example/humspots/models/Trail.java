package com.example.humspots.models;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Headers;

@Parcel
public class Trail {

//    String imageURL;
//    String name;
//    String summary;
//    String rating;
//    String location;
//    String longitude;
//    String latitude;
//    String length;
//    String difficulty;
//    String type;

   // public static  String DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?place_id=ChIJx4SjSoFQ0VQRnLSl4bnJcIU&fields=name,review&key=AIzaSyB2SbC24Cm4_D1Dl8qooOLLckDtBa362bM";
    String LOG = "TrailDeets";
    String business_status;
    String formatted_address;
    String photo_reference;
    String place_name;
    String place_id;
    String top_review;
    String url = "https://lh3.googleusercontent.com/p/AF1QipOrmthTtYIIPbv6yQtrJDjxEo9SsN2MHzUBrIWa=s1600-w400";

    double place_lat;
    double place_long;

    double place_rating;
    int user_ratings_total;

    //empty constructor needed by the parceler library.
    public Trail() {
    }

    public Trail(final JSONObject jsonObject) throws JSONException {
        /*imageURL = jsonObject.getString("imgSmallMed");
        name = jsonObject.getString("name");
        summary = jsonObject.getString("summary");
        rating = jsonObject.getString("stars");
        location = jsonObject.getString("location");
        longitude = jsonObject.getString("longitude");
        latitude = jsonObject.getString("latitude");
        length = jsonObject.getString("length");
        difficulty = jsonObject.getString("difficulty");
        type = jsonObject.getString("type");*/

        //google places
        //THESE ARE FREE
        business_status = jsonObject.getString("business_status");
        formatted_address = jsonObject.getString("formatted_address");
        if(jsonObject.has("photos")){
        photo_reference = jsonObject.getJSONArray("photos").getJSONObject(0).getString("photo_reference");}
        place_name = jsonObject.getString("name");
        place_id = jsonObject.getString("place_id");

        place_lat = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        place_long = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        String DETAILS_URL = String.format("https://maps.googleapis.com/maps/api/place/details/json?place_id=%s&fields=name,review&key=AIzaSyB2SbC24Cm4_D1Dl8qooOLLckDtBa362bM", place_id);
        String PHOTO_URL = String.format("https://maps.googleapis.com/maps/api/place/photo?maxwidth=250&photoreference=%s&key=AIzaSyB2SbC24Cm4_D1Dl8qooOLLckDtBa362bM", photo_reference);
        AsyncHttpClient client = new AsyncHttpClient();

        //Get Photo
        client.get(PHOTO_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("TAG", "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                     JSONArray url = jsonObject.names();
                    Log.i("HHH", "" + url.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

        ///THIS WORKS, BUT IT GETS EXPENSIVE TO RUN SO MANY REQUESTS OVER AND OVER
        //JSON Request
        client.get(DETAILS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(LOG, "onSuccess");

                JSONObject review_obj = json.jsonObject;

                try {
                    JSONObject review = review_obj.getJSONObject("result").getJSONArray("reviews").getJSONObject(0);
                    top_review = review.getString("text");
                    Log.i(LOG, "hi:" + top_review);

                } catch (JSONException e) {
                    Log.e(LOG, "hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(LOG, "onFailure");
            }
        });

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

    public String getBusiness_status() {
        return business_status;
    }

    public String getFormatted_address(){
        return formatted_address;
    }

    public String getIcon(){
        return url;
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

    public String getReview() {return top_review;}

    /*public String getImageURL() {
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
    }*/
}
