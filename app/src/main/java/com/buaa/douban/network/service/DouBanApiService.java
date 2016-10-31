package com.buaa.douban.network.service;

import com.buaa.douban.model.douban.DouBanInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2016/10/26.
 */
public interface DouBanApiService {
    @GET("in_theaters")
    Observable<DouBanInfo> getHot();

}
