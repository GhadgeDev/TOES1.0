package com.example.toes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkerResponseRecyclerAdapter extends RecyclerView.Adapter<WorkerResponseRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<WorkerResponseList> mData;
    Dialog myWorkerResponseDialog;

    public WorkerResponseRecyclerAdapter(Context context, List<WorkerResponseList> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.workerrequestresponselist,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);

        //Dialog
        myWorkerResponseDialog = new Dialog(mContext);
        myWorkerResponseDialog.setContentView(R.layout.dialog_request_accepted_workerside);

        vHolder.workerResponseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView recruiter_name = myWorkerResponseDialog.findViewById(R.id.recruiter_name_dialog);
                recruiter_name.setText(mData.get(vHolder.getAdapterPosition()).getrName());

                Button call_recruiter = myWorkerResponseDialog.findViewById(R.id.call_recruiter_btn);
                call_recruiter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:1234567890"));    //Call recruiter(get number form database)
                        mContext.startActivity(intent);
                    }
                });
                myWorkerResponseDialog.show();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv.setText(mData.get(position).getrName());
        holder.tv_requirement.setText(mData.get(position).getrRequirement());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private TextView tv_requirement;
        private LinearLayout workerResponseList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            workerResponseList = itemView.findViewById(R.id.inside_worker_response_list);
            tv = itemView.findViewById(R.id.response_list);
            tv_requirement = itemView.findViewById(R.id.response_list_requirement);
        }
    }
}
