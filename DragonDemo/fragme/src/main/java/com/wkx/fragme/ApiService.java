package com.wkx.fragme;

import com.google.gson.JsonObject;
import com.wkx.bean.BaseInfo;
import com.wkx.bean.CourseListInfo;
import com.wkx.bean.DataBean;
import com.wkx.bean.DataGroupListEntity;
import com.wkx.bean.GroupDetailEntity;
import com.wkx.bean.IndexCommondEntity;
import com.wkx.bean.LatesteEssenceBean;
import com.wkx.bean.LoginInfo;
import com.wkx.bean.MainAdEntity;
import com.wkx.bean.MeansBean;
import com.wkx.bean.PersonHeader;
import com.wkx.bean.SpecialtyChooseEntity;
import com.wkx.bean.TestBean;
import com.wkx.bean.VipListBean;
import com.wkx.bean.VipPlayerBean;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiService {
    @GET(".")
    Observable<TestBean> getTestInfo(@QueryMap Map<String, Object> param, @Query("page_id") int pageId);

    @GET
    Observable<BaseInfo<MainAdEntity>> getAdvert(@Url String url, @QueryMap Map<String, Object> pMap);

    @GET
    Observable<BaseInfo<List<SpecialtyChooseEntity>>> getSubjectList(@Url String url);


    @GET
    Observable<BaseInfo<String>> getLoginVerify(@Url String url, @Query("mobile") String mobile);

    @GET("loginByMobileCode")
    Observable<BaseInfo<LoginInfo>> loginByVerify(@QueryMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<PersonHeader>> getHeaderInfo(@Url String url, @FieldMap Map<String, Object> params);

    @GET
    Observable<BaseInfo<List<IndexCommondEntity>>> getCommonList(@Url String url, @QueryMap Map<String, Object> params);

    @GET
    Observable<JsonObject> getBannerLive(@Url String url, @QueryMap Map<String, Object> params);


    //课程的接口
    @GET
    Observable<BaseInfo<CourseListInfo>> getCourseChildData(@Url String url, @QueryMap Map<String, Object> params);

    //vip直播接口
    @GET
    // Observable<BaseInfo<VipPlayerBean>> getVipPlayer(@Url String url, @QueryMap Map<String, Object> params);
    Observable<JsonObject> getVipPlayer(@Url String url, @QueryMap Map<String, Object> params);

    //vip接口
    @GET
    Observable<BaseInfo<VipListBean>> getVipInfo(@Url String url, @QueryMap Map<String, Object> params);

    @GET
    Observable<BaseInfo<List<DataGroupListEntity>>> getGroupList(@Url String url, @QueryMap Map<String, Object> params);//列表

    @GET
    Observable<BaseInfo<List<DataBean>>> getMeansList(@Url String url, @QueryMap Map<String, Object> params);//列表


    @POST
    @FormUrlEncoded
    Observable<BaseInfo> removeFocus(@Url String url, @FieldMap Map<String, Object> params);//取消

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> focus(@Url String url, @FieldMap Map<String, Object> params);//关注


    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkVerifyCode(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkPhoneIsUsed(@Url String url, @Field("mobile") Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> sendRegisterVerify(@Url String url, @Field("mobile") Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> checkName(@Url String url, @Field("username") Object mobile);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> registerCompleteWithSubject(@Url String url, @FieldMap Map<String, Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> loginByAccount(@Url String url, @FieldMap Map<String, Object> params);

    @GET
    Observable<JsonObject> getWechatToken(@Url String url, @QueryMap Map<String,Object> parmas);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo<LoginInfo>> loginByWechat(@Url String url, @FieldMap Map<String,Object> params);

    @POST
    @FormUrlEncoded
    Observable<BaseInfo> bindThirdAccount(@Url String url, @FieldMap Map<String,Object> params);

    @GET
    Observable<BaseInfo<GroupDetailEntity>> getGroupDetail(@Url String url, @Query("gid") Object object);

    @GET
    Observable<JsonObject> getGroupDetailFooterData(@Url String url, @QueryMap Map<String,Object> parmas);
}
