package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class RecruiterHomeActivity extends AppCompatActivity {
    Spinner spinner1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_home);

        spinner1 = findViewById(R.id.spinner1);

        String[] jobTitles = new String[]{"Carpenter", "Painter", "Driver", "Electrician", "Plumber"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.style_select_job_titles,jobTitles);
        spinner1.setAdapter(arrayAdapter);
    }
}