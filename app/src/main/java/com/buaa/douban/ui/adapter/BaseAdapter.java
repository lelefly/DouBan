package com.buaa.douban.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buaa.douban.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/1.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    protected static final int VIEW_TYPE_FOOTER = -1;
    protected static final int VIEW_TYPE_FOOTER_VIEW = -2;
    protected static final int VIEW_TYPE_NORMAL = 0;

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> mDataList = new ArrayList<>();

    private List<View> mHeadViews;
    private Map<Integer,View> mTypeViewMap;
    private Map<View,Integer> mViewTypeMap;

    private int mMinHeaderType;
    private int mMaxHeaderType;
    private int mLastHeaderType;

    private int mMinFooterType;
    private int mMaxFooterType;

    private boolean mEnableLoadingMore;

    private View loadView;

    @BindView(R.id.pb_loadmore)
    protected ProgressBar pb_loadmore;

    @BindView(R.id.tv_no_data)
    protected TextView tv_no_data;

    public BaseAdapter(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mHeadViews = new ArrayList<>();
        mTypeViewMap = new HashMap<>();
        mViewTypeMap = new HashMap<>();

        mMinHeaderType = Integer.MIN_VALUE;
        mMaxHeaderType = mMinHeaderType + 100;
        mLastHeaderType = mMinHeaderType;

        mMaxFooterType = Integer.MAX_VALUE;
        mMinFooterType = mMaxFooterType - 100;
        loadView = mInflater.inflate(R.layout.loadmore_view,null);
        ButterKnife.bind(this,loadView);
    }

    public final void addHeaderView(View view){
        addHeaderView(view, false);
    }

    public final void addHeaderView(View view , boolean hasAnimation){
        if(view == null){
            throw new RuntimeException("headerView cannot be null");
        }
        if(!mHeadViews.contains(view)){
            mHeadViews.add(view);
            mLastHeaderType++;
            mTypeViewMap.put(mLastHeaderType,view);
            mViewTypeMap.put(view, mLastHeaderType);
            if(hasAnimation){
                notifyItemInserted(mHeadViews.size() - 1);
            }else{
                notifyDataSetChanged();
            }
        }
    }

    public int getHeadViewCount(){
        return mHeadViews.size();
    }

    private boolean isHeaderViewType(int viewType){
        return viewType >= mMinHeaderType && viewType <= mMaxHeaderType;
    }

    public final void removeHeaderView(View view){
        removeHeaderView(view, false);
    }

    public final void removeHeaderView(View view, boolean hasAnimation){
        if(mHeadViews.contains(view)){
            int index = mHeadViews.indexOf(view);
            int viewType = mViewTypeMap.remove(view);
            mHeadViews.remove(index);
            mTypeViewMap.remove(viewType);
            if(hasAnimation){
                notifyItemRemoved(index);
            }else{
                notifyDataSetChanged();
            }
        }
    }

    public T getAdapterDataItem(int dataListPosition){
        if(dataListPosition >= 0 && dataListPosition < mDataList.size()){
            return mDataList.get(dataListPosition);
        }
        return null;
    }

    public void setDataList(List<T> dataList){
        mDataList.clear();
        if(dataList != null && !dataList.isEmpty()){
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    public void enableLoadingMore(boolean enable) {
        if (mEnableLoadingMore != enable) {
            mEnableLoadingMore = enable;
            setDataList(new LinkedList<T>(mDataList));
        }
        if(enable){
            pb_loadmore.setVisibility(View.VISIBLE);
            tv_no_data.setVisibility(View.GONE);
        }else{
            pb_loadmore.setVisibility(View.GONE);
            tv_no_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_FOOTER){
            return onCreateLoadingViewHolder(parent, viewType);
        }else if(isHeaderViewType(viewType)){
            return onCreateHeaderViewHolder(parent,viewType);
        }else{
            return onCreateAdapterViewHolder(parent, viewType);
        }
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if(viewType == VIEW_TYPE_NORMAL){
            onBindAdapterViewHolder(holder, position - getHeadViewCount());
        }
    }

    public abstract void onBindAdapterViewHolder(BaseViewHolder holder, int dataListPosition);

    private BaseViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder((mTypeViewMap.get(viewType)));
    }

    protected BaseViewHolder onCreateLoadingViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(loadView);
    }

    public abstract BaseViewHolder onCreateAdapterViewHolder(ViewGroup parent, int viewType);

    @Override
    public final int getItemViewType(int position) {
        if(position < getHeadViewCount()){
            return mViewTypeMap.get(mHeadViews.get(position));
        }else{
            if (mEnableLoadingMore && position == getItemCount() - 1 ) {
                return VIEW_TYPE_FOOTER;
            }  else {
                return VIEW_TYPE_NORMAL;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mEnableLoadingMore) {
            return getHeadViewCount() + mDataList.size() + 1;
        }else{
            return getHeadViewCount() + mDataList.size();
        }
    }
}
