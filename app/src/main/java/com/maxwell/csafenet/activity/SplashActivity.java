package com.maxwell.csafenet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maxwell.csafenet.MainActivity;
import com.maxwell.csafenet.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2500);
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    finish();
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
