package com.example.bccprojecttest3;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {

    private static int splashTimeOut = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LoginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(LoginIntent);
                finish();
            }
        },splashTimeOut);
    }
}
