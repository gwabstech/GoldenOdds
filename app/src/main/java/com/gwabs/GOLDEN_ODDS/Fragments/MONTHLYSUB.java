/*
 * Copyright (c)
 *   * Created by Gwabstech on 1/8/22, 12:59 PM
 *   * Copyright (c) 2022 . All rights reserved.
 *   * Last modified 1/8/22, 12:59 PM
 */

package com.gwabs.GOLDEN_ODDS.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gwabs.GOLDEN_ODDS.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MONTHLYSUB#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MONTHLYSUB extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MONTHLYSUB() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MONTHLYSUB.
     */
    // TODO: Rename and change types and number of parameters
    public static MONTHLYSUB newInstance(String param1, String param2) {
        MONTHLYSUB fragment = new MONTHLYSUB();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_m_o_n_t_h_l_y_s_u_b, container, false);
    }
}