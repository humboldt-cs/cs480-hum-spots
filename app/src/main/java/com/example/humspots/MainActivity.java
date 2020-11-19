package com.example.humspots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
//import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.Event;
import com.example.humspots.fragments.EventsFragment;
import com.example.humspots.fragments.MapFragment;
import com.example.humspots.fragments.SettingsFragment;
import com.example.humspots.fragments.TrailsFragment;
import com.example.humspots.models.Trail;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.amplifyframework.datastore.generated.model.Event;

import okhttp3.Headers;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

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



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_map:
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

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        /*Event item = Event.builder()
                .date("11/03/2020")
                .description("Build an Android application using Amplify")
                .posterUrl("")
                .id("1")
                .summary("Building a new thing for AWS AppSync")
                .title("AppSync Non-sense")
                .venueId("123")
                .build();
*/

    }

    //request permission for location
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION}, 1);
    }
}
