package com.example.toes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SelectRoleActivity extends AppCompatActivity {

    String selectedLanguage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        Intent intent = getIntent();
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);
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