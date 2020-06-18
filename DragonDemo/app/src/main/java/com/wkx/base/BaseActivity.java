package com.wkx.base;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wkx.fragme.ApiLoadConfig;
import com.wkx.infaces.DataLister;

public class BaseActivity extends AppCompatActivity {
    public MyApplication myApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        myApplication=(MyApplication)getApplication();

    }

    public void initRecycleView(RecyclerView pRecyclerView, SmartRefreshLayout pSmartRefreshLayout, final DataLister dataLister) {
        if (pRecyclerView != null)
            pRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        Toast.makeText(getApplicationContext(), content.toString(), Toast.LENGTH_SHORT).show();
    }

    private static final String TAG = "BaseActivity";

}
