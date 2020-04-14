package com.example.humspots.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.humspots.DetailActivity;
import com.example.humspots.R;
import com.example.humspots.models.Event;

import org.parceler.Parcels;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    Context context;

    List<Event> events;

    public EventAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    //usually involves inflating a layout from XML and returning the viewholder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(eventView);
    }

    //involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the event at the given position
        Event event = events.get(position);
        //bind the event data into the view holder
        holder.bind(event);
    }

    //returns the total number of items in the list
    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout container;
        TextView tvDay;
        TextView tvMonth;
        TextView tvEventTitle;
        TextView tvSummary;
        ImageView ivEventImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.container);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvMonth = itemView.findViewById(R.id.tvMonth);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvSummary = itemView.findViewById(R.id.tvSummary);
            ivEventImage = itemView.findViewById(R.id.ivEventImage);
        }

        public void bind(final Event event) {
            tvDay.setText(event.getDayOfMonth());
            tvMonth.setText(event.getMonthOfYear());
            tvEventTitle.setText(event.getTitle());
            tvSummary.setText(event.getSummary());

            Glide.with(context).load(event.getPosterURL()).into(ivEventImage);

            //register the click listener on the whole container.
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //then, navigate to new activity on click.
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("event", Parcels.wrap(event));
                    context.startActivity(i);
                }
            });
        }
    }
}
