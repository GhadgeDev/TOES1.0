package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitingChargesActivity extends AppCompatActivity {
public static ArrayList<String> job = new ArrayList<>();
int flag;
Button btnJob1,btnJob2,btnJob3,btnGo;
EditText etJob1VC,etJob1Exp;
EditText etJob2VC,etJob2Exp;
EditText etJob3VC,etJob3Exp;

String job1 = null,job1VC = null;
int job1Exp = 0;
String job2 = null,job2VC = null;
int job2Exp = 0;
String job3 = null,job3VC = null;
int job3Exp = 0;

String token = "" ;
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

        etJob1VC = (EditText)findViewById(R.id.etVisitingCharges);
        etJob2VC = (EditText)findViewById(R.id.etVisitingCharges2);
        etJob3VC = (EditText)findViewById(R.id.etVisitingCharges3);

        etJob1Exp = (EditText)findViewById(R.id.etExperience);
        etJob2Exp = (EditText)findViewById(R.id.etExperience2);
        etJob3Exp = (EditText)findViewById(R.id.etExperience3);

        token = "token "+ LoginActivity.token;
        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("http://52.201.220.252/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit1.create(JsonPlaceHolderApi.class);


        Intent intent = getIntent();
        job = intent.getStringArrayListExtra("arrl");
        System.out.println("In Visiting charge"+JobSeletionActivity.job);
        System.out.println("In Visiting Token -------- "+token);
        System.out.println("In Visiting Token -------- "+JobSeletionActivity.city);

        //flag = intent.getIntExtra("f",0);
        flag = JobSeletionActivity.flag;
        System.out.println("In Visiting charge"+JobSeletionActivity.job.size());

        if(flag == 1){
            job1 = job.get(0);
            btnJob1.setText(job.get(0));
            t1.setText(job.get(0));
        }else if (flag == 2){
            btnJob2.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.VISIBLE);

            job1 = job.get(0);
            btnJob1.setText(job.get(0));

            job2 = job.get(1);
            btnJob2.setText(job.get(1));

            t1.setText(job.get(0));
            t2.setText(job.get(1));
        }else if (flag ==3){
            btnJob2.setVisibility(View.VISIBLE);
            btnJob3.setVisibility(View.VISIBLE);

            ll2.setVisibility(View.VISIBLE);
            ll3.setVisibility(View.VISIBLE);

            job1 = job.get(0);
            btnJob1.setText(job.get(0));

            job2 = job.get(1);
            btnJob2.setText(job.get(1));

            job3 = job.get(2);
            btnJob3.setText(job.get(2));

            t1.setText(job.get(0));
            t2.setText(job.get(1));
            t3.setText(job.get(2));
        }


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(flag == 1){
                    job1 = job.get(0);
                    job1VC = etJob1VC.getText().toString();
                    job1Exp = Integer.parseInt(etJob1Exp.getText().toString());

                }else if (flag == 2){

                    job1 = job.get(0);
                    job2 = job.get(1);


                    job1VC = etJob1VC.getText().toString();
                    job1Exp = Integer.parseInt(etJob1Exp.getText().toString());

                    job2VC = etJob2VC.getText().toString();
                    job2Exp = Integer.parseInt(etJob2Exp.getText().toString());


                }else if (flag ==3){

                    job1 = job.get(0);
                    job2 = job.get(1);
                    job3 = job.get(2);


                    job1VC = etJob1VC.getText().toString();
                    job1Exp = Integer.parseInt(etJob1Exp.getText().toString());

                    job2VC = etJob2VC.getText().toString();
                    job2Exp = Integer.parseInt(etJob2Exp.getText().toString());

                    job3VC = etJob3VC.getText().toString();
                    job3Exp = Integer.parseInt(etJob3Exp.getText().toString());
                }

                Call<WorkerJobDetails> call = jsonPlaceHolderApi.insertWorkerJobInfo(token,JobSeletionActivity.city,job1,job1VC,job1Exp,job2,job2VC,job2Exp,job3,job3VC,job3Exp,SelectRoleActivity.id);
                call.enqueue(new Callback<WorkerJobDetails>() {
                    @Override
                    public void onResponse(Call<WorkerJobDetails> call, Response<WorkerJobDetails> response) {
                        if (!response.isSuccessful()) {
                            System.out.println("Response : _--------- " + response.code());
                            System.out.println("Response M : _--------- " + response.message());

                            return;
                        }

                        Toast toast = Toast.makeText(VisitingChargesActivity.this, "Congratulation !\n You are details saved successfully !.", Toast.LENGTH_SHORT);
                        View view =toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.parseColor("#2E7D32"));
                        toast.show();

                        WorkerJobDetails postResponse = response.body();
                        System.out.println("Code :------------------- " + response.code());
                        String content = "";
                        content += "Id : " + postResponse.getId() + "\n";
                        content += "city : " + postResponse.getCity() + "\n";
                        content += "job1 : " + postResponse.getCategory_1() + "\n";
                        content += "job1_VC : " + postResponse.getCategory_1_vc() + "\n";
                        content += "job1_exp : " + postResponse.getCategory_1_exp() + "\n";

                        content += "job2 : " + postResponse.getCategory_2() + "\n";
                        content += "job2_VC : " + postResponse.getCategory_2_vc() + "\n";
                        content += "job2_exp : " + postResponse.getCategory_2_exp() + "\n";

                        content += "job3 : " + postResponse.getCategory_3() + "\n";
                        content += "job3_VC : " + postResponse.getCategory_3_vc() + "\n";
                        content += "job3_exp : " + postResponse.getCategory_3_exp() + "\n";


                        System.out.println("Data : _--------- " + content);

                        Intent go = new Intent(VisitingChargesActivity.this,SelectJobActivity.class);

                        startActivity(go);

                    }

                    @Override
                    public void onFailure(Call<WorkerJobDetails> call, Throwable t) {
                        Toast toast = Toast.makeText(VisitingChargesActivity.this, "You are disconnected from internet\nOr Server is under maintenance", Toast.LENGTH_SHORT);
                        View view =toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.RED);
                        toast.show();
                        System.out.println("fail : _--------- " + t.getMessage());

                    }
                });

            }
        });
    }
}