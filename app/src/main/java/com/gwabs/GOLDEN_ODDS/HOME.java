package com.gwabs.GOLDEN_ODDS;

import android.annotation.SuppressLint;

import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HOME extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private AdView mAdView;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;




    public InterstitialAd mInterstitialAd;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home1);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        loadAds();



        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navView = findViewById(R.id.navView);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Golden Odds");
        navView.setNavigationItemSelectedListener( this);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, (R.string.open), (R.string.close));
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        View header = navView.getHeaderView(0);
        actionBarDrawerToggle.syncState();



        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contener_fragment,new TabLayoutfragment());
        fragmentTransaction.commit();

        mAdView = (AdView) header.findViewById(R.id.adViewhdv);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);






    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.VipGms:
                if (mInterstitialAd == null) {
                    loadAds();
                    showAdds();
                }

                showAdds();
               Intent intent = new Intent(getApplicationContext(),VVIP.class);
               startActivity(intent);
                drawerLayout.closeDrawers();
                break;
            case R.id.LiveScore:

                if (mInterstitialAd == null) {
                    loadAds();
                    showAdds();
                }

                showAdds();
                Toast.makeText(getApplicationContext(),
                        "This feature is coming soon",Toast.LENGTH_SHORT).show();
                break;
            case R.id.prevGames:

                if (mInterstitialAd == null) {
                    loadAds();
                    showAdds();
                }

                showAdds();
                Intent intent7 = new Intent(getApplicationContext(),previouseGames.class);
                startActivity(intent7);
                drawerLayout.closeDrawers();
                break;
            case R.id.Share_App:
                ShareApp();
                break;
            case R.id.Exit:

                if (mInterstitialAd == null) {
                    loadAds();
                    showAdds();
                }

                showAdds();
                finishAffinity();
                finish();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return true;
    }

    private void ShareApp(){

        if (mInterstitialAd == null) {
            loadAds();
            showAdds();
        }

        showAdds();

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "I recommends this app ";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
  public void loadAds(){
      AdRequest adRequest = new AdRequest.Builder().build();
      InterstitialAd.load(
              this,
              AD_UNIT_ID,
              adRequest,
              new InterstitialAdLoadCallback() {
                  @Override
                  public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                      // The mInterstitialAd reference will be null until
                      // an ad is loaded.
                      HOME.this.mInterstitialAd = interstitialAd;
                      Log.i("TAG", "onAdLoaded");
                      Toast.makeText(HOME.this, "onAdLoaded()", Toast.LENGTH_SHORT).show();
                      interstitialAd.setFullScreenContentCallback(
                              new FullScreenContentCallback() {
                                  @Override
                                  public void onAdDismissedFullScreenContent() {
                                      // Called when fullscreen content is dismissed.
                                      // Make sure to set your reference to null so you don't
                                      // show it a second time.
                                      HOME.this.mInterstitialAd = null;
                                      Log.d("TAG", "The ad was dismissed.");
                                  }

                                  @Override
                                  public void onAdFailedToShowFullScreenContent(AdError adError) {
                                      // Called when fullscreen content failed to show.
                                      // Make sure to set your reference to null so you don't
                                      // show it a second time.
                                      Log.d("TAG", "The ad failed to show.");
                                  }

                                  @Override
                                  public void onAdShowedFullScreenContent() {
                                      // Called when fullscreen content is shown.
                                      Log.d("TAG", "The ad was shown.");
                                  }
                              });
                  }

                  @Override
                  public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                      // Handle the error
                    Log.i("Tag",loadAdError.getMessage().toString());
                     mInterstitialAd = null;

                      @SuppressLint("DefaultLocale") String error =
                              String.format(
                                      "domain: %s, code: %d, message: %s",
                                      loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                      Toast.makeText(
                              HOME.this, "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                              .show();
                  }
              });
  }



    public void showAdds( ){
        try {
            if (mInterstitialAd == null) {
                loadAds();
                mInterstitialAd.show(HOME.this);
            } else {

                mInterstitialAd.show(HOME.this);
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }



}