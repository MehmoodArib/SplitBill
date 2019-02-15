package com.example.mehmood.splitbill;

import android.app.Application;
import android.content.Context;

public class App extends Application {
   static Context mContext=null;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this.getApplicationContext();
    }

    public static Context getContext()
    {
        return mContext;
    }
}
