package com.example.humspots;

import android.app.Application;

import com.parse.Parse;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("humspots_cs480")
                .clientKey("QfXcq27rsB")
                .server("https://humspots.herokuapp.com/parse/").build());
    }
}
