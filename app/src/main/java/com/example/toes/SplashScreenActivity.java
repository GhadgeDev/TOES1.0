package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static com.example.toes.LoginActivity.token;
import static com.example.toes.LoginActivity.tokenDetail;

public class SplashScreenActivity extends AppCompatActivity {

    static boolean IsLoggedIn = false;
    static int rId;
    // static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int SPLASH_TIME = 2500;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.sp = getSharedPreferences("login",MODE_PRIVATE);
                LoginActivity.token = LoginActivity.sp.getString("token","");
                LoginActivity.userMeId = LoginActivity.sp.getInt("uid",0);

                System.out.println("Token : ----------------------- "+token);
                if(LoginActivity.sp.getBoolean("logged",false)){
                    Intent intent1 = new Intent(SplashScreenActivity.this, SelectRoleActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }else{
                    Intent intent1 = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent1);
                }


            }
        }, SPLASH_TIME);

        JsonPlaceHolderApi jsonPlaceHolderApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);


    }

}