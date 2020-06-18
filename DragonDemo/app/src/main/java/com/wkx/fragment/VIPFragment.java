package com.wkx.fragment;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wkx.adapter.MainHomeAdapter;
import com.wkx.adapter.VipAdapter;
import com.wkx.base.BaseMvpFragment;
import com.wkx.bean.BaseInfo;
import com.wkx.bean.IndexCommondEntity;
import com.wkx.bean.LiveBean;
import com.wkx.bean.MainBannerBean;
import com.wkx.bean.VipListBean;
import com.wkx.bean.VipPlayerBean;
import com.wkx.dragondemo.R;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.ApiLoadConfig;
import com.wkx.fragme.IComonModel;
import com.wkx.infaces.DataLister;
import com.wkx.model.MainPageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VIPFragment extends BaseMvpFragment implements DataLister {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private int page = 1;
    private int sort = 1;
    private VipAdapter mVipAdapter;

    // private List<VipPlayerBean.LunbotuBean> bannerData = new ArrayList<>();//banner
    private List<String> bannerData = new ArrayList<>();//banner
    private List<VipPlayerBean.LiveBeanX.LiveBean> mLiveBeans = new ArrayList<>();//zhibo
    private List<VipListBean.ListBean> data = new ArrayList<>();//liebiao


    @Override
    protected IComonModel setModel() {
        return new MainPageModel();
    }

    @Override
    protected void setupData() {
        comonPresenter.getData(ApiConfig.VIP_BANNER_LIVE, ApiLoadConfig.NORMAL);//banner和直播
        comonPresenter.getData(ApiConfig.VIP_LIST, ApiLoadConfig.NORMAL, page, sort);//vip展示列表
    }

    @Override
    protected void setupView() {
        initRecycleView(recyclerView, refreshLayout, this);

        mVipAdapter = new VipAdapter(getActivity(), bannerData, mLiveBeans, data);
        recyclerView.setAdapter(mVipAdapter);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_v_i_p;
    }

    private boolean mainList = false, banLive = false;

    @Override
    protected void setOnSuccess(int witchApi, Object[] pObjects) {
        switch (witchApi) {
            case ApiConfig.VIP_LIST:

                int loadMode = (int) ((Object[]) pObjects[1])[0];
                BaseInfo<VipListBean> vipPageListBaseInfo = (BaseInfo<VipListBean>) pObjects[0];

                if (vipPageListBaseInfo.isSuccess()) {
                    if (loadMode == ApiLoadConfig.MORE) refreshLayout.finishLoadMore();
                    if (loadMode == ApiLoadConfig.REFRESH) {
                        data.clear();
                        refreshLayout.finishRefresh();
                    }
                    data.addAll(vipPageListBaseInfo.result.getList());
                    mainList = true;
                    if (banLive) {
                        mVipAdapter.notifyDataSetChanged();
                        mainList = false;
                    }
                } else showToast("列表加载错误");

                break;
            case ApiConfig.VIP_BANNER_LIVE:
                JsonObject jsonObject = (JsonObject) pObjects[0];
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    if (object.getString("errNo").equals("0")) {
                        int load = (int) ((Object[]) pObjects[1])[0];
                        if (load == ApiLoadConfig.REFRESH) {
                            bannerData.clear();
                            mLiveBeans.clear();
                        }
                        String result = object.getString("result");
                        JSONObject resultObject = new JSONObject(result);
                        String live = resultObject.getString("live");
                        if (live.equals("true") || live.equals("false")) {
                            resultObject.remove("live");
                        }
                        result = resultObject.toString();
                        Gson gson = new Gson();
                        VipPlayerBean vipPlayerBean = gson.fromJson(result, VipPlayerBean.class);

                        List<VipPlayerBean.LunbotuBean> lunbotu = vipPlayerBean.getLunbotu();

                        for (VipPlayerBean.LunbotuBean datum : lunbotu) {
                            bannerData.add(datum.getImg());
                        }
                        Log.d(TAG, "setOnSuccess: " + bannerData.size());

                        if (vipPlayerBean.getLive() != null) {
                            mLiveBeans.addAll(vipPlayerBean.getLive().getLive());
                        }
                        banLive = true;
                        if (mainList) {
                            mVipAdapter.notifyDataSetChanged();
                            banLive = false;
                        }
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }


                break;
        }
    }

    private static final String TAG = "VIPFragment";

    @Override
    public void setData(int mode) {
        if (mode == ApiLoadConfig.REFRESH) {
            mainList = false;
            banLive = false;
            comonPresenter.getData(ApiConfig.VIP_LIST, ApiLoadConfig.REFRESH, 1);
            comonPresenter.getData(ApiConfig.VIP_BANNER_LIVE, ApiLoadConfig.REFRESH);
        } else {
            page++;
            comonPresenter.getData(ApiConfig.MAIN_PAGE_LIST, ApiLoadConfig.MORE, page);
        }
    }
}