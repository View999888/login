package com.wkx.fragme;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class ComonPresenter<V extends IComonView, M extends IComonModel> implements IComonPresenter {

    private SoftReference<V> mView;
    private SoftReference<M> mModel;
    public List<Disposable> disposableList;

    public ComonPresenter(V view, M model) {
        disposableList=new ArrayList<>();
        mView = new SoftReference<>(view);
        mModel = new SoftReference<>(model);
    }

    @Override
    public void getData(int witchApi, Object... params) {
        if (mModel != null && mModel.get() != null) mModel.get().getData(this, witchApi, params);
    }

    @Override
    public void addObService(Disposable disposable) {
        if (disposableList == null) return;
        disposableList.add(disposable);
    }

    @Override
    public void OnSuccessful(int witchApi, Object... vs) {
        if (mView != null && mView.get() != null) mView.get().OnSuccessful(witchApi, vs);
    }

    @Override
    public void OnFailed(int witchApi, Throwable throwable) {
        if (mView != null && mView.get() != null) mView.get().OnSuccessful(witchApi, throwable);
    }

    public void clear() {
        for (Disposable disposable : disposableList) {
            if (disposable != null && !disposable.isDisposed()) disposable.dispose();
        }
        if (mView != null) {
            mView.clear();
            mView = null;
        }
        if (mModel != null) {
            mModel.clear();
            mModel = null;
        }
    }
}
