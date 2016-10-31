package com.buaa.douban.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buaa.douban.R;
import com.buaa.douban.ui.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/18.
 */
public class DouBanFragment extends Fragment{

    @BindView(R.id.vp_douban)
    protected ViewPager vp_douban;

    @BindView(R.id.tl_douban)
    protected TabLayout tl_douban;

    private MyPagerAdapter adapter;

    private List<Fragment> list = new ArrayList<>();

    private String[] tabs = new String[]{
            "正在热映","Top250"
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.douban_layout,null);
        ButterKnife.bind(this,view);
        tl_douban.setTabMode(TabLayout.MODE_FIXED);
        initFragment();
        adapter = new MyPagerAdapter(getFragmentManager(),tabs,list);
        vp_douban.setAdapter(adapter);
        return view;
    }

    private void initFragment() {
        DouBanHotFragment douBanHotFragment = new DouBanHotFragment();
        DouBanTop250Fragment douBanTop250Fragment = new DouBanTop250Fragment();
        list.add(douBanHotFragment);
        list.add(douBanTop250Fragment);
    }
}
