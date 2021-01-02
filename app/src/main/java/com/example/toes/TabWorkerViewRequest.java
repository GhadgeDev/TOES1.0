package com.example.toes;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabWorkerViewRequest extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<GetWorkerViewRequestModel> lstViewRequest;
    WorkerViewRequestRecyclerAdapter adapter;

    public TabWorkerViewRequest() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_worker_view_request,container,false);
        myRecyclerView = v.findViewById(R.id.WorkerViewRequestRecycler);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        JsonPlaceHolderApi workerSideViewRequest = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
        Call<List<GetWorkerViewRequestModel>> call = workerSideViewRequest.getWorkerViewRequest("token " + LoginActivity.token,
                LoginActivity.userMeId);
        call.enqueue(new Callback<List<GetWorkerViewRequestModel>>() {
            @Override
            public void onResponse(Call<List<GetWorkerViewRequestModel>> call, Response<List<GetWorkerViewRequestModel>> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getActivity(), "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                lstViewRequest = response.body();
                adapter = new WorkerViewRequestRecyclerAdapter(getContext(),lstViewRequest);
                myRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<GetWorkerViewRequestModel>> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(), "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
