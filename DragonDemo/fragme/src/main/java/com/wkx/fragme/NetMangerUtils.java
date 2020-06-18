package com.wkx.fragme;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetMangerUtils {

    public static ApiService sApiService;

    //单例模式
    private NetMangerUtils() {
    }

    private static volatile NetMangerUtils netMangerUtils;

    public static final NetMangerUtils getInstance() {
        if (netMangerUtils == null) {
            synchronized (NetMangerUtils.class) {

                netMangerUtils = new NetMangerUtils();
                sApiService = getService();
            }
        }
        return netMangerUtils;
    }

    private static ApiService getService() {
        return new Retrofit.Builder()
                .baseUrl(ApiServiceConfig.AD_OPENAPI)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(initClient())
                .build()
                .create(ApiService.class);
    }


    //封装Retrofit+rejava
    public <T> ApiService getApiSerVice(T... ts) {
        String baseUrl = ApiServiceConfig.AD_OPENAPI;
        if (ts != null && ts.length != 0) {
            baseUrl = (String) ts[0];
        }

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(initClient())
                .build().create(ApiService.class);
    }


    private static OkHttpClient initClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.addInterceptor(new CommonHeadersInterceptor());
        builder.addInterceptor(new CommonParamsInterceptor());
        builder.addInterceptor(initLogInterceptor());
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(15, TimeUnit.SECONDS);
        return builder.build();
    }

    private static Interceptor initLogInterceptor() {
        HttpLoggingInterceptor log = new HttpLoggingInterceptor();
        log.setLevel(HttpLoggingInterceptor.Level.BODY);
        return log;
    }


    /**
     * 封装观察者  对网络数据的请求方法 及 切换线程模式
     */

    public <T, O> void getNetWork(Observable<T> observable, final IComonPresenter iComonPresenter, final int witchApi, final O... ts) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObservice() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                        iComonPresenter.addObService(d);
                    }

                    @Override
                    public void netSuccess(Object values) {
                        iComonPresenter.OnSuccessful(witchApi, values, ts);
                    }

                    @Override
                    public void netFail(Throwable throwable) {
                        iComonPresenter.OnFailed(witchApi, throwable);
                    }
                });
    }
}
