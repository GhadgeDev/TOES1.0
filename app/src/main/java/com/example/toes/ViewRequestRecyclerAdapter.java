package com.example.toes;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewRequestRecyclerAdapter extends RecyclerView.Adapter<ViewRequestRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<GetRecruiterViewRequestModel> mData;
    Dialog myDialog;

    public ViewRequestRecyclerAdapter(Context context, List<GetRecruiterViewRequestModel> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.viewrequestlist,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        //Dialog
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_view_request);
        viewHolder.RecruiterViewRequestList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wFName = mData.get(viewHolder.getAdapterPosition()).getWokerFname();
                String wLName = mData.get(viewHolder.getAdapterPosition()).getWokerLname();
                String wAmount = mData.get(viewHolder.getAdapterPosition()).getAmount().toString();

                TextView dialog_worker_name = myDialog.findViewById(R.id.view_worker_name_dialog);
                dialog_worker_name.setText(wFName + " " + wLName);
                TextView dialog_worker_amount = myDialog.findViewById(R.id.worker_amount);
                dialog_worker_amount.setText(wAmount);
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
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
}
