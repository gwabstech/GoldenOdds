/*
 * Copyright (c)
 *   * Created by Gwabstech on 1/14/22, 3:25 PM
 *   * Copyright (c) 2022 . All rights reserved.
 *   * Last modified 1/14/22, 3:25 PM
 */

package com.gwabs.GOLDEN_ODDS.Model;

import java.util.ArrayList;

public class In_App_Products {

    private String Name, Discription,Price;
    private ArrayList<In_App_Products> vipgamesList;

    public In_App_Products(String name, String discription, String price) {
        Name = name;
        Discription = discription;
        Price = price;
    }

    public ArrayList<In_App_Products> getVipgamesList() {
        return vipgamesList;
    }

    public void setVipgamesList(ArrayList<In_App_Products> vipgamesList) {
        this.vipgamesList = vipgamesList;
    }

    public String getName() {
        return Name;
    }



    public String getDiscription() {
        return Discription;
    }


    public String getPrice() {
        return Price;
    }


}
