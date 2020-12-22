package com.example.toes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecruiterAdapter extends RecyclerView.Adapter<RecruiterAdapter.RecruiterViewHolder> {
    Context mContext;
    List<RecruiterListModel> mData;

    public RecruiterAdapter(Context mContext, List<RecruiterListModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecruiterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.job_list_layout,parent,false);
        RecruiterViewHolder vHolder = new RecruiterViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecruiterViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_requirement.setText(mData.get(position).getRequirement());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class RecruiterViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_requirement;

        public RecruiterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.recruiter_name);
            tv_requirement = itemView.findViewById(R.id.recruiter_requirement);
        }
    }
}
