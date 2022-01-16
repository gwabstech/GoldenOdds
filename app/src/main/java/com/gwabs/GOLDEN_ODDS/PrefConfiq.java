/*
 * Copyright (c)
 *   * Created by Gwabstech on 1/16/22, 11:26 AM
 *   * Copyright (c) 2022 . All rights reserved.
 *   * Last modified 1/16/22, 11:26 AM
 */

package com.gwabs.GOLDEN_ODDS;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gwabs.GOLDEN_ODDS.Model.In_App_Products;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PrefConfiq {

    private static final String LIST_KEY = "ProductList";

    Context context;

    public static void writeListToPref(Context context, List<In_App_Products> inAppProductsList){

        Gson gson = new Gson();
        String jsonString = gson.toJson(inAppProductsList);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LIST_KEY,jsonString);
        editor.apply();
    }

    public static List<In_App_Products> getInAppProducts(Context context, ArrayList<In_App_Products> inAppProductsArrayList){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = preferences.getString(LIST_KEY,"");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<In_App_Products>>(){}.getType();
        List<In_App_Products> list = gson.fromJson(jsonString,type);

        return list;
    }
}
