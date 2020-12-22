package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class VisitingChargesActivity extends AppCompatActivity {
ArrayList<String> job = new ArrayList<>();
int flag;
Button btnJob1,btnJob2,btnJob3,btnGo;
LinearLayout ll1,ll2,ll3;
TextView t1,t2,t3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visiting_charges);

        btnJob1= (Button)findViewById(R.id.btnJob1);
        btnJob2= (Button)findViewById(R.id.btnJob2);
        btnJob3= (Button)findViewById(R.id.btnJob3);

        ll1 = (LinearLayout)findViewById(R.id.li1);
        ll2 = (LinearLayout)findViewById(R.id.li2);
        ll3 = (LinearLayout)findViewById(R.id.li3);

        t1 = (TextView)findViewById(R.id.txtJ1);
        t2 = (TextView)findViewById(R.id.txtJ2);
        t3 = (TextView)findViewById(R.id.txtJ3);

        btnGo = (Button)findViewById(R.id.btnGo);
        Intent intent = getIntent();
        job = intent.getStringArrayListExtra("arrl");
        System.out.println("In Visiting charge"+job);

        flag = intent.getIntExtra("f",0);
        System.out.println("In Visiting charge"+flag);

        if(flag == 1){
            btnJob1.setText(job.get(0));
            t1.setText(job.get(0));
        }else if (flag == 2){
            btnJob2.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.VISIBLE);




            btnJob1.setText(job.get(0));
            btnJob2.setText(job.get(1));
            t1.setText(job.get(0));
            t2.setText(job.get(1));
        }else if (flag ==3){
            btnJob2.setVisibility(View.VISIBLE);
            btnJob3.setVisibility(View.VISIBLE);

            ll2.setVisibility(View.VISIBLE);
            ll3.setVisibility(View.VISIBLE);
            btnJob1.setText(job.get(0));
            btnJob2.setText(job.get(1));
            btnJob3.setText(job.get(2));

            t1.setText(job.get(0));
            t2.setText(job.get(1));
            t3.setText(job.get(2));
        }
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(VisitingChargesActivity.this,SelectJobActivity.class);
                startActivity(intent1);
            }
        });
    }
}