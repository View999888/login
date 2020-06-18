package com.wkx.base;

import android.os.Bundle;
import android.text.TextUtils;
import com.wkx.fragme.ComonPresenter;
import com.wkx.fragme.IComonModel;
import com.wkx.fragme.IComonView;

import butterknife.ButterKnife;

public abstract class BaseMvpActivity<M extends IComonModel> extends BaseActivity implements IComonView {
    private M testModel;
    public ComonPresenter comonPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        ButterKnife.bind(this);
        testModel = setModel();
        comonPresenter = new ComonPresenter(this, testModel);
        setupView();
        setupData();
    }

    protected abstract M setModel();

    protected abstract void setupData();

    protected abstract void setupView();

    protected abstract int setLayout();

    protected abstract void setOnSuccess(int witchApi, Object[] objects);

    protected void setFail(int witchApi, Throwable throwable) {
    }


    @Override
    public void OnSuccessful(int witchApi, Object[] objects) {
        setOnSuccess(witchApi, objects);
    }

    @Override
    public void OnFailed(int witchApi, Throwable pThrowable) {
        setLog("net work error: " + witchApi + "error content" + pThrowable != null && !TextUtils.isEmpty(pThrowable.getMessage()) ? pThrowable.getMessage() : "不明错误类型");
        setFail(witchApi, pThrowable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        comonPresenter.clear();
    }
}
