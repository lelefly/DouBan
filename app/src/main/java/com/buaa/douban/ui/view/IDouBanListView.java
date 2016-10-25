package com.buaa.douban.ui.view;

import com.buaa.douban.model.entity.DouBanInfo;

import java.util.List;

/**
 * Created by admin on 2016/10/25.
 */

public interface IDouBanListView {
    void onLoading();
    void onLoadSuccess(List<DouBanInfo> list);
    void onLoadMore(List<DouBanInfo> list);
}
