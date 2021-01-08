package com.example.toes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class
SelectRoleActivity extends AppCompatActivity {
    SharedPreferences prf;
    TextView txtName;

    public static String textUserfName;
    public static String textUserlName;

    String selectedLanguage;
    Button btnFindJob, btnFindWorker;
    String token = (String) LoginActivity.token;
    public static int id;
    static boolean userPresent = false;
    ArrayList<String> tokenDetails = new ArrayList<>();
    static int user;
    static String gender;
    public static int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    FloatingActionButton callBtn;


    String latitude, longitude;
    private static final int REQUEST_LOCATION = 1;
    private FusedLocationProviderClient fusedLocationClient;
    double lat, longi;
    String addr,locality,country;
    static String eContact;

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", getClass().getName());
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        FusedLocationProviderClient mFusedLocationClient;
        Intent intent = getIntent();
        tokenDetails = intent.getStringArrayListExtra("tokenDetails");
        selectedLanguage = intent.getStringExtra(Intent.EXTRA_TEXT);
        System.out.println("Token :---------- " + LoginActivity.token);
        System.out.println("Details :---------- " + LoginActivity.tokenDetail);

        btnFindJob = (Button) findViewById(R.id.btnFindJob);
        btnFindWorker = (Button) findViewById(R.id.btnFindWorker);
        txtName = (TextView) findViewById(R.id.txtname);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        token = "Token " + LoginActivity.token;

        callBtn = (FloatingActionButton) findViewById(R.id.btnCall);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if (ActivityCompat.checkSelfPermission(SelectRoleActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When Permission granted
            getLocation();
        } else {
            //When permission denied
            ActivityCompat.requestPermissions(SelectRoleActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    44);
        }
        getLocation();

        //asking the permission for sending sms
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "Grant the permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            }

        } else {

        }


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

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit1.create(JsonPlaceHolderApi.class);

        Call<Post> call = jsonPlaceHolderApi.getPost(token);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {

                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());

                    return;
                }

                Post postResponse = response.body();
                id = postResponse.getId();
                System.out.println("Code :------------------- " + response.code());
                String content = "";
                content += "name : " + postResponse.getFirst_name() + "\n";
                content += "lName : " + postResponse.getLast_name() + "\n";

                gender = postResponse.getGender();
                if (gender.equals("female") || gender.equals("Female")) {

                    callBtn.setVisibility(View.VISIBLE);

                } else {
                    callBtn.setVisibility(View.GONE);
                }
                textUserfName = postResponse.getFirst_name();
                textUserlName = postResponse.getLast_name();
                txtName.setText(postResponse.getFirst_name() + ",");
                System.out.println("Data : _--------- " + content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("Filed in selectRole : " + t.getMessage());

            }
        });

        btnFindWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent recruiter = new Intent(SelectRoleActivity.this,RecruiterHomeActivity.class);
                startActivity(recruiter);*/
                Intent recruiter = new Intent(SelectRoleActivity.this, RecentPostedJobActivity.class);
                startActivity(recruiter);
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<EmergencyContact> getEContact = jsonPlaceHolderApi.getEmergencyContact("token " + LoginActivity.token, LoginActivity.userMeId);
                getEContact.enqueue(new Callback<EmergencyContact>() {
                    @Override
                    public void onResponse(Call<EmergencyContact> call, Response<EmergencyContact> response) {
                        if (!response.isSuccessful()) {
                            System.out.println("Response : _--------- " + response.code());
                            System.out.println("Response M : _--------- " + response.message());

                            return;
                        }

                        Toast.makeText(SelectRoleActivity.this, "Details Saved successfully", Toast.LENGTH_SHORT).show();
                        eContact = response.body().getContact_no();
                        Intent intent = new Intent(SelectRoleActivity.this, WorkerViewRequestRecyclerAdapter.class);
                        PendingIntent pi = PendingIntent.getActivity(SelectRoleActivity.this.getApplicationContext(), 0, intent, 0);

                        //setting string and phone no to send message
                        String msg = "Message From Toes : " + "\n" + "Hey " + textUserfName + " " + textUserlName +
                                " here," + "\n" + "I need your help,I am in trouble! \n"+"Team TOES is sending location...";
                        String locMsg = textUserfName+" "+textUserlName+"'s Location is \nLatitude : "+lat
                                +"\nLogitude : "+longi+"\n"
                                + "\nLocality : "
                                +locality
                                +"\nCountry : "+country
                                +"\n"+" Address : "+addr;

                        System.out.println("Location : ---------- "+msg);
                        SmsManager sms = SmsManager.getDefault();    //android mobile sms manager
                        sms.sendTextMessage(eContact, null, msg, pi, null);        //method to send sms
                        sms.sendTextMessage(eContact, null, locMsg, pi, null);        //method to send sms
                        Toast.makeText(SelectRoleActivity.this.getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                        System.out.println("E Contact " + response.body().getContact_no());
                    }

                    @Override
                    public void onFailure(Call<EmergencyContact> call, Throwable t) {
                        System.out.println("In button find job ------------ " + t.getMessage());
                    }
                });
            }

        });

        btnFindJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<List<Post>> call = jsonPlaceHolderApi.getId(token);

                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        if (!response.isSuccessful()) {

                            System.out.println("Response : _--------- " + response.code());
                            System.out.println("Response M : _--------- " + response.message());

                            return;
                        }

                        List<Post> posts = response.body();

                        System.out.println("Code :------------------- " + response.code());
                        for (Post post : posts) {
                            user = post.getUser();
                            if (user == id) {
                                userPresent = true;
                                break;
                            } else {
                                userPresent = false;
                            }
                        }

                        if (userPresent) {
                            Intent recruiter = new Intent(SelectRoleActivity.this, SelectJobActivity.class);
                            startActivity(recruiter);
                        } else {
                            Toast toast = Toast.makeText(SelectRoleActivity.this, "Welcome to TOES!\nPlease Enter your Details ", Toast.LENGTH_SHORT);
                            View view = toast.getView();
                            TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.parseColor("#2E7D32"));
                            toast.show();
                            Intent recruiter = new Intent(SelectRoleActivity.this, JobSeletionActivity.class);
                            startActivity(recruiter);
                        }

                        System.out.println("User Data : _---------------- " + user);
                        System.out.println("User present : _---------------- " + userPresent);
                        System.out.println("Id Data : _---------------- " + id);
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        System.out.println("In button find job ------------ " + t.getMessage());
                    }
                });
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location = task.getResult();
                if(location != null){


                    //Initializing address list
                    try {
                        //Initialize geocode
                        Geocoder geocoder = new Geocoder(SelectRoleActivity.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);

                        //setting la and logi
                        lat = addresses.get(0).getLatitude();
                        longi = addresses.get(0).getLongitude();
                        locality = addresses.get(0).getLocality();
                        country = addresses.get(0).getCountryName();
                        addr = addresses.get(0).getAdminArea();
                        System.out.println("Location : ----------------------------- "+lat  );
                        System.out.println("Location : ----------------------------- "+ longi );


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menues,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.menue_profile:
                Intent Profileintent = new Intent(SelectRoleActivity.this,ProfileActivity.class);
                Profileintent.putExtra(Intent.EXTRA_TEXT,token);
                startActivity(Profileintent);
                break;
            case R.id.menu_logout:
                LogoutDialog logoutDialog = new LogoutDialog();
                logoutDialog.show(getSupportFragmentManager(),"logout dialog");
                break;

        }

        return true;
    }



}