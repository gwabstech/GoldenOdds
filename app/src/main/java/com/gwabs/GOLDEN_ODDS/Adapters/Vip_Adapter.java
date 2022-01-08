/*
 * Copyright (c)
 *   * Created by Gwabstech on 1/8/22, 12:47 PM
 *   * Copyright (c) 2022 . All rights reserved.
 *   * Last modified 1/8/22, 12:47 PM
 */

package com.gwabs.GOLDEN_ODDS.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gwabs.GOLDEN_ODDS.Fragments.FGBTTS;
import com.gwabs.GOLDEN_ODDS.Fragments.FgMultiGoals;
import com.gwabs.GOLDEN_ODDS.Fragments.MONTHLYSUB;
import com.gwabs.GOLDEN_ODDS.Fragments.ONE_OFF;
import com.gwabs.GOLDEN_ODDS.Fragments.fg1x2;

public class Vip_Adapter extends FragmentPagerAdapter {


    public Vip_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ONE_OFF();
            case 1:
                return new MONTHLYSUB();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "ONE-OFF";
            case 1:
                return "SUBSCRIPTION";
            default:
                return null;
        }
    }
}
