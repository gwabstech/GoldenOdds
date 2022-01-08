/*
 * Copyright (c)
 *   * Created by Gwabstech on 1/7/22, 1:01 AM
 *   * Copyright (c) 2022 . All rights reserved.
 *   * Last modified 1/7/22, 1:01 AM
 */

package com.gwabs.GOLDEN_ODDS.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.gwabs.GOLDEN_ODDS.Fragments.PremioumAndVipTabLayout;
import com.gwabs.GOLDEN_ODDS.Fragments.TabLayoutfragment;
import com.gwabs.GOLDEN_ODDS.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VipProducts extends AppCompatActivity {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    BillingClient billingClient;
    private TextView txtProductName1, txtProductName2, txtProductName3;

   private Button btnProductPrice1, btnProductPrice2, btnProductPrice3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.products_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("VIP SECTION");

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contener_fragment,new PremioumAndVipTabLayout());
        fragmentTransaction.commit();
/*
        txtProductName1 = findViewById(R.id.txtProduct1Name);
        txtProductName2 = findViewById(R.id.txtProduct2Name);
        txtProductName3 = findViewById(R.id.txtProduct3Name);

        btnProductPrice1 = findViewById(R.id.btnPriceProduct1);
        btnProductPrice2 = findViewById(R.id.btnPriceProduct2);
        btnProductPrice3 = findViewById(R.id.btnPriceProduct3);


        billingClient = BillingClient.newBuilder(VipProducts.this)
                .enablePendingPurchases()
                .setListener(new PurchasesUpdatedListener() {
                    @Override
                    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

                    }
                })
                .build();
        connectToPlayStoreBilling();

 */
    }

    private void  connectToPlayStoreBilling(){
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                connectToPlayStoreBilling();
                Toast.makeText(getApplicationContext(), "faild", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    getProductDetails();
                }
            }
        });

    }

    private void getProductDetails(){

        List<String> productId = new ArrayList<>();
        //getProductId and add to arrayList
        productId.add("viptkt3");
        productId.add("vipticket2");
        productId.add("viptikt1");

        SkuDetailsParams getsProductsData = SkuDetailsParams
                .newBuilder()
                .setSkusList(productId)
                .setType(BillingClient.SkuType.INAPP)
                .build();


        billingClient.querySkuDetailsAsync(getsProductsData, new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    if (list != null){
                        // here i set the product to screen

                        Log.i("list",list.toString());



                        String Product1Name = list.get(0).getDescription();
                        txtProductName1.setText(Product1Name);
                        Log.i("p",list.get(0).getDescription());
                        String Product1price = list.get(0).getPrice();
                        btnProductPrice1.setText(Product1price);
                        Log.i("p",list.get(0).getPrice().toString());


                        String Product2Name = list.get(1).getDescription();
                        txtProductName2.setText(Product2Name);

                        String Product2price = list.get(1).getPrice();
                        btnProductPrice2.setText(Product2price);



                        String Product3Name = list.get(2).getDescription();
                        txtProductName3.setText(Product3Name);

                        String Product3price = list.get(2).getPrice();
                        btnProductPrice3.setText(Product3price);



                    }
                }
            }
        });

    }

}