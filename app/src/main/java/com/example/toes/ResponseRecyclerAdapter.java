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

import java.util.List;

public class ResponseRecyclerAdapter extends RecyclerView.Adapter<ResponseRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<RecruiterResponseList> mData;
    Dialog myDialog;

    public ResponseRecyclerAdapter(Context context, List<RecruiterResponseList> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.requestresponselist,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_responce_accepted);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.workerResponseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv = myDialog.findViewById(R.id.worker_name_dialog);
                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());

                Button call_worker = myDialog.findViewById(R.id.worker_call_btn);
                call_worker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:7894561230"));    //Call worker(get number form database)
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
        holder.tv.setText(mData.get(position).getName());
        holder.tv1.setText(mData.get(position).getProfession());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

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
