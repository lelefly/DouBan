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
    private int mVisibleItemCount = 0;
    private int mTotalItemCount = 0;
    private int mPreviousTotal = 0;
    private int mFirstVisibleItem;

    private boolean loading = true;

    private boolean enableLoadMore;


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
        rv_base.addOnScrollListener(onScrollListener);
    }

    public void setAdapter(BaseAdapter adapter){
        rv_base.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        rv_base.setLayoutManager(layoutManager);
    }


    public void enableLoadMore(boolean enable){
        enableLoadMore = enable;
        if(enable){
            loading = true;
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
        if(refreshing){
            mPreviousTotal = 0;
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
                    mFirstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                    break;
                case GRID:
                    mFirstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();
                    break;
            }
            if(loading){
                if(mTotalItemCount > mPreviousTotal){
                    loading = false;
                    mPreviousTotal = mTotalItemCount;
                }
            }
            if(enableLoadMore&&!loading&&(mTotalItemCount - mVisibleItemCount) <= mFirstVisibleItem){
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.loadMore();
                    loading = true;
                }
            }
        }
    };

    public enum LAYOUT_MANAGER_TYPE{
        LINEAR,
        GRID,
    }
}
