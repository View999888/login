package com.wkx.liveutils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Glide图片加载工具类
 * Created by huangwy on 2017/9/11.
 * email: kisenhuang@163.com.
 */
class GlideLoader implements ILoader {

    private final DiskCacheStrategy strategy = DiskCacheStrategy.AUTOMATIC;
    private ZLImageLoader.LoaderConfig mConfig;

    @Override
    public void setConfig(ZLImageLoader.LoaderConfig config) {
        mConfig = config;
    }

    @Override
    public void loadImageForNet(Context context, String url, ImageView view,
                                ZLImageLoader.LoaderConfig config, LoaderListener callback) {
        RequestBuilder creator = creator(context, url);
        setupOptions(creator, config);
        setupListener(creator, callback);
        creator.into(view);
    }

    @Override
    public void loadImageForNet(Context context, String url, Target target,
                                ZLImageLoader.LoaderConfig config, LoaderListener callback) {
        RequestBuilder creator = creator(context, url);
        setupOptions(creator, config);
        setupListener(creator, callback);
        creator.into(target);
    }

    @Override
    public void loadImageForLocal(Context context, Uri uri, ImageView view,
                                  ZLImageLoader.LoaderConfig config) {
        RequestBuilder creator = creator(context, uri);
        setupOptions(creator, config);
        creator.into(view);
    }

    @Override
    public void cancelCall(Context context) {
        Glide.with(context).onDestroy();
    }

    /**
     * 获取creator
     */
    private RequestBuilder creator(Context context, String url) {
        if (isGif(url)) {
            return Glide.with(context).asGif().load(url);
        } else { // 兼容CircleImageView等自定义ImageView控件。
            return Glide.with(context).asDrawable().load(url);
        }
    }

    /**
     * 获取creator
     */
    private RequestBuilder creator(Context context, Uri uri) {
        return Glide.with(context).load(uri);
    }

    /**
     * 设置图片加载监听
     */
    @SuppressWarnings("unchecked")
    private void setupListener(RequestBuilder creator, final LoaderListener callback) {
        if (callback == null)
            return;
        creator.listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
                return callback.error(e);
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                return callback.success(resource);
            }
        });
    }

    /**
     * 设置图片加载配置
     */
    private void setupOptions(RequestBuilder creator, ZLImageLoader.LoaderConfig config) {
        config = checkConfig(config);
        if (config == null) {
            return;
        }
        RequestOptions options = new RequestOptions();
        if (config.errorResId > 0)
            options.error(config.errorResId);
        if (config.defaultResId > 0)
            options.placeholder(config.defaultResId);
        for (ZLImageLoader.ImageStyle imageStyle : config.imageStyle) {
            switch (imageStyle) {
                case centerCrop:
                    options.centerCrop();
                    break;
                case centerInside:
                    options.centerInside();
                    break;
                case fit:
                    options.fitCenter();
                    break;
                case noFade:
                    options.dontTransform();
                    break;
            }
        }
        if (strategy != null)
            options.diskCacheStrategy(strategy);
        if (config.width != 0 && config.height != 0)
            options.override(config.width, config.height);
        creator.apply(options);
    }

    /**
     * 检测LoaderConfig
     * 用全局config补充临时config
     */
    @Nullable
    private ZLImageLoader.LoaderConfig checkConfig(ZLImageLoader.LoaderConfig config) {
        if (config == null) {
            config = mConfig;
        } else if (mConfig != null) {
            if (config.defaultResId == 0)
                config.defaultResId = mConfig.defaultResId;
            if (config.errorResId == 0)
                config.errorResId = mConfig.errorResId;
            if (config.imageStyle == null)
                config.imageStyle = mConfig.imageStyle;
        }
        return config;
    }

    /**
     * 检测是否是Gif图
     *
     * @param url 图片地址
     */
    private boolean isGif(@Nullable String url) {
        if (TextUtils.isEmpty(url))
            return false;
        int index = url.lastIndexOf('.');
        return index > 0 && "gif".toUpperCase().equals(url.substring(index + 1).toUpperCase());
    }


}
