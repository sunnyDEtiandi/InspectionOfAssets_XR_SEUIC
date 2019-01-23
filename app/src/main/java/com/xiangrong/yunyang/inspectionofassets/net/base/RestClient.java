package com.xiangrong.yunyang.inspectionofassets.net.base;

import com.xiangrong.yunyang.inspectionofassets.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者    yunyang
 * 时间    2019/1/23 14:49
 * 文件    InspectionOfAssets
 * 描述   Retrofit的封装
 */
public class RestClient {

    private static Retrofit sRetrofit;

    /**
     * Retrofit配置
     */
    private static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient
                    .Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            if (BuildConfig.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                // 设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
            }

            OkHttpClient okHttpClient = builder.build();
            sRetrofit = new Retrofit
                    .Builder()
                    .baseUrl(ApiService.API_SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

        }

        return sRetrofit;
    }

    public static ApiService getRestClient() {
        return getRetrofit().create(ApiService.class);
    }

}
