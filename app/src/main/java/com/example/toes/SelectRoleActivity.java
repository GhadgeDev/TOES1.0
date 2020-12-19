package com.example.toes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SelectRoleActivity extends AppCompatActivity {

    String selectedLanguage;
    Button btnFindJob,btnFindWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        Intent intent = getIntent();
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);

        btnFindJob = (Button)findViewById(R.id.btnFindJob);
        btnFindWorker = (Button)findViewById(R.id.btnFindWorker);

        btnFindWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recruiter = new Intent(SelectRoleActivity.this,RecruiterHomeActivity.class);
                startActivity(recruiter);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menues,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.menue_profile:

                break;
            case R.id.menu_logout:
                Intent intent = new Intent(SelectRoleActivity.this,LoginActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT,selectedLanguage);
                startActivity(intent);
                break;

        }

        return true;
    }
}