package com.wkx.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wkx.fragme.ComonPresenter;
import com.wkx.fragme.IComonModel;
import com.wkx.fragme.IComonView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpFragment<M extends IComonModel> extends BaseFragment implements IComonView {
    private M testModel;
    public ComonPresenter comonPresenter;
    private Unbinder mBind;
    private boolean isInit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(setLayout(), container, false);
        mBind = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        if (testModel == null) testModel = setModel();
        if (comonPresenter == null) comonPresenter = new ComonPresenter(this, testModel);
        if (!isInit) {
            setupData();
            isInit = true;
        }
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
    public void onDestroy() {
        super.onDestroy();
        comonPresenter.clear();
        if (mBind != null) mBind.unbind();
    }
}
