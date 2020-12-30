package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecruiterHomeActivity extends AppCompatActivity {
    Spinner spinner1;
    EditText jobDesc;
    String jobDescVal;
    private Button msearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_home);
        spinner1 = findViewById(R.id.spinnerSelectJob);

        jobDesc = findViewById(R.id.jobDescription);
        jobDescVal = jobDesc.getText().toString();

        String[] jobTitles = new String[]{"Select Job", "Carpenter", "Painter", "Driver", "Electrician", "Plumber"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.style_select_job_titles, jobTitles);
        spinner1.setAdapter(arrayAdapter);

        msearch = findViewById(R.id.btnsearch);

        msearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner1.getSelectedItem().toString();
                if (!text.equals("Select Job")) {
                    Intent intent = SelectWorkerActivity.newIntent(RecruiterHomeActivity.this, text);
                    uploadRecruiterProfile(text);
                    startActivity(intent);
                } else {
                    Toast.makeText(RecruiterHomeActivity.this, R.string.select, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadRecruiterProfile(String t) {
        JsonPlaceHolderApi jsonPlaceHolderApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
        Call<GetRecruiterJobInfo> call = jsonPlaceHolderApi.getRecruiterJobInfo(LoginActivity.token, t,
                jobDescVal, 1, ProfileActivity.userMeId);

        call.enqueue(new Callback<GetRecruiterJobInfo>() {
            @Override
            public void onResponse(Call<GetRecruiterJobInfo> call, Response<GetRecruiterJobInfo> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(RecruiterHomeActivity.this, "Incorrect username or password ! ", Toast.LENGTH_SHORT);
                    toast.show();
                }

                Toast toast = Toast.makeText(RecruiterHomeActivity.this, "Job uploaded successfully", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<GetRecruiterJobInfo> call, Throwable t) {
                Toast toast = Toast.makeText(RecruiterHomeActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }
}