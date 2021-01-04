package com.example.toes;

import android.graphics.Color;
import android.os.Bundle;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabWorkerRequestResponse extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<GetWorkerResponses> lstRequestResponse;
    WorkerResponseRecyclerAdapter adapter;
    private SwipeRefreshLayout refreshWorkerResponses;

    public TabWorkerRequestResponse() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_worker_request_response, container, false);
        myRecyclerView = v.findViewById(R.id.WorkerRequestResponseRecycler);
        refreshWorkerResponses = v.findViewById(R.id.refresh_worker_responses);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        callToGetWorkerResponses();

        refreshWorkerResponses.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callToGetWorkerResponses();
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    JsonPlaceHolderApi workerResponses = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
    public void callToGetWorkerResponses(){
        Call<List<GetWorkerResponses>> call = workerResponses.getWorkerResponse("token " + LoginActivity.token, LoginActivity.userMeId);
        call.enqueue(new Callback<List<GetWorkerResponses>>() {
            @Override
            public void onResponse(Call<List<GetWorkerResponses>> call, Response<List<GetWorkerResponses>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getActivity(), "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                lstRequestResponse = response.body();
                adapter = new WorkerResponseRecyclerAdapter(getContext(), lstRequestResponse);
                myRecyclerView.setAdapter(adapter);

                refreshWorkerResponses.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<GetWorkerResponses>> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(), "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }
}
