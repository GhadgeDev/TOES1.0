package com.example.toes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    Calendar myCal = Calendar.getInstance();
    public static final int PICK_IMAGE = 1;
    EditText etName,etDob,etContact,etAddr;
    CircleImageView circleImageView;
    TextView txtDilouge,txtName,txtContact,txtAddr,txtDob,txtGender,txtNext;
    RadioButton rbtnGenderMale,rbtnGenderFemale,rbtnGenderOther;

    String selectedLanguage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        circleImageView = (CircleImageView)findViewById(R.id.cimgprofile);

        txtDilouge = (TextView)findViewById(R.id.txtDilouge);
        txtName = (TextView)findViewById(R.id.txtName);
        txtContact = (TextView)findViewById(R.id.txtContact);
        txtAddr = (TextView)findViewById(R.id.txtAddr);
        txtDob = (TextView)findViewById(R.id.txtdob);
        txtGender = (TextView)findViewById(R.id.txtGender);
        txtNext = (TextView)findViewById(R.id.txt_next);

        etName = (EditText)findViewById(R.id.etName);
        etContact = (EditText)findViewById(R.id.etContact);
        etDob = (EditText) findViewById(R.id.etDob);
        etAddr = (EditText)findViewById(R.id.etAddr);
        rbtnGenderMale = (RadioButton) findViewById(R.id.rbtnGenderMale);
        rbtnGenderFemale = (RadioButton) findViewById(R.id.rbtnGenderFemale);
        rbtnGenderOther = (RadioButton) findViewById(R.id.rbtnGenderOther);


        Intent intent = getIntent();
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);

        switch (selectedLanguage){

            case "0":

                break;
            case "1":
                txtDilouge.setText(R.string.Mdilouge);
                txtName.setText(R.string.MName);
                txtContact.setText(R.string.Musername);
                txtAddr.setText(R.string.MAddr);
                txtDob.setText(R.string.MDob);
                txtGender.setText(R.string.MGender);
                txtNext.setText(R.string.Mnext);

                etName.setHint(R.string.MName);
                etContact.setHint(R.string.Musername);
                etAddr.setHint(R.string.MAddr);
                etDob.setHint(R.string.MDob);

                rbtnGenderMale.setText(R.string.Male);
                rbtnGenderFemale.setText(R.string.Female);
                rbtnGenderOther.setText(R.string.MOther);
                break;
            case "2":
                txtDilouge.setText(R.string.Hdilouge);
                txtName.setText(R.string.HName);
                txtContact.setText(R.string.Husername);
                txtAddr.setText(R.string.HAddr);
                txtDob.setText(R.string.HDob);
                txtGender.setText(R.string.HGender);
                txtNext.setText(R.string.Hnext);

                etName.setHint(R.string.HName);
                etContact.setHint(R.string.Husername);
                etAddr.setHint(R.string.HAddr);
                etDob.setHint(R.string.HDob);

                rbtnGenderMale.setText(R.string.Male);
                rbtnGenderFemale.setText(R.string.Female);
                rbtnGenderOther.setText(R.string.HOther);
                break;

        }

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCal.set(Calendar.YEAR, year);
                myCal.set(Calendar.MONTH, month);
                myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, date, myCal
                        .get(Calendar.YEAR), myCal.get(Calendar.MONTH),
                        myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    Bitmap bitmap=null;


        private void updateLabel () {
            String myFormat = "dd/MM/yyyy"; //In which you need put here

            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

            etDob.setText(sdf.format(myCal.getTime()));
        }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = selectedImageUri.getPath();
                //  tv.setText(selectedImagePath);
                circleImageView.setImageURI(selectedImageUri);
            }
        }
    }
}