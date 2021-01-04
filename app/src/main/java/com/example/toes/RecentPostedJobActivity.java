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

       System.out.println("RID___-------------"+SplashScreenActivity.rId);


        Call<GetRecruiterJobDetails> call1 = jsonPlaceHolderApi.getRecentJob("token "+LoginActivity.token,SplashScreenActivity.rId);
        call1.enqueue(new Callback<GetRecruiterJobDetails>() {
            @Override
            public void onResponse(Call<GetRecruiterJobDetails> call, Response<GetRecruiterJobDetails> response) {
                if(!response.isSuccessful()){
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                }
             //   List<String> list = response.body().getJobDescription();
                System.out.println("------------------------------ "+response.code());
                List<GetRecruiterJobDetails> lis = (List<GetRecruiterJobDetails>) response.body();

                String jd1;
                List<String> desc = null;
                for (GetRecruiterJobDetails post : lis){
                    jd1 = post.getJobDescription();
                    desc.add(jd1);
                }
                // String[] jobs = {"painting a wall", "Repairing taps", "Developing website", "Electric Work", "painting a wall", "Repairing taps", "Developing website", "Electric Work", "painting a wall", "Repairing taps", "Developing website", "Electric Work"};
                rvRecentJobList.setAdapter(new RecentPostedJobAdapter(desc));

            }

            @Override
            public void onFailure(Call<GetRecruiterJobDetails> call, Throwable t) {

            }
        });
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