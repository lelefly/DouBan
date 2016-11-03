package com.buaa.douban.presenter.contract;

/**
 * Created by admin on 2016/10/25.
 */

public interface IDouBanListPresenter {
    void loadHotData(int start);
    void loadComingData(int start);
    void loadTopData(int start);
}
