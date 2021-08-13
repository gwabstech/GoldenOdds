package com.gwabs.GOLDEN_ODDS;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        ImageButton imageButton = findViewById(R.id.onexBetBanner1);


        clickBack.setOnClickListener(v -> onBackPressed());

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
                AffiliateMarketing afm = new AffiliateMarketing();
                show_PromoCodeDialog(afm.getOnebetPromocode(),afm.getOneXbetmessage(),afm.getOnexbetTitle(),afm.getOnexbetAfLink());
            }
        });

    }

    private void myAffiliated(String aflink) {

        try {
            Intent intent = new Intent(ACTION_VIEW, Uri.parse(aflink));
            // The URL should either launch directly in a non-browser app (if it's the
            // default), or in the disambiguation dialog.
            intent.addCategory(CATEGORY_BROWSABLE);
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_REQUIRE_NON_BROWSER);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Only browser apps are available, or a browser is the default.
            // So you can open the URL directly in your app, for example in a
            // Custom Tab.

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(aflink));
            startActivity(browserIntent);

        }
    }


    @SuppressLint("ResourceAsColor")
    public void show_PromoCodeDialog(String promoCode, String message, String Title, String aflink) {


        AlertDialog.Builder builder
                = new AlertDialog.Builder(this);
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
                ClipboardManager clipboard = (ClipboardManager)
                        getSystemService(getApplicationContext().CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("simple text", promoCode);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Promo code copied",Toast.LENGTH_SHORT).show();

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


}