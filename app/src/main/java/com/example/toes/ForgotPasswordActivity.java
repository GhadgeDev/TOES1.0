package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ProgressDialog loadingBar;
    Button btnSendOtp;
    EditText etPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btnSendOtp = (Button)findViewById(R.id.btnSendOTP);
        etPhone = (EditText)findViewById(R.id.etPhone);
        loadingBar = new ProgressDialog(this);
        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();


        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("https://tcp-api.herokuapp.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit1.create(JsonPlaceHolderApi.class);

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                loadingBar.setTitle("Loading");
                loadingBar.setMessage("Please wait..");
                loadingBar.setCanceledOnTouchOutside(true);
                loadingBar.show();
                Call<User> call = jsonPlaceHolderApi.sendOTP(etPhone.getText().toString());

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            loadingBar.dismiss();
                            System.out.println("Response : _--------- " + response.code());
                            System.out.println("Response M : _--------- " + response.message());
                            if (response.code() == 400) {
                                Toast toast = Toast.makeText(ForgotPasswordActivity.this, "Phone number is not registered ! ", Toast.LENGTH_SHORT);
                                View view = toast.getView();
                                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                                toastMessage.setTextColor(Color.RED);
                                toast.show();
                            }
                            return;
                        }
                        loadingBar.dismiss();

                        //     Post postResponse = response.body();
                        System.out.println("----------------------------------------------------");
                        Toast toast = Toast.makeText(ForgotPasswordActivity.this, "OTP Sent Successfully ! " , Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.parseColor("#2E7D32"));
                        toast.show();

                        Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);

                        String content = "";
                        content += "code : " + response.code() + "\n";
                        System.out.println("Data : _--------- " + content);

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        loadingBar.dismiss();

                        System.out.println("fail : _--------- " + t.getMessage());
                        Toast toast = Toast.makeText(ForgotPasswordActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.RED);
                        toast.show();
                    }
                });
            }
        });



    }
}