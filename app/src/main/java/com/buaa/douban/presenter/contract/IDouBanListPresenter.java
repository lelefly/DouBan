package com.buaa.douban.presenter.contract;

/**
 * Created by admin on 2016/10/25.
 */

public interface IDouBanListPresenter {
    void loadHotData();
    void loadComingData(int start);
    void loadTopDdata(int start);
}
