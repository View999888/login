package com.wkx.fragme;

public interface IComonView<V> {
    void OnSuccessful(int witchApi, V... vs);

    void OnFailed(int witchApi, Throwable throwable);
}
