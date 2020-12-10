package com.example.humspots.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.load.*;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.R;
import com.example.humspots.TrailDetails;
import com.example.humspots.models.Trail;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.Collections;
import java.util.List;

import okhttp3.Headers;

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
        TextView tvDistanceFrom;

        public ViewHolder(View trailView) {
            super(trailView);

            trailContainer = trailView.findViewById(R.id.trailContainer);
            ivTrailImage = trailView.findViewById(R.id.ivTrailImage);
            tvTrailName = trailView.findViewById(R.id.tvTrailName);
            tvTrailSummary = trailView.findViewById(R.id.tvTrailSummary);
            tvDistanceFrom = trailView.findViewById(R.id.tvDistanceFrom);
        }

        public void bind(final Trail trail) {
            if (!Places.isInitialized()) {
                //initialize places
                Places.initialize(getApplicationContext(), getApplicationContext().getResources().getString(R.string.places_api_key));
            }

            //create new Places Client instance
            final PlacesClient placesClient = Places.createClient(getApplicationContext());

            //specify fields, Requests for photos must always have the PHOTO_METADATAS field.
            final List<Place.Field> fields = Collections.singletonList(Place.Field.PHOTO_METADATAS);

            for (int i = 0; i < trails.size(); i++) {
                String id = trails.get(i).getPlace_id();

                //Get a place object (this uses fetchPlace(), but can be replaced by findCurrentPlace())
                final FetchPlaceRequest placeRequest = FetchPlaceRequest.newInstance(id, fields);

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

                        AsyncHttpClient client = new AsyncHttpClient();

                        String DETAILS_REQUEST_URL = String.format("https://maps.googleapis.com/maps/api/place/details/json?place_id=%s&fields=name,reviews&key=AIzaSyBaBdfVTrR03AxYbbGipw7cMv39L3uaezA",id);

                        //JSON Request
                        client.get(DETAILS_REQUEST_URL, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                Log.d(TAG, "onSuccess");
                                JSONObject outerMost = json.jsonObject;

                                try {
                                    //get the top review for the location
                                    JSONArray jsonArray = outerMost.getJSONObject("result").getJSONArray("reviews");
                                    String top = jsonArray.getJSONObject(0).getString("text");
                                    trail.setReview(top);

                                } catch (JSONException e) {
                                    trail.setReview("No reviews for this location yet!");
                                    Log.e(TAG, "hit json exception, no data for " + id, e);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.d(TAG, "onFailure");
                            }
                        });
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
            tvDistanceFrom.setText(String.format("%s miles away", String.valueOf(trail.getDistanceFrom())));
            tvTrailSummary.setText(trail.getReview());
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
