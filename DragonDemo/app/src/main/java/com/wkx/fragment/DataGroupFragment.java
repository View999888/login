package com.wkx.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wkx.adapter.DataGroupAdapter;
import com.wkx.base.BaseMvpFragment;
import com.wkx.bean.BaseInfo;
import com.wkx.bean.DataGroupListEntity;
import com.wkx.constants.ConstantKey;
import com.wkx.dragondemo.HomeActivity;
import com.wkx.dragondemo.LoginActivity;
import com.wkx.dragondemo.R;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.ApiLoadConfig;
import com.wkx.fragme.FrameApplication;
import com.wkx.infaces.DataLister;
import com.wkx.infaces.OnRecyclerItemClick;
import com.wkx.model.ComnomHomeMdoel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.wkx.adapter.DataGroupAdapter.FOCUS_TYPE;
import static com.wkx.adapter.DataGroupAdapter.ITEM_TYPE;
import static com.wkx.methods.JumpConstant.DATAGROUPFRAGMENT_TO_LOGIN;
import static com.wkx.methods.JumpConstant.JUMP_KEY;

public class DataGroupFragment extends BaseMvpFragment<ComnomHomeMdoel> implements DataLister, OnRecyclerItemClick {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int page;
    private DataGroupAdapter mMAdapter;

    private List<DataGroupListEntity> mList = new ArrayList<>();

    public static DataGroupFragment newInstance() {
        DataGroupFragment fragment = new DataGroupFragment();
        return fragment;
    }

    @Override
    protected ComnomHomeMdoel setModel() {
        return new ComnomHomeMdoel();
    }

    @Override
    protected void setupData() {
        comonPresenter.getData(ApiConfig.DATA_GROUP, ApiLoadConfig.NORMAL, page);
    }

    @Override
    protected void setupView() {
        initRecycleView(recyclerView, refreshLayout, this);
        mMAdapter = new DataGroupAdapter(mList, getContext());
        recyclerView.setAdapter(mMAdapter);
        mMAdapter.setOnRecyclerItemClick(this);
    }

    @Override
    protected int setLayout() {
        return R.layout.refresh_list_layout;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] pD) {
        switch (witchApi) {
            case ApiConfig.DATA_GROUP:
                BaseInfo<List<DataGroupListEntity>> info = (BaseInfo<List<DataGroupListEntity>>) pD[0];
                if (info.isSuccess()) {
                    List<DataGroupListEntity> result = info.result;
                    int loadMode = (int) ((Object[]) pD[1])[0];
                    if (loadMode == ApiLoadConfig.REFRESH) {
                        mList.clear();
                        refreshLayout.finishRefresh();
                    } else if (loadMode == ApiLoadConfig.MORE) refreshLayout.finishLoadMore();
                    mList.addAll(result);
                    mMAdapter.notifyDataSetChanged();
                }
                break;
            case ApiConfig.CLICK_CANCEL_FOCUS:
                BaseInfo base = (BaseInfo) pD[0];
                int clickPos = (int) ((Object[]) pD[1])[0];
                if (base.isSuccess()) {
                    showToast("取消成功");
                    mList.get(clickPos).setIs_ftop(0);
                    mMAdapter.notifyItemChanged(clickPos);
                }
                break;
            case ApiConfig.CLICK_TO_FOCUS:
                BaseInfo baseSuc = (BaseInfo) pD[0];
                int clickJoinPos = (int) ((Object[]) pD[1])[0];
                if (baseSuc.isSuccess()) {
                    showToast("关注成功");
                    mList.get(clickJoinPos).setIs_ftop(1);
                    mMAdapter.notifyItemChanged(clickJoinPos);
                }
                break;
        }
    }

    @Override
    public void setData(int mode) {
        if (mode == ApiLoadConfig.REFRESH) {
            comonPresenter.getData(ApiConfig.DATA_GROUP, ApiLoadConfig.REFRESH, 1);
        } else {
            page++;
            comonPresenter.getData(ApiConfig.DATA_GROUP, ApiLoadConfig.MORE, page);
        }
    }

    @Override
    public void onItemClick(int pos, Object[] pObjects) {
        if (pObjects != null && pObjects.length != 0) {
            switch ((int) pObjects[0]) {
                case ITEM_TYPE:
                    HomeActivity activity = (HomeActivity) getActivity();
                    Bundle bundle = new Bundle();
                    bundle.putString(ConstantKey.GROU_TO_DETAIL_GID, mList.get(pos).getGid());
                    activity.navController.navigate(R.id.dataGroupDetailFragment, bundle);
                    break;
                case FOCUS_TYPE:
                    boolean login = FrameApplication.getFrameApplication().isLogin();
                    if (login) {
                        DataGroupListEntity entity = mList.get(pos);
                        if (entity.isFocus()) {//已经关注，取消关注
                            comonPresenter.getData(ApiConfig.CLICK_CANCEL_FOCUS, entity.getGid(), pos);//绿码
                        } else {//没有关注，点击关注
                            comonPresenter.getData(ApiConfig.CLICK_TO_FOCUS, entity.getGid(), entity.getGroup_name(), pos);
                        }
                    } else {
                        startActivity(new Intent(getContext(), LoginActivity.class).putExtra(JUMP_KEY, DATAGROUPFRAGMENT_TO_LOGIN));
                    }
                    break;
            }
        }
    }
}
