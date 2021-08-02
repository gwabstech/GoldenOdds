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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fg_previouse_games);

        AdView mAdView = findViewById(R.id.adViewprv);
        RecyclerView recyclerView = findViewById(R.id.recyclerview_prv);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ImageView clickBack = findViewById(R.id.clickback);
        TextView title = findViewById(R.id.txttoolbarvvip);
        String titlee = "VIP PREVIOUS GAMES";
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
        HOME home = new HOME();
        DatabaseReference myreff = FirebaseDatabase.getInstance().getReference();
        // Arraylist
        ArrayList<Massage> massagelist = new ArrayList<>();
        myAdapter myAdapter = new myAdapter(getApplicationContext(),massagelist);
        home.ClearAll(massagelist,myAdapter);
        final String sunandata = "previeusGames";
        home.getDataFromFirebase(sunandata,massagelist,recyclerView,myAdapter,myreff);

        ;

    }


}