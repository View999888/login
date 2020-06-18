package com.wkx.fragment;


import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wkx.adapter.MainHomeAdapter;
import com.wkx.base.BaseMvpFragment;
import com.wkx.bean.BaseInfo;
import com.wkx.bean.IndexCommondEntity;
import com.wkx.bean.LiveBean;
import com.wkx.bean.MainBannerBean;
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

public class MainPageFragment extends BaseMvpFragment implements DataLister {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int currentPage = 1;

    private List<IndexCommondEntity> bottomList = new ArrayList<>();
    private List<String> bannerData = new ArrayList<>();
    private List<LiveBean> liveData = new ArrayList<>();

    private MainHomeAdapter mMainHomeAdapter;

    @Override
    protected IComonModel setModel() {
        return new MainPageModel();
    }

    @Override
    protected void setupData() {
        comonPresenter.getData(ApiConfig.MAIN_PAGE_LIST, ApiLoadConfig.NORMAL, currentPage);
        comonPresenter.getData(ApiConfig.BANNER_LIVE, ApiLoadConfig.NORMAL);
    }

    @Override
    protected void setupView() {
        initRecycleView(recyclerView, refreshLayout, this);

        mMainHomeAdapter = new MainHomeAdapter(bottomList, bannerData, liveData, getActivity());
        recyclerView.setAdapter(mMainHomeAdapter);
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_main_page;
    }

    private boolean mainList = false, banLive = false;

    @Override
    protected void setOnSuccess(int witchApi, Object[] pObjects) {
        switch (witchApi) {
            case ApiConfig.MAIN_PAGE_LIST:
                int loadMode = (int) ((Object[]) pObjects[1])[0];
                BaseInfo<List<IndexCommondEntity>> baseInfo = (BaseInfo<List<IndexCommondEntity>>) pObjects[0];
                if (baseInfo.isSuccess()) {
                    if (loadMode == ApiLoadConfig.MORE) refreshLayout.finishLoadMore();
                    if (loadMode == ApiLoadConfig.REFRESH) {
                        bottomList.clear();
                        refreshLayout.finishRefresh();
                    }
                    bottomList.addAll(baseInfo.result);
                    mainList = true;
                    if (banLive) {
                        mMainHomeAdapter.notifyDataSetChanged();
                        mainList = false;
                    }
                } else showToast("列表加载错误");
                break;
            case ApiConfig.BANNER_LIVE:
                JsonObject jsonObject = (JsonObject) pObjects[0];
                try {
                    JSONObject object = new JSONObject(jsonObject.toString());
                    if (object.getString("errNo").equals("0")) {
                        int load = (int) ((Object[]) pObjects[1])[0];
                        if (load == ApiLoadConfig.REFRESH) {
                            bannerData.clear();
                            liveData.clear();
                        }
                        String result = object.getString("result");
                        JSONObject resultObject = new JSONObject(result);
                        String live = resultObject.getString("live");
                        if (live.equals("true") || live.equals("false")) {
                            resultObject.remove("live");
                        }
                        result = resultObject.toString();
                        Gson gson = new Gson();
                        MainBannerBean mainBannerBean = gson.fromJson(result, MainBannerBean.class);
                        for (MainBannerBean.CarouselBean data : mainBannerBean.Carousel) {
                            bannerData.add(data.getThumb());
                        }
                        if (mainBannerBean.live != null) {
                            liveData.addAll(mainBannerBean.live);
                        }
                        banLive = true;
                        if (mainList) {
                            mMainHomeAdapter.notifyDataSetChanged();
                            banLive = false;
                        }
                    }
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }
                break;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
    }

    @Override
    public void setData(int mode) {
        if (mode == ApiLoadConfig.REFRESH) {
            mainList = false;
            banLive = false;
            comonPresenter.getData(ApiConfig.MAIN_PAGE_LIST, ApiLoadConfig.REFRESH, 1);
            comonPresenter.getData(ApiConfig.BANNER_LIVE, ApiLoadConfig.REFRESH);
        } else {
            currentPage++;
            comonPresenter.getData(ApiConfig.MAIN_PAGE_LIST, ApiLoadConfig.MORE, currentPage);
        }
    }
}
