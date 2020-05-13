package com.example.humspots.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.humspots.R;
import com.example.humspots.TrailDetails;
import com.example.humspots.models.Trail;

import org.parceler.Parcels;

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

        RelativeLayout trailContainer;
        ImageView ivTrailImage;
        TextView tvTrailName;
        TextView tvTrailSummary;
        TextView tvTrailLength;

        public ViewHolder(View trailView) {
            super(trailView);

            trailContainer = trailView.findViewById(R.id.trailContainer);
            ivTrailImage = trailView.findViewById(R.id.ivTrailImage);
            tvTrailName = trailView.findViewById(R.id.tvTrailName);
            tvTrailSummary = trailView.findViewById(R.id.tvTrailSummary);
            tvTrailLength = trailView.findViewById(R.id.tvTrailLength);
        }

        public void bind(final Trail trail) {
            tvTrailName.setText(trail.getName());
            tvTrailLength.setText("(" + trail.getLength() + " mi)");
            tvTrailSummary.setText(trail.getSummary());

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.unavailableimage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);
            Glide.with(context).load(trail.getImageURL()).apply(options).into(ivTrailImage);

            //register the click listener on the whole container.
            trailContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //then, navigate to new activity on click.
                    Intent i = new Intent(context, TrailDetails.class);
                    i.putExtra("trail", Parcels.wrap(trail));
                    context.startActivity(i);
                }
            });

        }
    }
}
