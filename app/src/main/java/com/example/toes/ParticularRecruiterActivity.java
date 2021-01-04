package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private Integer amount = 0;

    private Button hire_btn;

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
        hire_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAmount = findViewById(R.id.worker_edit_amount);
                amount = Integer.parseInt(editAmount.getText().toString());
                if (amount > 20) {

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
                } else{
                    Toast.makeText(ParticularRecruiterActivity.this,"Fill appropriate amount !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}