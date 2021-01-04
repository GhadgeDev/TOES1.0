package com.example.toes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    private List<GetRecruiterJobDetails> lstResponse;
    RecentPostedJobAdapter adapter;
    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_posted_job);

        RecyclerView rvRecentJobList = (RecyclerView) findViewById(R.id.rvRecentJobList);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        rvRecentJobList.setLayoutManager(new LinearLayoutManager(this));

        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("http://52.201.220.252/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit1.create(JsonPlaceHolderApi.class);


        Call<List<GetRecruiterJobDetails>> jobInfo = jsonPlaceHolderApi.getRecentJob("token " + LoginActivity.token, LoginActivity.userMeId);
        jobInfo.enqueue(new Callback<List<GetRecruiterJobDetails>>() {
            @Override
            public void onResponse(Call<List<GetRecruiterJobDetails>> call, Response<List<GetRecruiterJobDetails>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                    return;
                }
                List<GetRecruiterJobDetails> postResponse = response.body();

                System.out.println("Code :------------------- " + response.code());
                String content = "";

                lstResponse = response.body();
                adapter = new RecentPostedJobAdapter(getBaseContext(), lstResponse);
                rvRecentJobList.setAdapter(adapter);

                System.out.println("Data : _--------- " + content);
                System.out.println("id : -------------------------- " + postResponse);
            }

            @Override
            public void onFailure(Call<List<GetRecruiterJobDetails>> call, Throwable t) {
                System.out.println("Filed in RecentJOb Posted : " + t.getMessage());

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecentPostedJobActivity.this, RecruiterHomeActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recentjobmenue, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menue_profile:
                Intent Profileintent = new Intent(RecentPostedJobActivity.this, ProfileActivity.class);
                Profileintent.putExtra(Intent.EXTRA_TEXT, LoginActivity.token);
                startActivity(Profileintent);
                break;
            case R.id.menu_logout:
                LogoutDialog logoutDialog = new LogoutDialog();
                logoutDialog.show(getSupportFragmentManager(), "logout dialog");
                break;
            case R.id.menu_Notification:
                Intent notification = new Intent(RecentPostedJobActivity.this, WokerNotificationActivity.class);
                startActivity(notification);
                break;

        }

        return true;
    }

}