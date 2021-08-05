package com.gwabs.GOLDEN_ODDS;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class fg_Combo_gm extends Fragment {

    private ImageButton imageButton;

    DatabaseReference myreff;

    public fg_Combo_gm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fg__combo_gm, container, false);

        imageButton = view.findViewById(R.id.melBetBanner1);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview5);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        HOME home = new HOME();
        // firebase




        // Arraylist

        ArrayList<Massage> massagelist = new ArrayList<>();

        myAdapter myAdapter = new myAdapter(getContext(),massagelist);
        // clearAll
        home.ClearAll(massagelist,myAdapter);


        final String Sunan = "combo";

        myreff = FirebaseDatabase.getInstance().getReference();
        home.getDataFromFirebase(Sunan,massagelist,recyclerView,myAdapter,myreff);

        imageButton.setOnClickListener(v -> myAffiliated());

        return  view;
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

        };
    }


}