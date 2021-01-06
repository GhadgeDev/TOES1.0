package com.example.toes;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRequestRecyclerAdapter extends RecyclerView.Adapter<ViewRequestRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<GetRecruiterViewRequestModel> mData;
    Dialog myDialog;
    Button AcceptBtn, RejectBtn;
    String fullWName;
    String wPhoneNumber;
    String wAddress;
    int status;
    int jbid;


    public ViewRequestRecyclerAdapter(Context context, List<GetRecruiterViewRequestModel> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.viewrequestlist, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        //Dialog
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_view_request);
        viewHolder.RecruiterViewRequestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String wFName = mData.get(viewHolder.getAdapterPosition()).getWokerFname();
                String wLName = mData.get(viewHolder.getAdapterPosition()).getWokerLname();

                fullWName = wFName +" "+ wLName;
                wPhoneNumber = mData.get(viewHolder.getAdapterPosition()).getWokerPhoneNo();
                wAddress = mData.get(viewHolder.getAdapterPosition()).getWokerAddress();

                String wAmount = mData.get(viewHolder.getAdapterPosition()).getAmount().toString();
                String jbTitle = mData.get(viewHolder.getAdapterPosition()).getJobTitle();

                TextView dialog_worker_name = myDialog.findViewById(R.id.view_worker_name_dialog);
                dialog_worker_name.setText(wFName + " " + wLName);

                TextView dialog_worker_amount = myDialog.findViewById(R.id.worker_amount);
                dialog_worker_amount.setText(wAmount);

                TextView dialog_worker_job = myDialog.findViewById(R.id.job_title);
                dialog_worker_job.setText(jbTitle);

                AcceptBtn = myDialog.findViewById(R.id.worker_accept_btn);
                AcceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status = 2;
                        Toast.makeText(mContext, "Worker hired", Toast.LENGTH_SHORT).show();
                        callToAcceptRejectApi();
                        myDialog.dismiss();
                    }
                });

                RejectBtn = myDialog.findViewById(R.id.worker_reject_btn);
                RejectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status = 3;
                        Toast.makeText(mContext, "Request declined", Toast.LENGTH_SHORT).show();
                        callToAcceptRejectApi();
                        myDialog.dismiss();
                    }
                });


                myDialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String wFName = mData.get(position).getWokerFname();
        String wLName = mData.get(position).getWokerLname();
        holder.tv_worker_name.setText(wFName + " " + wLName);
        holder.tv_worker_profession.setText(mData.get(position).getJobTitle());
        jbid = mData.get(position).getJbid();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_worker_name;
        private TextView tv_worker_profession;
        private LinearLayout RecruiterViewRequestList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_worker_name = itemView.findViewById(R.id.view_worker_name);
            tv_worker_profession = itemView.findViewById(R.id.view_worker_profession);
            RecruiterViewRequestList = itemView.findViewById(R.id.recruiter_view_request_list);
        }

    }
    JsonPlaceHolderApi accRejApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);

    public void callToAcceptRejectApi() {
        Call<GetAcpRejClickRecruiter> call = accRejApi.getAccRejClickRecruiter("token " + LoginActivity.token, status,jbid);
        call.enqueue(new Callback<GetAcpRejClickRecruiter>() {
            @Override
            public void onResponse(Call<GetAcpRejClickRecruiter> call, Response<GetAcpRejClickRecruiter> response) {
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
                    sendSmsToWorker(wPhoneNumber);
                }
            }

            @Override
            public void onFailure(Call<GetAcpRejClickRecruiter> call, Throwable t) {
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
        String msg = "Message From Toes" + "\n" + "Worker name: " + fullWName + "\n" + "Contact no: " + wPhoneNumber + "\n" + "Address: " + wAddress;
        String no = LoginActivity.userPhoneNumber;
        SmsManager sms = SmsManager.getDefault();    //android mobile sms manager
        sms.sendTextMessage(no,null,msg,pi,null);        //method to send sms

        Toast.makeText(mContext.getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
    }

    public void sendSmsToWorker(String wPhoneNumber) {

        //Messages
        //Creating intent of current activity/fragment/context
        Intent intent = new Intent(mContext.getApplicationContext(), WorkerViewRequestRecyclerAdapter.class);
        PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);

        //setting string and phone no to send message
        String msg = "Message From Toes" + "\n" + "Recruiter name: " + LoginActivity.userName + "\n" + "Contact no: " + LoginActivity.userPhoneNumber + "\n" + "Address: " + LoginActivity.userAddress;
        String no = wPhoneNumber;
        SmsManager sms = SmsManager.getDefault();    //android mobile sms manager
        sms.sendTextMessage(no, null, msg, pi, null);        //method to send sms

        Toast.makeText(mContext.getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
    }
}
