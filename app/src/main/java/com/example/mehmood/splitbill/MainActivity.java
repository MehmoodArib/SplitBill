package com.example.mehmood.splitbill;


import android.content.Intent;
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

import com.example.mehmood.splitbill.Utills.FragmentUtility;
import com.example.mehmood.splitbill.Utills.SharedPreferencesUtility;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;


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
        String id1 =  SharedPreferencesUtility.getInstance(this).getString(SharedPreferencesUtility.Key.name);
        if (TextUtils.isEmpty(id1)) {
            Intent intent = new Intent(MainActivity.this,LogInActivity.class);
            startActivity(intent);
            finish();

        } else {
            EventListFragment eventListFragment = new EventListFragment();
            FragmentUtility.inflateFragment(eventListFragment, getSupportFragmentManager(), R.id.fragmentContainer, false, false, null);


        }
        setNavHeader();

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

}
