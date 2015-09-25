package android.cp.hseya.com.cleaner.adapter;

import android.cp.hseya.com.cleaner.fragment.DayFragment;
import android.cp.hseya.com.cleaner.fragment.MonthFragment;
import android.cp.hseya.com.cleaner.fragment.WeekFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by ayesh on 8/10/15.
 */
public class InspectionFPAdapter extends android.support.v4.app.FragmentPagerAdapter{

    final int PAGE_COUNT = 3;

    public InspectionFPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        Bundle data = new Bundle();
        switch(arg0){

            /** tab1 is selected */
            case 0:
                 Fragment fragment1 = new DayFragment();
                return fragment1;

            /** tab2 is selected */
            case 1:
                Fragment fragment2 = new WeekFragment();
                return fragment2;
            /** tab2 is selected */
            case 2:
                Fragment fragment3 = new MonthFragment();
                return fragment3;
        }
        return null;
    }

    /** Returns the number of pages */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
