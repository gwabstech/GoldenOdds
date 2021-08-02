package com.gwabs.GOLDEN_ODDS;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class VVIP extends AppCompatActivity {

    private    ImageView clickBack;
    private   TextView title;
    private   String titlee;
    private   Button whatsapp;
    private DatabaseReference myreff;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_v_i_p);

        myreff = FirebaseDatabase.getInstance().getReference();

        whatsapp = findViewById(R.id.whatsapp);
        clickBack = findViewById(R.id.clickback);
        title = findViewById(R.id.txttoolbarvvip);
        titlee = "VVIP GAMES";
        title.setText(titlee);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    getdata();
                } catch (NullPointerException nullPointerException) {
                    Log.i("stack", nullPointerException.toString());
                }


            }
        });


        clickBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    private void getdata(){
        Query query  = myreff.child("vvipuri");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String url= snapshot.getValue().toString();

                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                recreate();
            }
        });
    }



}