package com.example.humspots.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.humspots.R;
import com.example.humspots.models.Trail;

import java.util.List;

public class TrailsAdapter extends RecyclerView.Adapter<TrailsAdapter.ViewHolder> {

    Context context;

    List<Trail> trails;

    public TrailsAdapter(Context context, List<Trail> trails) {
        this.context = context;
        this.trails = trails;
    }

    //usually involves inflating a layout from XML and returning the viewholder
    @NonNull
    @Override
    public TrailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View trailView = LayoutInflater.from(context).inflate(R.layout.item_trail, parent, false);
        return new ViewHolder(trailView);
    }

    //involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull TrailsAdapter.ViewHolder holder, int position) {
        //get the event at the given position
        Trail trail = trails.get(position);
        //bind the event data into the view holder
        holder.bind(trail);
    }

    //returns the total number of items in the list
    @Override
    public int getItemCount() {
        return trails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivTrailImage;
        TextView tvTrailName;
        TextView tvTrailSummary;
        TextView tvTrailLength;

        public ViewHolder(View trailView) {
            super(trailView);
            ivTrailImage = trailView.findViewById(R.id.ivTrailImage);
            tvTrailName = trailView.findViewById(R.id.tvTrailName);
            tvTrailSummary = trailView.findViewById(R.id.tvTrailSummary);
            tvTrailLength = trailView.findViewById(R.id.tvTrailLength);
        }

        public void bind(Trail trail) {
            tvTrailName.setText(trail.getName());
            tvTrailLength.setText("(" + trail.getLength() + " mi)");
            tvTrailSummary.setText(trail.getSummary());
            Glide.with(context).load(trail.getImageURL()).into(ivTrailImage);

        }
    }
}
