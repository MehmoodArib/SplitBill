package com.example.mehmood.splitbill;


import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehmood.splitbill.Utills.SplitBillUtility;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView navHeaderNameTextView;
    private TextView navHeaderEmailTextView;
    private ImageView navHeaderPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        View headerLayout = navigationView.getHeaderView(0);
        navHeaderEmailTextView = headerLayout.findViewById(R.id.nav_header_textViewEmail);
        navHeaderNameTextView = headerLayout.findViewById(R.id.nav_header_textViewName);
        navHeaderPicture = headerLayout.findViewById(R.id.nav_header_imageButton);


//        if(savedInstanceState==null){
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Message_Fragment()).commit();
//            navigationView.setCheckedItem(R.id.message);
//        }


        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String id1 = sharedPref.getString(getString(R.string.id), "");
        if (TextUtils.isEmpty(id1)) {
            LogInFragment loginFragment = new LogInFragment();
            SplitBillUtility.inflateFragment(loginFragment, getSupportFragmentManager(), R.id.fragementContainer, false, false, null);
        } else {
            EventListFragment eventListFragment = new EventListFragment();
            SplitBillUtility.inflateFragment(eventListFragment, getSupportFragmentManager(), R.id.fragementContainer, false, false, null);


        }
        // setNavHeader();

    }


    public void setNavHeader() {
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        navHeaderNameTextView.setText(sharedPref.getString(getString(R.string.name), ""));
        navHeaderEmailTextView.setText(sharedPref.getString(getString(R.string.email), ""));
        String profileUrl = sharedPref.getString(getString(R.string.profileUrl), "");
        ImageLoadTask imageLoadTask = new ImageLoadTask(profileUrl, navHeaderPicture);
        imageLoadTask.execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.message:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Message_Fragment()).commit();
                break;
            case R.id.profile:

                ProfileFragment profileFragment = new ProfileFragment();
                SplitBillUtility.inflateFragment(profileFragment, getSupportFragmentManager(), R.id.fragementContainer, true, true, null);

                break;
            case R.id.chat:
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Chat_Fragment()).commit();
                break;
            case R.id.Share:
                Toast.makeText(this, "Share", Toast.LENGTH_LONG).show();
                break;
            case R.id.Logout:
                // clearing app data
                ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!


        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
