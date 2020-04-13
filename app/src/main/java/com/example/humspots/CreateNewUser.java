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
import com.parse.SignUpCallback;

public class CreateNewUser extends AppCompatActivity {
    public static final String TAG = "CreateNewUser";
    private EditText etUserNameCreate;
    private EditText etPasswordCreate;
    private EditText etEmailCreate;
    private EditText etConfirmPassword;
    private Button btnCreateUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_user);

        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etUserNameCreate = findViewById(R.id.etUserNameCreate);
        etPasswordCreate = findViewById(R.id.etPasswordCreate);
        etEmailCreate = findViewById(R.id.etEmailCreate);
        btnCreateUser = findViewById(R.id.btnCreateUser);

        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEditTexts()){
                    String UserName = etUserNameCreate.getText().toString();
                    String Password = etPasswordCreate.getText().toString();
                    String Email = etEmailCreate.getText().toString();
                    CreateNew(UserName,Password,Email);
                }
            }
        });
    }

    private void CreateNew(String userName, String password, String email) {
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null) {
                    Toast.makeText(CreateNewUser.this, "User Login Successful", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "User create successful");
                    goMainActivity();
                } else {
                    Log.e(TAG, "User create Unsuccessful", e);
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


    private boolean checkEditTexts() {
        if((etUserNameCreate.getText().toString().contentEquals("User Name")) || (etUserNameCreate.getText().toString().isEmpty())
                || (etPasswordCreate.getText().toString().contentEquals("Password")) || (etPasswordCreate.getText().toString().isEmpty())
                || (etConfirmPassword.getText().toString().contentEquals("Password")) || (etConfirmPassword.getText().toString().isEmpty())
                || (etEmailCreate.getText().toString().contentEquals("Email")) || (etEmailCreate.getText().toString().isEmpty())
        ){
            Toast.makeText(this, "Username: " + etUserNameCreate.getText().toString(), Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }
}
