package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    EditText etContact,etPass;
    CheckBox cbShow;
    Button btnLogIn,btnSignUp;
    TextView txtWelcome,txtSign,labelContact,lablePass,txtForgotPass,txtOr;
    String selectedLanguage = "1";
    int l ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etContact = (EditText)findViewById(R.id.etContactNo);
        etPass = (EditText)findViewById(R.id.etPass);

        txtWelcome = (TextView)findViewById(R.id.txt_welcome);
        txtSign = (TextView)findViewById(R.id.txt_sign);
        txtOr = (TextView)findViewById(R.id.txtOr);
        labelContact = (TextView)findViewById(R.id.lableContact);
        lablePass = (TextView)findViewById(R.id.lablePass);
        txtForgotPass = (TextView)findViewById(R.id.txtForgotPass);

        cbShow = (CheckBox)findViewById(R.id.cbShowPass);

        btnLogIn = (Button)findViewById(R.id.btnSignIn);
        btnSignUp = (Button)findViewById(R.id.btnCreateNewAcc);
        selectedLanguage = "1";
        Intent intent = getIntent();
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);
        System.out.println("---------------------------------------------------"+selectedLanguage);
        switch (selectedLanguage){
             case "0":

                 break;
             case "1":
                 txtWelcome.setText(R.string.Mwelcome_back);
                 txtSign.setText(R.string.Msign_in_to_continue);
                 labelContact.setText(R.string.Mcontact_number);
                 etContact.setHint(R.string.Mcontact_number);
                 lablePass.setText(R.string.Mpassword);
                 etPass.setHint(R.string.Mpassword);
                 cbShow.setText(R.string.Mshow_password);
                 btnLogIn.setText(R.string.Mlog_in);
                 btnSignUp.setText(R.string.Mcreate_new_account);
                 btnSignUp.setText(R.string.Mcreate_new_account);
                 txtForgotPass.setText(R.string.Mforgot_password);
                 txtOr.setText(R.string.Mor);
                 break;

             case "2":
                 txtWelcome.setText(R.string.Hwelcome_back);
                 txtSign.setText(R.string.Hsign_in_to_continue);
                 labelContact.setText(R.string.Hcontact_number);
                 etContact.setText(R.string.Hcontact_number);
                 lablePass.setText(R.string.Mpassword);
                 etPass.setText(R.string.Hpassword);
                 cbShow.setText(R.string.Hshow_password);
                 btnLogIn.setText(R.string.Hlog_in);
                 btnSignUp.setText(R.string.Hcreate_new_account);
                 btnSignUp.setText(R.string.Hcreate_new_account);
                 txtForgotPass.setText(R.string.Hforgot_password);
                 txtOr.setText(R.string.Mor);
                 break;



         }

       if(!cbShow.isChecked()){
           etPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        }else{
           etPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
       //Signup
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(LoginActivity.this,SignUpActivity.class);
                intent1.putExtra(Intent.EXTRA_TEXT,selectedLanguage);
                startActivity(intent1);
            }
        });
    }


}