/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/8/21, 3:24 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/8/21, 3:24 PM
 */

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


public class fgO2_5 extends Fragment {



    public fgO2_5() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fg_o2_5, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview4);
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
        final String sunandata = "over";
        home.getDataFromFirebase(sunandata,massagelist,recyclerView,myAdapter,myreff);




        return view;
    }

}