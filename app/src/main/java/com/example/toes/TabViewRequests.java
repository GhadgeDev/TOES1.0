package com.example.toes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabViewRequests#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabViewRequests extends Fragment {
    View v;
    private RecyclerView myRecyclerView;
    private List<ViewRequestListModel> lstworker;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabViewRequests() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabViewRequests.
     */
    // TODO: Rename and change types and number of parameters
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
        lstworker = new ArrayList<>();
        lstworker.add(new ViewRequestListModel("Ruturaj Varne","Carpenter"));
        lstworker.add(new ViewRequestListModel("Aditya Jambulkar","Driver"));
        lstworker.add(new ViewRequestListModel("Gauri Raskar","Painter"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tab_view_requests, container, false);
        myRecyclerView = v.findViewById(R.id.viewRequestRecycler);
        ViewRequestRecyclerAdapter recyclerAdapter = new ViewRequestRecyclerAdapter(getContext(),lstworker);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecyclerView.setAdapter(recyclerAdapter);
        return v;
    }
}