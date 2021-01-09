package com.example.toes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.toes.SelectRoleActivity.userPresent;

public class RecentPostedJobActivity extends AppCompatActivity implements RecentPostedJobAdapter.OnPostedJobListener {
    private List<GetRecruiterJobDetails> lstResponse;
    RecentPostedJobAdapter adapter;
    FloatingActionButton btnAdd;
    RecyclerView rvRecentJobList;
    public static int jbId;
    public static String jbDesc;

    public static int indicator;

    private SwipeRefreshLayout refreshRecentJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_posted_job);

        rvRecentJobList =  findViewById(R.id.rvRecentJobList);
        btnAdd =  findViewById(R.id.btnAdd);
        rvRecentJobList.setLayoutManager(new LinearLayoutManager(this));

        refreshRecentJob = findViewById(R.id.recent_job_refresh);
        callToRecentJobs();
        refreshRecentJob.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callToRecentJobs();
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

    JsonPlaceHolderApi recentJob = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
    private void callToRecentJobs() {
        Call<List<GetRecruiterJobDetails>> call = recentJob.getRecentJob("token " + LoginActivity.token, LoginActivity.userMeId);
        call.enqueue(new Callback<List<GetRecruiterJobDetails>>() {
            @Override
            public void onResponse(Call<List<GetRecruiterJobDetails>> call, Response<List<GetRecruiterJobDetails>> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(RecentPostedJobActivity.this, "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                lstResponse = response.body();
                adapter = new RecentPostedJobAdapter(RecentPostedJobActivity.this,lstResponse, RecentPostedJobActivity.this);
                rvRecentJobList.setAdapter(adapter);

                refreshRecentJob.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<GetRecruiterJobDetails>> call, Throwable t) {
                Toast toast = Toast.makeText(RecentPostedJobActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
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

    @Override
    public void onJobClick(int position) {
        indicator = 1;
        Intent intent = new Intent(this, SelectWorkerActivity.class);
        intent.putExtra("selected_job_tile", lstResponse.get(position).getJobTitle());
        jbDesc = lstResponse.get(position).getJob_Description();
        jbId = lstResponse.get(position).getJob_id();
        startActivity(intent);
    }
}