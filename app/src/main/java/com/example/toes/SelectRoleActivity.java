package com.example.toes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class
SelectRoleActivity extends AppCompatActivity {
    SharedPreferences prf;
    TextView txtName;
    public static String textUserfName;
    public static String textUserlName;
    String selectedLanguage;
    Button btnFindJob,btnFindWorker;
    String token = (String) LoginActivity.token;
    public static int id ;
    static boolean userPresent = false;
    ArrayList<String> tokenDetails = new ArrayList<>();
    static int user ;

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        Intent intent = getIntent();
        tokenDetails = intent.getStringArrayListExtra("tokenDetails");
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);
        System.out.println("Token :---------- "+LoginActivity.token);
         System.out.println("Details :---------- "+LoginActivity.tokenDetail);

        btnFindJob = (Button)findViewById(R.id.btnFindJob);
        btnFindWorker = (Button)findViewById(R.id.btnFindWorker);
        txtName = (TextView)findViewById(R.id.txtname);
        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        token = "Token "+LoginActivity.token;

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
                id = postResponse.getId();
                System.out.println("Code :------------------- " + response.code());
                String content = "";
                content += "name : " + postResponse.getfName() + "\n";
                content += "lName : " + postResponse.getlName() + "\n";

                txtName.setText(postResponse.getfName()+",");
                textUserfName = postResponse.getfName();
                textUserlName = postResponse.getlName();
                System.out.println("Data : _--------- " + content);

            }
            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("Filed in selectRole : "+t.getMessage());

            }
        });

        btnFindWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent recruiter = new Intent(SelectRoleActivity.this,RecruiterHomeActivity.class);
                startActivity(recruiter);*/
                Intent recruiter = new Intent(SelectRoleActivity.this,RecentPostedJobActivity .class);
                startActivity(recruiter);
            }
        });

        btnFindJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<List<Post>> call = jsonPlaceHolderApi.getId(token);

                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        if (!response.isSuccessful()) {

                            System.out.println("Response : _--------- " + response.code());
                            System.out.println("Response M : _--------- " + response.message());

                            return;
                        }

                        List<Post> posts = response.body();

                        System.out.println("Code :------------------- " + response.code());
                        for (Post post : posts){
                            user = post.getUser();
                            if (user == id){
                                userPresent = true;
                                break;
                            }else {
                                userPresent = false;
                            }
                        }

                        if (userPresent){
                            Intent recruiter = new Intent(SelectRoleActivity.this,SelectJobActivity.class);
                            startActivity(recruiter);
                        }else{
                            Toast toast = Toast.makeText(SelectRoleActivity.this, "Welcome to TOES!\nPlease Enter your Details " , Toast.LENGTH_SHORT);
                            View view = toast.getView();
                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.parseColor("#2E7D32"));
                            toast.show();
                            Intent recruiter = new Intent(SelectRoleActivity.this,JobSeletionActivity.class);
                            startActivity(recruiter);
                        }

                        System.out.println("User Data : _---------------- "+user);
                        System.out.println("User present : _---------------- "+userPresent);
                        System.out.println("Id Data : _---------------- "+id);
                    }
                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        System.out.println("In button find job ------------ "+t.getMessage());
                    }
                });
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menues,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.menue_profile:
                Intent Profileintent = new Intent(SelectRoleActivity.this,ProfileActivity.class);
                Profileintent.putExtra(Intent.EXTRA_TEXT,token);
                startActivity(Profileintent);
                break;
            case R.id.menu_logout:
                LogoutDialog logoutDialog = new LogoutDialog();
                logoutDialog.show(getSupportFragmentManager(),"logout dialog");
                break;

        }

        return true;
    }
}