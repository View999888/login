package com.wkx.infaces;

public interface OnRecyclerItemClick<T> {
    void onItemClick(int pos, T... pTS);
}
