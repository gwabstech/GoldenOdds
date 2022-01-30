/*
 * Copyright (c)
 *   * Created by Gwabstech on 1/7/22, 1:01 AM
 *   * Copyright (c) 2022 . All rights reserved.
 *   * Last modified 1/7/22, 1:01 AM
 */

package com.gwabs.GOLDEN_ODDS.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.gwabs.GOLDEN_ODDS.Adapters.VipProductAdapter;
import com.gwabs.GOLDEN_ODDS.Model.In_App_Products;
import com.gwabs.GOLDEN_ODDS.PrefConfiq;
import com.gwabs.GOLDEN_ODDS.R;
import com.gwabs.GOLDEN_ODDS.clickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VipProducts extends AppCompatActivity implements Serializable{

    private RecyclerView oneOfRv;
    private BillingClient billingClient;
    private ImageButton IMBoneWinBanner ;
    private VipProductAdapter vipProductAdapter;
    private List<String> productId;
    private ArrayList<In_App_Products> inAppProductsArrayList;
    private In_App_Products inAppProducts;
    private List<SkuDetails> skuDetails;

    protected ProgressDialog myProgressDialog;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_o_n_e__o_f);
        inAppProductsArrayList = new ArrayList<>();

        // initializing billing client
        billingClient = BillingClient.newBuilder(this)
                .enablePendingPurchases()
                .setListener(new PurchasesUpdatedListener() {
                    @Override
                    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {

                        if (billingResult.getResponseCode()  == BillingClient.BillingResponseCode.OK && list != null){

                            for (Purchase purchase :  list){
                                if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()){


                                }
                            }
                        }
                    }
                })
                .build();
        // connecting to play billing and retrieve products
        connectToPlayBilling();







        List<In_App_Products> app_products = PrefConfiq.getInAppProducts(VipProducts.this,inAppProductsArrayList);
        if (app_products != null){
            vipProductAdapter = new VipProductAdapter(VipProducts.this, (ArrayList<In_App_Products>) app_products, new clickListener() {
                @Override
                public void onItemClick(View itemView, int position) {
                    Button btnbuy = itemView.findViewById(R.id.btnBuy);
                    btnbuy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.i("te",skuDetails.get(position).toString());
                            billingClient.launchBillingFlow(VipProducts.this,
                                    BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetails.get(position))
                                            .build());
                        }
                    });
                }
            });

            LinearLayoutManager linearLayoutManager  = new LinearLayoutManager(getApplicationContext());
            oneOfRv.setLayoutManager(linearLayoutManager);
            oneOfRv.setHasFixedSize(true);
            oneOfRv.setAdapter(vipProductAdapter);
            vipProductAdapter.notifyDataSetChanged();

        }else {
            Toast.makeText(getApplicationContext(), "Problem with retrieving products from server please try again", Toast.LENGTH_SHORT).show();
            // myProgressDialog.dismiss();
        }
        IMBoneWinBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    private void connectToPlayBilling(){
        billingClient.startConnection(
                new BillingClientStateListener() {
                    @Override
                    public void onBillingServiceDisconnected() {
                        connectToPlayBilling();
                    }

                    @Override
                    public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                            getProductDetails();
                        }
                    }
                }
        );
    }

    private void getProductDetails() {

        productId = new ArrayList<>();
        productId.add(0,"viplow1");
        productId.add(1,"viplow2");
        productId.add(2,"viptkt3");
        productId.add(3,"vipticket2");
        productId.add(4,"viptikt1");

        SkuDetailsParams getProductDetails = SkuDetailsParams
                .newBuilder()
                .setSkusList(productId)
                .setType(BillingClient.SkuType.INAPP)
                .build();

        billingClient.querySkuDetailsAsync(getProductDetails, new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null){

                   // myProgressDialog = ProgressDialog.show(VipProducts.this, "", getText(R.string.please_wait), true);
                    skuDetails = new ArrayList<>();
                    Log.i("Test",list.toString());
                    for (int i = 0 ; i < list.size() ; i ++){
                        inAppProducts = new In_App_Products(list.get(i).getTitle(),list.get(i).getDescription(),list.get(i).getPrice());
                        inAppProductsArrayList.add(inAppProducts);
                        skuDetails.add(list.get(i));
                    }

                    PrefConfiq.writeListToPref(VipProducts.this,inAppProductsArrayList);

                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}