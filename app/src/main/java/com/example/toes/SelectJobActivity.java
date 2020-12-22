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
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SelectJobActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private List<RecruiterListModel> lstRecruiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_job);

       // Toolbar toolbar = findViewById(R.id.worker_toolbar);
       // setSupportActionBar(toolbar);
        mDrawer = findViewById(R.id.worker_drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_notifications:
                        Intent intent = new Intent(SelectJobActivity.this,WokerNotificationActivity.class);
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

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        RecyclerView myRecyclerView = findViewById(R.id.recruiter_list);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lstRecruiter = new ArrayList<>();
        lstRecruiter.add(new RecruiterListModel("Aadesh Sighwan","Plumber"));
        lstRecruiter.add(new RecruiterListModel("Aditya Mali","Carpenter"));
        lstRecruiter.add(new RecruiterListModel("Damodar Dikonda","Electrician"));
        lstRecruiter.add(new RecruiterListModel("Tanmay Mahamulkar","Plumber"));
        myRecyclerView.setAdapter(new RecruiterAdapter(this,lstRecruiter));
    }
    public void openLogOutDialog(){
        LogoutDialog logoutDialog = new LogoutDialog();
        logoutDialog.show(getSupportFragmentManager(),"logout dialog");
    }
    public void openSwitchRolesDialog(){
        SwitchRolesDialog switchRolesDialog = new SwitchRolesDialog();
        switchRolesDialog.show(getSupportFragmentManager(),"Switch roles");
    }
    @Override
    public void onBackPressed(){
        if(mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}