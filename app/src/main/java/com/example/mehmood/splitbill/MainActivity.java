package com.example.mehmood.splitbill;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.mehmood.splitbill.Utills.SplitBillUtility;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        String id1 = sharedPref.getString(getString(R.string.id), "");
        if (TextUtils.isEmpty(id1)) {
            LogInFragment loginFragment = new LogInFragment();
            SplitBillUtility.inflateFragment(loginFragment, getSupportFragmentManager(), R.id.fragementContainer, false, false, null);
        }
        else {
            EventListFragment eventListFragment = new EventListFragment();
            SplitBillUtility.inflateFragment(eventListFragment, getSupportFragmentManager(), R.id.fragementContainer, false, false, null);

        }

    }

}
