/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/11/21, 4:33 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/11/21, 4:26 PM
 */

package com.gwabs.GOLDEN_ODDS.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.gwabs.GOLDEN_ODDS.R;

import mehdi.sakout.aboutpage.AboutPage;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View aboutPage = new AboutPage(this)
                .isRTL(false)
                //.setCustomFont(String) // or Typeface
                .setImage(R.drawable.aa)
               // .addItem(versionElement)
                //.addItem(adsElement)
                .setDescription("GOLDEN ODDS is a soccer prediction app developed and manage by Gwabstech Nigeria")
                .addGroup("Connect with us")
                .addEmail("Goldenodd5986@gmail.com")
                .addFacebook("https://www.facebook.com/GoldenOdds5986/")
                .addPlayStore("https://play.google.com/store/apps/dev?id=8203445375623794120")
                .addInstagram("Goldenodds5986")
                .create();


        setContentView(aboutPage);
    }

}