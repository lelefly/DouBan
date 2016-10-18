package com.buaa.douban.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.SimpleDrawerListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;

import com.buaa.douban.R;
import com.buaa.douban.ui.fragment.DouBanFragment;
import com.buaa.douban.ui.fragment.TuDouFragment;
import com.buaa.douban.ui.listener.NavigationOpenClickListener;

import java.util.ArrayList;
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

    @BindView(R.id.fl_content)
    protected FrameLayout fl_content;

    @BindViews({R.id.ctv_douban,R.id.ctv_tudou})
    protected List<CheckedTextView> navItemList;

    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tl_main.setNavigationOnClickListener(new NavigationOpenClickListener(dl_main));
        fragmentList = new ArrayList<>();
        fragmentManager = getFragmentManager();
        initFragment();
    }

    private void initFragment() {
        DouBanFragment douBanFragment = new DouBanFragment();
        fragmentList.add(douBanFragment);
        TuDouFragment tuDouFragment = new TuDouFragment();
        fragmentList.add(tuDouFragment);
    }

    private void setFragment(int position){
        fragmentManager.beginTransaction().replace(R.id.fl_content,fragmentList.get(position)).commitAllowingStateLoss();
    }
    @OnClick({R.id.ctv_douban,R.id.ctv_tudou})
    public void OnNavItemClick(CheckedTextView checkedTextView){
        for(CheckedTextView view : navItemList){
            view.setChecked(view.getId() == checkedTextView.getId());
        }
        dl_main.closeDrawers();
    }
    private final DrawerLayout.DrawerListener drawerListener = new SimpleDrawerListener() {
        @Override
        public void onDrawerClosed(View drawerView) {
            for(CheckedTextView view : navItemList){
                if(view.isChecked()){
                    switch (view.getId()){
                        case R.id.ctv_douban:
                            setFragment(0);
                            break;
                        case R.id.ctv_tudou:
                            setFragment(1);
                            break;
                        default:
                            break;
                    }
                    break;
                }
            }
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
        }
    };



}
