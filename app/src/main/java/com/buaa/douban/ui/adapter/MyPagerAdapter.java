package com.buaa.douban.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class MyPagerAdapter extends android.support.v13.app.FragmentPagerAdapter{

    private String[] tabs;
    private List<Fragment> list;

    public MyPagerAdapter(FragmentManager fm, String[] tabs, List<Fragment> list) {
        super(fm);
        this.tabs = tabs;
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return tabs[0];
            case 1:
                return tabs[1];
            case 2:
                return tabs[2];
            case 3:
                return tabs[3];
            case 4:
                return tabs[4];
            case 5:
                return tabs[5];
            default:
                return "";
        }
    }
}
