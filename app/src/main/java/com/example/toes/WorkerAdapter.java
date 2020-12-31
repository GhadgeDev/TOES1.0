package com.example.toes;

import android.app.AppOpsManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> {

    Context mContext;
    List<GetSpecificWorkerModel> mData;

    private OnNoteListener mOnNoteListener;

    public WorkerAdapter(Context context, List<GetSpecificWorkerModel> data, OnNoteListener onNoteListener) {
        mContext = context;
        mData = data;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.worker_list_layout, parent, false);
        WorkerViewHolder viewHolder = new WorkerViewHolder(v, mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        holder.wName.setText(mData.get(position).getWorkerName());
        holder.vCharges.setText(mData.get(position).getVisitingCharges());
        holder.wExperience.setText(""+mData.get(position).getExperience());
        int a = mData.get(position).getWorkerId();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class WorkerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //ImageView imgIcon;
        TextView wName;
        TextView vCharges;
        TextView wExperience;

        OnNoteListener onNoteListener;

        public WorkerViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            //   imgIcon = itemView.findViewById(R.id.worker_image);           setting image of worker for recruiter to see in the list
            wName = itemView.findViewById(R.id.worker_name);
            vCharges = itemView.findViewById(R.id.worker_visiting_fee);
            wExperience = itemView.findViewById(R.id.experience);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
