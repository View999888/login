package com.wkx.dragondemo;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.wkx.base.BaseSplashActivity;
import com.wkx.bean.BaseInfo;
import com.wkx.bean.LoginInfo;
import com.wkx.bean.MainAdEntity;
import com.wkx.bean.SpecialtyChooseEntity;
import com.wkx.constants.ConstantKey;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.IComonModel;
import com.wkx.model.LauchModel;
import com.wkx.secret.SystemUtils;
import com.wkx.utils.newAdd.GlideUtil;
import com.wkx.utils.newAdd.SharedPrefrenceUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.wkx.methods.JumpConstant.JUMP_KEY;
import static com.wkx.methods.JumpConstant.SPLASH_TO_LOGIN;
import static com.wkx.methods.JumpConstant.SPLASH_TO_SUB;

/**
 * 引导页
 */
public class SplashActivity extends BaseSplashActivity {


    private SpecialtyChooseEntity.DataBean mSelectedInfo;
    private BaseInfo<MainAdEntity> mInfo;
    private int preTime = 4;
    private Disposable mSubscribe;

    @Override
    protected IComonModel setModel() {
        return new LauchModel();
    }

    @Override
    protected void setupData() {
        mSelectedInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.SUBJECT_SELECT);
        String specialtyId = "";
        if (mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id())) {
            myApplication.setSelectedInfo(mSelectedInfo);
            specialtyId = mSelectedInfo.getSpecialty_id();
        }

        //获取屏幕的信息
        Point realSize = SystemUtils.getRealSize(this);
        //获取 接口标识 所选专业的id  屏幕长和宽
        comonPresenter.getData(ApiConfig.ADVERT, specialtyId, realSize.x, realSize.y);
        //如果网络不好,直接3秒倒计时跳转
        new Handler().postDelayed(() -> {
            if (mInfo == null) jump();
        }, 3000);

        LoginInfo loginInfo = SharedPrefrenceUtils.getObject(this, ConstantKey.LOGIN_INFO);
        if (loginInfo != null && !TextUtils.isEmpty(loginInfo.getUid()))
            myApplication.setLoginInfo(loginInfo);
    }


    @Override
    protected void setupView() {
        //去除状态栏和标题栏 2.Activity全屏显示，且状态栏被覆盖掉
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initDevice();//获取用户手机的信息
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] pD) {
        mInfo = (BaseInfo<MainAdEntity>) pD[0];
        Log.i(TAG, "setOnSuccess: " + mInfo.result.getInfo_url());
        GlideUtil.loadImage(advertImage, mInfo.result.getInfo_url());
        timeView.setVisibility(View.VISIBLE);
        goTime();
    }

    private static final String TAG = "SplashActivity";

    private void goTime() {
        mSubscribe = Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(pLong -> {
                    if (preTime - pLong > 0) timeView.setText(preTime - pLong + "s");
                    else jump();
                });
    }

    private void jump() {
        if (mSubscribe != null) mSubscribe.dispose();
        Observable.just("我是防抖动").debounce(20, TimeUnit.MILLISECONDS).subscribe(p -> {
            if (mSelectedInfo != null && !TextUtils.isEmpty(mSelectedInfo.getSpecialty_id())) {
                if (myApplication.isLogin()) {
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class).putExtra(JUMP_KEY, SPLASH_TO_LOGIN));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, SubjectActivity.class).putExtra(JUMP_KEY, SPLASH_TO_SUB));
            }
            finish();
        });
    }

    @Override
    public void OnFailed(int witchApi, Throwable pThrowable) {
        super.OnFailed(witchApi, pThrowable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe != null && !mSubscribe.isDisposed()) mSubscribe.dispose();
    }


    @OnClick({R.id.advert_image, R.id.time_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.advert_image:
                if (mInfo != null) {
//                    mInfo.result.getJump_url();
                }
                break;
            case R.id.time_view:
                jump();
                Toast.makeText(myApplication, "tinzhua", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}