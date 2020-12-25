package com.example.toes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TabWorkerViewRequest extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<WorkerViewRequestListModel> lstViewRequest;

    public TabWorkerViewRequest() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_worker_view_request,container,false);
        myRecyclerView = v.findViewById(R.id.WorkerViewRequestRecycler);
        WorkerViewRequestRecyclerAdapter myAdapter = new WorkerViewRequestRecyclerAdapter(getContext(),lstViewRequest);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(myAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstViewRequest = new ArrayList<>();
        lstViewRequest.add(new WorkerViewRequestListModel("Aishwarya Shinde","Driver"));
        lstViewRequest.add(new WorkerViewRequestListModel("Vaibhav Shinde","Painter"));
        lstViewRequest.add(new WorkerViewRequestListModel("Devendra Ghadge","Plumber"));
    }
}
