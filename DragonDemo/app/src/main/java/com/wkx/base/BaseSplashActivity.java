package com.wkx.base;

import android.widget.ImageView;
import android.widget.TextView;

import com.wkx.bean.Device;
import com.wkx.dragondemo.R;
import com.wkx.fragme.FrameApplication;
import com.wkx.secret.SystemUtils;
import com.wkx.utils.NetworkUtils;

import butterknife.BindView;


public abstract class BaseSplashActivity extends BaseMvpActivity {
    @BindView(R.id.advert_image)
    public ImageView advertImage;
    @BindView(R.id.time_view)
    public TextView timeView;

    @Override
    protected int setLayout() {//初始化布局
        return R.layout.activity_splash;
    }

    /**
     * 获取 手机的信息
     */
    public void initDevice() {
        Device device = new Device();//仪器
        device.setScreenWidth(SystemUtils.getSize(this).x);//设置屏幕宽度
        device.setScreenHeight(SystemUtils.getSize(this).y);//设置屏幕高度
        device.setDeviceName(SystemUtils.getDeviceName());//版本名称
        device.setSystem(SystemUtils.getSystem(this));//系统版本
        device.setVersion(SystemUtils.getVersion(this));//版本号
        device.setDeviceId(SystemUtils.getDeviceId(this));//设备ID
        device.setLocalIp(NetworkUtils.getLocalIpAddress());//获取ip
        FrameApplication.getFrameApplication().setDeviceInfo(device);
    }
}
