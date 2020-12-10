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

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Event;
import com.example.humspots.R;
import com.example.humspots.adapters.EventAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;
import static com.google.common.collect.ComparisonChain.start;
import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */


public class EventsFragment extends Fragment {

    public static final String TAG = "EventsFragment";

    EventAdapter eventAdapter;
    List<Event> events;
    RecyclerView rvEvents;

    public EventsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        events = new ArrayList<>();
        //create the adapter
        eventAdapter = new EventAdapter(getContext(), events);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvEvents = view.findViewById(R.id.rvEvents);
        //set a layout manager on RV
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        //set the adapter on the recycler view
        rvEvents.setAdapter(eventAdapter);

        amplifyAndSetAdapter();
        //for some reason the code only works with both notifyDataSetChanged (other is in the other thread)
        eventAdapter.notifyDataSetChanged();

    }

    private void addEventToAWS() {
        Event todo = Event.builder().eventTitle("My todo")
                .eventDate("11/21/20")
                .eventTime("11:30:00")
                .extraInfo("AHHHHHHHHHHHHHHHHH")
                .category("temp")
                .description("this is a description")
                .template("Lost Coast")
                .postUrl("https://docs.amplify.aws/lib/graphqlapi/mutate-data/q/platform/android")
                .venue("McDonalds")
                .build();

        Amplify.API.mutate(
                ModelMutation.create(todo),
                response -> Log.i(TAG, "Added Todo with id: " + response.getData().getId()),
                error -> Log.e(TAG, "Create failed", error)
        );
    }

    private void amplifyAndSetAdapter() {
        initializeAmplify();
        amplifyQuery();
    }

    private void initializeAmplify() {
        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

            Log.i(TAG, "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }
    }

    private void amplifyQuery() {
        Amplify.API.query(
                ModelQuery.list(Event.class),
                response -> {
                    for (Event event : response.getData()) {
                        Log.i("Amplify", "Title: " + event.getEventTitle() + " Date: " + event.getEventDate() + " Time: " + event.getEventTime()
                                + " PostURL: " + event.getPostUrl() + " ExtraInfo: " + event.getExtraInfo() + " Venue: " + event.getVenue() + " Template: " + event.getTemplate());

                        //addEvents(event);
                        if(event == null){
                            continue;
                        }
                        try {
                            events.add(event);
                            Log.i(TAG, "Events: " + events.size());
                        } catch (Exception e) {
                            Log.e(TAG, "Events: ", e);
                        }
                    }
                    //Used to seperate the ui using a wait function to add the proper time delay so that an error isnt thrown
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            try {
                                synchronized (this) {
                                    wait(500);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            eventAdapter.notifyDataSetChanged();
                                        }
                                    });

                                }
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }};
                    };
                    thread.start();
                },
                error -> Log.e("Amplify", "Query failure", error)
        );
    }

    private void addEvents(Event event) {
        try {
            events.add(event);
            Log.i(TAG, "Events: " + events.size());
        } catch (Exception e) {
            Log.e(TAG, "Events: ", e);
        }
    }

}
