package com.example.toes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LogoutDialog extends DialogFragment {
    private TextView mTextSureMsg;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.logout_dialog,null);


        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("http://tcp-api.herokuapp.com/token/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit1.create(JsonPlaceHolderApi.class);



        builder.setView(view).setTitle("Logout").setIcon(R.drawable.icon)
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing will happen
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    int flag = 0;

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        retrofit2.Call<User> call = jsonPlaceHolderApi.logOut("Token "+LoginActivity.token);

                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(retrofit2.Call<User> call, Response<User> response) {
                                if (!response.isSuccessful()) {
                                    System.out.println("Response : _--------- " + response.code());
                                    System.out.println("Response M : _--------- " + response.message());
                                    return;
                                }
                                System.out.println("----------------------------------------------------");
                               // goToLoginActivity();
                                //  Post postResponse = response.body();
                                flag = 1;
                                System.out.println("Flag :------------------ "+flag);





                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                System.out.println("fail : _--------- " + t.getMessage());

                            }
                        });


                        Intent intent = new Intent(getActivity() ,LoginActivity.class);
                        LoginActivity.sp.edit().putBoolean("logged",false).apply();
                        LoginActivity.sp.edit().putString("token","").apply();
                        LoginActivity.sp.edit().putInt("uid",0).apply();
                        Toast toast = Toast.makeText(getActivity(), "Logged out successfully ! " , Toast.LENGTH_SHORT);
                        View view = toast.getView();
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);

                        toastMessage.setTextColor(Color.parseColor("#2E7D32"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    }
                });
        mTextSureMsg = view.findViewById(R.id.sure_msg);
        return builder.create();
    }

}
