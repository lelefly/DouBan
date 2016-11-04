package com.buaa.douban.ui.view;

import com.buaa.douban.model.douban.DouBanInfo;

/**
 * Created by admin on 2016/10/25.
 */

public interface IDouBanListView {
    void onLoading();
    void onLoadingFailed(Throwable e);
    void onLoadSuccess(DouBanInfo info);
    void onLoadMore(DouBanInfo info);
}
