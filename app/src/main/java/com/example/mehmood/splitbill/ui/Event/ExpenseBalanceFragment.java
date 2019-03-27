package com.example.mehmood.splitbill.ui.Event;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.utils.Adapters.ViewPagerAdapter;
import com.example.mehmood.splitbill.utils.Utilities.Utility;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;


public class ExpenseBalanceFragment extends androidx.fragment.app.Fragment {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Integer eventId;
    private int[] tabIcons = {
            R.drawable.expense_ic,
            R.drawable.trans_ic,
    };
    private int[] tabIcons2 = {
            R.drawable.expense_ic_2,
            R.drawable.trans_ic_2,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("ExBalFragment","On Create");
        View view = inflater.inflate(R.layout.fragment_expense_balance, container, false);
        assert getArguments() != null;
        eventId = getArguments().getInt(Utility.eventId);
        mTabLayout = view.findViewById(R.id.tabLayout);
        TabItem tabBalances = view.findViewById(R.id.balancesTab);
        TabItem tabExpences = view.findViewById(R.id.expensesTab);
        mViewPager = view.findViewById(R.id.pager);
        mTabLayout.getTabAt(0).setIcon(tabIcons2[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), mTabLayout.getTabCount(), eventId);
        mViewPager.setAdapter(viewPagerAdapter);
        // mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                mTabLayout.getTabAt(tab.getPosition()).setIcon(tabIcons2[tab.getPosition()]);


                if (tab.getPosition() == 0) {

                } else if (tab.getPosition() == 1) {

                } else {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mTabLayout.getTabAt(tab.getPosition()).setIcon(tabIcons[tab.getPosition()]);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ExBalFragment","On Resume");
    }
}
