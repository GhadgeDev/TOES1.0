package com.example.toes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectJobActivity extends AppCompatActivity implements RecruiterAdapter.OnRecruiterListener {
    TextView dUserName;
    private DrawerLayout mDrawer;
    private List<GetSpecificRecruiterModel> lstRecruiter;
    RecyclerView myRecyclerView;
    RecruiterAdapter adapter;
    public static int recruiterId;      //WrecruiterID --> For my own reference

    public static int WjbDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_job);

        myRecyclerView = findViewById(R.id.recruiter_list);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toolbar toolbar = findViewById(R.id.worker_toolbar);
        setSupportActionBar(toolbar);

        //Call to Api
        JsonPlaceHolderApi recruiterInfoList = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
        Call<List<GetSpecificRecruiterModel>> call = recruiterInfoList.getRecruiterInfo("token " + LoginActivity.token, LoginActivity.userMeId);
        call.enqueue(new Callback<List<GetSpecificRecruiterModel>>() {
            @Override
            public void onResponse(Call<List<GetSpecificRecruiterModel>> call, Response<List<GetSpecificRecruiterModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(SelectJobActivity.this, "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                lstRecruiter = response.body();
                System.out.println("Whole List Of recruiter " + lstRecruiter);
                adapter = new RecruiterAdapter(SelectJobActivity.this, lstRecruiter, SelectJobActivity.this);
                myRecyclerView.setAdapter(adapter);

                dUserName = findViewById(R.id.nav_text_click);
                String dfname = SelectRoleActivity.textUserfName;
                String dlname = SelectRoleActivity.textUserlName;
                dUserName.setText(dfname + " " + dlname);
            }

            @Override
            public void onFailure(Call<List<GetSpecificRecruiterModel>> call, Throwable t) {
                Toast toast = Toast.makeText(SelectJobActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });


        mDrawer = findViewById(R.id.worker_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_notifications:
                        Intent intent = new Intent(SelectJobActivity.this, WokerNotificationActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        openLogOutDialog();
                        break;
                    case R.id.nav_switch_roles:
                        openSwitchRolesDialog();
                        break;
                }
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        View headerview = navigationView.getHeaderView(0);
        ImageView profileImage = headerview.findViewById(R.id.nav_image_click);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectJobActivity.this, ProfileActivity.class);
                startActivity(intent);
                mDrawer.closeDrawer(GravityCompat.START);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    public void openLogOutDialog() {
        LogoutDialog logoutDialog = new LogoutDialog();
        logoutDialog.show(getSupportFragmentManager(), "logout dialog");
    }

    public void openSwitchRolesDialog() {
        SwitchRolesDialog switchRolesDialog = new SwitchRolesDialog();
        switchRolesDialog.show(getSupportFragmentManager(), "Switch roles");
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    JsonPlaceHolderApi recruiterJbInfo = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);

    @Override
    public void onRecruiterClick(int position) {
        Intent intent = new Intent(SelectJobActivity.this, ParticularRecruiterActivity.class);
        String tv_fname = lstRecruiter.get(position).getRecruiterFname();
        String tv_lname = lstRecruiter.get(position).getRecruiterLname();
        recruiterId = lstRecruiter.get(position).getRecruiterId();
        WjbDetail = lstRecruiter.get(position).getJobId();

        Call<List<GetRecruiterJobDetails>> call = recruiterJbInfo.getRecruiterJobIfo("token " + LoginActivity.token, recruiterId);
        call.enqueue(new Callback<List<GetRecruiterJobDetails>>() {
            @Override
            public void onResponse(Call<List<GetRecruiterJobDetails>> call, Response<List<GetRecruiterJobDetails>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(SelectJobActivity.this, "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                intent.putExtra("recruiter name", tv_fname + " " + tv_lname);
                intent.putExtra("recruiter jbDesc", response.body().get(position).getJobDescription());
                intent.putExtra("recruiter_add", response.body().get(position).getAddress());
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<GetRecruiterJobDetails>> call, Throwable t) {
                Toast toast = Toast.makeText(SelectJobActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }
}