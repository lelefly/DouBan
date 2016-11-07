package com.buaa.douban.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buaa.douban.R;
import com.buaa.douban.model.douban.DouBanItemInfo;
import com.buaa.douban.util.ImageLoader;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/1.
 */
public class DouBanHotAdapter extends BaseAdapter<DouBanItemInfo> {

    public DouBanHotAdapter(Context context) {
        super(context);
    }

    @Override
    public void onBindAdapterViewHolder(BaseViewHolder holder, int dataListPosition) {
        DouBanItemInfo douBanItemInfo = getAdapterDataItem(dataListPosition);
        if(holder instanceof DouBanHolder){
            DouBanHolder douBanHolder = (DouBanHolder)holder;
            douBanHolder.tv_title.setText(douBanItemInfo.images.large);
            Glide.with(mContext).load(douBanItemInfo.images.large).into(douBanHolder.iv_hot);
        }
    }

    @Override
    public BaseViewHolder onCreateAdapterViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_NORMAL){
            return new DouBanHolder(LayoutInflater.from(mContext).inflate(R.layout.douban_hot_item,parent,false));
        }else{
            return new BaseViewHolder(new View(mContext));
        }
    }

    public class DouBanHolder extends BaseViewHolder<DouBanItemInfo>{
        @BindView(R.id.tv_title)
        protected TextView tv_title;

        @BindView(R.id.iv_hot)
        protected ImageView iv_hot;

        public DouBanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
