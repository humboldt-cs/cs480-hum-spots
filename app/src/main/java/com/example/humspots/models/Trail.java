package com.example.humspots.models;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

@Parcel
public class Trail extends Application {
    //for distance_from
    private static DecimalFormat df2 = new DecimalFormat("#.#");

    String TAG = "TrailDetails";

    String place_name;
    String place_id;
    Bitmap place_photo;
    Double distance_from;
    String top_review;

    double place_lat;
    double place_long;

    //empty constructor needed by the parceler library.
    public Trail() {
    }

    public Trail(final JSONObject jsonObject) throws JSONException {

        //google places
        //THESE ARE FREE
        if(jsonObject.has("name")) {
            place_name = jsonObject.getString("name");
        }
        else {
            place_name = jsonObject.getString("place_name");
        }

        place_id = jsonObject.getString("place_id");
        place_lat = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        place_long = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
    }

    public static List<Trail> fromJsonArray(JSONArray trailJsonArray) throws JSONException {
        List<Trail> trails = new ArrayList<>();

        for(int i = 0; i < trailJsonArray.length(); i++) {
            trails.add(new Trail(trailJsonArray.getJSONObject(i)));
        }
        return trails;
    }

    public Double getDistanceFrom() {
        return Double.valueOf(df2.format(distance_from));
    }

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

    public String getReview() {return top_review;}

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

    public void setDistance_from(double lat1, double long1, double lat2, double long2){
        double distLat = toRadians(lat2 - lat1);
        double distLong = toRadians(long2 - long1);

        double a = (pow(sin(distLat/2),2) + cos(toRadians(lat1)) * cos(toRadians(lat2)) * pow(sin(distLong/2),2));
        double c = 2 * atan2(sqrt(a), sqrt(1-a));
        double d = 3958.75 * c;

        distance_from = d;
    }

    public void setReview(String o) {
        top_review = o;
    }
}
