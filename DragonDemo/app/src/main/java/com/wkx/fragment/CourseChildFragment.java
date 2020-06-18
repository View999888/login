package com.wkx.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wkx.adapter.CourseChildAdapter;
import com.wkx.base.BaseMvpFragment;
import com.wkx.bean.BaseInfo;
import com.wkx.bean.CourseListInfo;
import com.wkx.bean.SearchItemEntity;
import com.wkx.dragondemo.R;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.ApiLoadConfig;
import com.wkx.fragme.IComonModel;
import com.wkx.infaces.DataLister;
import com.wkx.model.ComnomHomeMdoel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class CourseChildFragment extends BaseMvpFragment implements DataLister {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int mIndex;
    private int page = 1;
    private List<SearchItemEntity> mList = new ArrayList<>();
    private CourseChildAdapter mAdapter;
    private List<SearchItemEntity> mLists;

    public static CourseChildFragment getInstance(int index) {
        CourseChildFragment fragment = new CourseChildFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("whichFragment", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIndex = (int) getArguments().get("whichFragment");
        }
    }


    @Override
    protected IComonModel setModel() {
        return new ComnomHomeMdoel();
    }

    @Override
    protected void setupData() {
        comonPresenter.getData(ApiConfig.COURSE_CHILD, ApiLoadConfig.NORMAL, mIndex, page);
    }

    @Override
    protected void setupView() {
        initRecycleView(recyclerView, refreshLayout, this);
        mAdapter = new CourseChildAdapter(mList, getContext());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLayout.autoRefresh();
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] pObjects) {
        switch (witchApi) {
            case ApiConfig.COURSE_CHILD:
                BaseInfo<CourseListInfo> baseInfo = (BaseInfo<CourseListInfo>) pObjects[0];
                if (baseInfo.isSuccess()) {
                    mLists = baseInfo.result.lists;
                    int loadMode = (int) ((Object[]) pObjects[1])[0];
                    if (loadMode == ApiLoadConfig.REFRESH) {
                        refreshLayout.finishRefresh();
                        mList.clear();
                    } else if (loadMode == ApiLoadConfig.MORE) refreshLayout.finishLoadMore();
                    mList.addAll(mLists);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


    @Override
    public void setData(int mode) {
        if (mode == ApiLoadConfig.REFRESH)
            comonPresenter.getData(ApiConfig.COURSE_CHILD, ApiLoadConfig.REFRESH, mIndex, 1);
        else {
            page++;
            comonPresenter.getData(ApiConfig.COURSE_CHILD, ApiLoadConfig.MORE, mIndex, page);
        }
    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && recyclerView != null && mAdapter != null) {
            setupData();
        }
    }*/
}
