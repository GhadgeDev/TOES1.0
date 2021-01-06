package com.example.toes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecentPostedJobAdapter extends RecyclerView.Adapter<RecentPostedJobAdapter.RecentPostedJobHolder> {

    private List<GetRecruiterJobDetails> data;
    private OnPostedJobListener mOnPostedJobListener;
    Context context;
    int jbId;

    public RecentPostedJobAdapter(Context context, List<GetRecruiterJobDetails> data, OnPostedJobListener onPostedJobListener) {
        this.context = context;
        this.data = data;

        this.mOnPostedJobListener = onPostedJobListener;
    }

    @NonNull
    @Override
    public RecentPostedJobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(context).inflate(R.layout.list_item_posted_jobs, parent, false);
        RecentPostedJobHolder viewHolder = new RecentPostedJobHolder(v, mOnPostedJobListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentPostedJobHolder holder, int position) {

        String jobDesc = data.get(position).getJob_Description();

        holder.txtDesc.setText(jobDesc);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class RecentPostedJobHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgIcon;
        TextView txtDesc;
        OnPostedJobListener onPostedJobListener;

        public RecentPostedJobHolder(@NonNull View itemView, OnPostedJobListener onPostedJobListener) {
            super(itemView);

            imgIcon = itemView.findViewById(R.id.imgIcon);
            txtDesc = itemView.findViewById(R.id.txtJobDescription);
            this.onPostedJobListener = onPostedJobListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onPostedJobListener.onJobClick(getAdapterPosition());
        }
    }

    public interface OnPostedJobListener{
        void onJobClick(int position);
    }
}