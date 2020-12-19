package com.example.toes;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class IdentityProofActivity extends AppCompatActivity {
    Calendar myCal = Calendar.getInstance();
    public static final int PICK_IMAGE = 1;

    CircleImageView circleImageView;
    TextView txtDilouge,txtName,txtContact,txtAddr,txtDob,txtGender,txtNext;

    Button btnNext;
    String selectedLanguage;
    String selectedImagePath;
    //String args[];

    Bitmap bitmap=null;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_proof);
        circleImageView = (CircleImageView)findViewById(R.id.cimgprofile);




        btnNext = (Button)findViewById(R.id.btnGo);

        Intent intent = getIntent();
        String[] args = intent.getStringArrayExtra("args");

        selectedLanguage = args[0];
        selectedImagePath = args[1];

        //System.out.println("-----------------ARGS[0]--------------"+args[0]);
       // System.out.println("-----------------ARGS[1]--------------"+args[1]);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(IdentityProofActivity.this,SelectRoleActivity.class);
                go.putExtra(Intent.EXTRA_TEXT,selectedLanguage);
                startActivity(go);
            }
        });



    }



}