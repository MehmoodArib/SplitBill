package com.example.mehmood.splitbill;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mehmood.splitbill.ui.Event.AddEventFragment;
import com.example.mehmood.splitbill.ui.Event.DetailedEventActivity;
import com.example.mehmood.splitbill.ui.Event.EventListFragment;
import com.example.mehmood.splitbill.ui.LogIn.LogInActivity;
import com.example.mehmood.splitbill.ui.Profile.ProfileFragment;
import com.example.mehmood.splitbill.utils.Utilities.FragmentUtility;
import com.example.mehmood.splitbill.utils.Utilities.SharedPreferencesUtility;
import com.facebook.login.LoginManager;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.wafflecopter.multicontactpicker.ContactResult;
import com.wafflecopter.multicontactpicker.MultiContactPicker;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


/**
 * This is our Launch Activity
 * from here we check weather the user is log-ed-In or Not
 * by checking the shared preference values null or not.
 * <p>
 * if values are null it means user not log-ed-In.
 * and we launch Login Activity.
 * <p>
 * else we launch EventList Fragment.
 */


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView navHeaderNameTextView;
    private TextView navHeaderEmailTextView;
    private ImageView navHeaderPicture;
    NavigationView navigationView;

    public static final int CONTACT_PICKER_REQUEST = 0; //Contact picker Request Code.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        View headerLayout = navigationView.getHeaderView(0);
        navHeaderEmailTextView = headerLayout.findViewById(R.id.nav_header_textViewEmail);
        navHeaderNameTextView = headerLayout.findViewById(R.id.nav_header_textViewName);
        navHeaderPicture = headerLayout.findViewById(R.id.nav_header_imageButton);
        String name = SharedPreferencesUtility.getInstance(this).getString(SharedPreferencesUtility.Key.name);
        String email = SharedPreferencesUtility.getInstance(this).getString(SharedPreferencesUtility.Key.email);
        String phone = SharedPreferencesUtility.getInstance(this).getString(SharedPreferencesUtility.Key.phone);
        if (TextUtils.isEmpty(name)|TextUtils.isEmpty(email)|TextUtils.isEmpty(phone)) {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();

        } else {
            handleIntent(getIntent());
            EventListFragment eventListFragment = new EventListFragment();
            FragmentUtility.inflateFragment(eventListFragment, getSupportFragmentManager(), R.id.fragmentContainer, false, false, null);
            navigationView.setCheckedItem(R.id.home);
        }
        setNavHeader();//fill the NavHeader Values names,email&photo

    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String appLinkAction = intent.getAction();
        Uri appLinkData = intent.getData();
        if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
            Integer eventId = Integer.parseInt(appLinkData.getLastPathSegment());
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
            case R.id.profile:
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentUtility.inflateFragment(profileFragment, getSupportFragmentManager(), R.id.fragmentContainer, true, true, null);
                break;
            case R.id.home:
                EventListFragment eventListFragment = new EventListFragment();
                FragmentUtility.inflateFragment(eventListFragment, getSupportFragmentManager(), R.id.fragmentContainer, false, true, null);
                break;
            case R.id.Share:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;
            case R.id.Rate:
                rateApp();
                break;
            case R.id.Logout:
                LoginManager.getInstance().logOut();
                SharedPreferencesUtility.getInstance(this).clear();
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
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
        }
        else {
            super.onBackPressed();
        }
    }

    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    /**
     * This code In Activity is for choosing contacts used in Add Event Fragment
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                List<ContactResult> results = MultiContactPicker.obtainResult(data);
                AddEventFragment addEventFragment = (AddEventFragment) getSupportFragmentManager().findFragmentByTag(AddEventFragment.class.getSimpleName());
                addEventFragment.setContacts(results);
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("xyz", "User closed the picker without selecting items.");
            }
        }
    }

    public interface ContactInfo {
        void setContacts(List<ContactResult> contacts);
    }
}
