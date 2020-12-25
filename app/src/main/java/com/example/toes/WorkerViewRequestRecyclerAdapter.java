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

public class WorkerViewRequestRecyclerAdapter extends RecyclerView.Adapter<WorkerViewRequestRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<WorkerViewRequestListModel> mData;
    Dialog myDialog;

    public WorkerViewRequestRecyclerAdapter(Context context, List<WorkerViewRequestListModel> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.workerviewrequestlist,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);

        //Dialog
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_view_request_workerside);

        /*get worker visiting charges, recruiter address, recruiter description*/

        viewHolder.workerViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_recruiter_name = myDialog.findViewById(R.id.view_request_recruiter_name_dialog);
                dialog_recruiter_name.setText(mData.get(viewHolder.getAdapterPosition()).getVrName());
                myDialog.show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.viewRecruiterNameList.setText(mData.get(position).getVrName());
        holder.viewRecruiterRequirementList.setText(mData.get(position).getVrRequirement());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
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
}
