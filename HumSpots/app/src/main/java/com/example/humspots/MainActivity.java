package com.example.humspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // sets up the recycler view
        RecyclerView rvEvents = findViewById(R.id.rvEvents);

        final TimelineAdapter timelineAdapter = new TimelineAdapter(this);
        // set the adapter on the recycler view
        rvEvents.setAdapter(timelineAdapter);
        // set a layout manager on the recycler view
        rvEvents.setLayoutManager(new LinearLayoutManager(this));

    }
}
