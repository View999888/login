package com.wkx.fragme;


import android.app.Application;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.text.TextUtils;

import com.wkx.bean.Device;
import com.wkx.bean.LoginInfo;
import com.wkx.bean.SpecialtyChooseEntity;
import com.wkx.utils.UtilsApplication;


public class FrameApplication extends UtilsApplication {
    private static FrameApplication application;
    private Device mDeviceInfo;
    private LoginInfo mLoginInfo;
    private String cookie;

    private boolean isLogin;

    private SpecialtyChooseEntity.DataBean selectedInfo;

    public SpecialtyChooseEntity.DataBean getSelectedInfo() {
        return selectedInfo;
    }

    public void setSelectedInfo(SpecialtyChooseEntity.DataBean selectedInfo) {
        this.selectedInfo = selectedInfo;
    }

    public boolean isLogin() {
        return mLoginInfo != null && !TextUtils.isEmpty(mLoginInfo.getUid());
    }


    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public LoginInfo getLoginInfo() {
        return mLoginInfo;
    }

    public void setLoginInfo(LoginInfo mLoginInfo) {
        this.mLoginInfo = mLoginInfo;
    }

    public Device getDeviceInfo() {
        return mDeviceInfo;
    }

    public void setDeviceInfo(Device mDeviceInfo) {//设备标识
        this.mDeviceInfo = mDeviceInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static FrameApplication getFrameApplication() {
        return application;
    }

    public static Context getFrameApplicationContext() {
        return application.getApplicationContext();
    }
}
