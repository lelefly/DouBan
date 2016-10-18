package com.buaa.douban.ui.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CheckedTextView;

import com.buaa.douban.R;
import com.buaa.douban.ui.listener.NavigationOpenClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.dl_main)
    protected DrawerLayout dl_main;

    @BindView(R.id.tl_main)
    protected Toolbar tl_main;

    @BindViews({R.id.ctv_douban,R.id.ctv_tudou})
    protected List<CheckedTextView> navItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tl_main.setNavigationOnClickListener(new NavigationOpenClickListener(dl_main));
    }

    @OnClick({R.id.ctv_douban,R.id.ctv_tudou})
    public void OnNavItemClick(CheckedTextView checkedTextView){
        for(CheckedTextView view : navItemList){
            view.setChecked(view.getId() == checkedTextView.getId());
        }
        dl_main.closeDrawers();
    }


}
