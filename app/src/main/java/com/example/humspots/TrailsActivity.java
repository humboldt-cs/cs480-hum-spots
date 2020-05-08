package com.example.humspots;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.humspots.fragments.EventsFragment;
import com.example.humspots.fragments.MapFragment;
import com.example.humspots.fragments.TrailsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TrailsActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.action_events:
                        fragment = new EventsFragment();
                        break;
                    case R.id.action_trails:
                        fragment = new TrailsFragment();
                        break;
                    case R.id.action_map:
                    default:
                        Toast.makeText(TrailsActivity.this, "Map Activity", Toast.LENGTH_SHORT).show();
                        fragment = new MapFragment();
                        break;
                }

                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
    }
}
