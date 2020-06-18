package com.wkx.model;

import android.content.Context;

import com.wkx.base.MyApplication;
import com.wkx.constants.Constants;
import com.wkx.dragondemo.R;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.ApiServiceConfig;
import com.wkx.fragme.FrameApplication;
import com.wkx.fragme.IComonModel;
import com.wkx.fragme.IComonPresenter;
import com.wkx.fragme.NetMangerUtils;
import com.wkx.fragme.ParamHashMap;
import com.wkx.methods.Method;


public class MainPageModel implements IComonModel {
    NetMangerUtils mNetMangerUtils = NetMangerUtils.getInstance();
    Context mContext = MyApplication.get07ApplicationContext();

    @Override
    public void getData(IComonPresenter pComonPresenter, int witchApi, Object[] pObjects) {
        String specialty_id = FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id();
        switch (witchApi) {
            case ApiConfig.MAIN_PAGE_LIST:
                ParamHashMap add = new ParamHashMap().add("specialty_id", specialty_id).add("page", pObjects[1]).add("limit", Constants.LIMIT_NUM).add("new_banner", 1);
                mNetMangerUtils.getNetWork(mNetMangerUtils.sApiService.getCommonList(ApiServiceConfig.EDU_OPENAPI + Method.GETINDEXCOMMEND, add), pComonPresenter, witchApi, pObjects[0]);
                break;
            case ApiConfig.BANNER_LIVE:
                ParamHashMap live = new ParamHashMap().add("pro", specialty_id).add("more_live", "1").add("is_new", 1).add("new_banner", 1);
                mNetMangerUtils.getNetWork(mNetMangerUtils.sApiService.getBannerLive(ApiServiceConfig.EDU_OPENAPI + Method.GETCAROUSELPHOTO, live), pComonPresenter, witchApi, pObjects[0]);
                break;

            case ApiConfig.VIP_BANNER_LIVE://vip直播的
                ParamHashMap mVipMap = new ParamHashMap().add("pro", specialty_id);
                mNetMangerUtils.getNetWork(mNetMangerUtils.sApiService.getVipPlayer(ApiServiceConfig.EDU_OPENAPI + Method.GETLISTGROUPOPENAPI, mVipMap), pComonPresenter, witchApi, pObjects[0]);
                break;
            case ApiConfig.VIP_LIST://vip列表数据
                ParamHashMap mVipListMap = new ParamHashMap().add("specialty_id",specialty_id ).add("sort","1").add("page",1);
                mNetMangerUtils.getNetWork(mNetMangerUtils.sApiService.getVipInfo(ApiServiceConfig.EDU_OPENAPI + Method.GETLISTVIPOPENAPI, mVipListMap), pComonPresenter, witchApi, pObjects[0]);
                break;
        }
    }
}
