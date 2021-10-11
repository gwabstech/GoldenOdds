/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/11/21, 4:32 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/11/21, 4:29 PM
 */

package com.gwabs.GOLDEN_ODDS.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.gwabs.GOLDEN_ODDS.R;

import java.util.Objects;

import im.delight.android.webview.AdvancedWebView;

public class Privacypolicy extends AppCompatActivity implements AdvancedWebView.Listener {
    private AdvancedWebView mWebView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacypolicy);
        Toolbar toolbar =findViewById(R.id.toolbar);
        final String privacyUrl ="https://golden-odd.flycricket.io/privacy.html";
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Privacy Policy");

         progressDialog = new ProgressDialog(this);


        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait");


        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.setListener(this, this);
        mWebView.setMixedContentAllowed(false);
        mWebView.loadUrl(privacyUrl);





    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        // ...
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        mWebView.onPause();
        // ...
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        // ...
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
        // ...
    }

    @Override
    public void onBackPressed() {
        if (!mWebView.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }
    @Override
    public void onPageStarted(String url, Bitmap favicon) {
        progressDialog.show();

    }

    @Override
    public void onPageFinished(String url) {
        progressDialog.dismiss();
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
        Privacypolicy.this.recreate();

    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {

    }

    @Override
    public void onExternalPageRequest(String url) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}