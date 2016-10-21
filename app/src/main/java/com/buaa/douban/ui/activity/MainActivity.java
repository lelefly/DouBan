package com.buaa.douban.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.SimpleDrawerListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.Toast;

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

    private SearchView searchView;

    private List<Fragment> fragmentList;
    private FragmentManager fragmentManager;
    private Fragment tempFragment;
    private String[] titles = new String[]{"豆瓣","土豆"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(tl_main);
        fragmentList = new ArrayList<>();
        fragmentManager = getFragmentManager();
        initFragment();
        setFragment(0);
        dl_main.addDrawerListener(drawerListener);
        tl_main.setNavigationOnClickListener(new NavigationOpenClickListener(dl_main));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(MainActivity.this, "搜索!!!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initSearchView() {
        searchView.setQueryHint("电影");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this,"搜索"+query,Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        initSearchView();
        return super.onCreateOptionsMenu(menu);
    }

    private void initFragment() {
        DouBanFragment douBanFragment = new DouBanFragment();
        fragmentList.add(douBanFragment);
        TuDouFragment tuDouFragment = new TuDouFragment();
        fragmentList.add(tuDouFragment);
        fragmentManager.beginTransaction().add(R.id.fl_content,douBanFragment).add(R.id.fl_content,tuDouFragment).hide(tuDouFragment).commitAllowingStateLoss();
        tempFragment = douBanFragment;
    }

    private void setFragment(int position){
        fragmentManager.beginTransaction().hide(tempFragment).show(fragmentList.get(position)).commitAllowingStateLoss();
        tempFragment = fragmentList.get(position);
        tl_main.setTitle(titles[position]);
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
