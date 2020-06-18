package com.wkx.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wkx.fragme.ApiLoadConfig;
import com.wkx.infaces.DataLister;

public class BaseFragment extends Fragment {
    public void initRecycleView(RecyclerView pRecyclerView, SmartRefreshLayout pSmartRefreshLayout, final DataLister dataLister) {
        if (pRecyclerView != null) {
            pRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            //将recycleview的出场动画禁掉，防止刷新闪烁
            ((SimpleItemAnimator)pRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        }

        if (pSmartRefreshLayout != null && dataLister != null) {
            pSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    dataLister.setData(ApiLoadConfig.MORE);
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                    dataLister.setData(ApiLoadConfig.REFRESH);
                }
            });
        }
    }


    public void setLog(Object object) {
        Log.e(TAG, object.toString());
    }

    public void showToast(Object content) {
        Toast.makeText(getContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }
    public int setColor(@ColorRes int pColor){
        return ContextCompat.getColor(getContext(),pColor);
    }

    private static final String TAG = "BaseFragment";

}
