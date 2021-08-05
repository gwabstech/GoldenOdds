package com.gwabs.GOLDEN_ODDS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FgAwey_Win extends Fragment {



    public FgAwey_Win() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fg_awey__win, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview2);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        HOME home = new HOME();


        // firebase

        DatabaseReference myreff = FirebaseDatabase.getInstance().getReference();

        // Arraylist


        ArrayList<Massage> massagelist = new ArrayList<>();
        myAdapter myAdapter = new myAdapter(getContext(),massagelist);

        home.ClearAll(massagelist,myAdapter);


        final String sunandata = "AwayWins";

        home.getDataFromFirebase(sunandata,massagelist,recyclerView,myAdapter,myreff);


        return  view;
        //
    }



}