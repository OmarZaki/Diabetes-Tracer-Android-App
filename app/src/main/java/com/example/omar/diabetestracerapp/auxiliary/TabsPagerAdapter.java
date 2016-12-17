package com.example.omar.diabetestracerapp.auxiliary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.omar.diabetestracerapp.fragments.AskDoctorFragment;
import com.example.omar.diabetestracerapp.fragments.MainFragment;
import com.example.omar.diabetestracerapp.fragments.ScheduleFragment;
import com.example.omar.diabetestracerapp.fragments.StatisticsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //TODO: Change arguments received by fragments
        switch(position){
            case 0:
                return MainFragment.newInstance("One", "Two");
            case 1:
                return ScheduleFragment.newInstance("One", "Two");
            case 2:
                return AskDoctorFragment.newInstance("One","Two");
            case 3:
                return StatisticsFragment.newInstance("A","Thing");
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Main";
            case 1:
                return "Schedule";
            case 2:
                return "Ask Doctor";
            case 3:
                return "Statistics";
        }
        return "TAB " + (position + 1);
    }
}
