package com.example.humspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Event;
import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.humspots.fragments.MapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "DetailActivity";

    //String venName;

    static String subscriptionKey = "ArW0CmIBq00h3BNC79eevgIXL_HuNwBXpb2Z1E_xd_9w7kPioxAt9UQRusd5zZVn";
    static String host = "https://api.cognitive.microsoft.com";
    static String path = "/bing/v7.0/images/search";

    ImageView ivImage;
    ImageView ivMapTest;
    TextView tvDay;
    TextView tvMonth;
    TextView tvTitle;
    TextView tvLinks;
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
        tvLinks = findViewById(R.id.tvLinks);
        tvDescription = findViewById(R.id.tvDescription);

        /*final EventModel event = Parcels.unwrap(getIntent().getParcelableExtra("event"));

        Glide.with(this).load(event.getPosterURL()).into(ivImage);*/
        Bundle bundle = getIntent().getExtras();

        String day = bundle.getString("Date").substring(5,7);
        String month = bundle.getString("Date").substring(0,3);

        String extraInfo = (bundle.getString("ExtraInfo") == "" ||
                bundle.getString("ExtraInfo") == " "||
                bundle.getString("ExtraInfo") == null ||
                bundle.getString("ExtraInfo").isEmpty()) ? "" : "\nContact Info:\n" + bundle.getString("ExtraInfo");

        tvDay.setText(day);
        tvMonth.setText(month);
        tvTitle.setText(bundle.getString("Title"));
        tvDescription.setText(bundle.getString("Description"));
        tvLinks.setText(bundle.getString("PostURL") + "\n" + extraInfo);

        /*
        // construct the search request URL (in the form of endpoint + query string)
        URL url = null;
        try {
            url = new URL(host + path + "?q=" +  URLEncoder.encode(bundle.getString("Venue"), "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        HttpsURLConnection connection = null;
        try {
            connection = (HttpsURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("Ocp-Apim-Subscription-Key", subscriptionKey);

        // receive JSON body
        InputStream stream = null;
        try {
            stream = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = new Scanner(stream).useDelimiter("\\A").next();
        // construct result object for return
        SearchResponse results = new SearchResults(new HashMap<String, String>(), response);
        */


        /*ivMapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Map loading..", Toast.LENGTH_LONG).show();

                Bundle bundle = new Bundle();
                bundle.putString("lat", venLatitude);
                bundle.putString("long", venLongitude);
                bundle.putString("name", venName);

                // set Fragment Arguments
                Fragment fragment = new MapFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.eFrame, fragment).commit();
            }
        });*/
    }
}
