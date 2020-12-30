package com.example.toes;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Base64;
import android.view.View;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignUpActivity extends AppCompatActivity {
    Calendar myCal = Calendar.getInstance();
    public static final int PICK_IMAGE = 1;
    EditText etName, etLName, etDob, etContact, etAddr;
    CircleImageView circleImageView;
    TextView txtDilouge, txtName, txtLName, txtContact, txtAddr, txtDob, txtGender, txtNext;
    RadioButton rbtnGenderMale, rbtnGenderFemale, rbtnGenderOther;

    FloatingActionButton btnNext;
    String selectedLanguage;
    public static String selectedImagePath;
    //Uri selectedImageUri;

    Bitmap bitmap;
    public static String encodedImg;

    String args[] = {"", ""};

    String fName = "", lName = "", contact = "", address = "", dob = "", gender = "", pass = "", phone = "";
    int l = 0;

    File file;
    ArrayList<String> details1 = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        circleImageView = (CircleImageView) findViewById(R.id.cimgprofile);

        txtDilouge = (TextView) findViewById(R.id.txtDilouge);
        txtName = (TextView) findViewById(R.id.txtFName);
        txtLName = (TextView) findViewById(R.id.txtLName);
        txtContact = (TextView) findViewById(R.id.txtContact);
        txtAddr = (TextView) findViewById(R.id.txtAddr);
        txtDob = (TextView) findViewById(R.id.txtdob);
        txtGender = (TextView) findViewById(R.id.txtGender);
        txtNext = (TextView) findViewById(R.id.txt_next);

        etName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);
        etContact = (EditText) findViewById(R.id.etContact);
        etDob = (EditText) findViewById(R.id.etDob);
        etAddr = (EditText) findViewById(R.id.etAddr);

        rbtnGenderMale = (RadioButton) findViewById(R.id.rbtnGenderMale);
        rbtnGenderFemale = (RadioButton) findViewById(R.id.rbtnGenderFemale);
        rbtnGenderOther = (RadioButton) findViewById(R.id.rbtnGenderOther);

        btnNext = (FloatingActionButton) findViewById(R.id.btn_next);


        Intent intent = getIntent();
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);
        args[0] = selectedLanguage;
        switch (selectedLanguage) {

            case "0":

                break;
            case "1":
                txtDilouge.setText(R.string.Mdilouge);
                txtName.setText(R.string.MName);
                txtLName.setText(R.string.MLName);

                txtContact.setText(R.string.Musername);
                txtAddr.setText(R.string.MAddr);
                txtDob.setText(R.string.MDob);
                txtGender.setText(R.string.MGender);
                txtNext.setText(R.string.Mnext);

                etName.setHint(R.string.MName);
                etLName.setHint(R.string.MLName);
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
                txtLName.setText(R.string.HLName);
                txtContact.setText(R.string.Husername);
                txtAddr.setText(R.string.HAddr);
                txtDob.setText(R.string.HDob);
                txtGender.setText(R.string.HGender);
                txtNext.setText(R.string.Hnext);

                etName.setHint(R.string.HName);
                etLName.setHint(R.string.HLName);
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
                new DatePickerDialog(SignUpActivity.this,R.style.DialogTheme, date, myCal
                        .get(Calendar.YEAR), myCal.get(Calendar.MONTH),
                        myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        etContact.addTextChangedListener(new PhoneNumberFormattingTextWatcher("+91"));
        //next Button
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty() || etLName.getText().toString().isEmpty() || etAddr.getText().toString().isEmpty() || etContact.getText().toString().isEmpty() || etDob.getText().toString().isEmpty()) {

                    Toast.makeText(SignUpActivity.this, "Please fill all fields !", Toast.LENGTH_SHORT).show();
                } else if (etContact.getText().toString().length() < 10) {
                    Toast.makeText(SignUpActivity.this, "Please enter 10 digit number !", Toast.LENGTH_SHORT).show();
                    System.out.println("--------------------------------sImage" + selectedImagePath);
                    System.out.println("--------------------------------contact : " + etContact.getText().toString().length());

                } else {
                    fName = etName.getText().toString();
                    lName = etLName.getText().toString();
                    phone = etContact.getText().toString();
                    dob = etDob.getText().toString();
                    address = etAddr.getText().toString();
                    if (rbtnGenderMale.isChecked()) {
                        gender = rbtnGenderMale.getText().toString();
                    } else if (rbtnGenderFemale.isChecked()) {
                        gender = rbtnGenderFemale.getText().toString();
                    } else {
                        gender = rbtnGenderOther.getText().toString();
                    }


                    System.out.println("--------------------------------Name " + fName);
                    System.out.println("-------------------------------- Lname " + lName);
                    System.out.println("--------------------------------cno " + phone);
                    System.out.println("--------------------------------addr" + address);
                    System.out.println("--------------------------------dob" + dob);
                    System.out.println("--------------------------------gender" + gender);


                    details1.add(fName);
                    details1.add(lName);
                    details1.add(phone);
                    details1.add(address);
                    details1.add(dob);
                    details1.add(gender);
                    details1.add(selectedImagePath);
                    Intent next = new Intent(SignUpActivity.this, IdentityProofActivity.class);
                    next.putExtra("args", args);
                    next.putExtra("details1", details1);
                    System.out.println("--------------------------------sImage" + selectedImagePath);
                    System.out.println("--------------------------------contact" + etContact.getText().toString());
                    startActivity(next);


                }
            }
        });

    }


    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        etDob.setText(sdf.format(myCal.getTime()));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                // selectedImageUri = data.getData();
                // selectedImagePath = selectedImageUri.getPath();
                //  circleImageView.setImageURI(selectedImageUri);
                // file = new File(selectedImageUri.toString());
                // args[1] = selectedImagePath;

                Uri path = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                    circleImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, byteArrayOutputStream);
                byte[] imgInBit = byteArrayOutputStream.toByteArray();

                encodedImg = Base64.encodeToString(imgInBit, Base64.DEFAULT);
            }
        }
    }


}