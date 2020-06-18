package com.wkx.model;

import android.content.Context;

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
import com.wkx.secret.RsaUtil;

public class AccountModel implements IComonModel {
    private NetMangerUtils netMangerUtils = NetMangerUtils.getInstance();
    private Context mContext = MyApplication.get07ApplicationContext();

    @Override
    public void getData(IComonPresenter iComonPresenter, int witchApi, Object[] objects) {
        switch (witchApi) {
            case ApiConfig.SEND_VERIFY:
                netMangerUtils.getNetWork(netMangerUtils.sApiService.getLoginVerify(ApiServiceConfig.PASSPORT_OPENAPI_USER + Method.LOGINBYMOBILECODE, (String) objects[0]), iComonPresenter, witchApi);
                break;

            case ApiConfig.VERIFY_LOGIN:
                netMangerUtils.getNetWork(netMangerUtils.getApiSerVice(mContext.getString(R.string.passport_openapi_user)).loginByVerify(new ParamHashMap().add("mobile", objects[0]).add("code", objects[1])), iComonPresenter, witchApi);
                break;
            case ApiConfig.GET_HEADER_INFO:
                String uid = FrameApplication.getFrameApplication().getLoginInfo().getUid();
                netMangerUtils.getNetWork(netMangerUtils.sApiService.getHeaderInfo(ApiServiceConfig.PASSPORT_API + Method.GETUSERHEADERFORMOBILE, new ParamHashMap().add("zuid", uid).add("uid", uid)), iComonPresenter, witchApi);
                break;
            case ApiConfig.REGISTER_PHONE:
                netMangerUtils.getNetWork(netMangerUtils.sApiService.checkVerifyCode(ApiServiceConfig.PASSPORT_API + Method.CHECKMOBILECODE, new ParamHashMap().add("mobile", objects[0]).add("code", objects[1])), iComonPresenter, witchApi);
                break;
            case ApiConfig.CHECK_PHONE_IS_USED:
                netMangerUtils.getNetWork(netMangerUtils.sApiService.checkPhoneIsUsed(ApiServiceConfig.PASSPORT_API + Method.CHECKMOBILEISUSE, objects[0]), iComonPresenter, witchApi);
                break;
            case ApiConfig.SEND_REGISTER_VERIFY:
                netMangerUtils.getNetWork(netMangerUtils.sApiService.sendRegisterVerify(ApiServiceConfig.PASSPORT_API + Method.SENDMOBILECODE, objects[0]), iComonPresenter, witchApi);
                break;
            case ApiConfig.NET_CHECK_USERNAME:
                netMangerUtils.getNetWork(netMangerUtils.sApiService.checkName(ApiServiceConfig.PASSPORT + Method.USERNAMEISEXIST, objects[0]), iComonPresenter, witchApi);
                break;
            case ApiConfig.COMPLETE_REGISTER_WITH_SUBJECT:
                ParamHashMap param = new ParamHashMap().add("username", objects[0]).add("password", RsaUtil.encryptByPublic((String) objects[1]))
                        .add("tel", objects[2]).add("specialty_id", FrameApplication.getFrameApplication().getSelectedInfo().getSpecialty_id())
                        .add("province_id", 0).add("city_id", 0).add("sex", 0).add("from_reg_name", 0).add("from_reg", 0);
                netMangerUtils.getNetWork(netMangerUtils.sApiService.registerCompleteWithSubject(ApiServiceConfig.PASSPORT_API + Method.USERREGFORSIMPLE, param), iComonPresenter, witchApi);
                break;
            case ApiConfig.ACCOUNT_LOGIN:
                ParamHashMap add = new ParamHashMap().add("ZLSessionID", "").add("seccode", "").add("loginName", objects[0])
                        .add("passwd", RsaUtil.encryptByPublic((String) objects[1])).add("cookieday", "")
                        .add("fromUrl", "android").add("ignoreMobile", "0");
                netMangerUtils.getNetWork(netMangerUtils.sApiService.loginByAccount(ApiServiceConfig.PASSPORT_OPENAPI + Method.USERLOGINNEWAUTH, add), iComonPresenter, witchApi);
                break;

            case ApiConfig.GET_WE_CHAT_TOKEN:
                ParamHashMap wxobjects = new ParamHashMap().add("appid", ConstantKey.WX_APP_ID).add("secret", ConstantKey.WX_APP_SECRET).add("code", objects[0]).add("grant_type", "authorization_code");
                netMangerUtils.getNetWork(netMangerUtils.sApiService.getWechatToken(ApiServiceConfig.WX_OAUTH + Method.ACCESS_TOKEN, wxobjects), iComonPresenter, witchApi);
                break;
            case ApiConfig.POST_WE_CHAT_LOGIN_INFO:
                ThirdLoginData data = (ThirdLoginData) objects[0];
                ParamHashMap add1 = new ParamHashMap().add("openid", data.openid).add("type", data.type).add("url", "android");
                netMangerUtils.getNetWork(netMangerUtils.sApiService.loginByWechat(ApiServiceConfig.PASSPORT_API + Method.THIRDLOGIN, add1), iComonPresenter, witchApi);
                break;

            case ApiConfig.BIND_ACCOUNT:
                String account = (String) objects[0];
                String password = (String) objects[1];
                ThirdLoginData thirdLoginData = (ThirdLoginData) objects[2];
                ParamHashMap thirdDataParam = new ParamHashMap().add("username", account).add("password", RsaUtil.encryptByPublic(password))
                        .add("openid", thirdLoginData.openid).add("t_token", thirdLoginData.token)
                        .add("utime", thirdLoginData.utime).add("type", thirdLoginData.type)
                        .add("url", "android").add("state", 1);
                netMangerUtils.getNetWork(netMangerUtils.sApiService.bindThirdAccount(ApiServiceConfig.PASSPORT_API+Method.NEWTHIRDBIND,thirdDataParam),iComonPresenter,witchApi);
                break;

        }
    }
}
