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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class RecruiterHomeActivity extends AppCompatActivity {
    Spinner spinner1;
    private Button msearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruiter_home);

        spinner1 = findViewById(R.id.spinnerSelectJob);

        String[] jobTitles = new String[]{"Select Job","Carpenter", "Painter", "Driver", "Electrician", "Plumber"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,R.layout.style_select_job_titles,jobTitles);
        spinner1.setAdapter(arrayAdapter);

        msearch = findViewById(R.id.btnsearch);
        msearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = spinner1.getSelectedItem().toString();
                if(!text.equals("Select Job")) {
                    Intent intent = SelectWorkerActivity.newIntent(RecruiterHomeActivity.this, text);
                    startActivity(intent);
                  /*  Intent intent = new Intent(RecruiterHomeActivity.this,SelectWorkerActivity.class);
                    startActivity(intent);*/
                }
                else{
                    Toast.makeText(RecruiterHomeActivity.this,R.string.select, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}