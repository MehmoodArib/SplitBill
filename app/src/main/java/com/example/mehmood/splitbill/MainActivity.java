package com.example.mehmood.splitbill;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.mehmood.splitbill.data.Event;
import com.example.mehmood.splitbill.data.MyDataBase;
import com.example.mehmood.splitbill.ui.DetailedEventActivity;
import com.example.mehmood.splitbill.ui.EventListFragment;
import com.example.mehmood.splitbill.ui.LogInActivity;
import com.example.mehmood.splitbill.ui.ProfileFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mehmood.splitbill.utils.FragmentUtility;
import com.example.mehmood.splitbill.utils.SharedPreferencesUtility;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import androidx.room.Room;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView navHeaderNameTextView;
    private TextView navHeaderEmailTextView;
    private ImageView navHeaderPicture;
    public static MyDataBase myDataBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // local db for testing only
        myDataBase = Room.databaseBuilder(getApplicationContext(), MyDataBase.class, "eventDb").allowMainThreadQueries().build();
       // setEvents();   //hardcoding some Events   Delete it when connected with online database

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

        String id1 = SharedPreferencesUtility.getInstance(this).getString(SharedPreferencesUtility.Key.name);
        if (TextUtils.isEmpty(id1)) {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();

        } else {
            handleIntent(getIntent());
            EventListFragment eventListFragment = new EventListFragment();
            FragmentUtility.inflateFragment(eventListFragment, getSupportFragmentManager(), R.id.fragmentContainer, false, false, null);
            navigationView.setCheckedItem(R.id.home);
        }
        setNavHeader();

    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            String eventId = appLinkData.getLastPathSegment();
            Intent intent2 = new Intent(this, DetailedEventActivity.class);
            intent2.putExtra("EventId", eventId);
            startActivity(intent2);
        }
    }

    public void setNavHeader() {
        navHeaderNameTextView.setText(SharedPreferencesUtility.getInstance(this).getString(SharedPreferencesUtility.Key.name));
        navHeaderEmailTextView.setText(SharedPreferencesUtility.getInstance(this).getString(SharedPreferencesUtility.Key.email));
        String profileUrl = SharedPreferencesUtility.getInstance(this).getString(SharedPreferencesUtility.Key.profileUrl);
        Picasso.get().load(profileUrl).resize(250, 250).transform(new CropCircleTransformation()).into(navHeaderPicture);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.message:
                break;
            case R.id.profile:
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentUtility.inflateFragment(profileFragment, getSupportFragmentManager(), R.id.fragmentContainer, true, true, null);
                break;
            case R.id.home:
                EventListFragment eventListFragment = new EventListFragment();
                FragmentUtility.inflateFragment(eventListFragment, getSupportFragmentManager(), R.id.fragmentContainer, false, false, null);
                break;
            case R.id.Share:
                Toast.makeText(this, "Share", Toast.LENGTH_LONG).show();
                break;
            case R.id.Logout:
                LoginManager.getInstance().logOut();
                SharedPreferencesUtility.getInstance(this).clear();
                Intent intent = new Intent(MainActivity.this,LogInActivity.class);
                startActivity(intent);
                finish();
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

    public void setEvents() {
        Event event1 = new Event("Shimla Trip", "Official", "123",null);
        Event event2 = new Event("Mumbai Trip", "Meeting", "212","INR");
        Event event3 = new Event("Delhi Trip", null, "343","EURO");
        Event event4 = new Event("London", null, "125","DOLLAR");
        Event event5 = new Event("Taj Mahal", "Agra", "432","INR");
        MainActivity.myDataBase.myDao().addEvent(event1);
        MainActivity.myDataBase.myDao().addEvent(event2);
        MainActivity.myDataBase.myDao().addEvent(event3);
        MainActivity.myDataBase.myDao().addEvent(event4);
        MainActivity.myDataBase.myDao().addEvent(event5);
    }
}
