package com.example.mehmood.splitbill.utils.Adapters;

import android.os.Bundle;

import com.example.mehmood.splitbill.ui.Balance.BalancesFragment;
import com.example.mehmood.splitbill.ui.Expense.ExpenseFragment;
import com.example.mehmood.splitbill.utils.Utilities.Utility;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
/**Adapter used in Detailed Event Activity*/
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    private Integer eventId;

    private ViewPagerAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs, Integer eventId)
    {
        this(fm,numOfTabs);
        this.eventId=eventId;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment frg=new ExpenseFragment();
                Bundle data=new Bundle();
                data.putInt(Utility.eventId,eventId);
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
