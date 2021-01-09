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

    EditText editFname, editLname, editAddress,etEcno;
    String fname, lname, contact, address;

    RadioButton rBtnMale, rBtnFemale, rBtnOther;
    String gender;
    String eno ;
    Button btnSave;
    JsonPlaceHolderApi editProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editFname = (EditText) findViewById(R.id.edit_profile_fname);
        editLname = (EditText) findViewById(R.id.edit_profile_lname);
        editAddress = (EditText) findViewById(R.id.edit_profile_address);
        etEcno = (EditText) findViewById(R.id.edit_EmergencyContact);


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
                //    etEcno.setVisibility(View.INVISIBLE);
                } else if (rBtnFemale.isChecked()) {
                    gender = rBtnFemale.getText().toString();
                  //  etEcno.setVisibility(View.VISIBLE);
                    //eUpdateEContact();
                } else {
                    gender = rBtnOther.getText().toString();
                  //  etEcno.setVisibility(View.INVISIBLE);
                }

                if (editFname.getText().toString().isEmpty() || editLname.getText().toString().isEmpty() || editAddress.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill all fields !", Toast.LENGTH_SHORT).show();
                }
                if (rBtnMale.getText().toString().isEmpty() || rBtnFemale.getText().toString().isEmpty() || rBtnOther.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfileActivity.this, "Please fill all fields !", Toast.LENGTH_SHORT).show();
                }

                 editProfile = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
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
                    //    if(gender.equals("female") || gender.equals("Female")){
                        //    eUpdateEContact();

                          //  Intent intent = new Intent(EditProfileActivity.this, SelectRoleActivity.class);
                         //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                         //   startActivity(intent);

                     //   }else {
                            Intent intent = new Intent(EditProfileActivity.this, SelectRoleActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                    //    }
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

    private void eUpdateEContact() {
        if(etEcno.getText().toString().isEmpty() || etEcno.getText().toString().equals("")){
            Toast.makeText(EditProfileActivity.this,"Please Enter Contact Number",Toast.LENGTH_SHORT).show();
        }else {
            String eContact = etEcno.getText().toString();
            Call<EmergencyContact> postEContact = editProfile.updateEmergencyContact(eContact, LoginActivity.userMeId);
            postEContact.enqueue(new Callback<EmergencyContact>() {
                @Override
                public void onResponse(Call<EmergencyContact> call, Response<EmergencyContact> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("Response : _--------- " + response.code());
                        System.out.println("Response M : _--------- " + response.message());

                        return;
                    }
                    System.out.println("E Contact " + response.body().getContact_no());
                    Toast.makeText(EditProfileActivity.this, "Emergency contact updated successfully", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(Call<EmergencyContact> call, Throwable t) {

                }
            });
        }
    }
}