package com.example.humspots;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.humspots.fragments.MapFragment;
import com.example.humspots.models.Trail;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.parceler.Parcels;

import java.util.Arrays;

public class TrailDetails extends AppCompatActivity {

    ImageView ivTrailImage;
    ImageView ivTrailMap;
    TextView tvName;
    TextView tvLength;
    TextView tvTrailDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_details);

        ivTrailImage = findViewById(R.id.ivTrailImage);
        ivTrailMap = findViewById(R.id.ivTrailMap);
        tvLength = findViewById(R.id.tvLength);
        tvName = findViewById(R.id.tvName);
        tvTrailDescription = findViewById(R.id.tvTrailDescription);

        final Trail trail = Parcels.unwrap(getIntent().getParcelableExtra("trail"));

        Glide.with(this).load(trail.getIcon()).into(ivTrailImage);

        //tvLength.setText(trail.getLength() + " mi");
        tvName.setText(trail.getName());
        //tvTrailDescription.setText(trail.getReview());

        ivTrailMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Map loading..", Toast.LENGTH_SHORT).show();

                //send trail information to map fragment to load into map marker for trail head.
                Bundle bundle = new Bundle();
                bundle.putDouble("lat", trail.getPlace_lat());
                bundle.putDouble("long", trail.getPlace_long());
                bundle.putString("name", trail.getName());

                // set Fragment Arguments
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.tFrame, fragment).commit();
            }
        });
    }
}
