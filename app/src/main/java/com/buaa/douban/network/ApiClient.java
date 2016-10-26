package com.buaa.douban.network;

import com.buaa.douban.network.service.DouBanApiService;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/10/26.
 */
public class ApiClient {
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConvertFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    public static final DouBanApiService douBanService = new Retrofit.Builder()
            .baseUrl(Api.DOUBAN_BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(rxJavaCallAdapterFactory)
            .addConverterFactory(gsonConvertFactory)
            .build()
            .create(DouBanApiService.class);
}
