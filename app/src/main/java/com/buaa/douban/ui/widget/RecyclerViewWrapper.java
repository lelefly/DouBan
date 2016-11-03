package com.buaa.douban.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.buaa.douban.R;
import com.buaa.douban.ui.adapter.BaseAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/31.
 */
public class RecyclerViewWrapper extends FrameLayout{
    @BindView(R.id.rv_base)
    public RecyclerView rv_base;

    @BindView(R.id.srl_base)
    public SwipeRefreshLayout srl_base;

    private LayoutInflater inflater;

    private LAYOUT_MANAGER_TYPE layout_manager_type;
    private int lastVisibleItemPosition;
    private int mVisibleItemCount = 0;
    private int mTotalItemCount = 0;
    private int mPreviousTotal = 0;
    private int mFirstVisibleItem;


    private OnLoadMoreListener onLoadMoreListener;
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;


    public RecyclerViewWrapper(Context context) {
        this(context, null);
    }

    public RecyclerViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflater = LayoutInflater.from(getContext());
        View rootView = inflater.inflate(R.layout.rvw_layout,this);
        rv_base = (RecyclerView) rootView.findViewById(R.id.rv_base);
        srl_base = (SwipeRefreshLayout) rootView.findViewById(R.id.srl_base);
//        srl_base.removeView(rv_base);
//        View verticalView = inflater.inflate(R.layout.recyclerview_vertical, srl_base, true);
//        rv_base = (RecyclerView) verticalView.findViewById(R.id.rv_recycler);
//        ButterKnife.bind(rootView);
    }

    public void setAdapter(BaseAdapter adapter){
        rv_base.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        rv_base.setLayoutManager(layoutManager);
    }

    public void enableRefresh(boolean enable) {
        if (enable != srl_base.isEnabled()) {
            srl_base.setEnabled(enable);
        }
    }

    public void enableLoadMore(boolean enable){
        if(enable){
            rv_base.addOnScrollListener(onScrollListener);
        }
        BaseAdapter adapter = (BaseAdapter) (rv_base.getAdapter());
        if(adapter!=null) {
            adapter.enableLoadingMore(enable);
        }
    }

    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
        onRefreshListener = listener;
        srl_base.setOnRefreshListener(listener);
        srl_base.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void setRefreshing(boolean refreshing){
        if(srl_base!=null){
            srl_base.setRefreshing(refreshing);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        onLoadMoreListener = listener;
    }

    public interface OnLoadMoreListener{
        void loadMore();
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = rv_base.getLayoutManager();
            if(layout_manager_type == null){
                if(layoutManager instanceof GridLayoutManager){
                    layout_manager_type = LAYOUT_MANAGER_TYPE.GRID;
                }else if(layoutManager instanceof LinearLayoutManager){
                    layout_manager_type = LAYOUT_MANAGER_TYPE.LINEAR;
                }else{
                    throw new RuntimeException("Unsupported LayoutManager");
                }
            }
            switch (layout_manager_type){
                case LINEAR:
                    mVisibleItemCount = layoutManager.getChildCount();
                    mTotalItemCount = layoutManager.getItemCount();
                    lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                    mFirstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                    break;
                case GRID:
                    lastVisibleItemPosition = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
                    mFirstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                    break;
            }
                if((mTotalItemCount - mVisibleItemCount) <= mFirstVisibleItem){
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.loadMore();
                    }
                }
        }
    };

    public enum LAYOUT_MANAGER_TYPE{
        LINEAR,
        GRID,
    }
}
