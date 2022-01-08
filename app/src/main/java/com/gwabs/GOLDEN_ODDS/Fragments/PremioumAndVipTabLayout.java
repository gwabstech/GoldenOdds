/*
 * Copyright (c)
 *   * Created by Gwabstech on 1/8/22, 12:41 PM
 *   * Copyright (c) 2022 . All rights reserved.
 *   * Last modified 1/8/22, 12:41 PM
 */

package com.gwabs.GOLDEN_ODDS.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.gwabs.GOLDEN_ODDS.Adapters.Vip_Adapter;
import com.gwabs.GOLDEN_ODDS.Adapters.tabAdapter;
import com.gwabs.GOLDEN_ODDS.R;


public class PremioumAndVipTabLayout extends Fragment {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    Vip_Adapter vip_adapter;
    public PremioumAndVipTabLayout() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tab_layoutfragment, container, false);

        try {
            viewPager=view.findViewById(R.id.viewpager);
            tabLayout=view.findViewById(R.id.tablayout);
            vip_adapter = new Vip_Adapter(requireActivity().getSupportFragmentManager(),2);
            viewPager.setAdapter(vip_adapter);

            tabLayout.setupWithViewPager(viewPager,true);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

}