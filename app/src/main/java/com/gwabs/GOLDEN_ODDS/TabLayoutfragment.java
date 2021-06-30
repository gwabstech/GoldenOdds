package com.gwabs.GOLDEN_ODDS;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;


public class TabLayoutfragment extends Fragment {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private tabAdapter tabAdapter;

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
            tabAdapter = new tabAdapter(getActivity().getSupportFragmentManager(),5);
            viewPager.setAdapter(tabAdapter);

            tabLayout.setupWithViewPager(viewPager,true);
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}