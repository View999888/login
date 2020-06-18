package com.wkx.dragondemo;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wkx.adapter.TestAdapter;
import com.wkx.base.BaseMvpActivity;
import com.wkx.bean.TestBean;
import com.wkx.fragme.ApiConfig;
import com.wkx.fragme.ApiLoadConfig;
import com.wkx.fragme.ParamHashMap;
import com.wkx.model.TestModel;
import com.wkx.infaces.DataLister;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/***.
 *  git 上传代码
 */
public class MainActivity extends BaseMvpActivity<TestModel> {

    @BindView(R.id.recycle_test)
    RecyclerView recycleTest;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    private TestAdapter testAdapter;
    private Map mParams;
    private int pageId = 0;

    @Override
    protected TestModel setModel() {
        return new TestModel();
    }

    @Override
    protected void setupData() {
        mParams = new ParamHashMap().add("c", "api").add("a", "getList");
        comonPresenter.getData(ApiConfig.TEST_GET, ApiLoadConfig.NORMAL, mParams, pageId);

    }

    @Override
    protected void setupView() {
        testAdapter = new TestAdapter(this);
        recycleTest.setAdapter(testAdapter);

        initRecycleView(recycleTest, smart, new DataLister() {
            @Override
            public void setData(int mode) {
                switch (mode) {
                    case ApiLoadConfig.REFRESH:
                        comonPresenter.getData(ApiConfig.TEST_GET, ApiLoadConfig.REFRESH, mParams, pageId);
                        break;
                    case ApiLoadConfig.MORE:
                        comonPresenter.getData(ApiConfig.TEST_GET, ApiLoadConfig.MORE, mParams, pageId);
                        break;
                }
            }
        });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void setOnSuccess(int witchApi, Object[] pD) {
        switch (witchApi) {
            case ApiConfig.TEST_GET:
                List<TestBean.DatasBean> datas = ((TestBean) pD[0]).getDatas();
                testAdapter.setDatasBeanList(datas);

/*
                if ((int)pD[0] == ApiLoadConfig.MORE) {
                    smart.finishLoadmore();
                } else if ((int)pD[0] == ApiLoadConfig.REFRESH) {
                    smart.finishRefresh();
                    datas.clear();
                }*/
                break;
        }
    }

    @Override
    public void OnFailed(int witchApi, Throwable pThrowable) {
        super.OnFailed(witchApi, pThrowable);
        comonPresenter.OnFailed(witchApi, pThrowable);
    }
}