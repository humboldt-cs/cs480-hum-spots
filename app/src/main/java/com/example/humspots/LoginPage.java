package com.example.humspots;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                }
            }
        });
    }

    private boolean checkUserNameAndPassword() {
        if((etUserName.getText().toString().contentEquals("User Name")) || (etUserName.getText().toString().isEmpty())
        || (etPassword.getText().toString().contentEquals("Password")) || (etPassword.getText().toString().isEmpty())){
            Toast.makeText(this, "Username: " + etUserName.getText().toString() + " Password: " + etPassword.getText().toString(), Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }
}
