package com.example.customkeyboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dynatrace.android.agent.Dynatrace;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    private final int iTIMER_INTERVAL = 1000;
    private final int lTIMER_MAX = 10000;

    private CountDownTimer countDownTimer;
    private boolean isForeground = true;
    private boolean isTimeout = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.bBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        findViewById(R.id.bTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SecondActivity.this, "Btn Test", Toast.LENGTH_SHORT).show();
            }
        });
        countDownTimer = new MyCountDownTimer(lTIMER_MAX, iTIMER_INTERVAL);
        countDownTimer.start();
    }

    private void logout() {
        Dynatrace.enterAction("Logout").leaveAction();
        countDownTimer.cancel();
        finish();
//        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        isForeground = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        isForeground = true;
        if(isTimeout) {
            logout();
        }
    }


    public class MyCountDownTimer extends CountDownTimer {
        MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
            Log.d(TAG, "MyCountDownTimer init");
        }

        @Override
        public void onFinish() {
            //DO WHATEVER YOU WANT HERE
            Dynatrace.endVisit();
            if(isForeground) {
                Log.d(TAG, "MyCountDownTimer onFinish foreground");
                logout();
            }
            else {
                Log.d(TAG, "MyCountDownTimer onFinish background");
                isTimeout = true;
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            Log.d(TAG, "MyCountDownTimer onTick");
        }
    }
}
