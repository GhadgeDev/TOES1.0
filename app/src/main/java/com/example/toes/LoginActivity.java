package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.List;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity  {

    //variable Declaration
    EditText etContact,etPass;
    CheckBox cbShow;
    Button btnLogIn,btnSignUp;
    TextView txtWelcome,txtSign,labelContact,lablePass,txtForgotPass,txtOr;
    String selectedLanguage = "1";
    int l ;
    String uName = "", pass = "";
 int code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initialization
        etContact = (EditText) findViewById(R.id.etContactNo);
        etPass = (EditText) findViewById(R.id.etPass);

        txtWelcome = (TextView) findViewById(R.id.txt_welcome);
        txtSign = (TextView) findViewById(R.id.txt_sign);
        txtOr = (TextView) findViewById(R.id.txtOr);
        labelContact = (TextView) findViewById(R.id.lableContact);
        lablePass = (TextView) findViewById(R.id.lablePass);
        txtForgotPass = (TextView) findViewById(R.id.txtForgotPass);

        cbShow = (CheckBox) findViewById(R.id.cbShowPass);

        btnLogIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnCreateNewAcc);

        //code
        selectedLanguage = "0";
        Intent intent = getIntent();
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);
        System.out.println("---------------------------------------------------" + selectedLanguage);
        switch (selectedLanguage) {
            case "0":

                break;
            case "1":
                txtWelcome.setText(R.string.Mwelcome_back);
                txtSign.setText(R.string.Msign_in_to_continue);
                labelContact.setText(R.string.Mcontact_number);
                etContact.setHint(R.string.Mcontact_number);
                lablePass.setText(R.string.Mpassword);
                etPass.setHint(R.string.Mpassword);
                cbShow.setText(R.string.Mshow_password);
                btnLogIn.setText(R.string.Mlog_in);
                btnSignUp.setText(R.string.Mcreate_new_account);
                btnSignUp.setText(R.string.Mcreate_new_account);
                txtForgotPass.setText(R.string.Mforgot_password);
                txtOr.setText(R.string.Mor);
                break;

            case "2":
                txtWelcome.setText(R.string.Hwelcome_back);
                txtSign.setText(R.string.Hsign_in_to_continue);
                labelContact.setText(R.string.Hcontact_number);
                etContact.setText(R.string.Hcontact_number);
                lablePass.setText(R.string.Mpassword);
                etPass.setText(R.string.Hpassword);
                cbShow.setText(R.string.Hshow_password);
                btnLogIn.setText(R.string.Hlog_in);
                btnSignUp.setText(R.string.Hcreate_new_account);
                btnSignUp.setText(R.string.Hcreate_new_account);
                txtForgotPass.setText(R.string.Hforgot_password);
                txtOr.setText(R.string.Mor);
                break;


        }

        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient  = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();


        //connecting to base url
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://52.201.220.252/token/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);






        //Signup
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivity.this, SignUpActivity.class);
                intent1.putExtra(Intent.EXTRA_TEXT, selectedLanguage);
                startActivity(intent1);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uName = etContact.getText().toString();
                pass = etPass.getText().toString();

                Post post = new Post(pass, uName);
                Call<Post> call = jsonPlaceHolderApi.createPost(post);
                Intent intent1 = new Intent(LoginActivity.this, SelectRoleActivity.class);

                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (!response.isSuccessful()) {
                            System.out.println("Response : _--------- " + response.code());
                            System.out.println("Response M : _--------- " + response.message());
                            if(response.code() == 400){

                                Toast toast = Toast.makeText(LoginActivity.this, "Incorrect username or password ! ", Toast.LENGTH_SHORT);
                                View view =toast.getView();
                                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                                toastMessage.setTextColor(Color.RED);
                                toast.show();



                            }
                            return;
                        }
                        Post postResponse = response.body();
                        System.out.println("----------------------------------------------------");



                        Toast toast = Toast.makeText(LoginActivity.this, "Log In successfully !", Toast.LENGTH_SHORT);
                        View view =toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.GREEN);
                        toast.show();
                            startActivity(intent1);

                        String content = "";
                        content += "code : " + response.code() + "\n";
                          System.out.println("Data : _--------- " + content);
                          System.out.println("body : _--------- " );
                    }


                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                        System.out.println("fail : _--------- " + t.getMessage());
                        Toast toast = Toast.makeText(LoginActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                        View view =toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.RED);
                        toast.show();

                    }
                });


            }
        });


    }



    }


