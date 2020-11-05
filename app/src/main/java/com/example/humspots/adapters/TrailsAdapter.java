package com.example.humspots.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.parse.Parse.getApplicationContext;

public class TrailsAdapter extends RecyclerView.Adapter<TrailsAdapter.ViewHolder> {
    String TAG = "TrailsAdapter";
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
            if (!Place.isInitialized()) {
                //initialize places
                Places.initialize(getApplicationContext(), getApplicationContext().getResources().getString(R.string.places_api_key));
            }

            //create new Places Client instance
            final PlacesClient placesClient = Places.createClient(getApplicationContext());

            //specify fields, Requests for photos must always have the PHOTO_METADATAS field.
            final List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);

            for (int i = 0; i < trails.size(); i++) {
                //Get a place object (this uses fetchPlace(), but can be replaced by findCurrentPlace())
                final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(trails.get(i).getPlace_id(), fields);

                int finalI = i;
                placesClient.fetchPlace(placeRequest).addOnSuccessListener((response) -> {
                    final Place place = response.getPlace();
                    //get photo metadata.
                    final List<PhotoMetadata> metadata = place.getPhotoMetadatas();
                    if (metadata == null || metadata.isEmpty()) {
                        Log.w(TAG, "No photo metadata.");
                        return;
                    }

                    final PhotoMetadata photoMetadata = metadata.get(0);

                    //get the attribution text
                    final String attributions = photoMetadata.getAttributions();

                    //create a FetchPhotoRequest
                    final FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(photoMetadata)
                            .setMaxWidth(400)
                            .setMaxHeight(250)
                            .build();
                    placesClient.fetchPhoto(photoRequest).addOnSuccessListener((fetchPhotoResponse) -> {
                        Bitmap bitmap = fetchPhotoResponse.getBitmap();
                        //ivTrailImage.setImageBitmap(bitmap);
                        trails.get(finalI).setIcon(bitmap);
                    }).addOnFailureListener((exception) -> {
                        if (exception instanceof ApiException) {
                            final ApiException apiException = (ApiException) exception;
                            Log.e(TAG, "Place not found:" + exception.getMessage());
                            final int statusCode = apiException.getStatusCode();
                        }
                    });
                });
            }
                tvTrailName.setText(trail.getName());
                //tvTrailLength.setText("(" + trail.getLength() + " mi)");
                //tvTrailSummary.setText(trail.getReview());

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.unavailableimage)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH);
                //Glide.with(context).load(trail.getIcon()).apply(options).into(ivTrailImage);
                ivTrailImage.setImageBitmap(trail.getIcon());

                //register the click listener on the whole container.
                trailContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //navigate to new activity on click.
                        Intent i = new Intent(context, TrailDetails.class);
                        i.putExtra("trail", Parcels.wrap(trail));
                        context.startActivity(i);
                    }
                });
        }
    }
}