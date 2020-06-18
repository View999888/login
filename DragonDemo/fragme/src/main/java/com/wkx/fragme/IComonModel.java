package com.wkx.fragme;

public interface IComonModel<T> {
    void getData(IComonPresenter iComonPresenter, int witchApi, T... ts);
}
