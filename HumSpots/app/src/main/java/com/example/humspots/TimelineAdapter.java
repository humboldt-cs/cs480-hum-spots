package com.example.humspots;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter {

    Context context;
    // list of data for the events
    //List<Event> events;
    // pass in list of events
    public TimelineAdapter(Context context)
    {
        this.context = context;
        //this.events = events;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "OnCreateViewHolder");
        View timelineView = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(timelineView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView ev_title;
        TextView ev_img;
        ImageView ev_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ev_title = itemView.findViewById(R.id.ev_title);
            ev_img = itemView.findViewById(R.id.ev_img);
            ev_date = itemView.findViewById(R.id.ev_date);
            container = itemView.findViewById(R.id.container);
        }


    }


}
