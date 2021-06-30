package com.gwabs.GOLDEN_ODDS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class previouseGames extends AppCompatActivity {

    private AdView mAdView;
    private RecyclerView recyclerView;
    private ArrayList<Massage> Massagelist;
    private myAdapter myAdapter;
    private DatabaseReference myreff;
    private   ImageView clickBack;
    private TextView title;
   private   String titlee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fg_previouse_games);

        mAdView = findViewById(R.id.adViewprv);
        recyclerView = findViewById(R.id.recyclerview_prv);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        clickBack = findViewById(R.id.clickback);
        title = findViewById(R.id.txttoolbarvvip);
        titlee = "VIP PREVIOUS GAMES";
        title.setTextSize(18);
        title.setText(titlee);


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // firebase

        myreff = FirebaseDatabase.getInstance().getReference();

        // Arraylist

        Massagelist = new ArrayList<>();

        // clearAll
        ClearAll();


        getDatafromFirebase();



    }

    private void getDatafromFirebase() {
        String sunandata = "previeusGames";
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
                myAdapter = new myAdapter(getApplicationContext(),Massagelist);
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