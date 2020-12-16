package com.example.customkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dynatrace.android.agent.Dynatrace;
import com.dynatrace.android.agent.conf.DataCollectionLevel;
import com.dynatrace.android.agent.conf.UserPrivacyOptions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dynatrace.applyUserPrivacyOptions(UserPrivacyOptions.builder()
                .withDataCollectionLevel(DataCollectionLevel.USER_BEHAVIOR)
                .withCrashReportingOptedIn(true)
                .build());

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int result = 1;
                Dynatrace.modifyUserAction(userAction -> {
                    userAction.setActionName("click custom user action naming");
                });

                Toast.makeText(mContext, "Click Btn 1", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dynatrace.enterAction("btn2");
                Toast.makeText(mContext, "Click Btn 2", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.bNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
//                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dynatrace.identifyUser("Pixel XL Api 28 Pie");
    }

    public void restart() {
        finish();
        startActivity(getIntent());
    }
}
