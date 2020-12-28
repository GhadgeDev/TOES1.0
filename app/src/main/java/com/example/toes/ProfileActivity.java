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

    TextView txtName,txtPhone,txtGender,txtaadhar,txtaddr;
    ImageView profile_image;

    String token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = (TextView)findViewById(R.id.txtprofile_name);
        txtPhone = (TextView)findViewById(R.id.txtprofile_contact);
        txtGender = (TextView)findViewById(R.id.txtprofile_gender);
        txtaadhar = (TextView)findViewById(R.id.txtprofile_adhar);
        txtaddr = (TextView)findViewById(R.id.txtprofile_address);
        profile_image = (ImageView)findViewById(R.id.profile_image);
        mLogoutButton = findViewById(R.id.btnlogout);
        mEditButton = findViewById(R.id.edit_profile_btn);

        Intent intent1= getIntent();
        token = intent1.getStringExtra(Intent.EXTRA_TEXT);


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

                txtName.setText(postResponse.getfName()+" "+postResponse.getlName());
                txtPhone.setText(postResponse.getPhone());
                txtGender.setText(postResponse.getGender());
                txtaadhar.setText(postResponse.getAdharNo());
                txtaddr.setText(postResponse.getAddress());
            //    String mImgUrl = "";
          //      mImgUrl = "http://52.201.220.252/" + postResponse.getProfile_image();
           //     profile_image.setImageURI(Uri.parse(mImgUrl));

                System.out.println("Data : _--------- " + content);


            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("Filed in ProfileActivity : "+t.getMessage());

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
    public void openLogOutDialog(){
        LogoutDialog logoutDialog = new LogoutDialog();
        logoutDialog.show(getSupportFragmentManager(),"logout dialog");
    }
}