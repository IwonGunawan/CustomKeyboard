package com.example.customkeyboard;

import android.app.Application;

import com.dynatrace.android.agent.Dynatrace;

public class IdentifyActivity extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Dynatrace.identifyUser("Pixel XL Api 28 Pie");
    }
}
