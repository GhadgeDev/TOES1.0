package com.example.toes;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.System.getString;

public class WorkerViewRequestRecyclerAdapter extends RecyclerView.Adapter<WorkerViewRequestRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<GetWorkerViewRequestModel> mData;
    Dialog myDialog;
    Button workerAcceptBtn;
    Button workerRejectBtn;
    String rFName;
    String rLName;
    String rFullName;
    String rAdd;
    String rPhoneNumber;
    int status;
    public int viewJob_id;

    public WorkerViewRequestRecyclerAdapter(Context context, List<GetWorkerViewRequestModel> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.workerviewrequestlist, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        //Dialog
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_view_request_workerside);

        /*get worker visiting charges, recruiter address, recruiter description*/

        viewHolder.workerViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewJob_id = mData.get(viewHolder.getAdapterPosition()).getJobId();

                rFName = mData.get(viewHolder.getAdapterPosition()).getRecruiterFname();
                rLName = mData.get(viewHolder.getAdapterPosition()).getRecruiterLname();
                String rDesc = mData.get(viewHolder.getAdapterPosition()).getJobDescription();

                rAdd = mData.get(viewHolder.getAdapterPosition()).getAddress();
                rPhoneNumber = mData.get(viewHolder.getAdapterPosition()).getRecruiterPhoneNo();
                rFullName = rFName + " " + rLName;

                TextView dialog_recruiter_name = myDialog.findViewById(R.id.view_request_recruiter_name_dialog);
                TextView dialog_recruiter_desc = myDialog.findViewById(R.id.view_request_recruiter_desc);
                TextView dialog_recruiter_add = myDialog.findViewById(R.id.view_request_recruiter_address);
                workerAcceptBtn = myDialog.findViewById(R.id.worker_accept_btn);
                workerRejectBtn = myDialog.findViewById(R.id.worker_reject_btn);


                dialog_recruiter_name.setText(rFName + " " + rLName);
                dialog_recruiter_desc.setText(rDesc);
                dialog_recruiter_add.setText(rAdd);
                myDialog.show();

                workerAcceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status = 2;
                        Toast.makeText(mContext, "Job accepted", Toast.LENGTH_SHORT).show();
                        callAcceptRejectApi();
                        myDialog.dismiss();
                    }
                });

                workerRejectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status = 3;
                        Toast.makeText(mContext, "Job Rejected", Toast.LENGTH_SHORT).show();
                        callAcceptRejectApi();
                        myDialog.dismiss();
                    }
                });
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String rFname = mData.get(position).getRecruiterFname();
        String rLName = mData.get(position).getRecruiterLname();
        holder.viewRecruiterNameList.setText(rFname + " " + rLName);
        holder.viewRecruiterRequirementList.setText(mData.get(position).getJobTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView viewRecruiterNameList;
        private TextView viewRecruiterRequirementList;
        private LinearLayout workerViewList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            viewRecruiterNameList = itemView.findViewById(R.id.view_recruiter_name_list);
            viewRecruiterRequirementList = itemView.findViewById(R.id.view_recruiter_requirement_list);
            workerViewList = itemView.findViewById(R.id.inside_worker_view_request_list);
        }
    }

    JsonPlaceHolderApi acceptRejectApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);

    public void callAcceptRejectApi() {
        Call<GetAcceptRejectBtnClick> call = acceptRejectApi.getAcceptRejectBtnClick("token " + LoginActivity.token, status, viewJob_id);
        call.enqueue(new Callback<GetAcceptRejectBtnClick>() {
            @Override
            public void onResponse(Call<GetAcceptRejectBtnClick> call, Response<GetAcceptRejectBtnClick> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(mContext, "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                }


                //We’ll check the permission is granted or not . If not granted the displaying message
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                    Toast toast = Toast.makeText(mContext, "Give SMS permission to this app from setting then again accept job", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    sendSmsToMySelf();
                    sendSmsToRecruiter(rPhoneNumber);
                }


            }


            @Override
            public void onFailure(Call<GetAcceptRejectBtnClick> call, Throwable t) {
                Toast toast = Toast.makeText(mContext, "Please Check your Internet Connection", Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }


    public void sendSmsToMySelf() {

        //Messages
        //Creating intent of current activity/fragment/context
        Intent intent = new Intent(mContext.getApplicationContext(), WorkerViewRequestRecyclerAdapter.class);
        PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);

        //setting string and phone no to send message
        String msg = "Message From Toes" + "\n" + "Recruiter name: " + rFullName + "\n" + "Contact no: " + rPhoneNumber + "\n" + "Address: " + rAdd;
        String no = LoginActivity.userPhoneNumber;
        SmsManager sms = SmsManager.getDefault();    //android mobile sms manager
        sms.sendTextMessage(no, null, msg, pi, null);        //method to send sms
        Toast.makeText(mContext.getApplicationContext(), "Message Sent successfully!",Toast.LENGTH_LONG).show();
    }

    public void sendSmsToRecruiter(String wPhoneNumber) {

        //Messages
        //Creating intent of current activity/fragment/context
        Intent intent = new Intent(mContext.getApplicationContext(), WorkerViewRequestRecyclerAdapter.class);
        PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);

        //setting string and phone no to send message
        String msg = "Message From Toes" + "\n" + "Worker name: " + LoginActivity.userName + "\n" + "Contact no: " + LoginActivity.userPhoneNumber + "\n" + "Address: " + LoginActivity.userAddress;
        String no = LoginActivity.userPhoneNumber;
        SmsManager sms = SmsManager.getDefault();    //android mobile sms manager
        sms.sendTextMessage(no, null, msg, pi, null);        //method to send sms

        Toast.makeText(mContext.getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
    }
}
