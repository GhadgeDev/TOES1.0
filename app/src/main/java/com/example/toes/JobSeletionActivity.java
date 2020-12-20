package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class JobSeletionActivity extends AppCompatActivity {
    int j = 0;
    Spinner spinnerCity;
    CheckBox chkboxJob1, chkboxJob2, chkboxJob3, chkboxJob4, chkboxJob5, chkboxJob6, chkboxOther;
    EditText etOtherJob;
    Button btnStart;
    int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_seletion);


        chkboxJob1 = (CheckBox) findViewById(R.id.chkboxJob1);
        chkboxJob2 = (CheckBox) findViewById(R.id.chkboxJob2);
        chkboxJob3 = (CheckBox) findViewById(R.id.chkboxJob3);
        chkboxJob4 = (CheckBox) findViewById(R.id.chkboxJob4);
        chkboxJob5 = (CheckBox) findViewById(R.id.chkboxJob5);
        chkboxJob6 = (CheckBox) findViewById(R.id.chkboxJob6);
        chkboxOther = (CheckBox) findViewById(R.id.chkboxOther);

        etOtherJob = (EditText) findViewById(R.id.etOtherJob);

        btnStart = (Button) findViewById(R.id.btnStart);

        spinnerCity = findViewById(R.id.spinnerCity);

        String[] jobTitles = new String[]{"Select Your City", "Satara", "Pune", "Mumbai", "Nagpur", "Solapur"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.style_select_job_titles, jobTitles);
        spinnerCity.setAdapter(arrayAdapter);

       /* final CheckBox[] checkbox = {chkboxJob1,chkboxJob2,chkboxJob3,chkboxJob4,chkboxJob5,chkboxJob6,chkboxOther};


        for(j = 0 ; j < checkbox.length; j++) {
            checkbox[j].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 2) {
                        // check if there are already two checkboxes selected
                        // you can use checkbox.isChecked() and a loop for that
                        Toast.makeText(getApplicationContext(), "Only two checkboxes allowed", Toast.LENGTH_SHORT).show();
                        return true; //this will prevent checkbox from changing state
                    }
                    return false; // this is default and lets event propagate
                }
            });
        }*/
    }

    public void onCheck(View view) {
        boolean checked = ((CheckBox) view).isChecked();


        switch (view.getId()) {
            case R.id.chkboxJob1:
                if (flag < 2) {

                    if (checked) {
                        System.out.println("-----------------------+++++-" + flag);
                        ++flag;
                    } else {
                        System.out.println("-----------------------+++++-" + flag);
                        --flag;
                    }
                } else {
                    chkboxJob1.setChecked(false);
                    Toast.makeText(JobSeletionActivity.this, "Maximum 3 jobs are allowed", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.chkboxJob2:

                if (flag < 2) {
                    if (checked) {
                        ++flag;
                    } else {
                        --flag;
                    }
                } else {
                    chkboxJob2.setChecked(false);
                    Toast.makeText(JobSeletionActivity.this, "Maximum 3 jobs are allowed", Toast.LENGTH_SHORT).show();
                }
                // I'm lactose intolerant
                break;
            case R.id.chkboxJob3:
                if (flag < 2) {
                    if (checked) {
                        ++flag;
                    } else {
                        --flag;
                    }
                } else {
                    chkboxJob3.setChecked(false);
                    Toast.makeText(JobSeletionActivity.this, "Maximum 3 jobs are allowed", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.chkboxJob4:
                if (flag < 2) {
                    if (checked) {
                        ++flag;
                    } else {
                        --flag;
                    }
                } else {
                    chkboxJob4.setChecked(false);
                    Toast.makeText(JobSeletionActivity.this, "Maximum 3 jobs are allowed", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.chkboxJob5:
                if (flag < 2) {
                    if (checked) {
                        ++flag;
                    } else {
                        --flag;
                    }
                } else {

                    chkboxJob5.setChecked(false);
                    Toast.makeText(JobSeletionActivity.this, "Maximum 3 jobs are allowed", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.chkboxOther:
                if (flag < 2) {
                    if (checked) {
                        chkboxOther.setClickable(true);
                        etOtherJob.setVisibility(View.VISIBLE);
                        ++flag;
                        System.out.println("-----------------------+++++-" + flag);
                    } else {
                        chkboxOther.setClickable(true);
                        etOtherJob.setVisibility(View.INVISIBLE);
                        --flag;
                        System.out.println("------------------------" + flag);
                    }
                    if (!checked){
                        etOtherJob.setVisibility(View.INVISIBLE);
                    }

                } else {
                    if (flag >= 2) {
                        chkboxOther.setChecked(false);
                    }
                    Toast.makeText(JobSeletionActivity.this, "Maximum 3 jobs are allowed", Toast.LENGTH_SHORT).show();
                    etOtherJob.setVisibility(View.INVISIBLE);
                }

                break;

        }
    }
}