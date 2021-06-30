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


public class fgHome_win extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Massage> Massagelist;
    private myAdapter myAdapter;
    private DatabaseReference myreff;
    private AdView mAdView;

    public fgHome_win() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fghome_win, container, false);
        mAdView = view.findViewById(R.id.adView02);
        recyclerView = view.findViewById(R.id.recyclerview1);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
       /// loading adds
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // firebase

        myreff = FirebaseDatabase.getInstance().getReference();

        // Arraylist

        Massagelist = new ArrayList<>();

        // clearAll
        ClearAll();


        getDatafromFirebase();

        return view;
    }

    private void getDatafromFirebase() {
        String sunandata = "HomeWins";
        Query query  = myreff.child(sunandata);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ClearAll();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Massage massage = new Massage();
                    massage.setDate(snapshot.child("Date").getValue().toString());
                    massage.setTime(snapshot.child("Time").getValue().toString());
                    massage.setGame(snapshot.child("Game").getValue().toString());
                    massage.setOdds(snapshot.child("odds").getValue().toString());
                    massage.setTips(snapshot.child("tips").getValue().toString());
                    massage.setStatus(snapshot.child("status").getValue().toString());
                    massage.setCountry(snapshot.child("country").getValue().toString());
                    Massagelist.add(massage);
                }
                myAdapter = new myAdapter(getContext(),Massagelist);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                try {
                    Log.i("error","some issoes please refresh");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void ClearAll(){
        if (Massagelist != null){
            Massagelist.clear();

            if (myAdapter != null){
                myAdapter.notifyDataSetChanged();
            }


        }
        Massagelist = new ArrayList<>();
    }
}