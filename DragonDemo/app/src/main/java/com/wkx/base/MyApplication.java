package com.wkx.base;

import android.app.Application;
import android.content.Context;

import com.wkx.fragme.FrameApplication;

public class MyApplication extends FrameApplication {
    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static Context getContext() {
        return myApplication;
    }

    public static Context get07ApplicationContext(){
        return myApplication.getApplicationContext();
    }
}
