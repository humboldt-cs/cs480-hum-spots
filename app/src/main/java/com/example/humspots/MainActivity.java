package com.example.humspots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.humspots.fragments.EventsFragment;
import com.example.humspots.fragments.MapFragment;
import com.example.humspots.fragments.SettingsFragment;
import com.example.humspots.fragments.TrailsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FusedLocationProviderClient client;
    String currentLocLat;
    String currentLocLong;
    String test;
    String currentLocName = "Current Location";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        requestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);

        //permission not given.
        if(ActivityCompat.checkSelfPermission(MainActivity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            currentLocLong = "-124.0789";
            currentLocLat = "40.8747";
        }

        client.getLastLocation().addOnSuccessListener(MainActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null) {
                    test = location.toString();
                    currentLocLat = String.valueOf(location.getLatitude());
                    currentLocLong = String.valueOf(location.getLongitude());
                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_map:
                        Toast.makeText(MainActivity.this, "Map Loading...", Toast.LENGTH_SHORT).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("lat", currentLocLat);
                        bundle.putString("long", currentLocLong);
                        bundle.putString("name", currentLocName);

                        fragment = new MapFragment();
                        fragment.setArguments(bundle);
                        break;
                    case R.id.action_trails:
                        fragment = new TrailsFragment();
                        break;
                    case R.id.action_settings:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.action_events:
                    default:
                        fragment = new EventsFragment();
                        break;
                }

                Log.i("LOOO", "coordinates: (" + currentLocLat + ", " + currentLocLong + ")");
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
