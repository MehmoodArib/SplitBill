package com.example.mehmood.splitbill.ui.Event;

import android.os.Bundle;
import android.widget.TextView;

import com.example.mehmood.splitbill.R;
import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.EventViewModel;
import com.example.mehmood.splitbill.utils.Adapters.ViewPagerAdapter;
import com.example.mehmood.splitbill.utils.Utilities.Utility;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

/**
 * This activity is Launched on click of the event in eventList created by EventListFragment
 * It shows the details of that event
 * this activity launch two fragments in ViewPager Expense fragment and Balance Fragment
 * ExpenseFragment shows the List of the Expense in Event
 * balance fragment shows the balances of the participants.
 */
public class DetailedEventActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TextView mTitle;
    private TextView mSubTitle;
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

        Toolbar mToolbar = findViewById(R.id.toolbar);
        mTitle = mToolbar.findViewById(R.id.toolbar_title);
        mSubTitle = mToolbar.findViewById(R.id.toolbar_sub_title);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTabLayout = findViewById(R.id.tabLayout);
        TabItem tabBalances = findViewById(R.id.balancesTab);
        TabItem tabExpences = findViewById(R.id.expensesTab);
        mViewPager = findViewById(R.id.pager);
        mTabLayout.getTabAt(0).setIcon(tabIcons2[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mTabLayout.getTabCount(), eventId);
        mViewPager.setAdapter(viewPagerAdapter);

        //mTabLayout.setupWithViewPager(mViewPager);
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

    }

}
