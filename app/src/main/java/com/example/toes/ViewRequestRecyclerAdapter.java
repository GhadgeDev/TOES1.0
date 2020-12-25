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
    List<ViewRequestListModel> mData;
    Dialog myDialog;

    public ViewRequestRecyclerAdapter(Context context, List<ViewRequestListModel> data) {
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
                TextView dialog_worker_name = myDialog.findViewById(R.id.view_worker_name_dialog);
                dialog_worker_name.setText(mData.get(viewHolder.getAdapterPosition()).getWorker_name());
                //add experience, worker image, requested amount: like this
                myDialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tv_worker_name.setText(mData.get(position).getWorker_name());
        holder.tv_worker_profession.setText(mData.get(position).getWorker_profession());
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
