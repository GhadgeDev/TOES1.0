package com.example.toes;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
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

public class ResponseRecyclerAdapter extends RecyclerView.Adapter<ResponseRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<GetRecruiterResponses> mData;
    Dialog myDialog;

    public ResponseRecyclerAdapter(Context context, List<GetRecruiterResponses> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.requestresponselist, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_responce_accepted);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.workerResponseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv = myDialog.findViewById(R.id.worker_name_dialog);
                TextView dialog_request_status = myDialog.findViewById(R.id.request_status);

                String fName = mData.get(vHolder.getAdapterPosition()).getWorkerFname();
                String lName = mData.get(vHolder.getAdapterPosition()).getWorkerLname();
                String Contact = mData.get(vHolder.getAdapterPosition()).getContactNo();
                String status = mData.get(vHolder.getAdapterPosition()).getStatus();

                dialog_name_tv.setText(fName + " " + lName);
                dialog_request_status.setText(status);

                Button call_worker = myDialog.findViewById(R.id.worker_call_btn);
                call_worker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + Contact));    //Call worker(get number form database)
                        mContext.startActivity(intent);
                    }
                });
                myDialog.show();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String fName = mData.get(position).getWorkerFname();
        String lName = mData.get(position).getWorkerLname();
        String profession = mData.get(position).getJobTitle();
        holder.tv.setText(fName +" "+lName);
        holder.tv1.setText(profession);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private TextView tv1;
        private LinearLayout workerResponseList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            workerResponseList = itemView.findViewById(R.id.recruiter_response_list);
            tv = itemView.findViewById(R.id.worker_name);
            tv1 = itemView.findViewById(R.id.worker_profession);
        }
    }
}
