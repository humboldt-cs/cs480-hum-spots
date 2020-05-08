package com.example.humspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.fragments.MapFragment;
import com.example.humspots.models.Event;
import com.example.humspots.models.Venue;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";

    Venue venue;
    JSONArray results;
    int test;

    ImageView ivImage;
    ImageView ivMapTest;
    TextView tvDay;
    TextView tvMonth;
    TextView tvTitle;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivImage = findViewById(R.id.ivImage);
        ivMapTest = findViewById(R.id.ivMapTest);
        tvDay = findViewById(R.id.tvDay);
        tvMonth = findViewById(R.id.tvMonth);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);

        final Event event = Parcels.unwrap(getIntent().getParcelableExtra("event"));

        Glide.with(this).load(event.getPosterURL()).into(ivImage);

        tvDay.setText(event.getDayOfMonth());
        tvMonth.setText(event.getMonthOfYear());
        tvTitle.setText(event.getTitle());
        tvDescription.setText(event.getDescription());

        //get venue info
        final String venueId = event.getVenueId();
        String requestVenue = String.format("https://www.eventbriteapi.com/v3/venues/%s/?token=FXZ47VT64UDMVS6KNOP4", venueId);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(requestVenue, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    results = jsonObject.getJSONArray("address");
                    venue = (Venue) Venue.fromJsonArray(results);
                    Log.i(TAG, "Results: " + results.toString());
                } catch (JSONException e) {
                    Log.e(TAG, "hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        ivMapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Map loading..", Toast.LENGTH_SHORT).show();

//                Bundle bundle = new Bundle();
//                bundle.putString("lat", venue.getVenueLatitude());
//                bundle.putString("long", venue.getVenueLongitude());
//                bundle.putString("name", venue.getVenueName());

                // set Fragment Arguments
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                //fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.eFrame, fragment).commit();
            }
        });
    }
}
