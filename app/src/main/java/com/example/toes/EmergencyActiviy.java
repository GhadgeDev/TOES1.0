package com.example.toes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmergencyActiviy extends AppCompatActivity {
    TextView txtName;
    EditText etCno;
    Button btnGo;

    String contact;
    String name = IdentityProofActivity.name;
    static int emergencyID;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_activiy);

        txtName = (TextView) findViewById(R.id.txtName);
        etCno = (EditText) findViewById(R.id.etEcontact);
        btnGo = (Button) findViewById(R.id.btnGetIn);
        txtName.setText(name);

        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("http://65.1.2.12/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        jsonPlaceHolderApi = retrofit1.create(JsonPlaceHolderApi.class);


        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etCno.getText().toString().isEmpty() || etCno.getText().toString().equals("")) {
                    Toast.makeText(EmergencyActiviy.this, "Please Enter Contact Number", Toast.LENGTH_SHORT).show();
                } else {
                    contact = etCno.getText().toString();
                    if (EditProfileActivity.eCo == true) {
                        txtName.setText(SelectRoleActivity.textUserlName);
                        System.out.println("textUserfName : _--------- " + SelectRoleActivity.textUserfName);
                        eUpdateEContact();


                    } else {
                        Call<EmergencyContact> postEContact = jsonPlaceHolderApi.postEmergencyContact(contact, IdentityProofActivity.uid);
                        postEContact.enqueue(new Callback<EmergencyContact>() {
                            @Override
                            public void onResponse(Call<EmergencyContact> call, Response<EmergencyContact> response) {
                                if (!response.isSuccessful()) {
                                    System.out.println("Response : _--------- " + response.code());
                                    System.out.println("Response M : _--------- " + response.message());

                                    return;
                                }
                                EmergencyContact contact = response.body();
                                EmergencyActiviy.emergencyID = contact.getId();
                                System.out.println("E Contact " + response.body().getContact_no());
                                Toast.makeText(EmergencyActiviy.this, "Details Saved successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EmergencyActiviy.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<EmergencyContact> call, Throwable t) {

                            }
                        });
                    }
                }


            }
        });


    }
    private void eUpdateEContact() {


        Call<EmergencyContact> getEContact = jsonPlaceHolderApi.getEmergencyContact("token " + LoginActivity.token, LoginActivity.userMeId);
        getEContact.enqueue(new Callback<EmergencyContact>() {
            @Override
            public void onResponse(Call<EmergencyContact> call, Response<EmergencyContact> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());

                    return;
                }
                    EmergencyContact ec = response.body();
                    EmergencyActiviy.emergencyID = ec.getId();

                    System.out.println("ID ----------------------------- "+emergencyID);
                    System.out.println("Cno ------------ "+ec.getContact_no());
                    System.out.println("user ------------ "+ec.getUser());

                Call<EmergencyContact> postEContact = jsonPlaceHolderApi.updateEmergencyContact(contact,ec.getId());
                postEContact.enqueue(new Callback<EmergencyContact>() {
                    @Override
                    public void onResponse(Call<EmergencyContact> call, Response<EmergencyContact> response) {
                        if (!response.isSuccessful()) {
                            System.out.println("Response : _--------- " + response.code());
                            System.out.println("Response M : _--------- " + response.message());

                            return;
                        }
                        System.out.println("E Contact " + response.body().getContact_no());
                        Toast.makeText(EmergencyActiviy.this, "Emergency contact updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EmergencyActiviy.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<EmergencyContact> call, Throwable t) {
                        System.out.println("Error M : _--------- " + t.getMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<EmergencyContact> call, Throwable t) {
                System.out.println("In button find job ------------ " + t.getMessage());
            }
        });


    }
}