package com.example.toes;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SelectWorkerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        WorkerAdapter.OnNoteListener {

    private DrawerLayout drawer;
    TextView dUserName;
    private RecyclerView workerList;
    private List<GetSpecificWorkerModel> lstWorker;
    private String mSelectedItemIs;
    private TextView mJobNameTextView;
    private WorkerAdapter adapter;

    public static int workerId;
    private static final String EXTRA_ITEM_SELECTED_IS = "recruiter.home.activity.itemSelected";

    public static Intent newIntent(Context packageContext, String selectedItem) {
        Intent intent = new Intent(packageContext, SelectWorkerActivity.class);
        intent.putExtra(EXTRA_ITEM_SELECTED_IS, selectedItem);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);

        workerList = findViewById(R.id.worker_list);
        workerList.setLayoutManager(new LinearLayoutManager(this));
        
       // lstWorker = new ArrayList<>();

        mSelectedItemIs = getIntent().getStringExtra(EXTRA_ITEM_SELECTED_IS);
        mJobNameTextView = findViewById(R.id.job_name);
        mJobNameTextView.setText(mSelectedItemIs);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        Retrofit.Builder retrofit = new Retrofit.Builder()
                .baseUrl("http://52.201.220.252/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.client(okHttpClient).build();

        JsonPlaceHolderApi workerInfoList = retrofit1.create(JsonPlaceHolderApi.class);

       Call<List<GetSpecificWorkerModel>> call = workerInfoList.getWorkerInfo("token " + LoginActivity.token, mSelectedItemIs.toLowerCase());


        call.enqueue(new Callback<List<GetSpecificWorkerModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<GetSpecificWorkerModel>> call, @NotNull Response<List<GetSpecificWorkerModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(SelectWorkerActivity.this, "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                lstWorker = response.body();
                adapter = new WorkerAdapter(SelectWorkerActivity.this,lstWorker,SelectWorkerActivity.this);
                workerList.setAdapter(adapter);


                dUserName = findViewById(R.id.nav_text_click);
                CharSequence dfname = SelectRoleActivity.textUserfName;
                CharSequence dlname = SelectRoleActivity.textUserfName;
                dUserName.setText(dfname);
            }

            @Override
            public void onFailure(Call<List<GetSpecificWorkerModel>> call, Throwable t) {
                Toast toast = Toast.makeText(SelectWorkerActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        View headerview = navigationView.getHeaderView(0);
        ImageView profileImage = headerview.findViewById(R.id.nav_image_click);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectWorkerActivity.this, ProfileActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_notifications:
                Intent intent = new Intent(this, RecruiterNotificationActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                openLogOutDialog();
                break;
            case R.id.nav_switch_roles:
                openSwitchRolesDialog();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openLogOutDialog() {
        LogoutDialog logoutDialog = new LogoutDialog();
        logoutDialog.show(getSupportFragmentManager(), "logout dialog");


    }

    public void openSwitchRolesDialog() {
        SwitchRolesDialog switchRolesDialog = new SwitchRolesDialog();
        switchRolesDialog.show(getSupportFragmentManager(), "Switch Roles");
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, ParticularWorkerActivity.class);
        intent.putExtra("worker name", lstWorker.get(position).getWorkerName());
        intent.putExtra("worker fees", lstWorker.get(position).getVisitingCharges());
        intent.putExtra("worker experience", lstWorker.get(position).getExperience());
        workerId = lstWorker.get(position).getWorkerId();
        startActivity(intent);
    }
}