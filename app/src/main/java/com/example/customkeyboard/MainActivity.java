package com.example.customkeyboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dynatrace.android.agent.Dynatrace;
import com.dynatrace.android.agent.conf.DataCollectionLevel;
import com.dynatrace.android.agent.conf.UserPrivacyOptions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Context mContext = MainActivity.this;
    private Button btn1;
    private Button btn2;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btnNext = findViewById(R.id.bNext);

        // init dt
        Dynatrace.applyUserPrivacyOptions(UserPrivacyOptions.builder()
                .withDataCollectionLevel(DataCollectionLevel.USER_BEHAVIOR)
                .withCrashReportingOptedIn(true)
                .build());


        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                showBtn1();
                break;

            case R.id.button2:
                showBtn2();
                break;

            case R.id.bNext:
                showbNext();
                break;

        }
    }

    private void showBtn1(){
        int result = 1;
        Dynatrace.modifyUserAction(userAction -> {
            userAction.setActionName("custom action naming : btn 1");
        });

        Toast.makeText(mContext, "Click Btn 1", Toast.LENGTH_SHORT).show();
    }

    private void showBtn2(){
        Dynatrace.enterAction("enter action : btn 2");
        Toast.makeText(mContext, "Click Btn 2", Toast.LENGTH_SHORT).show();
    }

    private void showbNext(){
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
        //finish();
    }



    @Override
    protected void onResume() {
        super.onResume();
        Dynatrace.identifyUser("Development Device");
    }

    public void restart() {
        finish();
        startActivity(getIntent());
    }


}
