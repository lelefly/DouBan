package com.buaa.douban.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.buaa.douban.R;
import com.buaa.douban.model.douban.DouBanInfo;
import com.buaa.douban.presenter.contract.IDouBanListPresenter;
import com.buaa.douban.presenter.implement.DouBanListPresenter;
import com.buaa.douban.ui.adapter.DouBanHotAdapter;
import com.buaa.douban.ui.view.IDouBanListView;
import com.buaa.douban.ui.widget.RecyclerViewWrapper;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.douban_film_layout,null);
        ButterKnife.bind(this, view);
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText("www.baidu.com");
        douBanListPresenter = new DouBanListPresenter(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                douBanListPresenter.loadHotData();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvw_hot.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvw_hot.enableLoadMore(true);
        adapter = new DouBanHotAdapter(getActivity());
        rvw_hot.setAdapter(adapter);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingFailed() {
        Toast.makeText(getActivity(),"加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadSuccess(DouBanInfo info) {
        if(info!=null){
            Log.i("testlog",info.subjects.get(0).images.medium+"");
            adapter.setDataList(info.subjects);
        }
    }

    @Override
    public void onLoadMore(DouBanInfo info) {

    }

}
