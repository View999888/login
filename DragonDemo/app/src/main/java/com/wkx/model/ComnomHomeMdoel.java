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

import java.util.Map;

public class ComnomHomeMdoel implements IComonModel {
    NetMangerUtils mNetMangerUtils = NetMangerUtils.getInstance();
    //Context mContext = MyApplication.get07ApplicationContext();


    @Override
    public void getData(IComonPresenter pComonPresenter, int witchApi, Object[] pObjects) {
        String subjectId = FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id();
        switch (witchApi) {
            case ApiConfig.COURSE_CHILD:
                ParamHashMap add = new ParamHashMap().add("specialty_id", subjectId).add("page", pObjects[2]).add("limit", Constants.LIMIT_NUM).add("course_type", pObjects[1]);
                mNetMangerUtils.getInstance().getNetWork(mNetMangerUtils.sApiService.getCourseChildData(ApiServiceConfig.EDU_OPENAPI + Method.GETLESSONLISTFORAPI, add), pComonPresenter, witchApi, pObjects[0]);
                break;
            case ApiConfig.DATA_GROUP:
                ParamHashMap add0 = new ParamHashMap().add("type", 1).add("fid", FrameApplication.getFrameApplication().getSelectedInfo().getFid()).add("page", pObjects[1]);
                mNetMangerUtils.getInstance().getNetWork(mNetMangerUtils.sApiService.getGroupList(ApiServiceConfig.BBS_OPENAPI + Method.GETGROUPLIST, add0), pComonPresenter, witchApi, pObjects[0]);
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                ParamHashMap add1 = new ParamHashMap().add("group_id", pObjects[0]).add("type", 1).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                mNetMangerUtils.getInstance().getNetWork(mNetMangerUtils.sApiService.removeFocus(ApiServiceConfig.BBS_API + Method.REMOVEGROUP, add1), pComonPresenter, witchApi, pObjects[1]);
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                ParamHashMap add2 = new ParamHashMap().add("gid", pObjects[0]).add("group_name", pObjects[1]).add("screctKey", FrameApplication.getFrameApplicationContext().getString(R.string.secrectKey_posting));
                mNetMangerUtils.getInstance().getNetWork(mNetMangerUtils.sApiService.focus(ApiServiceConfig.BBS_API + Method.JOINGROUP, add2), pComonPresenter, witchApi, pObjects[2]);
                break;
            case ApiConfig.DATA_ESSENCE:
                ParamHashMap add3 = new ParamHashMap().add("fid", FrameApplication.getFrameApplication().getSelectedInfo().getFid()).add("page", pObjects[1]);
                mNetMangerUtils.getInstance().getNetWork(mNetMangerUtils.sApiService.getMeansList(ApiServiceConfig.BBS_OPENAPI + Method.GETTHREADESSENCE, add3), pComonPresenter, witchApi, pObjects[0]);
                break;
            case ApiConfig.GROUP_DETAIL:
                mNetMangerUtils.getInstance().getNetWork(mNetMangerUtils.sApiService.getGroupDetail(ApiServiceConfig.BBS_OPENAPI+Method.GETGROUPTHREADLIST,pObjects[0]),pComonPresenter,witchApi);
                break;
            case ApiConfig.GROUP_DETAIL_FOOTER_DATA:
                mNetMangerUtils.getInstance().getNetWork(mNetMangerUtils.sApiService.getGroupDetailFooterData(ApiServiceConfig.BBS_OPENAPI+Method.GETGROUPTHREADLIST, (Map<String, Object>) pObjects[1]),pComonPresenter,witchApi,pObjects[0]);
                break;
        }
    }
}
