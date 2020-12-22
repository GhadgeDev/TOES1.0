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

public class TabWorkerRequestResponse extends Fragment {

    View v;
    private RecyclerView myRecyclerView;
    private List<WorkerResponseList> lstRequestResponse;
    public TabWorkerRequestResponse() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tab_worker_request_response,container,false);
        myRecyclerView = v.findViewById(R.id.WorkerRequestResponseRecycler);
        WorkerResponseRecyclerAdapter workerResponseRecyclerAdapter = new WorkerResponseRecyclerAdapter(getContext(),lstRequestResponse);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(workerResponseRecyclerAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstRequestResponse = new ArrayList<>();
        lstRequestResponse.add(new WorkerResponseList("Swapnil Bansode"));
        lstRequestResponse.add(new WorkerResponseList("Megha Gurav"));
        lstRequestResponse.add(new WorkerResponseList("Ruturaj Varne"));
    }
}
