package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SPLASH_TIME = 3000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mySuperIntent = new Intent(SplashScreenActivity.this, SelectLanguageActivity.class);
                startActivity(mySuperIntent);
                finish();

            }
        }, SPLASH_TIME);

        JsonPlaceHolderApi jsonPlaceHolderApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);


    }
}