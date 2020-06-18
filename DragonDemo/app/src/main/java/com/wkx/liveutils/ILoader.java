package com.wkx.liveutils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;

/**
 * Created by huangwy on 2017/9/11.
 * email: kisenhuang@163.com.
 */

interface ILoader {

    void setConfig(ZLImageLoader.LoaderConfig config);

    void loadImageForNet(Context context, String url, ImageView view,
                         ZLImageLoader.LoaderConfig config, LoaderListener callback);

    void loadImageForNet(Context context, String url, Target target,
                         ZLImageLoader.LoaderConfig config, LoaderListener callback);

    void loadImageForLocal(Context context, Uri uri, ImageView view,
                           ZLImageLoader.LoaderConfig config);

    void cancelCall(Context context);
}
