package com.example.humspots;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.humspots.fragments.MapFragment;
import com.example.humspots.models.Trail;

import org.parceler.Parcels;

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

        if(trail != null) {
            tvName.setText(trail.getName());
            tvLength.setText(String.valueOf(trail.getDistanceFrom()) + " miles away");
            tvTrailDescription.setText(trail.getReview());
            ivTrailImage.setImageBitmap(trail.getIcon());
        }

        ivTrailMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Map loading..", Toast.LENGTH_SHORT).show();

                //send trail information to map fragment to load into map marker for trail head.
                Bundle bundle = new Bundle();
                bundle.putString("lat", String.valueOf(trail.getPlace_lat()));
                bundle.putString("long", String.valueOf(trail.getPlace_long()));
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
