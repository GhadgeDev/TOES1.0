package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

    TextView txtName, txtPhone, txtGender, txtaadhar, txtaddr, txtProfession;
    Button btnEditJob,btnshowJob;
    ImageView profile_image;
    private ProgressDialog loadingBar;
    Button btnJob1, btnJob2, btnJob3, btnGo;
    static boolean updateJob = false;
    static int updateId;
    String token = "";
    static ArrayList<String> jobs = new ArrayList<>();
    JsonPlaceHolderApi jsonPlaceHolderApi;
    static boolean btn1 = false,btn2 = false,btn3 = false;
    String cat1 = "";
    String cat2 = "";
    String cat3 = "";

 /*   @Override
    protected void onPause() {
        super.onPause();
        cat1 = "";
        cat2 = "";
        cat3 = "";
        jobs.clear();
    }*/

    @Override
    protected void onStop() {
        super.onStop();
        cat1 = "";
        cat2 = "";
        cat3 = "";
        jobs.clear();
    }

    @Override
    public void onStart() {
        super.onStart();
        showJob();
    }

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
//        btnshowJob = (Button) findViewById(R.id.btnShowJob);
        txtProfession = (TextView) findViewById(R.id.txtNoProfession);
        loadingBar = new ProgressDialog(this);
        Intent intent1 = getIntent();
        token = "Token " + LoginActivity.token;
        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("http://65.1.2.12/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        jsonPlaceHolderApi  = retrofit1.create(JsonPlaceHolderApi.class);
        loadingBar.setTitle("Loading");
        loadingBar.setMessage("Please wait..");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();
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


        Call<Post> call = jsonPlaceHolderApi.getPost(token);
        call.enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                    loadingBar.dismiss();
                    return;
                }

                Post postResponse = response.body();
                // File photo = new File(postResponse.getProfile_image());
                // RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), photo);
                System.out.println("Code :------------------- " + response.code());
                String content = "";
                content += "name : " + postResponse.getFirst_name() + "\n";
                content += "lName : " + postResponse.getLast_name() + "\n";
                content += "Phone : " + postResponse.getPhone() + "\n";
                content += "Gender : " + postResponse.getGender() + "\n";
                content += "Aadhar : " + postResponse.getAadhar_no() + "\n";
                content += "address : " + postResponse.getAddress() + "\n";
                txtName.setText(postResponse.getFirst_name() + " " + postResponse.getLast_name());
                txtPhone.setText(postResponse.getPhone());
                txtGender.setText(postResponse.getGender());
                txtaadhar.setText("Aadhar Card No : "+postResponse.getAadhar_no());
                txtaddr.setText("Address : "+postResponse.getAddress());
                //    String mImgUrl = "";
                //      mImgUrl = "http://65.1.2.12/" + postResponse.getProfile_image();
                //     profile_image.setImageURI(Uri.parse(mImgUrl));

                System.out.println("Data : _--------- " + content);
                System.out.println("id : -------------------------- " + postResponse.getId());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("Filed in ProfileActivity : " + t.getMessage());

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
    //to fetch the jobs
    public void showJob(){
        Call<List<Worker>> job = jsonPlaceHolderApi.getJobs(token,SelectRoleActivity.id);
        job.enqueue(new Callback<List<Worker>>() {
            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                if (!response.isSuccessful()) {

                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                    loadingBar.dismiss();
                    return;
                }
                loadingBar.dismiss();
                List<Worker> worker = response.body();
                ArrayList<String> l = new ArrayList<>();

                for (Worker job : response.body()) {
                    cat1 = job.getCategory_1();
                    cat2 = job.getCategory_2();
                    cat3 = job.getCategory_3();

                    l.add(job.getId());
                }

                jobs.add(cat1);
                jobs.add(cat2);
                jobs.add(cat3);
                System.out.println("jobs :------------------- " + jobs);
                System.out.println("cat1 :------------------- " + cat1);
                System.out.println("cat2 :------------------- " + cat2);
                System.out.println("cat3 :------------------- " + cat3);
                cat1 = "";
                cat2 = "";
                cat3 = "";

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

                    if (jobs.size() == 3) {
                        if (jobs.get(2).toString().equals("")) {
                            jobs.remove(2);
                        } else {
                            l.add(jobs.get(2));
                            // l.add(jobs.get(4));
                        }
                    }
                }
                int size = jobs.size();

                System.out.println("Jobs after remove :------------------- " + jobs);
                System.out.println("Size list :------------------- " + size);
                if (size >=1 ) {
                    if(jobs.get(0).equals("")){

                    }else {
                        updateId = Integer.parseInt(l.get(0));
                        System.out.println("UId :------------------- " + updateId);
                        if (size == 1) {
                            txtProfession.setVisibility(View.GONE);
                            btnJob1.setVisibility(View.VISIBLE);
                            btnJob1.setText(jobs.get(0));
                        } else if (size == 2) {
                            txtProfession.setVisibility(View.GONE);
                            btnJob1.setVisibility(View.VISIBLE);
                            btnJob1.setText(jobs.get(0));

                            btnJob2.setVisibility(View.VISIBLE);
                            btnJob2.setText(jobs.get(1));
                        } else if (size >= 3) {
                            txtProfession.setVisibility(View.GONE);


                            txtProfession.setVisibility(View.GONE);
                            btnJob1.setVisibility(View.VISIBLE);
                            btnJob1.setText(jobs.get(0));


                            btnJob2.setVisibility(View.VISIBLE);
                            btnJob2.setText(jobs.get(1));

                            btnJob3.setVisibility(View.VISIBLE);
                            btnJob3.setText(jobs.get(2));
                        } else {

                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t) {
                loadingBar.dismiss();

                System.out.println("Filed in category : " + t.getMessage());
            }
        });

    }

}

