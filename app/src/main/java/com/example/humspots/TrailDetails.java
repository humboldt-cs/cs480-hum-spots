package com.example.humspots;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
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

        Glide.with(this).load(trail.getImageURL()).into(ivTrailImage);

        tvLength.setText(trail.getLength() + " mi");
        tvName.setText(trail.getName());
        tvTrailDescription.setText(trail.getSummary());

        ivTrailMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Map loading..", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("lat", trail.getLatitude());
                bundle.putString("long", trail.getLongitude());
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
