package com.buaa.douban.ui.listener;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

/**
 * Created by Administrator on 2016/10/18.
 */
public class NavigationOpenClickListener implements View.OnClickListener {
    private final DrawerLayout drawerLayout;

    public NavigationOpenClickListener(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onClick(View v) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
