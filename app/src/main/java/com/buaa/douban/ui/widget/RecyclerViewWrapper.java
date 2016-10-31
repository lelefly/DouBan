package com.buaa.douban.ui.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.buaa.douban.R;

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
        View rootView = inflater.inflate(R.layout.rvw_layout,this,true);
        ButterKnife.bind(this);
    }

}
