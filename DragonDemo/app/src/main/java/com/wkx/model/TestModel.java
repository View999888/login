package com.wkx.model;

import com.wkx.bean.Device;
import com.wkx.bean.LoginInfo;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.FrameApplication;
import com.wkx.fragme.IComonModel;
import com.wkx.fragme.IComonPresenter;
import com.wkx.fragme.NetMangerUtils;

import java.util.Map;

/**
 * 获取网络数据
 */
public class TestModel implements IComonModel {
    //用单例对象 获取我们网络数据的方法
    NetMangerUtils netMangerUtils = NetMangerUtils.getInstance();

    @Override
    public void getData(IComonPresenter iComonPresenter, int witchApi, Object[] objects) {
        switch (witchApi) {
            case ApiConfig.TEST_GET:

                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUid("1");
                Device device = new Device();
                device.setDeviceName("aa");
                FrameApplication.getFrameApplication().setLoginInfo(loginInfo);
                FrameApplication.getFrameApplication().setDeviceInfo(device);

                netMangerUtils.getNetWork(netMangerUtils.getApiSerVice().getTestInfo((Map) objects[1], (int) objects[2]), iComonPresenter, witchApi, (int) objects[0]);
                break;
        }
    }
}
