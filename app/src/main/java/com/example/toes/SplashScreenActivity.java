package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    static boolean IsLoggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SPLASH_TIME = 3000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!IsLoggedIn){
                    Intent mySuperIntent = new Intent(SplashScreenActivity.this, SelectLanguageActivity.class);
                    startActivity(mySuperIntent);
                    finish();
                }else {
                    Intent mySuperIntent = new Intent(SplashScreenActivity.this, SelectRoleActivity.class);
                    startActivity(mySuperIntent);

                }


            }
        }, SPLASH_TIME);

    }
}