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

import java.net.ContentHandler;
import java.util.List;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> {

    Context mContext;
    List<WorkerListLayoutModel> mData;

    private OnNoteListener mOnNoteListener;

    public WorkerAdapter(Context context, List<WorkerListLayoutModel> data, OnNoteListener onNoteListener) {
        mContext = context;
        mData = data;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.worker_list_layout,parent,false);
        WorkerViewHolder viewHolder = new WorkerViewHolder(v,mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        holder.wName.setText(mData.get(position).getWorker_name());
        holder.vCharges.setText(mData.get(position).getWorker_fees());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class WorkerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //ImageView imgIcon;
        TextView wName;
        TextView vCharges;

        OnNoteListener onNoteListener;
        public WorkerViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
         //   imgIcon = itemView.findViewById(R.id.worker_image);           setting image of worker for recruiter to see in the list
            wName = itemView.findViewById(R.id.worker_name);
            vCharges = itemView.findViewById(R.id.worker_visiting_fee);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
