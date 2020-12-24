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
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;


public class SelectWorkerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private String mSelectedItemIs;
    private TextView mJobNameTextView;

    private static final String EXTRA_ITEM_SELECTED_IS = "recruiter.home.activity.itemSelected";

    public static Intent newIntent(Context packageContext, String selectedItem){
        Intent intent = new Intent(packageContext, SelectWorkerActivity.class);
        intent.putExtra(EXTRA_ITEM_SELECTED_IS, selectedItem);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_worker);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
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

        mSelectedItemIs = getIntent().getStringExtra(EXTRA_ITEM_SELECTED_IS);

     mJobNameTextView = findViewById(R.id.job_name);
        mJobNameTextView.setText(mSelectedItemIs);

        RecyclerView workerList = findViewById(R.id.worker_list);
        workerList.setLayoutManager(new LinearLayoutManager(this));

        String[] wName = {"Aditya Mali","Damodar Dikonda","Tanmay Mahamulkar","Aadesh Sighwan",
                "Devendra Ghadge","Megha Gurav","Swapnil Bansode","Niket Jadhav",
                "Vaibhav Shinde","Aishwarya Shinde", "Gauri Raskar", "Aditya Jambulkar","Ruturaj Varne"};
        String[] vCharges = {"10","20","30","40","50","60","70","80","90","100","110", "120", "130"};
       workerList.setAdapter(new WorkerAdapter(wName, vCharges));
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_notifications:
                Intent intent = new Intent(this,RecruiterNotificationActivity.class);
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
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

}