package com.example.mehmood.splitbill.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    private String eventId;
    private ViewPagerAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }
    public ViewPagerAdapter(FragmentManager fm, int numOfTabs, String eventId)
    {
        this(fm,numOfTabs);
        this.eventId=eventId;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Fragment frg=new ExpensesFragment();
                Bundle data=new Bundle();
                data.putString("eventId",eventId);
                frg.setArguments(data);
                return frg;
            case 1:
                return new BalancesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
