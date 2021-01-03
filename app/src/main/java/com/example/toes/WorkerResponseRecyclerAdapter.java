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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkerResponseRecyclerAdapter extends RecyclerView.Adapter<WorkerResponseRecyclerAdapter.MyViewHolder> {

    Context mContext;
    List<GetWorkerResponses> mData;
    Dialog myWorkerResponseDialog;

    public WorkerResponseRecyclerAdapter(Context context, List<GetWorkerResponses> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.workerrequestresponselist, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        //Dialog
        myWorkerResponseDialog = new Dialog(mContext);
        myWorkerResponseDialog.setContentView(R.layout.dialog_request_accepted_workerside);

        vHolder.workerResponseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView recruiter_name = myWorkerResponseDialog.findViewById(R.id.recruiter_name_dialog);
                TextView status = myWorkerResponseDialog.findViewById(R.id.status);

                String rFname = mData.get(vHolder.getAdapterPosition()).getRecruiterFname();
                String rLname = mData.get(vHolder.getAdapterPosition()).getRecruiterLname();
                String rContact = mData.get(vHolder.getAdapterPosition()).getContactNo();
                String rStatus = mData.get(vHolder.getAdapterPosition()).getStatus();

                recruiter_name.setText(rFname + " " + rLname);

                status.setText(rStatus);

                Button call_recruiter = myWorkerResponseDialog.findViewById(R.id.call_recruiter_btn);
                call_recruiter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + rContact));    //Call recruiter(get number form database)
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
        String rFname = mData.get(position).getRecruiterFname();
        String rLname = mData.get(position).getRecruiterLname();
        holder.tv.setText(rFname + " " + rLname);
        holder.tv_requirement.setText(mData.get(position).getJobTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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
