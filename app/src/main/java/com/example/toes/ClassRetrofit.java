package com.example.toes;

import java.security.PublicKey;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassRetrofit {

    public static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        if (retrofit == null) {
            //connecting to base url
            retrofit = new Retrofit.Builder().
                    baseUrl("http://65.1.2.12/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient).build();
        }
        return retrofit;
    }
}
