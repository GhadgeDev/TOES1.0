package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private Button mLogoutButton;
    private Button mEditButton;

    private SwipeRefreshLayout refreshJobs;

    TextView txtName, txtPhone, txtGender, txtaadhar, txtaddr, txtProfession;
    Button btnEditJob;
    ImageView profile_image;
    Button btnJob1, btnJob2, btnJob3, btnGo;
    static boolean updateJob = false;
    static int updateId;
    String token = "";
    static ArrayList<String> jobs = new ArrayList<>();

    static boolean btn1 = false, btn2 = false, btn3 = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (TextView) findViewById(R.id.txtprofile_name);
        txtPhone = (TextView) findViewById(R.id.txtprofile_contact);
        txtGender = (TextView) findViewById(R.id.txtprofile_gender);
        txtaadhar = (TextView) findViewById(R.id.txtprofile_adhar);
        txtaddr = (TextView) findViewById(R.id.txtprofile_address);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        mLogoutButton = findViewById(R.id.btnlogout);
        mEditButton = findViewById(R.id.edit_profile_btn);

        btnJob1 = (Button) findViewById(R.id.btnJob1);
        btnJob2 = (Button) findViewById(R.id.btnJob2);
        btnJob3 = (Button) findViewById(R.id.btnJob3);
        btnEditJob = (Button) findViewById(R.id.btnEditJob);
        txtProfession = (TextView) findViewById(R.id.txtNoProfession);

        refreshJobs = findViewById(R.id.refresh_jobs);

        callToRefreshJobs();
        refreshJobs.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callToRefreshJobs();
            }
        });

    /*    Intent intent1 = getIntent();
        token = "Token " + LoginActivity.token;*/

        JsonPlaceHolderApi jsonPlaceHolderApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
        Call<Post> call = jsonPlaceHolderApi.getPost("token " + LoginActivity.token);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                    return;
                }
                Post postResponse = response.body();
                txtName.setText(postResponse.getFirst_name() + " " + postResponse.getLast_name());
                txtPhone.setText(postResponse.getPhone());
                txtGender.setText(postResponse.getGender());
                txtaadhar.setText("Aadhar Card No : " + postResponse.getAadhar_no());
                txtaddr.setText("Address : " + postResponse.getAddress());

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("Filed in ProfileActivity : " + t.getMessage());

            }
        });

        btnEditJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call<WorkerJobDetails> deleteProfession1 = jsonPlaceHolderApi.deleteProfession1(token,updateId);
                // updateJob = true;
                DeleteDilouge deleteDilouge = new DeleteDilouge();
                deleteDilouge.show(getSupportFragmentManager(), "delete dialog");
                //  Intent editJobi = new Intent(ProfileActivity.this,JobSeletionActivity.class);
                //  startActivity(editJobi);
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogOutDialog();
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void openLogOutDialog() {
        LogoutDialog logoutDialog = new LogoutDialog();
        logoutDialog.show(getSupportFragmentManager(), "logout dialog");
    }

    JsonPlaceHolderApi fetchJob = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
    public void callToRefreshJobs(){
        //to fetch the jobs
        Call<List<Worker>> job = fetchJob.getJobs("token " + LoginActivity.token, SelectRoleActivity.id);
        job.enqueue(new Callback<List<Worker>>() {
            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                    return;
                }
                List<Worker> worker = response.body();
                for (Worker job : worker) {
                    jobs.add(job.getCategory_1());
                    jobs.add(job.getCategory_2());
                    jobs.add(job.getCategory_3());
                    jobs.add(job.getId());
                }
                ArrayList<String> l = new ArrayList<>();
                System.out.println("jobs :------------------- " + jobs);

                int jsize = jobs.size();
                System.out.println("Workers :------------------- " + response.body());

                if (jsize > 0) {
                    if (jobs.get(2).toString().equals("")) {
                        jobs.remove(2);
                    } else {

                    }
                    if (jobs.get(1).toString().equals("")) {
                        jobs.remove(1);
                    } else {

                    }

                    if (jobs.size() > 3) {
                        if (jobs.get(3).toString().equals("")) {
                            jobs.remove(3);
                        } else {
                            l.add(jobs.get(3));
                            // l.add(jobs.get(4));
                        }
                    }

                    int size = jobs.size();
                    updateId = Integer.parseInt(jobs.get(size - 1));
                    System.out.println("UId :------------------- " + updateId);
                    if (size == 2) {
                        txtProfession.setVisibility(View.GONE);
                        btnJob1.setVisibility(View.VISIBLE);
                        btnJob1.setText(jobs.get(0));
                    } else if (size == 3) {
                        txtProfession.setVisibility(View.GONE);
                        btnJob1.setVisibility(View.VISIBLE);
                        btnJob1.setText(jobs.get(0));

                        btnJob2.setVisibility(View.VISIBLE);
                        btnJob2.setText(jobs.get(1));
                    } else if (size == 4) {
                        txtProfession.setVisibility(View.GONE);
                        txtProfession.setVisibility(View.GONE);
                        btnJob1.setVisibility(View.VISIBLE);
                        btnJob1.setText(jobs.get(0));
                        btnJob2.setVisibility(View.VISIBLE);
                        btnJob2.setText(jobs.get(1));
                        btnJob3.setVisibility(View.VISIBLE);
                        btnJob3.setText(jobs.get(2));
                    }
                } else {

                }
                refreshJobs.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t) {
                System.out.println("Filed in category : " + t.getMessage());
            }
        });
    }
}