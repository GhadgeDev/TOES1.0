package com.example.toes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link TabRequestResponce#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabRequestResponce extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View v;
    private RecyclerView myrecyclerview;
    private List<RecruiterResponseList> lstResponse;

    public TabRequestResponce() {
        // Required empty public constructor
    }
 /*   /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabRequestResponce.
     */
     // TODO: Rename and change types and number of parameters
    public static TabRequestResponce newInstance(String param1, String param2) {
        TabRequestResponce fragment = new TabRequestResponce();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstResponse = new ArrayList<>();
        lstResponse.add(new RecruiterResponseList("Niket Jadhav"));
        lstResponse.add(new RecruiterResponseList("Tanmay Mahamulkar"));
        lstResponse.add(new RecruiterResponseList("Aditya Mali"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab_request_responce, container, false);
        myrecyclerview = v.findViewById(R.id.requestResponseRecycler);
        ResponseRecyclerAdapter responseRecyclerAdapter = new ResponseRecyclerAdapter(getContext(),lstResponse);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(responseRecyclerAdapter);
        return v;
    }
}