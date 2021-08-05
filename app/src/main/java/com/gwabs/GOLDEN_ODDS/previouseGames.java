package com.gwabs.GOLDEN_ODDS;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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
        RecyclerView recyclerView = findViewById(R.id.recyclerview_prv);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        ImageView clickBack = findViewById(R.id.clickback);
        TextView title = findViewById(R.id.txttoolbarvvip);
        String titlee = "VIP PREVIOUS GAMES";
        title.setTextSize(18);
        title.setText(titlee);

        ImageButton imageButton = findViewById(R.id.melBetBanner1);


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

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAffiliated();
            }
        });


        ;

    }

    private void myAffiliated() {
        String myLink = "https://refpakrtsb.top/L?tag=d_1099631m_18639c_&site=1099631&ad=18639";
        try {
            Intent intent = new Intent(ACTION_VIEW, Uri.parse(myLink));
            // The URL should either launch directly in a non-browser app (if it's the
            // default), or in the disambiguation dialog.
            intent.addCategory(CATEGORY_BROWSABLE);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_REQUIRE_NON_BROWSER);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Only browser apps are available, or a browser is the default.
            // So you can open the URL directly in your app, for example in a
            // Custom Tab.

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myLink));
            startActivity(browserIntent);

        }
    }


}