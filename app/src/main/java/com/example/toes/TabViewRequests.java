package com.example.toes;

import android.graphics.Color;
import android.os.Bundle;

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

public class TabViewRequests extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private List<GetRecruiterViewRequestModel> lstworker;
    ViewRequestRecyclerAdapter adapter;
    private SwipeRefreshLayout refreshRecruiterViewRequests;
    public static int viewJob_id;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TabViewRequests() {
        // Required empty public constructor
    }


    public static TabViewRequests newInstance(String param1, String param2) {
        TabViewRequests fragment = new TabViewRequests();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab_view_requests, container, false);
        myRecyclerView = v.findViewById(R.id.viewRequestRecycler);
        refreshRecruiterViewRequests = v.findViewById(R.id.refresh_recruiter_viewRequests);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        callToRecruiterViewRequests();
        refreshRecruiterViewRequests.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callToRecruiterViewRequests();
            }
        });
        return v;
    }

    JsonPlaceHolderApi recruiterSideViewRequest = ClassRetrofit.getRetrofit().create(JsonPlaceHolderApi.class);
    public void callToRecruiterViewRequests(){
        Call<List<GetRecruiterViewRequestModel>> call = recruiterSideViewRequest.getRecruiterViewRequest("token " + LoginActivity.token,
                LoginActivity.userMeId);
        call.enqueue(new Callback<List<GetRecruiterViewRequestModel>>() {
            @Override
            public void onResponse(Call<List<GetRecruiterViewRequestModel>> call, Response<List<GetRecruiterViewRequestModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getActivity(), "Unsuccessfully !", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                lstworker = response.body();
                adapter = new ViewRequestRecyclerAdapter(getContext(), lstworker);
                myRecyclerView.setAdapter(adapter);

                refreshRecruiterViewRequests.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<GetRecruiterViewRequestModel>> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(), "Please Check your Internet Connection !", Toast.LENGTH_SHORT);
                TextView toastMessage = toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }
        });
    }
}