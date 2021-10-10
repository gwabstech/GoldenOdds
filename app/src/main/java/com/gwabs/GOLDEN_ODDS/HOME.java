/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/8/21, 3:24 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/8/21, 3:22 PM
 */

package com.gwabs.GOLDEN_ODDS;

import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.CATEGORY_BROWSABLE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.content.Intent.FLAG_ACTIVITY_REQUIRE_NON_BROWSER;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gwabs.GOLDEN_ODDS.Adapters.myAdapter;
import com.gwabs.GOLDEN_ODDS.Fragments.TabLayoutfragment;
import com.gwabs.GOLDEN_ODDS.Model.AffiliateMarketing;
import com.gwabs.GOLDEN_ODDS.Model.Massage;
import com.sanojpunchihewa.updatemanager.UpdateManager;
import com.sanojpunchihewa.updatemanager.UpdateManagerConstant;
import com.startapp.sdk.adsbase.StartAppAd;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HOME extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    // FRAGMENT CLASS VERIABLES
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private FirebaseAuth mAuth;
    UpdateManager mUpdateManager;

    // ADS VARIABLES

    public InterstitialAd mInterstitialAd;
    private StartAppAd startAppAd ;
    private DatabaseReference myreff;
    private AffiliateMarketing afmData;
    private  ScheduledExecutorService scheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home1);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
      //  Toast.makeText(this, currentUser.getEmail(),Toast.LENGTH_SHORT).show();
        // ADS INTIALIZATION
        MobileAds.initialize(this, initializationStatus -> {});
        startAppAd = new StartAppAd(this);
        loadAds();

        // UI INITIALIZATION
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);
        setSupportActionBar(toolbar);

        View headerView = navView.getHeaderView(0);

        ImageButton mmelbetbanner = headerView.findViewById(R.id.melBetBanner1);
        TextView displaymail = headerView.findViewById(R.id.TxtEmail);
        displaymail.setText(Objects.requireNonNull(currentUser.getEmail()).toUpperCase(Locale.ROOT));
        // SETTING THE TIITLE OF ACTION BAR
        Objects.requireNonNull(getSupportActionBar()).setTitle("Golden Odds");
        navView.setNavigationItemSelectedListener( this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, (R.string.open), (R.string.close));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contener_fragment,new TabLayoutfragment());
        fragmentTransaction.commit();
        myreff = FirebaseDatabase.getInstance().getReference();

        afmData = new AffiliateMarketing();

        mmelbetbanner.setOnClickListener(v -> {

            show_PromoCodeDialog(afmData.getMelbetPromocode(),afmData.getMelbetmessage(),afmData.getMelbetTitle(),afmData.getMelbetAfLink());
        });

        // VARIABLLE DECLIRATION

        scheduler  =  Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            Log.i("hello", "world");
            runOnUiThread(() -> {

                loadAds();
                showAdds();
            });
        }, 60, 120, TimeUnit.SECONDS);


        InAppUpdate();


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


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.Melbet:
                show_PromoCodeDialog(afmData.getMelbetPromocode(),afmData.getMelbetmessage(),afmData.getMelbetTitle(),afmData.getMelbetAfLink());
                break;
            case R.id.onexbet:
                show_PromoCodeDialog(afmData.getOnebetPromocode(),afmData.getOneXbetmessage(),afmData.getOnexbetTitle(),afmData.getOnexbetAfLink());
                break;
            case R.id.LiveScore:
                Toast.makeText(getApplicationContext(),
                        "Coming soon",Toast.LENGTH_SHORT).show();
                break;

            case R.id.Share_App:
               // Toast.makeText(this,"i was clicked",Toast.LENGTH_LONG).show();
                ShareApp();
                loadAds();
                showAdds();

                break;

            case R.id.LogOut:

                mAuth.signOut();
                scheduler.shutdown();
                Intent intent = new Intent(this,LoginAndSignUp.class);
                startActivity(intent);
                showAdds();
                this.finish();



            case R.id.Exit:
                finishAffinity();
                finish();
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.VipGms) {
            try {
                getviplink();
                showAdds();
                loadAds();
            } catch (NullPointerException nullPointerException) {
                Log.i("stack", nullPointerException.toString());
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShareApp(){

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shared = "I Recommend this App ";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shared);
        startActivity(sharingIntent);


    }

  public void loadAds(){
      AdRequest adRequest = new AdRequest.Builder().build();
      InterstitialAd.load(
              this,
              getString(R.string.Add_Unit_Id_Intesetiial),
              //"ca-app-pub-3940256099942544/1033173712",
              adRequest, new InterstitialAdLoadCallback() {
                  @Override
                  public void onAdLoaded(@NonNull @NotNull InterstitialAd interstitialAd) {
                      mInterstitialAd =interstitialAd;

                     // super.onAdLoaded(interstitialAd);
                  }

                  @Override
                  public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                      super.onAdFailedToLoad(loadAdError);
                  }


              });


  }



    public void showAdds( ){
        try {

            if (mInterstitialAd != null) {
                mInterstitialAd.show(HOME.this);
            } else {
               startAppAd.loadAd();
               startAppAd.showAd();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    public void getDataFromFirebase(String sunandata ,
                                    ArrayList<Massage> Massagelist,
                                    RecyclerView recyclerView , myAdapter myAdapter, DatabaseReference myreff){




            Query query  = myreff.child(sunandata);
            query.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                            Massage massage = new Massage();
                            massage.setDate(Objects.requireNonNull(snapshot.child("Date").getValue()).toString());
                            massage.setTime(Objects.requireNonNull(snapshot.child("Time").getValue()).toString());
                            massage.setGame(Objects.requireNonNull(snapshot.child("Game").getValue()).toString());
                            massage.setOdds(Objects.requireNonNull(snapshot.child("odds").getValue()).toString());
                            massage.setTips(Objects.requireNonNull(snapshot.child("tips").getValue()).toString());
                            massage.setStatus(Objects.requireNonNull(snapshot.child("status").getValue()).toString());
                            massage.setCountry(Objects.requireNonNull(snapshot.child("country").getValue()).toString());
                            Massagelist.add(massage);
                    }

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

    @SuppressLint("NotifyDataSetChanged")
    public void ClearAll(ArrayList<Massage> Massagelist, myAdapter myAdapter){
        if (Massagelist != null){
            Massagelist.clear();

            if (myAdapter != null){
                myAdapter.notifyDataSetChanged();
            }


            Massagelist = new ArrayList<>();
        } else {
            Massagelist = new ArrayList<>();
        }
    }


    @Override
    protected void onPostCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }


    private void getviplink(){
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
    public void InAppUpdate(){
        mUpdateManager = UpdateManager.Builder(this);

        mUpdateManager.addUpdateInfoListener(new UpdateManager.UpdateInfoListener() {
            @Override
            public void onReceiveVersionCode(final int code) {
              //  txtAvailableVersion.setText(String.valueOf(code));
            }

            @Override
            public void onReceiveStalenessDays(final int days) {
               // txtStalenessDays.setText(String.valueOf(days));
            }
        });

        mUpdateManager = UpdateManager.Builder(this).mode(UpdateManagerConstant.IMMEDIATE);
        mUpdateManager.start();
    }


}