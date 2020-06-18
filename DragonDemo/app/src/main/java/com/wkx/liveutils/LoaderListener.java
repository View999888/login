package com.wkx.liveutils;

import android.graphics.drawable.Drawable;

/**
 * 图片加载监听
 * Created by huangwy on 2017/9/11.
 * email: kisenhuang@163.com.
 */
public interface LoaderListener {

    boolean success(Drawable resource);

    boolean error(Exception e);

}
