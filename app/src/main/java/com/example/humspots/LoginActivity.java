package com.example.humspots;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText password_et;
    private EditText userName_et;
    private Button create_btn;
    private Button login_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        password_et = findViewById(R.id.password_et);
        userName_et = findViewById(R.id.userName_et);
        create_btn = findViewById(R.id.create_btn);
        login_btn =findViewById(R.id.login_btn);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick create button");
                goCreateActivity();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Login button");
                String userName = userName_et.getText().toString();
                String password = password_et.getText().toString();
                LoginUser(userName, password);
            }
        });
    }

    private void LoginUser(String userName, String password) {
        Log.i(TAG, "User: " + userName);
        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    Toast.makeText(LoginActivity.this, "User signin successfull", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "User signin successfull");
                    goMainActivity();
                }
                else{
                    Log.e(TAG, "User signin unsuccessfull", e);
                    return;
                }
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void goCreateActivity() {
        Intent i = new Intent(this, CreateNewUserActivity.class);
        startActivity(i);
        finish();
    }


}
