package com.example.toes;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdentityProofActivity extends AppCompatActivity {
    Calendar myCal = Calendar.getInstance();
    public static final int PICK_IMAGE = 1;

    CircleImageView circleImageView;
    TextView txtDilouge,txtName,txtContact,txtAddr,txtDob,txtGender,txtNext;
    EditText etnewPass,etCPass,etAadhar,etCAadhar;
    Button btnNext;
    String selectedLanguage;
   // String selectedImagePath;
    //String args[];

    String newPass = "",cPass = "",aadharNo = "",cAadharNo ="";

    Bitmap bitmap=null;

    ArrayList<String> details2 = new ArrayList<>();

    JsonPlaceHolderApi jsonPlaceHolderApi;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_proof);
        circleImageView = (CircleImageView)findViewById(R.id.cimgprofile);

        etnewPass = (EditText)findViewById(R.id.etPass);
        etCPass = (EditText)findViewById(R.id.etCPass);
        etAadhar = (EditText)findViewById(R.id.etAdhar);
        etCAadhar = (EditText)findViewById(R.id.etCAdhar);



        btnNext = (Button)findViewById(R.id.btnGo);

        Intent intent = getIntent();
        String[] args = intent.getStringArrayExtra("args");

        details2  = intent.getStringArrayListExtra("details1");
        selectedLanguage = args[0];
      //  selectedImagePath = args[1];

        System.out.println("in IdentityProof "+details2);


        //For Server Connectivity
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient  = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder().
                baseUrl("http://52.201.220.252/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newPass = etnewPass.getText().toString();
                cPass = etCPass.getText().toString();
                aadharNo = etAadhar.getText().toString();
                cAadharNo = etCAadhar.getText().toString();
                if (newPass.equals("") || cPass.equals("") || aadharNo.equals("") || cAadharNo.equals("")){
                    Toast toast = Toast.makeText(IdentityProofActivity.this, "Fill All Details ! ", Toast.LENGTH_SHORT);
                    View view =toast.getView();
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();

                }else if (!newPass.equals(cPass)){
                    Toast toast = Toast.makeText(IdentityProofActivity.this, "Please check your confirm password\nPassword does not match ! ", Toast.LENGTH_SHORT);
                    View view =toast.getView();
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();

                }
                else if (!aadharNo.equals(cAadharNo)){
                    Toast toast = Toast.makeText(IdentityProofActivity.this, "Please check your aadhar card number\nIt does not match ! ", Toast.LENGTH_SHORT);
                    View view =toast.getView();
                    TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.RED);
                    toast.show();

                }else {
                    String phone = details2.get(2);
                    System.out.println("-------------------Phone-----------------"+phone);
                    Call<Post> call = jsonPlaceHolderApi.createUser(false,
                            false,
                            details2.get(0), details2.get(1), "TOES@" + details2.get(2), newPass, details2.get(4), details2.get(5),
                            aadharNo, SignUpActivity.encodedImg , details2.get(3), details2.get(2), cPass);

                    System.out.println("--------------------------------------------------------------------");

                    call.enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            if (!response.isSuccessful()) {
                                System.out.println("Response : _--------- " + response.code());
                                System.out.println("Response M : _--------- " + response.message());

                                return;
                            }

                            Toast toast = Toast.makeText(IdentityProofActivity.this, "Congratulation !\n You are connected with TOES successfully.", Toast.LENGTH_SHORT);
                            View view =toast.getView();
                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.parseColor("#2E7D32"));
                            toast.show();

                            Post postResponse = response.body();
                            System.out.println("Code :------------------- " + response.code());
                            String content = "";
                            content += "name : " + postResponse.getfName() + "\n";
                            content += "lName : " + postResponse.getlName() + "\n";
                            content += "Contact : " + postResponse.getPhone() + "\n";
                            content += "Address : " + postResponse.getAddress() + "\n";
                            content += "Adhar : " + postResponse.getAdharNo() + "\n";
                            content += "Dob : " + postResponse.getDob() + "\n";
                            content += "gender : " + postResponse.getGender() + "\n";
                            content += "username : " + postResponse.getUsername() + "\n";
                            content += "profile_image : " + postResponse.getProfileImage() + "\n";
                            System.out.println("Data : _--------- " + content);

                            Intent go = new Intent(IdentityProofActivity.this,LoginActivity.class);
                            go.putExtra(Intent.EXTRA_TEXT,selectedLanguage);
                            startActivity(go);

                        }


                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {
                            Toast toast = Toast.makeText(IdentityProofActivity.this, "You are disconnected from internet\nOr Server is under maintenance", Toast.LENGTH_SHORT);
                            View view =toast.getView();
                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.RED);
                            toast.show();
                            System.out.println("fail : _--------- " + t.getMessage());

                        }
                    });


                }


            }
        });



    }

    public void onCheck(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.cbShowPass:
                if (checked){
                    etnewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etCPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{

                    etnewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etCPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;


        }
    }

}