package com.example.humspots.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.humspots.LoginPage;
import com.example.humspots.R;
import com.google.firebase.auth.FirebaseAuth;


public class SettingsFragment extends Fragment {
    public static final String TAG = "SettingsFragment";
    Button btnSignOut;
    TextView tvUserName;
    public SettingsFragment () {
        //empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        btnSignOut = view.findViewById(R.id.btnSignOut);
        tvUserName = view.findViewById(R.id.tvUserName);

        tvUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Sign Out clicked");
                signOut();
            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        goToLogin();
    }

    private void goToLogin() {
        Intent i = new Intent(getContext(), LoginPage.class);
        startActivity(i);
    }
}
