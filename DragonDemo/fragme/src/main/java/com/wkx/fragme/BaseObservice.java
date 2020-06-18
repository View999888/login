package com.wkx.fragme;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 此类主要解决  当数据请求成功 . 请求失败 或程序 退出话,取消订阅关系 ,避免内存泄漏
 */
public abstract class BaseObservice implements Observer {
    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(Object o) {
        netSuccess(o);
        setDisposable();
    }

    @Override
    public void onError(Throwable e) {
        netFail(e);
        setDisposable();
    }

    @Override
    public void onComplete() {
        setDisposable();
    }


    //对两个经常使用的方法进行抽象
    public abstract void netSuccess(Object values);

    public abstract void netFail(Throwable throwable);

    public void setDisposable() {
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
    }
}
