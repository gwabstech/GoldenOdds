/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/8/21, 6:06 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/8/21, 3:24 PM
 */

package com.gwabs.GOLDEN_ODDS.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gwabs.GOLDEN_ODDS.Fragments.FGBTTS;
import com.gwabs.GOLDEN_ODDS.Fragments.FgMultiGoals;
import com.gwabs.GOLDEN_ODDS.Fragments.fg1x2;

public class tabAdapter extends FragmentPagerAdapter {
    public tabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new fg1x2();
            case 1:
                return new FgMultiGoals();
            case 2:
                return new FGBTTS();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "1X2";
            case 1:
                return "MULTI GOALS";
            case 2:
                return "BTTS";
            default:
                return null;
        }
    }
}
