package com.example.humspots.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Event;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.R;
import com.example.humspots.adapters.EventAdapter;

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



        RecyclerView rvEvents = (RecyclerView) view.findViewById(R.id.rvEvents);
        //bottomNavigationView = findViewById(R.id.bottomNavigation);

        events = new ArrayList<>();


        //create the adapter
        eventAdapter = new EventAdapter(getContext(), events);

        //set a layout manager on RV
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        //set the adapter on the recycler view
        rvEvents.setAdapter(eventAdapter);



        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Amplify", "Initialized Amplify");
        } catch (AmplifyException e) {
            Log.e("Amplify", "Could not initialize Amplify", e);
        }

        Amplify.API.query(
                ModelQuery.list(Event.class),
                response -> {
                    for (Event event : response.getData()) {
                        Log.i("Amplify", event.getTitle());
                        Log.i("Amplify", event.getDayOfMonth());
                        Log.i("Amplify", event.getDate());
                        try {
                            events.add(event);
                            eventAdapter.notifyDataSetChanged();
                            Log.i(TAG, "Events: " + events.size());
                        } catch (Exception e) {
                            Log.e(TAG, "Events: ", e);
                        }
                    }
                },
                error -> Log.e("Amplify", "Query failure", error)
        );


/*
        Event event6 = Event.builder()
                .title("Title")
                .summary("summary")
                .description("description")
                .posterurl("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
                .date("2020/11/08 14:29:30")
                .venueid("001")
                .build();
        Event event = Event.builder()
                .title("Title part 2")
                .summary("summary 2")
                .description("description 2")
                .posterurl("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
                .date("2020/11/08 14:29:30")
                .venueid("002")
                .build();
        Event event2 = Event.builder()
                .title("Title part 3")
                .summary("summary 3")
                .description("description 3")
                .posterurl("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
                .date("2020/11/08 14:29:30")
                .venueid("003")
                .build();
        Event event3 = Event.builder()
                .title("Title part 4")
                .summary("summary 4")
                .description("description 4")
                .posterurl("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
                .date("2020/11/08 14:29:30")
                .venueid("004")
                .build();
        Event event4 = Event.builder()
                .title("Title part 5")
                .summary("summary 5")
                .description("description 5")
                .posterurl("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
                .date("2020/11/08 14:29:30")
                .venueid("005")
                .build();
        Event event5 = Event.builder()
                .title("Title part 6")
                .summary("summary 6")
                .description("description 6")
                .posterurl("https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
                .date("2020/11/08 14:29:30")
                .venueid("006")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(event),
                response -> Log.i("Amplify", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("Amplify", "Create failed", error)
        );
        Amplify.API.mutate(
                ModelMutation.create(event2),
                response -> Log.i("Amplify", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("Amplify", "Create failed", error)
        );
        Amplify.API.mutate(
                ModelMutation.create(event3),
                response -> Log.i("Amplify", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("Amplify", "Create failed", error)
        );
        Amplify.API.mutate(
                ModelMutation.create(event4),
                response -> Log.i("Amplify", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("Amplify", "Create failed", error)
        );
        Amplify.API.mutate(
                ModelMutation.create(event5),
                response -> Log.i("Amplify", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("Amplify", "Create failed", error)
        );
        Amplify.API.mutate(
                ModelMutation.create(event6),
                response -> Log.i("Amplify", "Added Todo with id: " + response.getData().getId()),
                error -> Log.e("Amplify", "Create failed", error)
        );
        */
        //getEvents(events);


        /*AsyncHttpClient client = new AsyncHttpClient();

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
        });*/

    }
    private void getEvent(String id) {
        Amplify.API.query(
                ModelQuery.get(Event.class, id),
                response -> Log.i("Amplify", ((Event) response.getData()).getTitle()),
                error -> Log.e("Amplify", error.toString(), error)
        );
    }
    private void getEvents(List<Event> events) {
        Amplify.API.query(
                ModelQuery.list(Event.class),
                response -> {
                    for (Event event : response.getData()) {
                        Log.i("Amplify", event.getTitle());
                        Log.i("Amplify", event.getDayOfMonth());
                        Log.i("Amplify", event.getDate());
                        try {
                            events.add(event);
                            eventAdapter.notifyDataSetChanged();
                            Log.i(TAG, "Events: " + events.size());
                        } catch (Exception e) {
                            Log.e(TAG, "EventsAdapter exception", e);
                        }
                    }
                },
                error -> Log.e("Amplify", "Query failure", error)
        );
    }
}
