package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText editFname, editLname, editAddress;
    String fname, lname, contact, address;

    RadioButton rBtnMale, rBtnFemale, rBtnOther;
    String gender;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editFname = (EditText) findViewById(R.id.edit_profile_fname);
        editLname = (EditText) findViewById(R.id.edit_profile_lname);
        editAddress = (EditText) findViewById(R.id.edit_profile_address);


        rBtnMale = findViewById(R.id.edit_profile_male);
        rBtnFemale = findViewById(R.id.edit_profile_female);
        rBtnOther = findViewById(R.id.edit_profile_other);

        btnSave = findViewById(R.id.btn_save_profile);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = editFname.getText().toString();
                lname = editLname.getText().toString();
                address = editAddress.getText().toString();

                if (rBtnMale.isChecked()) {
                    gender = rBtnMale.getText().toString();
                } else if (rBtnFemale.isChecked()) {
                    gender = rBtnFemale.getText().toString();
                } else {
                    gender = rBtnOther.getText().toString();
                }

                if (editFname.getText().toString().isEmpty() || editLname.getText().toString().isEmpty() || editAddress.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill all fields !", Toast.LENGTH_SHORT).show();
                }
                if (rBtnMale.getText().toString().isEmpty() || rBtnFemale.getText().toString().isEmpty() || rBtnOther.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill all fields !", Toast.LENGTH_SHORT).show();
                }

                JsonPlaceHolderApi editProfile = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
                Call<Post> call = editProfile.editProfile("token " + LoginActivity.token, LoginActivity.userMeId, fname, lname, gender, address);
                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (!response.isSuccessful()) {
                            Toast toast = Toast.makeText(EditProfileActivity.this, "ERROR :( ", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }
                        Toast toast = Toast.makeText(EditProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(EditProfileActivity.this, SelectRoleActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Toast toast = Toast.makeText(EditProfileActivity.this, "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.RED);
                        toast.show();
                    }
                });
            }
        });
    }
}