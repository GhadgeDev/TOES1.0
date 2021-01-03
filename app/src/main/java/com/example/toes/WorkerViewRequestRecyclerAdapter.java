package com.example.toes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerViewRequestRecyclerAdapter extends RecyclerView.Adapter<WorkerViewRequestRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<GetWorkerViewRequestModel> mData;
    Dialog myDialog;
    Button workerAcceptBtn;
    Button workerRejectBtn;
    int status;
    public int viewJob_id;

    public WorkerViewRequestRecyclerAdapter(Context context, List<GetWorkerViewRequestModel> data) {
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
                viewJob_id = mData.get(viewHolder.getAdapterPosition()).getJobId();

                String rFName = mData.get(viewHolder.getAdapterPosition()).getRecruiterFname();
                String rLName = mData.get(viewHolder.getAdapterPosition()).getRecruiterLname();
                String rDesc = mData.get(viewHolder.getAdapterPosition()).getJobDescription();
                String rAdd = mData.get(viewHolder.getAdapterPosition()).getAddress();

                TextView dialog_recruiter_name = myDialog.findViewById(R.id.view_request_recruiter_name_dialog);
                TextView dialog_recruiter_desc = myDialog.findViewById(R.id.view_request_recruiter_desc);
                TextView dialog_recruiter_add = myDialog.findViewById(R.id.view_request_recruiter_address);
                workerAcceptBtn = myDialog.findViewById(R.id.worker_accept_btn);
                workerRejectBtn = myDialog.findViewById(R.id.worker_reject_btn);


                dialog_recruiter_name.setText(rFName + " " + rLName);
                dialog_recruiter_desc.setText(rDesc);
                dialog_recruiter_add.setText(rAdd);
                myDialog.show();

                workerAcceptBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status = 2;
                        Toast.makeText(mContext,"Job accepted",Toast.LENGTH_SHORT).show();
                        callAcceptRejectApi();
                        myDialog.dismiss();
                    }
                });

                workerRejectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        status = 3;
                        Toast.makeText(mContext,"Job Rejected",Toast.LENGTH_SHORT).show();
                        callAcceptRejectApi();
                        myDialog.dismiss();
                    }
                });
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String rFname = mData.get(position).getRecruiterFname();
        String rLName = mData.get(position).getRecruiterLname();
        holder.viewRecruiterNameList.setText(rFname + " " + rLName);
        holder.viewRecruiterRequirementList.setText(mData.get(position).getJobTitle());
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

    JsonPlaceHolderApi acceptRejectApi = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
    public void callAcceptRejectApi(){
        Call<GetAcceptRejectBtnClick> call = acceptRejectApi.getAcceptRejectBtnClick("token " + LoginActivity.token,status,viewJob_id);
        call.enqueue(new Callback<GetAcceptRejectBtnClick>() {
            @Override
            public void onResponse(Call<GetAcceptRejectBtnClick> call, Response<GetAcceptRejectBtnClick> response) {
                if(!response.isSuccessful()) {
                    Toast toast = Toast.makeText(mContext, "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                }
                Toast toast = Toast.makeText(mContext, "Operation Successful !", Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<GetAcceptRejectBtnClick> call, Throwable t) {
                Toast toast = Toast.makeText(mContext,"Please Check your Internet Connection",Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }
}
