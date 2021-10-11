/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/11/21, 4:32 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/11/21, 12:54 AM
 */

package com.gwabs.GOLDEN_ODDS.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gwabs.GOLDEN_ODDS.R;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

public class splashscreen extends AppCompatActivity {

   private boolean connected ;


    private FirebaseAuth mAuth;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


// ...
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        StartAppSDK.init(this, "209031346", false);
        StartAppAd.disableSplash();


    }


    public void splash(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashscreen.this,LoginAndSignUp.class);
                startActivity(i);
                finish();
            }
        }, 2000);

    }

    public void alartdialog(){
      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle("No Internet Connection");
      builder.setCancelable(false);
      builder.setMessage("Please connect to internet and Start the app again.....");
      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              System.exit(0);
          }
      });

      builder.create().show();
  }


    @Override
    protected void onStart() {
        super.onStart();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network

            connected = true;
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                Intent intent = new Intent(getApplicationContext(),HOME.class);
                startActivity(intent);
                finish();
            }else{
                splash();
            }
        }
        else {
            alartdialog();
            connected = false;

        }

    }
}