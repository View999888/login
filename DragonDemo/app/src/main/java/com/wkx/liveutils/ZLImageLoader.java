package com.wkx.liveutils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;
import com.wkx.dragondemo.R;


/**
 * 图片加载工具类
 * Created by huangwy on 2017/8/28.
 */
public class ZLImageLoader {

    private static LoaderListener mCallback;
    private static ZLImageLoader mInstance;
    private ILoader mLoader;

    public static ZLImageLoader getInstance() {
        if (mInstance == null) {
            mInstance = new ZLImageLoader();
        }
        return mInstance;
    }

    private ZLImageLoader() {
        mLoader = getLoader();
    }

    /**
     * 生成图片加载器
     */
    private ILoader getLoader() {
        return new GlideLoader();
    }

    /**
     * 为图片加载设置公共配置信息
     *
     * @param config 配置信息
     */
    public void setConfig(LoaderConfig config) {
        mLoader.setConfig(config);
    }

    /**
     * 设置图片加载全局监听
     */
    public void setLoaderListener(LoaderListener callback) {
        mCallback = callback;
    }

    /**
     * 加载图片方法
     */
    public void displayImage(Context context, Uri uri, ImageView view) {
        displayImage(context, uri, view, null);
    }

    /**
     * 加载图片方法
     *
     * @param context 上下文
     * @param uri     本地路径
     * @param view    目标view
     * @param config  动态配置的图片配置信息，只在本次使用有效
     */
    public void displayImage(Context context, Uri uri, ImageView view, LoaderConfig config) {
        try {

            mLoader.loadImageForLocal(context, uri, view, config);
        } catch (Exception e) {
            e.printStackTrace();
            if (config != null && config.errorResId != 0)
                view.setImageResource(config.errorResId);
        }
    }


    public static LoaderConfig getAvatarConfig() {
        final LoaderConfig config = new LoaderConfig();
        config.defaultResId = R.drawable.avatar;
        config.errorResId = R.drawable.avatar;
        return config;
    }


    /**
     * 获取资源文件中资源的Uri
     * 可以使用displayImage方法加载图片
     *
     * @param context 上下文
     * @param id      资源id
     * @return 资源id对应的Uri
     */
    public static Uri getUriFromResource(Context context, int id) {
        Resources resources = context.getResources();
        String path = ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" +
                resources.getResourcePackageName(id) +
                "/" +
                resources.getResourceTypeName(id) +
                "/" +
                resources.getResourceEntryName(id) +
                "/";
        return Uri.parse(path);
    }

    //--------------------------------------------------------------------------------------

    /**
     * 加载图片方法
     */
    public void displayImage(Context context, String url, ImageView view) {
        displayImage(context, url, view, null, mCallback);
    }

    /**
     * 加载图片方法
     */
    public void displayImage(Context context, String url, ImageView view, LoaderListener callback) {
        displayImage(context, url, view, null, callback);
    }

    /**
     * 加载图片方法
     */
    public void displayImage(Context context, String url, ImageView view, LoaderConfig config) {
        displayImage(context, url, view, config, mCallback);
    }

    /**
     * 加载图片方法
     */
    public void displayImage(Context context, String url, ImageView view, LoaderConfig config, LoaderListener callback) {
        load(context, url, view, config, callback);
    }

    /**
     * 加载图片方法
     */
    public void displayImage(Context context, String url, Target target, LoaderConfig config) {
        load(context, url, target, config, null);
    }

    /**
     * 加载图片方法
     */
    public void displayImage(Context context, String url, Target target, LoaderConfig config, LoaderListener callback) {
        load(context, url, target, config, callback);
    }

    /**
     * 图片加载最终方法
     *
     * @param context  上下文
     * @param url      路径
     * @param view     目标view
     * @param config   动态配置的图片配置信息，只在本次使用有效
     * @param callback 图片加载监听
     */
    private void load(Context context, String url, ImageView view, LoaderConfig config, LoaderListener callback) {
        try {
            mLoader.loadImageForNet(context, url, view, config, callback);
        } catch (Exception e) {
            e.printStackTrace();
            if (config != null && config.errorResId != 0)
                if (view == null)
                    return;
            view.setImageResource(config.errorResId);
        }
    }

    /**
     * 图片加载最终方法
     *
     * @param context  上下文
     * @param url      路径
     * @param target   目标view
     * @param config   动态配置的图片配置信息，只在本次使用有效
     * @param callback 图片加载监听
     */
    private void load(Context context, String url, Target target, LoaderConfig config, LoaderListener callback) {
        try {
            mLoader.loadImageForNet(context, url, target, config, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelCall(Context context) {
        mLoader.cancelCall(context);
    }

    /*-----------------------------------------------------------------------------------------*/

    /**
     * 图片加载配置类
     */
    public static class LoaderConfig {
        public int errorResId;
        public int defaultResId;
        public ImageStyle[] imageStyle;
        public int width;
        public int height;
    }

    /**
     * 图片填充类型
     */
    public enum ImageStyle {
        centerCrop,
        centerInside,
        fit,
        noFade,
    }

}
