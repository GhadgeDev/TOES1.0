package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.toes.SelectRoleActivity.userPresent;

public class RecentPostedJobActivity extends AppCompatActivity {
    private List<GetSpecificRecruiterModel> lstRecruiter;
    private List<GetSpecificRecruiterModel> jobD;
    FloatingActionButton btnAdd;
    int recId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_posted_job);

        RecyclerView rvRecentJobList = (RecyclerView)findViewById(R.id.rvRecentJobList);
        btnAdd = (FloatingActionButton)findViewById(R.id.btnAdd);
        rvRecentJobList.setLayoutManager(new LinearLayoutManager(this));

        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("http://52.201.220.252/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit1.create(JsonPlaceHolderApi.class);




      // Call<GetRecruiterJobInfo> jobInfo = jsonPlaceHolderApi.getRecentJob(" "+LoginActivity.token, LoginActivity.userMeId);



        String[] jobs = {"painting a wall", "Repairing taps", "Developing website", "Electric Work", "painting a wall", "Repairing taps", "Developing website", "Electric Work", "painting a wall", "Repairing taps", "Developing website", "Electric Work"};
        rvRecentJobList.setAdapter(new RecentPostedJobAdapter(jobs));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecentPostedJobActivity.this,RecruiterHomeActivity.class);
                startActivity(intent);
            }
        });








    }
}