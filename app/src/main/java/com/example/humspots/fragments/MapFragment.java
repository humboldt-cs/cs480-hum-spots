package com.example.humspots.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.humspots.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    MapView mMapView;
    View mView;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_map, container, false);

        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.mapMaster);

        if(mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mMap = googleMap;
        String name = getArguments().getString("name");

        if(name != null){
            double lat = Double.parseDouble(getArguments().getString("lat"));
            double longi = Double.parseDouble(getArguments().getString("long"));
            LatLng location = new LatLng(lat, longi);

            mMap.addMarker((new MarkerOptions().position(location).title(name)));
            CameraPosition thing = CameraPosition.builder().target(location).zoom(18).tilt(15).bearing(0).build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(thing));
        }
        else{
            LatLng defaultLocation = new LatLng(40.8747, -124.0789);
            mMap.addMarker((new MarkerOptions().position(defaultLocation).title("No Location Given")));
            CameraPosition thing = CameraPosition.builder().target(defaultLocation).zoom(18).tilt(15).bearing(0).build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(thing));
        }
    }
}
