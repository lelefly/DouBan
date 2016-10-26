package com.buaa.douban.presenter.implement;

import android.util.Log;
import android.widget.Toast;

import com.buaa.douban.model.entity.DouBanInfo;
import com.buaa.douban.network.ApiClient;
import com.buaa.douban.presenter.contract.IDouBanListPresenter;
import com.buaa.douban.ui.view.IDouBanListView;

import java.util.List;

import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/26.
 */
public class DouBanListPresenter implements IDouBanListPresenter{
    private final IDouBanListView douBanListView;

    public DouBanListPresenter(IDouBanListView douBanListView) {
        this.douBanListView = douBanListView;
    }

    @Override
    public void loadHotData() {
        ApiClient.douBanService.getHot()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DouBanInfo>() {
                       @Override
                       public void onCompleted() {

                       }

                       @Override
                       public void onError(Throwable e) {
                           Log.i("testlog",e.toString());
                           douBanListView.onLoadingFailed();
                       }

                       @Override
                       public void onNext(DouBanInfo douBanInfo) {
                            douBanListView.onLoadSuccess(douBanInfo);
                       }
                   }
                );
    }


    @Override
    public void loadComingData(int start) {

    }

    @Override
    public void loadTopData(int start) {

    }
}
