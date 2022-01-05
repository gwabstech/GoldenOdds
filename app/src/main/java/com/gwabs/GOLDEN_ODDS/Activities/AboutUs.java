/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/11/21, 4:33 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/11/21, 4:26 PM
 */

package com.gwabs.GOLDEN_ODDS.Activities;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gwabs.GOLDEN_ODDS.R;

import java.util.Objects;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutUs extends AppCompatActivity {
    private DatabaseReference myreff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myreff = FirebaseDatabase.getInstance().getReference();

        Element element = new Element();
        element.setIconDrawable(R.mipmap.whatsapplogo);
        element.setTitle("WhatsApp");
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWhatsApplink();
            }
        });

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                //.setCustomFont(String) // or Typeface
                .setImage(R.drawable.aa)
               // .addItem(versionElement)
                .setDescription("GOLDEN ODDS is a soccer prediction app developed and manage by Gwabstech Solutions Nigeria")
                .addGroup("Connect with on")
                .addEmail("Goldenodd5986@gmail.com"," Email")
                .addItem(element)
                .addFacebook("https://www.facebook.com/GoldenOdds5986/")
                .addPlayStore("https://play.google.com/store/apps/dev?id=8203445375623794120")
                .addInstagram("Goldenodds5986")
                .create();


        setContentView(aboutPage);
    }

    public  void getWhatsApplink(){
        Query query  = myreff.child("vvipuri");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url= Objects.requireNonNull(snapshot.getValue()).toString();


                try {
                    Intent intent = new Intent(ACTION_VIEW, Uri.parse(url));
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

                    Intent intent = new Intent(ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                recreate();
            }
        });
    }

}