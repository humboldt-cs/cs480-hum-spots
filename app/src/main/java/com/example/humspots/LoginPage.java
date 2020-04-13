package com.example.humspots;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginPage extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //linking the activity to this create
        btnLogin = findViewById(R.id.btnLogin);
        btnCreate = findViewById(R.id.btnCreate);
        etPassword = findViewById(R.id.etPassword);
        etUserName = findViewById(R.id.etUserName);

        //setting on clicks
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUserNameAndPassword()) {
                    Log.i(TAG, "Logging in user: " + etUserName.getText().toString() + " Password: " + etPassword.getText().toString());
                    String UserName = etUserName.getText().toString();
                    String Password = etPassword.getText().toString();
                    LoginUser(UserName, Password);
                }
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked create new user");
                goToCreate();
            }
        });
    }

    private void goToCreate() {
        Log.i(TAG, "Going to create new user!");
        Intent i = new Intent(this, CreateNewUser.class);
        startActivity(i);
        finish();
    }

    private void LoginUser(String userName, String password){
        ParseUser.logInInBackground(userName, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e == null){
                    Toast.makeText(LoginPage.this, "User Login Successful", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "User sign in successful");
                    goMainActivity();
                }
                else{
                    Log.e(TAG, "User sign in Unsuccessful", e);
                    return;
                }
            }
        });
    }

    private void goMainActivity() {
        Log.i(TAG, "Going to main!");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private boolean checkUserNameAndPassword() {
        if((etUserName.getText().toString().contentEquals("User Name")) || (etUserName.getText().toString().isEmpty())
        || (etPassword.getText().toString().contentEquals("Password")) || (etPassword.getText().toString().isEmpty())){
            Toast.makeText(this, "Username: " + etUserName.getText().toString(), Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }
}
