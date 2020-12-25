package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ParticularWorkerActivity extends AppCompatActivity {

    private String mWorkerName;
    private String mWorkerFees;
    private TextView particularWorkerName;
    private TextView particularWorkerFees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_worker);

        mWorkerName = getIntent().getStringExtra("worker name");
        particularWorkerName = findViewById(R.id.particular_worker_name);
        particularWorkerName.setText(mWorkerName);

        mWorkerFees = getIntent().getStringExtra("worker fees");
        particularWorkerFees = findViewById(R.id.particular_worker_fees);
        particularWorkerFees.setText(mWorkerFees);
    }
}