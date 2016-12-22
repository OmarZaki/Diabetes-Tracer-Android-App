package com.example.omar.diabetestracerapp.auxiliary;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.omar.diabetestracerapp.FragmentAskDoctor;
import com.example.omar.diabetestracerapp.MainFragment;
import com.example.omar.diabetestracerapp.ScheduleFragment;
import com.example.omar.diabetestracerapp.StatisticsFragment;

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
                return FragmentAskDoctor.newInstance();
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
