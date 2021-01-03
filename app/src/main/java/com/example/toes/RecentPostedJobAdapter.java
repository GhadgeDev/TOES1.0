package com.example.toes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecentPostedJobAdapter extends RecyclerView.Adapter<RecentPostedJobAdapter.RecentPostedJobHolder>{

    private List<String> data;
    private String[] data1;

    public RecentPostedJobAdapter(List<String> data) {
        this.data = data;
    }

    public RecentPostedJobAdapter(String[] data1) {
        this.data1 = data1;
    }

    @NonNull
    @Override
    public RecentPostedJobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext()) ;
        View view = inflater.inflate(R.layout.list_item_posted_jobs,parent,false);
        return new RecentPostedJobHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecentPostedJobHolder holder, int position) {

        //String title = data.get(position);
        String title = data1[position];
        holder.txtDesc.setText(title);


    }

    @Override
    public int getItemCount() {

        //return data.size();
        return data1.length;
    }

    public class RecentPostedJobHolder extends RecyclerView.ViewHolder{

        ImageView imgIcon;
        TextView txtDesc;

        public RecentPostedJobHolder(@NonNull View itemView) {
            super(itemView);

            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
            txtDesc = (TextView) itemView.findViewById(R.id.txtJobDescription);

        }
    }
}
