package com.example.humspots.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.R;
import com.example.humspots.adapters.TrailsAdapter;
import com.example.humspots.models.Trail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import okhttp3.Headers;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrailsFragment extends Fragment {

    public static final String TEST_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=Trails+in+Humboldt&key=AIzaSyB2SbC24Cm4_D1Dl8qooOLLckDtBa362bM";
    public static final String TAG = "TrailsActivity";

    List<Trail> trails;

    public TrailsFragment() {
        // Required empty public constructor
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trails, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvEvents = view.findViewById(R.id.rvTrails);

        trails = new ArrayList<>();

        //create the adapter
        final TrailsAdapter trailsAdapter = new TrailsAdapter(getContext(), trails);

        //set the adapter on the recycler view
        rvEvents.setAdapter(trailsAdapter);

        //set a layout manager on RV
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        AsyncHttpClient client = new AsyncHttpClient();

        //JSON Request
        client.get(TEST_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject outerMost = json.jsonObject;

                try {
                    //array of trail objects
                    JSONArray trail_arr = outerMost.getJSONArray("results");
                    Log.i(TAG, "hi:" + trail_arr.toString());
                    trails.addAll(Trail.fromJsonArray(trail_arr));
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