package com.example.toes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteDilouge extends DialogFragment {
    private TextView mTextSureMsg;
    ArrayList<String> jobs = new ArrayList<>();
    private String category_1;
    private String category_1_vc;
    private String category_1_exp;

    private String category_2;
    private String category_2_vc;
    private String category_2_exp;

    private String category_3;
    private String category_3_vc;
    private String category_3_exp;

    static boolean delete = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_dilouge,null);

        ProfileActivity pf = new ProfileActivity();


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


        builder.setView(view).setTitle("Delete Profession")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //DeleteDilouge.delete = false;
                       // ProfileActivity.updateJob = true;
                        //Intent editJobi = new Intent(getActivity(),JobSeletionActivity.class);
                       // startActivity(editJobi);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteDilouge.delete = true;
                        if (ProfileActivity.jobs.size() > 0){
                            Call<WorkerJobDetails> delete = jsonPlaceHolderApi.deleteProfession1("token "+LoginActivity.token, ProfileActivity.updateId);
                            delete.enqueue(new Callback<WorkerJobDetails>() {
                                @Override
                                public void onResponse(Call<WorkerJobDetails> call, Response<WorkerJobDetails> response) {
                                    if (!response.isSuccessful()) {
                                        System.out.println("Response : _--------- " + response.code());
                                        System.out.println("Response M : _--------- " + response.message());
                                        return;
                                    }
                                    System.out.println("Sucessfully deleted :-------------- ");
                                }

                                @Override
                                public void onFailure(Call<WorkerJobDetails> call, Throwable t) {
                                    System.out.println("fail : _--------- " + t.getMessage());
                                }
                            });


                            Intent editJobi = new Intent(getActivity(),JobSeletionActivity.class);
                            startActivity(editJobi);
                        }else{
                            DeleteDilouge.delete = true;
                            Intent editJobi = new Intent(getActivity(),JobSeletionActivity.class);
                            startActivity(editJobi);
                        }
                    }
                })  .setNeutralButton("No Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mTextSureMsg = view.findViewById(R.id.sure_msg);
        return builder.create();
    }
}