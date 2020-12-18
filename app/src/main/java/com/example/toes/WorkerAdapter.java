package com.example.toes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> {

    private String[] workerN;
    private String[] visitingC;
    public WorkerAdapter(String[] workerN, String[] visitingC){
        this.workerN = workerN;
        this.visitingC = visitingC;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.worker_list_layout, parent, false);
        return new WorkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        String title = workerN[position];
        holder.wName.setText(title);

        String title1 = visitingC[position];
        holder.vCharges.setText(title1);
    }

    @Override
    public int getItemCount() {
        return workerN.length;
    }

    public class WorkerViewHolder extends RecyclerView.ViewHolder{
        ImageView imgIcon;
        TextView wName;
        TextView vCharges;
        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.worker_image);
            wName = itemView.findViewById(R.id.worker_name);
            vCharges = itemView.findViewById(R.id.worker_visiting_fee);
        }
    }
}
