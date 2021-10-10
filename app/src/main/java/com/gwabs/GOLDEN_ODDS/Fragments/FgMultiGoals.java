/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/8/21, 6:02 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/8/21, 3:24 PM
 */

package com.gwabs.GOLDEN_ODDS.Fragments;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gwabs.GOLDEN_ODDS.HOME;
import com.gwabs.GOLDEN_ODDS.Model.AffiliateMarketing;
import com.gwabs.GOLDEN_ODDS.Model.Massage;
import com.gwabs.GOLDEN_ODDS.R;
import com.gwabs.GOLDEN_ODDS.Adapters.myAdapter;
import com.gwabs.GOLDEN_ODDS.clickListener;

import java.util.ArrayList;


public class FgMultiGoals extends Fragment {



    public FgMultiGoals() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fg_multi_goals, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview2);
        LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        HOME home = new HOME();


        // firebase

        DatabaseReference myreff = FirebaseDatabase.getInstance().getReference();

        // Arraylist


        ArrayList<Massage> massagelist = new ArrayList<>();
        myAdapter myAdapter = new myAdapter(getContext(), massagelist, new clickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                AffiliateMarketing affiliateMarketing = new AffiliateMarketing();

                show_PromoCodeDialog(affiliateMarketing.getMelbetPromocode(),affiliateMarketing.getMelbetmessage()
                        ,affiliateMarketing.getMelbetTitle(),affiliateMarketing.getMelbetAfLink());
            }
        });

        home.ClearAll(massagelist,myAdapter);


        final String sunandata = "multi goals";

        home.getDataFromFirebase(sunandata,massagelist,recyclerView,myAdapter,myreff);


        return  view;
        //
    }



    public void show_PromoCodeDialog(String promoCode, String message, String Title, String aflink) {


        AlertDialog.Builder builder
                = new AlertDialog.Builder(requireContext());
        builder.setTitle(Title);

        final View customLayout
                = getLayoutInflater()
                .inflate(
                        R.layout.afliate_layout,
                        null);
        builder.setView(customLayout);
        builder.setCancelable(true);
        TextView msg = customLayout.findViewById(R.id.ALMessage);
        LinearLayout copyPromocod = customLayout.findViewById(R.id.CopyPromocode);

        msg.setText(message);
        copyPromocod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext();
                ClipboardManager clipboard = (ClipboardManager)
                        requireActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", promoCode);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(requireContext(),"Promo code copied",Toast.LENGTH_SHORT).show();

            }
        });

        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myAffiliated(aflink);
            }
        });

        AlertDialog dialog
                = builder.create();
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogbg);

    }
    public void myAffiliated(String myLink) {
        try {
            Intent intent = new Intent(ACTION_VIEW, Uri.parse(myLink));
            // The URL should either launch directly in a non-browser app (if it's the
            // default), or in the disambiguation dialog.
            intent.addCategory(CATEGORY_BROWSABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_REQUIRE_NON_BROWSER);
            }
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Only browser apps are available, or a browser is the default.
            // So you can open the URL directly in your app, for example in a
            // Custom Tab.

            Intent browserIntent = new Intent(ACTION_VIEW, Uri.parse(myLink));
            startActivity(browserIntent);

        }
    }
}