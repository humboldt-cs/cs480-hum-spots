package com.example.humspots;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.humspots.models.Event;
import com.example.humspots.models.Trail;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    //final Event event = Parcels.unwrap(getIntent().getParcelableExtra("event"));
    //final Trail trail = Parcels.unwrap(getIntent().getParcelableExtra("trail"));

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng Luis = new LatLng(40.89911, -124.08345);
        //LatLng myTrail = new LatLng(Double.parseDouble(trail.getLatitude()), Double.parseDouble(trail.getLongitude()));

        mMap.addMarker(new MarkerOptions().position(Luis).title("Luis Home"));
        //mMap.addMarker(new MarkerOptions().position(myTrail).title(trail.getName()));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(Luis));

    }
}
