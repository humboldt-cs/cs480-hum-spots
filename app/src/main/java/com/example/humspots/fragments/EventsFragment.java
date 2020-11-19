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
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Event;
import com.example.humspots.R;
import com.example.humspots.adapters.EventAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    public static final String EVENTBRITE_URL = "https://www.eventbriteapi.com/v3/users/me/?token=FXZ47VT64UDMVS6KNOP4";
    public static final String HSU_URL = "https://25livepub.collegenet.com/calendars/student-project-humboldt-app.json";
    public static final String ORGANIZATION_URL = "https://www.eventbriteapi.com/v3/organizations/436148186604/events/?token=FXZ47VT64UDMVS6KNOP4";
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvEvents = view.findViewById(R.id.rvEvents);
        //bottomNavigationView = findViewById(R.id.bottomNavigation);

        events = new ArrayList<>();

        //create the adapter
        eventAdapter = new EventAdapter(this.getContext(), events);

        //set a layout manager on RV
        rvEvents.setLayoutManager(new LinearLayoutManager(this.getContext()));

        //set the adapter on the recycler view
        rvEvents.setAdapter(eventAdapter);
        amplifyAndSetAdapter();


        /*Event todo = Event.builder().eventTitle("My todo")
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
        );*/
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
                        Log.i("Amplify", event.getEventTitle() + " " + event.getEventDate() + " " + event.getEventTime() + " " + event.getCategory()
                                + " " + event.getPostUrl() + " " + event.getExtraInfo() + " " + event.getVenue() + " " + event.getTemplate());
                        addEvents(event);
                    }
                },
                error -> Log.e("Amplify", "Query failure", error)
        );
        eventAdapter.notifyDataSetChanged();
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
