package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;


public class SelectLanguageActivity extends AppCompatActivity {

    FloatingActionButton btnNext;
    Button btnEnglish,btnMarathi,btnHindi;
    TextView txtNext;
    static String selectedLanguage = "";
    boolean selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);
        btnNext =  (FloatingActionButton) findViewById(R.id.btn_next);
        btnEnglish =  (Button) findViewById(R.id.btn_english);
        btnMarathi =  (Button) findViewById(R.id.btn_marathi);
        btnHindi =  (Button) findViewById(R.id.btn_hindi);
        txtNext = (TextView)findViewById(R.id.txt_next) ;
       /* if(btnEnglish.isSelected()){
            btnEnglish.setBackgroundColor(2825921);
            btnEnglish.setTextColor(255);
        }
        if(btnMarathi.isSelected()){
            btnMarathi.setBackgroundColor(2825921);
            btnMarathi.setTextColor(255);
        }
        if(btnHindi.isSelected()){
            btnHindi.setBackgroundColor(2825921);
            btnHindi.setTextColor(255);
        }*/

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEnglish.setBackground(getResources().getDrawable(R.drawable.englishbutton));
                btnEnglish.setTextColor(Color.rgb(255,255,255));
                txtNext.setText(R.string.Enext);
                btnMarathi.setBackground(getResources().getDrawable(R.drawable.devnagaribtn));
                btnMarathi.setTextColor(Color.rgb(0,0,0));

                btnHindi.setBackground(getResources().getDrawable(R.drawable.devnagaribtn));
                btnHindi.setTextColor(Color.rgb(0,0,0));

                selectedLanguage = "0";
            }
        });

        btnMarathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnMarathi.setBackground(getResources().getDrawable(R.drawable.englishbutton));
                btnMarathi.setTextColor(Color.rgb(255,255,255));
                txtNext.setText(R.string.Mnext);
                btnEnglish.setBackground(getResources().getDrawable(R.drawable.devnagaribtn));
                btnEnglish.setTextColor(Color.rgb(0,0,0));

                btnHindi.setBackground(getResources().getDrawable(R.drawable.devnagaribtn));
                btnHindi.setTextColor(Color.rgb(0,0,0));
                selectedLanguage = "1";
            }
        });
        btnHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHindi.setBackground(getResources().getDrawable(R.drawable.englishbutton));
                btnHindi.setTextColor(Color.rgb(255,255,255));
                txtNext.setText(R.string.Hnext);
                btnMarathi.setBackground(getResources().getDrawable(R.drawable.devnagaribtn));
                btnMarathi.setTextColor(Color.rgb(0,0,0));

                btnEnglish.setBackground(getResources().getDrawable(R.drawable.devnagaribtn));
                btnEnglish.setTextColor(Color.rgb(0,0,0));

                selectedLanguage = "2";
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedLanguage.equals("")){
                    selectedLanguage = "0";
                    Intent intent =new Intent(SelectLanguageActivity.this,LoginActivity.class);
                    selectedLanguage.toString();
                    intent.putExtra(Intent.EXTRA_TEXT, selectedLanguage);
                    startActivity(intent);
                }else{
                    Intent intent =new Intent(SelectLanguageActivity.this,LoginActivity.class);
                    selectedLanguage.toString();
                    intent.putExtra(Intent.EXTRA_TEXT, selectedLanguage);
                    startActivity(intent);
                }

            }
        });

    }
   /* public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, SelectLanguageActivity.class);
        finish();
        startActivity(refresh);
    }*/
}