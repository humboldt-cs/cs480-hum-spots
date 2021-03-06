package com.example.humspots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Event;
import com.example.humspots.fragments.EventsFragment;
import com.example.humspots.fragments.MapFragment;
import com.example.humspots.fragments.SettingsFragment;
import com.example.humspots.fragments.TrailsFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.amplifyframework.datastore.generated.model.Event;

import okhttp3.Headers;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView bottomNavigationView;
    private FusedLocationProviderClient client;

    String TAG = "Main Activity";

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
            currentLocLong = "-124.08641";
            currentLocLat = "40.86849";
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

        //defaults the main menu to Events Fragement instead of the user having to choose something.
        fragmentManager.beginTransaction().replace(R.id.flContainer, new EventsFragment()).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                Bundle bbundle = new Bundle();
                bbundle.putString("lat", currentLocLat);
                bbundle.putString("long", currentLocLong);
                bbundle.putString("name", currentLocName);
                switch (menuItem.getItemId()) {
                    case R.id.action_map:

                        fragment = new MapFragment();
                        fragment.setArguments(bbundle);
                        break;
                    case R.id.action_trails:
                        Bundle bundle = new Bundle();
                        bundle.putString("lat", currentLocLat);
                        bundle.putString("long", currentLocLong);
                        bundle.putString("name", currentLocName);
                        fragment = new TrailsFragment();
                        fragment.setArguments(bundle);
                        break;
                    case R.id.action_settings:
                        fragment = new SettingsFragment();
                        break;
                    case R.id.action_events:
                    default:
                        fragment = new EventsFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

    }

    //request permission for location
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
