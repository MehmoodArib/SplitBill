package com.example.mehmood.splitbill.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.utils.Utility;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class DetailedEventActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private TabItem tabExpences;
    private TabItem tabBalances;
    private TextView mTitle;
    private TextView mSubTitle;
    Event event1;


    private int[] tabIcons = {
            R.drawable.expense_ic,
            R.drawable.trans_ic,
    };
    private int[] tabIcons2 = {
            R.drawable.expense_ic_2,
            R.drawable.trans_ic_2,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_event);
        Integer eventId = getIntent().getExtras().getInt("EventId");
        EventViewModel eventViewModel = ViewModelProviders.of(this).get(EventViewModel.class);
        eventViewModel.getEvent(eventId);
        eventViewModel.getEvent().observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {
                setTitle(event.getEventName());
                mTitle.setText(event.getEventName());
                mSubTitle.setText(Utility.getNameList(event.getParticipantsList()));
            }
        });

        toolbar = findViewById(R.id.toolbar);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mSubTitle = toolbar.findViewById(R.id.toolbar_sub_title);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        tabLayout = findViewById(R.id.tabLayout);
        tabBalances = findViewById(R.id.balancesTab);
        tabExpences = findViewById(R.id.expensesTab);
        viewPager = findViewById(R.id.pager);
        tabLayout.getTabAt(0).setIcon(tabIcons2[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), eventId);
        viewPager.setAdapter(viewPagerAdapter);

//        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabIcons2[tab.getPosition()]);

                if (tab.getPosition() == 0) {

                } else if (tab.getPosition() == 1) {

                } else {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.getTabAt(tab.getPosition()).setIcon(tabIcons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

}
