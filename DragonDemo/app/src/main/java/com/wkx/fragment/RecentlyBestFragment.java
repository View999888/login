package com.wkx.fragment;

import android.util.Log;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wkx.adapter.DataGroupAdapter;
import com.wkx.adapter.MeansAdapter;
import com.wkx.base.BaseMvpFragment;
import com.wkx.bean.BaseInfo;
import com.wkx.bean.DataBean;
import com.wkx.dragondemo.R;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.ApiLoadConfig;
import com.wkx.infaces.DataLister;
import com.wkx.model.ComnomHomeMdoel;

import java.util.List;

import butterknife.BindView;

public class RecentlyBestFragment extends BaseMvpFragment<ComnomHomeMdoel> implements DataLister {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private MeansAdapter mMMAdapter;
    private int page = 1;

    public static RecentlyBestFragment newInstance() {
        RecentlyBestFragment fragment = new RecentlyBestFragment();
        return fragment;
    }

    @Override
    protected ComnomHomeMdoel setModel() {
        return new ComnomHomeMdoel();
    }

    @Override
    protected void setupData() {
        comonPresenter.getData(ApiConfig.DATA_ESSENCE, ApiLoadConfig.NORMAL, page);
    }

    @Override
    protected void setupView() {
        initRecycleView(recyclerView, refreshLayout, this);
        mMMAdapter = new MeansAdapter(getContext());

        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mMMAdapter);
    }

    @Override
    protected int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] pObjects) {
        switch (witchApi) {
            case ApiConfig.DATA_ESSENCE:
                int loadMode = (int) ((Object[]) pObjects[1])[0];
                BaseInfo<List<DataBean>> listBaseInfo = (BaseInfo<List<DataBean>>) (pObjects[0]);
                List<DataBean> result = listBaseInfo.result;
                mMMAdapter.setResultBeans(result);
                Log.d(TAG, "setOnSuccess: " + result.size());
                if (loadMode == ApiLoadConfig.REFRESH) {
                    result.clear();
                    refreshLayout.finishRefresh();
                } else {
                    refreshLayout.finishLoadMore();
                }
                break;
        }
    }

    private static final String TAG = "RecentlyBestFragment";

    @Override
    public void setData(int mode) {
        switch (mode) {
            case ApiLoadConfig.REFRESH:
                comonPresenter.getData(ApiConfig.DATA_ESSENCE, ApiLoadConfig.REFRESH, page);
                break;
            case ApiLoadConfig.MORE:
                page++;
                comonPresenter.getData(ApiConfig.DATA_ESSENCE, ApiLoadConfig.MORE, page);
                break;
        }
    }
}
