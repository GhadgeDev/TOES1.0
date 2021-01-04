package com.example.toes;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabRequestResponce extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<GetRecruiterResponses> lstResponse;
    ResponseRecyclerAdapter adapter;
    private SwipeRefreshLayout refreshRecruiterResponses;

    public TabRequestResponce() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab_request_responce, container, false);
        myrecyclerview = v.findViewById(R.id.requestResponseRecycler);
        refreshRecruiterResponses = v.findViewById(R.id.refresh_recruiter_responses);

        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        callToGetAllRequestResponses();

        refreshRecruiterResponses.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callToGetAllRequestResponses();
            }
        });
        return v;
    }

    JsonPlaceHolderApi recruiterResponses = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
    public void callToGetAllRequestResponses(){
        Call<List<GetRecruiterResponses>> call = recruiterResponses.getRecruiterResponses("token " + LoginActivity.token, LoginActivity.userMeId);
        call.enqueue(new Callback<List<GetRecruiterResponses>>() {
            @Override
            public void onResponse(Call<List<GetRecruiterResponses>> call, Response<List<GetRecruiterResponses>> response) {
                if(!response.isSuccessful()){
                    Toast toast = Toast.makeText(getActivity(), "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                lstResponse = response.body();
                adapter = new ResponseRecyclerAdapter(getContext(),lstResponse);
                myrecyclerview.setAdapter(adapter);

                refreshRecruiterResponses.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<GetRecruiterResponses>> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(), "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }
}