package com.example.toes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParticularRecruiterActivity extends AppCompatActivity {

    private String recruiterName;
    private TextView recruiterNameTv;

    private String reAdd;
    private TextView recruiterAddress;

    private String reDesc;
    private TextView recruiterJbDesc;

    private EditText editAmount;
    private int amount = 0;

    private Button hire_btn;

    private List<GetRecruiterViewRequestModel> lstRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_recruiter);

        recruiterName = getIntent().getStringExtra("recruiter name");
        recruiterNameTv = findViewById(R.id.particular_recruiter_name);
        recruiterNameTv.setText(recruiterName);

        reAdd = getIntent().getStringExtra("recruiter_add");
        recruiterAddress = findViewById(R.id.recruiter_address);
        recruiterAddress.setText(reAdd);

        reDesc = getIntent().getStringExtra("recruiter jbDesc");
        recruiterJbDesc = findViewById(R.id.recruiter_description);
        recruiterJbDesc.setText(reDesc);

        JsonPlaceHolderApi sendReq = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
        hire_btn = findViewById(R.id.send_req);
        callToGetViewRequests();
        hire_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                editAmount = findViewById(R.id.worker_edit_amount);
                editAmount.setSelection(0);
                editAmount.setFocusable(false);
                editAmount.setClickable(false);
                editAmount.setEnabled(false);
                if (!editAmount.getText().toString().isEmpty()) {
                    amount = Integer.parseInt(editAmount.getText().toString());
                    if (amount >= 10) {
                        int cx = hire_btn.getWidth() / 2;
                        int cy = hire_btn.getHeight() / 2;
                        float radius = hire_btn.getWidth();
                        Animator anim = ViewAnimationUtils
                                .createCircularReveal(hire_btn, cx, cy, radius, 0);
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                hire_btn.setVisibility(View.INVISIBLE);
                            }
                        });
                        anim.start();
                        Call<WorkerSendPostRequest> call = sendReq.sendPostRequest("token " + LoginActivity.token, SelectJobActivity.recruiterId,
                                amount, 1, SelectJobActivity.WjbDetail, LoginActivity.userMeId);

                        call.enqueue(new Callback<WorkerSendPostRequest>() {
                            @Override
                            public void onResponse(Call<WorkerSendPostRequest> call, Response<WorkerSendPostRequest> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(ParticularRecruiterActivity.this, "Unsuccessful request", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(ParticularRecruiterActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<WorkerSendPostRequest> call, Throwable t) {
                                Toast toast = Toast.makeText(ParticularRecruiterActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                                toastMessage.setTextColor(Color.RED);
                                toast.show();
                            }
                        });
                    } else {
                        Toast.makeText(ParticularRecruiterActivity.this, "Please fill appropriate amount", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ParticularRecruiterActivity.this, "Please fill amount", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void callToGetViewRequests(){
        JsonPlaceHolderApi viewRequest = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
        Call<List<GetRecruiterViewRequestModel>> call = viewRequest.getRecruiterViewRequest("token " + LoginActivity.token, SelectJobActivity.recruiterId);
        call.enqueue(new Callback<List<GetRecruiterViewRequestModel>>() {
            @Override
            public void onResponse(Call<List<GetRecruiterViewRequestModel>> call, Response<List<GetRecruiterViewRequestModel>> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(ParticularRecruiterActivity.this, "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                lstRequests = response.body();
                for(GetRecruiterViewRequestModel lst : lstRequests){
                    int jId = lst.getJbid();
                    int rId = lst.getRecruiterId();
                    if((jId == SelectJobActivity.WjbDetail) && rId == (SelectJobActivity.recruiterId)){
                        hire_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Toast.makeText(ParticularRecruiterActivity.this,"You've already sent request to this recruiter",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            }
            @Override
            public void onFailure(Call<List<GetRecruiterViewRequestModel>> call, Throwable t) {
                Toast toast = Toast.makeText(ParticularRecruiterActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }
}