package com.gwabs.GOLDEN_ODDS;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;

import static com.startapp.sdk.adsbase.StartAppAd.*;
import static com.startapp.sdk.adsbase.StartAppSDK.*;

public class splashscreen extends AppCompatActivity {

    boolean connected ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        init(getApplicationContext(), "209031346", false);
        enableReturnAds(false);
        disableSplash();


        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            splash();
            connected = true;
        }
        else
            alartdialog();
            connected = false;



    }


    public void splash(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(splashscreen.this,HOME.class);
                startActivity(i);
                finish();
            }
        }, 5000);

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


}