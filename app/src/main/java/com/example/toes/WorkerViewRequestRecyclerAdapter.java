package com.example.toes;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.provider.Settings.System.getString;

public class WorkerViewRequestRecyclerAdapter extends RecyclerView.Adapter<WorkerViewRequestRecyclerAdapter.MyViewHolder> {
    Context mContext;
    List<GetWorkerViewRequestModel> mData;
    Dialog myDialog;
    Button workerAcceptBtn;
    Button workerRejectBtn;
    String rFName;
    String rLName;
    String rFullName;
    String rAdd;
    String rPhoneNumber;
    int status;
    static String no;
    public int viewJob_id;
    private Object IllegalAccessException;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    private Object IllegalArgumentException;

    public WorkerViewRequestRecyclerAdapter(Context context, List<GetWorkerViewRequestModel> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.workerviewrequestlist, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);


        //For http log
        HttpLoggingInterceptor okHttpLoggingInterceptor = new HttpLoggingInterceptor();
        okHttpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(okHttpLoggingInterceptor).build();

        //connecting to base url
        Retrofit.Builder retrofit = new Retrofit.Builder().
                baseUrl("https://tcp-api.herokuapp.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit1 = retrofit.build();

        jsonPlaceHolderApi  = retrofit1.create(JsonPlaceHolderApi.class);






        //Dialog
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_view_request_workerside);

        /*get worker visiting charges, recruiter address, recruiter description*/

        viewHolder.workerViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //viewJob_id = mData.get(viewHolder.getAdapterPosition()).getJobId();
                viewJob_id = TabWorkerViewRequest.jid.get(viewHolder.getAdapterPosition());
                rFName = mData.get(viewHolder.getAdapterPosition()).getRecruiterFname();
                rLName = mData.get(viewHolder.getAdapterPosition()).getRecruiterLname();
                String rDesc = mData.get(viewHolder.getAdapterPosition()).getJobDescription();
                rAdd = mData.get(viewHolder.getAdapterPosition()).getAddress();
                rPhoneNumber = mData.get(viewHolder.getAdapterPosition()).getRecruiterPhoneNo();
                rFullName = rFName + " " + rLName;
                System.out.println("rPhoneNumber __---------------"+rPhoneNumber);
                TextView dialog_recruiter_name = myDialog.findViewById(R.id.view_request_recruiter_name_dialog);
                TextView dialog_recruiter_desc = myDialog.findViewById(R.id.view_request_recruiter_desc);
                TextView dialog_recruiter_add = myDialog.findViewById(R.id.view_request_recruiter_address);
                workerAcceptBtn = myDialog.findViewById(R.id.worker_accept_btn);
                workerRejectBtn = myDialog.findViewById(R.id.worker_reject_btn);


                dialog_recruiter_name.setText(rFName + " " + rLName);
                dialog_recruiter_desc.setText(rDesc);
                dialog_recruiter_add.setText(rAdd);
                myDialog.show();

                try {
                    workerAcceptBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            status = 2;
                            Toast.makeText(mContext, "Job accepted", Toast.LENGTH_SHORT).show();
                            System.out.println("jid __---------------"+viewJob_id);

                            callAcceptRejectApi();
                            myDialog.dismiss();
                        }
                    });

                    workerRejectBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            status = 3;
                            Toast.makeText(mContext, "Job Rejected", Toast.LENGTH_SHORT).show();
                            System.out.println("jid __---------------"+viewJob_id);

                            callAcceptRejectApi();
                            myDialog.dismiss();
                        }
                    });
                }catch (IllegalArgumentException exception){
                    Toast.makeText(mContext,"Something went wrong try after restarting your app",Toast.LENGTH_SHORT).show();
                    System.out.println("jid __---------------"+viewJob_id);

                }

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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
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
    public void callAcceptRejectApi() {
        Call<GetAcceptRejectBtnClick> call = acceptRejectApi.getAcceptRejectBtnClick("token " + LoginActivity.token, status, viewJob_id);
        call.enqueue(new Callback<GetAcceptRejectBtnClick>() {
            @Override
            public void onResponse(Call<GetAcceptRejectBtnClick> call, Response<GetAcceptRejectBtnClick> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(mContext, "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                }


                //We’ll check the permission is granted or not . If not granted the displaying message
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                    Toast toast = Toast.makeText(mContext, "Give SMS permission to this app from setting then again accept job", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    if (status == 2) {
                        sendSmsToMySelf();
                        sendSmsToRecruiter();
                    }
                }
            }


            @Override
            public void onFailure(Call<GetAcceptRejectBtnClick> call, Throwable t) {
                Toast toast = Toast.makeText(mContext, "Please Check your Internet Connection", Toast.LENGTH_SHORT);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }


    public void sendSmsToMySelf() {
        Call<Post> call = jsonPlaceHolderApi.getPost("token "+LoginActivity.token);
        call.enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());

                    return;
                }

                Post postResponse = response.body();
                // File photo = new File(postResponse.getProfile_image());
                // RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), photo);
                System.out.println("Code :------------------- " + response.code());
                String content = "";
                content += "name : " + postResponse.getFirst_name() + "\n";
                content += "lName : " + postResponse.getLast_name() + "\n";
                content += "Phone : " + postResponse.getPhone() + "\n";
                content += "Gender : " + postResponse.getGender() + "\n";
                content += "Aadhar : " + postResponse.getAadhar_no() + "\n";
                content += "address : " + postResponse.getAddress() + "\n";

                no = postResponse.getPhone();
                Intent intent = new Intent(mContext.getApplicationContext(), WorkerViewRequestRecyclerAdapter.class);
                PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);

                //setting string and phone no to send message
                String msg = "Message From Toes" + "\n" + "Recruiter name: "+rFName +" "+rLName  + "\n" + "Contact no: "+rPhoneNumber  + "\n" + "Address: "+rAdd ;

                System.out.println("postResponse.getPhone(); __---------------"+postResponse.getPhone());
                // SmsManager sms = SmsManager.getDefault();    //android mobile sms manager
                try {
                    android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();

                    sms.sendTextMessage(postResponse.getPhone(), null, msg, pi,null);        //method to send sms
                    Toast.makeText(mContext.getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                    throw (Throwable) IllegalAccessException;
                } catch (Throwable throwable) {
                   // Toast.makeText(mContext.getApplicationContext(), "", Toast.LENGTH_LONG).show();
                    throwable.printStackTrace();
                }
                System.out.println("Data : _--------- " + content);
                //System.out.println("no : _--------- " + no);
                System.out.println("id : -------------------------- " + postResponse.getId());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("Filed in ProfileActivity : " + t.getMessage());

            }
        });
    }

    public void sendSmsToRecruiter() {
        Call<Post> call = jsonPlaceHolderApi.getPost("token "+LoginActivity.token);
        call.enqueue(new Callback<Post>() {

            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    System.out.println("Response : _--------- " + response.code());
                    System.out.println("Response M : _--------- " + response.message());

                    return;
                }

                Post postResponse = response.body();

                //Messages
                //Creating intent of current activity/fragment/context
                Intent intent = new Intent(mContext.getApplicationContext(), WorkerViewRequestRecyclerAdapter.class);
                PendingIntent pi = PendingIntent.getActivity(mContext.getApplicationContext(), 0, intent, 0);

                //setting string and phone no to send message
                String msg = "Message From Toes" + "\n" + "Worker name: " + postResponse.getFirst_name()+" "+postResponse.getLast_name() +  "\n" + "Contact no: " +  "\n"+postResponse.getPhone() +"\n"+ "Address: "+postResponse.getAddress()  ;
                //String no = rPhoneNumber;
                System.out.println("rPhoneNumberSelf1 __---------------"+rPhoneNumber);
                try {
                    System.out.println("rPhoneNumberSelfT1 __---------------"+rPhoneNumber);
                    System.out.println("rPhoneNumberSelfT1 __---------------"+LoginActivity.userName);
                    System.out.println("rPhoneNumberSelfT1 __---------------"+LoginActivity.userPhoneNumber );
                    System.out.println("rPhoneNumberSelfT1 __---------------"+LoginActivity.userAddress);
                    android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault() ;
                    sms.sendTextMessage(rPhoneNumber, null, msg, pi, null) ;        //m// ethod to send sms
                    System.out.println("rPhoneNumberSelfT2 __---------------"+rPhoneNumber);
                    Toast.makeText(mContext.getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();
                    throw (Throwable) IllegalArgumentException;
                }  catch (Throwable throwable) {
                    //Toast.makeText(mContext.getApplicationContext(), "", Toast.LENGTH_LONG).show();
                    throwable.printStackTrace();
                }

                System.out.println("id : -------------------------- " + postResponse.getId());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                System.out.println("Filed in ProfileActivity : " + t.getMessage());

            }
        });

    }
}
