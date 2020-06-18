package com.wkx.utils;


import android.app.Application;
import android.content.Context;


public class UtilsApplication extends Application {

    public static UtilsApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static Context getUtilsApplicaitonContext() {
        return mApplication.getApplicationContext();
    }
}
