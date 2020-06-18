package com.wkx.fragme;

import io.reactivex.disposables.Disposable;

/**
 * p 层 v层 与 m层之间的桥梁
 */
public interface IComonPresenter<P> extends IComonView {
    void getData(int witchApi, P... ps);//回传数据

    void addObService(Disposable disposable);//取消订阅关系
}
