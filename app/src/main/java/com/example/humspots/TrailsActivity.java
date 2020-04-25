package com.example.humspots;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.adapters.TrailsAdapter;
import com.example.humspots.models.Trail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TrailsActivity extends AppCompatActivity{

    public static final String TRAILS_URL = "https://www.hikingproject.com/data/get-trails?lat=40.875737&lon=-124.078594&maxDistance=25&key=200729737-84ca63f82302306e434390a4a8366855";
    public static final String TAG = "TrailsActivity";

    List<Trail> trails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvEvents = findViewById(R.id.rvEvents);

        trails = new ArrayList<>();
        //create the adapter
        final TrailsAdapter trailsAdapter = new TrailsAdapter(this, trails);

        //set the adapter on the recycler view
        rvEvents.setAdapter(trailsAdapter);

        //set a layout manager on RV
        rvEvents.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(TRAILS_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("trails");
                    Log.i(TAG, "Results: " + results.toString());
                    trails.addAll(Trail.fromJsonArray(results));
                    trailsAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Trails: " + trails.size());
                } catch (JSONException e) {
                    Log.e(TAG, "hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

}
