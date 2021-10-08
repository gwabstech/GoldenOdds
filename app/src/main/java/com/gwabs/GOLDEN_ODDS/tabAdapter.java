/*
 * Copyright (c)
 *   * Created by Gwabstech on 10/8/21, 3:24 PM
 *   * Copyright (c) 2021 . All rights reserved.
 *   * Last modified 10/8/21, 3:24 PM
 */

package com.gwabs.GOLDEN_ODDS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class tabAdapter extends FragmentPagerAdapter {
    public tabAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                fgHome_win fgHome_win = new fgHome_win();
                return fgHome_win;
            case 1:
                FgAwey_Win fgAwey_win = new FgAwey_Win();
                return fgAwey_win;
            case 2:
                FGBTTS fgbtts = new FGBTTS();
                return fgbtts;
            case 3:
                fgO2_5 fgO2_5 = new fgO2_5();
                return fgO2_5;
            case 4:
                fg_Combo_gm fg_combo_gm = new fg_Combo_gm();
                return  fg_combo_gm;


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "HOME WIN";
            case 1:
                return "AWAY WIN";
            case 2:
                return "BTTS";
            case 3:
                return "OVER 2.5";
            case 4:
                return "COMBO";

            default:
                return null;
        }
    }
}
