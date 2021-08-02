package com.gwabs.GOLDEN_ODDS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HOME extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    // FRAGMENT CLASS VERIABLES
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // ADS VARIABLES
    public InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home1);

        // ADS INTIALIZATION
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NotNull InitializationStatus initializationStatus) {}
        });
        loadAds();

        // UI INITIALIZATION
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navView = findViewById(R.id.navView);
        setSupportActionBar(toolbar);
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

        // VARIABLLE DECLIRATION

        ScheduledExecutorService scheduler  =  Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Log.i("hello", "world");
                runOnUiThread(new Runnable() {
                    public void run() {

                        loadAds();
                        showAdds();
                    }
                });
            }
        }, 60, 90, TimeUnit.SECONDS);



    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.LiveScore:

                Toast.makeText(getApplicationContext(),
                        "This feature is coming soon",Toast.LENGTH_SHORT).show();
                break;
            case R.id.prevGames:

                Intent intent7 = new Intent(getApplicationContext(),previouseGames.class);
                startActivity(intent7);
                drawerLayout.closeDrawers();
                break;

            case R.id.Share_App:

                Toast.makeText(this,"i was clicked",Toast.LENGTH_LONG).show();
                ShareApp();
                showAdds();
                loadAds();
                break;

            case R.id.Exit:
                finishAffinity();
                finish();
                showAdds();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.VipGms:
                Intent intent = new Intent(getApplicationContext(),VVIP.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                showAdds();
                loadAds();
                break;
            case R.id.referral:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
                showAdds();
                loadAds();
                break;
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
                loadAds();
                mInterstitialAd.show(HOME.this);
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    public void getDataFromFirebase(String sunandata ,
                                    ArrayList<Massage> Massagelist,
                                    RecyclerView recyclerView ,myAdapter myAdapter,DatabaseReference myreff){



        Query query  = myreff.child(sunandata);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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

    void ClearAll(ArrayList<Massage> Massagelist, myAdapter myAdapter){
        if (Massagelist != null){
            Massagelist.clear();

            if (myAdapter != null){
                myAdapter.notifyDataSetChanged();
            }


        }
        Massagelist = new ArrayList<>();
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
}