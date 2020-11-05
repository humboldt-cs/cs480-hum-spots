package com.example.humspots.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Scanner;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.R;
import com.example.humspots.adapters.EventAdapter;
//import com.example.humspots.models.Event;
import com.amplifyframework.datastore.generated.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    public static final String EVENTBRITE_URL = "https://www.eventbriteapi.com/v3/users/me/?token=FXZ47VT64UDMVS6KNOP4";
    public static final String HSU_URL = "https://25livepub.collegenet.com/calendars/student-project-humboldt-app.json";
    public static final String ORGANIZATION_URL = "https://www.eventbriteapi.com/v3/organizations/436148186604/events/?token=FXZ47VT64UDMVS6KNOP4";
    public static final String TAG = "EventsFragment";

    public EventAdapter eventAdapter;

    List<Event> events;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvEvents = view.findViewById(R.id.rvEvents);
        //bottomNavigationView = findViewById(R.id.bottomNavigation);

         events = new ArrayList<>();
         //events = new ArrayList<>();

        //create the adapter
        eventAdapter = new EventAdapter(getContext(), events);

        //set the adapter on the recycler view
        rvEvents.setAdapter(eventAdapter);

        //set a layout manager on RV
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        AsyncHttpClient client = new AsyncHttpClient();

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Amplify", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Amplify", "Could not initialize Amplify", e);
        }

        Amplify.DataStore.query(Event.class,
                events-> {
                    while(events.hasNext()){
                        Event event = events.next();
                        Log.i("Amplify", "==== Event ====");
                        if (event.getTitle() != null) {
                            Log.i("Tutorial", "Title: " + event.getTitle());
                        }
                        if (event.getId() != null) {
                            Log.i("Tutorial", "Id: " + event.getId());
                        }
                        if (event.getDescription() != null) {
                            Log.i("Tutorial", "Description: " + event.getDescription());
                        }
                        if (event.getPosterUrl() != null) {
                            Log.i("Tutorial", "PosterUrl: " + event.getPosterUrl());
                        }
                        if (event.getSummary() != null) {
                            Log.i("Tutorial", "Summary: " + event.getSummary());
                        }
                        if (event.getVenueId() != null) {
                            Log.i("Tutorial", "VenueId: " + event.getVenueId());
                        }
                        if (event.getDate() != null) {
                            Log.i("Tutorial", "Date: " + event.getDate());
                        }
                    }
                },
                failure -> Log.e("Amplify", "Could not query DataStore", failure)
        );
        /*client.get(ORGANIZATION_URL, new JsonHttpResponseHandler() {
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
        });*/
    }
}
