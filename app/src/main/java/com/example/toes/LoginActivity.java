package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
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


import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.toes.SplashScreenActivity.IsLoggedIn;


public class LoginActivity extends AppCompatActivity {

    //variable Declaration
    static SharedPreferences prf;
    EditText etContact, etPass;
    CheckBox cbShow;
    Button btnLogIn, btnSignUp;
    TextView txtWelcome, txtSign, labelContact, lablePass, txtForgotPass, txtOr;
    static String selectedLanguage = "1";
    int l;
    String uName = "", pass = "";
    int code = 0;

    public static String userPhoneNumber;
    public static String userName;
    public static String userAddress;
    public static boolean userBlocked;

    public static String token;
    public static int userMeId;
    public static SharedPreferences sp;
    private ProgressDialog loadingBar;
    static ArrayList<String> tokenDetail = new ArrayList<>();

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
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        cbShow = (CheckBox) findViewById(R.id.cbShowPass);
        loadingBar = new ProgressDialog(this);
        btnLogIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnCreateNewAcc);

        //code
        selectedLanguage = "0";
        Intent intent = getIntent();
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);
        System.out.println("---------------------------------------------------" + selectedLanguage);


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Logout in progress");
                //At this point you should start the login activity and finish this one
                finish();
            }
        }, intentFilter);
        selectedLanguage = SelectLanguageActivity.selectedLanguage;
    /*    switch (selectedLanguage) {
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


        }*/

        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("http://tcp-api.herokuapp.com/token/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit1.create(JsonPlaceHolderApi.class);


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
                Call<User> call = jsonPlaceHolderApi.createPost(post);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            System.out.println("Response : _--------- " + response.code());
                            System.out.println("Response M : _--------- " + response.message());
                            if (response.code() == 400) {
                                Toast toast = Toast.makeText(LoginActivity.this, "Incorrect username or password ! ", Toast.LENGTH_SHORT);
                                View view = toast.getView();
                                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                                toastMessage.setTextColor(Color.RED);
                                toastMessage.setBackgroundColor(Color.WHITE);
                                toast.show();
                            }
                            return;
                        }


                        //  Post postResponse = response.body();
                        System.out.println("----------------------------------------------------");
                        Toast toast = Toast.makeText(LoginActivity.this, "Log In successfully ! ", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setBackgroundColor(Color.WHITE);
                        toastMessage.setTextColor(Color.parseColor("#2E7D32"));
                        toast.show();
                        userMeId = response.body().getId();
                        LoginActivity.token = response.body().getAuth_token();
                        System.out.println("----------------------------------------------------" + LoginActivity.token);
                        callFirst();

                        tokenDetail.add(selectedLanguage);
                        tokenDetail.add(response.body().getAuth_token());
                        System.out.println("details" + tokenDetail);

                        sp.edit().putBoolean("logged", true).apply();
                        sp.edit().putString("token", token).apply();
                        Intent intent1 = new Intent(LoginActivity.this, SelectRoleActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.putExtra("tokenDetails", tokenDetail);
                        startActivity(intent1);

                        String content = "";

                        content += "code : " + response.code() + "\n";
                        System.out.println("Data : _--------- " + content);
                        System.out.println("body : _--------- ");
                        System.out.println("TOKEN = " + response.body().getAuth_token());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("fail : _--------- " + t.getMessage());
                        Toast toast = Toast.makeText(LoginActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.RED);
                        toastMessage.setBackgroundColor(Color.WHITE);
                        toast.show();
                    }
                });
            }
        });
    }

    public void onCheck(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.cbShowPass:
                if (checked) {
                    etPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {

                    etPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;

        }
    }

    public void forgotPass(View view) {
        Intent forgotPass = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
        startActivity(forgotPass);
    }

    JsonPlaceHolderApi callToApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);

    public void callFirst() {
        Call<Post> call1 = callToApi.getPost("token " + token);
        call1.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(LoginActivity.this, "ERROR :( ", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                userMeId = response.body().getId();
                sp.edit().putInt("uid", userMeId).apply();
                userPhoneNumber = response.body().getPhone();
                userName = response.body().getFirst_name() + " " + response.body().getLast_name();
                userAddress = response.body().getAddress();
                userBlocked = response.body().getIsblocked();

                System.out.println("userId ----------------------------------" + userMeId);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast toast = Toast.makeText(LoginActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toastMessage.setBackgroundColor(Color.WHITE);
                toastMessage.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
                toast.show();
            }
        });
    }
}