package com.example.toes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticularWorkerActivity extends AppCompatActivity {

    private String mWorkerName;
    private String mWorkerFees;
    private int mWorkerExp;
    private TextView particularWorkerName;
    private TextView particularWorkerFees;
    private TextView particularWorkerAddress;
    private TextView particularWorkerExperience;
    private TextView jobDescription;
    private Button hireButton;

    private TextView countDownText;
    private CountDownTimer countDownTimer;
    private long timeLeft = 600000;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_worker);

        countDownText = findViewById(R.id.count_down_tv);

        hireButton = findViewById(R.id.hire_btn);
        hireButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                int cx = hireButton.getWidth() / 2;
                int cy = hireButton.getHeight() / 2;
                float radius = hireButton.getWidth();
                Animator anim = ViewAnimationUtils
                        .createCircularReveal(hireButton, cx, cy, radius, 0);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        hireButton.setVisibility(View.INVISIBLE);
                    }
                });
                anim.start();

                startStop();
                postHireReq();
            }
        });

        mWorkerName = getIntent().getStringExtra("worker name");
        particularWorkerName = findViewById(R.id.particular_worker_name);
        particularWorkerName.setText(mWorkerName);

        particularWorkerAddress = findViewById(R.id.particular_worker_address);
        jobDescription = findViewById(R.id.txtprofession);

        mWorkerExp = getIntent().getIntExtra("worker experience", 1);
        particularWorkerExperience = findViewById(R.id.nOfexp);
        particularWorkerExperience.setText("" + mWorkerExp);

        mWorkerFees = getIntent().getStringExtra("worker fees");
        particularWorkerFees = findViewById(R.id.particular_worker_fees);
        particularWorkerFees.setText(mWorkerFees);

        JsonPlaceHolderApi particularWorkerInfo = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
        Call<Post> call = particularWorkerInfo.getPost("token " + LoginActivity.token);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());
                    return;
                }
                particularWorkerAddress.setText(response.body().getAddress());
                jobDescription.setText(RecruiterHomeActivity.jobDescVal);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast toast = Toast.makeText(ParticularWorkerActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }

    public void startStop() {
        if (!timerRunning) {
            startTimer();
        }
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning = true;
    }

    public void updateTimer() {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;

        String timeLeftText;
        timeLeftText = "" + minutes;
        timeLeftText += ":";

        if (seconds < 10) timeLeftText += "0";
        timeLeftText += seconds;

        countDownText.setText(timeLeftText);
    }

    JsonPlaceHolderApi jsonPlaceHolderApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
    public void postHireReq(){
        Call<RecruiterHirePostRequest> call = jsonPlaceHolderApi.postHireReq("token " + LoginActivity.token, LoginActivity.userMeId,
                1,RecruiterHomeActivity.RjbDetail,SelectWorkerActivity.RworkerId);

        call.enqueue(new Callback<RecruiterHirePostRequest>() {
            @Override
            public void onResponse(Call<RecruiterHirePostRequest> call, Response<RecruiterHirePostRequest> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ParticularWorkerActivity.this,"Unsuccessful request",Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(ParticularWorkerActivity.this,"Request Sent",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RecruiterHirePostRequest> call, Throwable t) {
                Toast toast = Toast.makeText(ParticularWorkerActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }
}