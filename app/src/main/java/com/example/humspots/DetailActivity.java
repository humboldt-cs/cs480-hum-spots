package com.example.humspots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.humspots.models.Event;


import org.parceler.Parcels;
import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    ImageView ivImage;
    ImageView ivMapTest;
    TextView tvDay;
    TextView tvMonth;
    TextView tvTitle;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivImage = findViewById(R.id.ivImage);
        ivMapTest = findViewById(R.id.ivMapTest);
        tvDay = findViewById(R.id.tvDay);
        tvMonth = findViewById(R.id.tvMonth);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);

        Event event = Parcels.unwrap(getIntent().getParcelableExtra("event"));

        Glide.with(this).load(event.getPosterURL()).into(ivImage);

        tvDay.setText(event.getDayOfMonth());
        tvMonth.setText(event.getMonthOfYear());
        tvTitle.setText(event.getTitle());
        tvDescription.setText(event.getDescription());

        ivMapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Map loading..", Toast.LENGTH_SHORT).show();
                //Intent i = new Intent(DetailActivity.this, MapsActivity.class);
                //DetailActivity.this.startActivity(i);
            }
        });
    }
}
