package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ParticularRecruiterActivity extends AppCompatActivity {

    private String recruiterName;
    private TextView recruiterNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_recruiter);

        recruiterName = getIntent().getStringExtra("recruiter name");
        recruiterNameTv = findViewById(R.id.particular_recruiter_name);
        recruiterNameTv.setText(recruiterName);
    }
}