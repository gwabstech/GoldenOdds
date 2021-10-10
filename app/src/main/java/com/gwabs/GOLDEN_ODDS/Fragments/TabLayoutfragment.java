/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/8/21, 6:09 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/8/21, 3:24 PM
 */

package com.gwabs.GOLDEN_ODDS.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.gwabs.GOLDEN_ODDS.Adapters.tabAdapter;
import com.gwabs.GOLDEN_ODDS.R;


public class TabLayoutfragment extends Fragment {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private com.gwabs.GOLDEN_ODDS.Adapters.tabAdapter tabAdapter;

    public TabLayoutfragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_layoutfragment, container, false);

        try {
            viewPager=view.findViewById(R.id.viewpager);
            tabLayout=view.findViewById(R.id.tablayout);
            tabAdapter = new tabAdapter(requireActivity().getSupportFragmentManager(),5);
            viewPager.setAdapter(tabAdapter);

            tabLayout.setupWithViewPager(viewPager,true);
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}