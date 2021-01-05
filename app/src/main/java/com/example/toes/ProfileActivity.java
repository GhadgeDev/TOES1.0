package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

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

    TextView txtName, txtPhone, txtGender, txtaadhar, txtaddr, txtProfession;
    ImageView profile_image;
    Button btnJob1, btnJob2, btnJob3, btnGo;
    String token = "";
    ArrayList<String> jobs = new ArrayList<>();

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

        txtProfession = (TextView) findViewById(R.id.txtNoProfession);

        Intent intent1 = getIntent();
        token = "Token " + LoginActivity.token;

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

        Call<Post> call = jsonPlaceHolderApi.getPost(token);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                    return;
                }
                Post postResponse = response.body();
                // File photo = new File(postResponse.getProfile_image());
                // RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), photo);
                System.out.println("Code :------------------- " + response.code());
                String content = "";
                content += "name : " + postResponse.getfName() + "\n";
                content += "lName : " + postResponse.getlName() + "\n";
                content += "Phone : " + postResponse.getPhone() + "\n";
                content += "Gender : " + postResponse.getGender() + "\n";
                content += "Aadhar : " + postResponse.getAdharNo() + "\n";
                content += "address : " + postResponse.getAddress() + "\n";
                txtName.setText(postResponse.getfName() + " " + postResponse.getlName());
                txtPhone.setText(postResponse.getPhone());
                txtGender.setText(postResponse.getGender());
                txtaadhar.setText(postResponse.getAdharNo());
                txtaddr.setText(postResponse.getAddress());
                //    String mImgUrl = "";
                //      mImgUrl = "http://52.201.220.252/" + postResponse.getProfile_image();
                //     profile_image.setImageURI(Uri.parse(mImgUrl));

                System.out.println("Data : _--------- " + content);
                System.out.println("id : -------------------------- " + postResponse.getId());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("Filed in ProfileActivity : " + t.getMessage());
            }
        });

        Call<List<Worker>> job = jsonPlaceHolderApi.getJobs(token,SelectRoleActivity.id);
        job.enqueue(new Callback<List<Worker>>() {
            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                if (!response.isSuccessful()) {

                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                    return;
                }
                List<Worker> worker = response.body();
                for(Worker job : worker){
                    jobs.add(job.getCategory_1());


                    //jobs.add(job.getCategory_2());



                  //  jobs.add(job.getCategory_3());

                }

                ArrayList<String> l = new ArrayList<>();
                System.out.println("jobs :------------------- " + jobs);
                System.out.println("Workers :------------------- " + worker);

                l.add(jobs.get(0));
                l.add(jobs.get(1));
                l.add(jobs.get(2));
                int size = l.size();
                System.out.println("L list :------------------- " + l);

                /*if (size == 1) {
                    txtProfession.setVisibility(View.GONE);
                    btnJob1.setVisibility(View.VISIBLE);
                    btnJob1.setText(l.get(0));
                } else if (size == 2) {
                    txtProfession.setVisibility(View.GONE);
                    btnJob1.setVisibility(View.VISIBLE);
                    btnJob1.setText(l.get(0));

                    btnJob2.setVisibility(View.VISIBLE);
                    btnJob2.setText(l.get(1));
                } else if (size == 3) {*/
                    txtProfession.setVisibility(View.GONE);

                    if(l.get(0).toString().equals(l.get(1).toString()) && l.get(2).toString().equals(l.get(1).toString()) && l.get(0).toString().equals(l.get(2).toString())  ){
                        txtProfession.setVisibility(View.GONE);
                        btnJob1.setVisibility(View.VISIBLE);
                        btnJob1.setText(l.get(0));
                    }else if(l.get(0).toString().equals(l.get(2).toString())){
                        txtProfession.setVisibility(View.GONE);
                        btnJob1.setVisibility(View.VISIBLE);
                        btnJob1.setText(l.get(0));
                        btnJob2.setVisibility(View.VISIBLE);
                        btnJob2.setText(l.get(1));
                    }else {
                        txtProfession.setVisibility(View.GONE);
                        btnJob1.setVisibility(View.VISIBLE);
                        btnJob1.setText(l.get(0));
                        btnJob2.setVisibility(View.VISIBLE);
                        btnJob2.setText(l.get(1));
                        btnJob3.setVisibility(View.VISIBLE);
                        btnJob3.setText(l.get(2));
                    }

              //  }




            }

            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t) {
                System.out.println("Filed in category : " + t.getMessage());
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
}