package com.buaa.douban.ui.view;

import com.buaa.douban.model.entity.DouBanInfo;

import java.util.List;

/**
 * Created by admin on 2016/10/25.
 */

public interface IDouBanListView {
    void onLoading();
    void onLoadingFailed();
    void onLoadSuccess(DouBanInfo info);
    void onLoadMore(DouBanInfo info);
}
