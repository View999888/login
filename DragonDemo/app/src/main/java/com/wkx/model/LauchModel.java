package com.wkx.model;

import android.content.Context;
import android.text.TextUtils;

import com.wkx.base.MyApplication;
import com.wkx.bean.ThirdLoginData;
import com.wkx.constants.ConstantKey;
import com.wkx.dragondemo.R;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.ApiServiceConfig;
import com.wkx.fragme.FrameApplication;
import com.wkx.fragme.IComonModel;
import com.wkx.fragme.IComonPresenter;
import com.wkx.fragme.NetMangerUtils;
import com.wkx.fragme.ParamHashMap;
import com.wkx.methods.Method;

public class LauchModel implements IComonModel {
    NetMangerUtils netMangerUtils = NetMangerUtils.getInstance();
    Context mContext = MyApplication.get07ApplicationContext();

    @Override
    public void getData(IComonPresenter iComonPresenter, int witchApi, Object[] params) {
        switch (witchApi) {
            case ApiConfig.ADVERT:
                ParamHashMap map = new ParamHashMap().add("w", params[1]).add("h", params[2]).add("positions_id", "APP_QD_01").add("is_show", 0);
                if (!TextUtils.isEmpty((String) params[0])) map.add("specialty_id", params[0]);
                netMangerUtils.getNetWork(netMangerUtils.sApiService.getAdvert(ApiServiceConfig.AD_OPENAPI + Method.ADVERT_PATH, map), iComonPresenter, witchApi);

                break;
            case ApiConfig.SUBJECT:
                netMangerUtils.getNetWork(netMangerUtils.sApiService.getSubjectList(ApiServiceConfig.EDU_OPENAPI+ Method.GETALLSPECIALTY), iComonPresenter, witchApi);
                break;

        }
    }
}
