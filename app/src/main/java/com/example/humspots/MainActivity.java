package com.example.humspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.adapters.EventAdapter;
import com.example.humspots.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String EVENTBRITE_URL = "https://www.eventbriteapi.com/v3/users/me/?token=FXZ47VT64UDMVS6KNOP4";
    public static final String TEST_URL = "https://www.eventbriteapi.com/v3/events/99084732101/?token=FXZ47VT64UDMVS6KNOP4";
    public static final String ORGANIZATION_URL = "https://www.eventbriteapi.com/v3/organizations/436148186604/events/?token=FXZ47VT64UDMVS6KNOP4";
    public static final String TAG = "MainActivity";

    List<Event> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvEvents = findViewById(R.id.rvEvents);

        events = new ArrayList<>();
        //create the adapter
        final EventAdapter eventAdapter = new EventAdapter(this, events);

        //set the adapter on the recycler view
        rvEvents.setAdapter(eventAdapter);

        //set a layout manager on RV
        rvEvents.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(ORGANIZATION_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("events");
                    Log.i(TAG, "Results: " + results.toString());
                    events.addAll(Event.fromJsonArray(results));
                    eventAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Events: " + events.size());
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
