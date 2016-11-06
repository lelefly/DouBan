package com.buaa.douban.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.buaa.douban.R;
import com.buaa.douban.model.douban.DouBanInfo;
import com.buaa.douban.model.douban.DouBanItemInfo;
import com.buaa.douban.presenter.contract.IDouBanListPresenter;
import com.buaa.douban.presenter.implement.DouBanListPresenter;
import com.buaa.douban.ui.adapter.DouBanHotAdapter;
import com.buaa.douban.ui.view.IDouBanListView;
import com.buaa.douban.ui.widget.RecyclerViewWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/18.
 */
public class DouBanHotFragment extends Fragment implements IDouBanListView{
    @BindView(R.id.rvw_hot)
    protected RecyclerViewWrapper rvw_hot;

    private IDouBanListPresenter douBanListPresenter;

    private DouBanHotAdapter adapter;

    private int start,total;

    private List<DouBanItemInfo> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.douban_film_layout,null);
        ButterKnife.bind(this, view);
        douBanListPresenter = new DouBanListPresenter(this);
        douBanListPresenter.loadHotData(start);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = new ArrayList<>();
        rvw_hot.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DouBanHotAdapter(getActivity());
        rvw_hot.setAdapter(adapter);
        rvw_hot.enableLoadMore(false);
        rvw_hot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rvw_hot.setRefreshing(true);
                rvw_hot.enableLoadMore(false);
                adapter.setLoading();
                list.clear();
                start = 0;
                douBanListPresenter.loadHotData(start);
            }
        });
        rvw_hot.setOnLoadMoreListener(new RecyclerViewWrapper.OnLoadMoreListener() {
            @Override
            public void loadMore() {
                start +=10;
                if(start<total) {
                    douBanListPresenter.loadHotData(start);
                }else{
                    adapter.setLoadNoData();
                    Toast.makeText(getActivity(),"没有数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingFailed(Throwable e) {
        Toast.makeText(getActivity(),"加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadSuccess(DouBanInfo info) {
        if(info!=null){
            total = info.total;
            if(info.subjects.size()!=0){
                list.addAll(info.subjects);
                adapter.setDataList(list);
            }
            rvw_hot.enableLoadMore(true);
            rvw_hot.setRefreshing(false);
        }
    }

    @Override
    public void onLoadMore(DouBanInfo info) {

    }

}
