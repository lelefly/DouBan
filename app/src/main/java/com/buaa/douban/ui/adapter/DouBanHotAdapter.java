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
import com.buaa.douban.ui.widget.StarRatingView;
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
            douBanHolder.tv_title.setText(douBanItemInfo.title);
            Glide.with(mContext).load(douBanItemInfo.images.large).into(douBanHolder.iv_hot);
            douBanHolder.star.setRate((int)douBanItemInfo.rating.average);
            String custStr = "";
            for(int i=0;i<douBanItemInfo.casts.size();i++){
                if(i == douBanItemInfo.casts.size()-1){
                    custStr = custStr+douBanItemInfo.casts.get(i).name;
                }else{
                    custStr = custStr+douBanItemInfo.casts.get(i).name+"/";
                }
            }
            String dires = "";
            for(int i=0;i<douBanItemInfo.directors.size();i++){
                if(i == douBanItemInfo.directors.size()-1){
                    dires = custStr+douBanItemInfo.directors.get(i).name;
                }else{
                    dires = custStr+douBanItemInfo.directors.get(i).name+"/";
                }
            }
            douBanHolder.tv_dire.setText("导演："+dires);
            douBanHolder.tv_cust.setText("主演："+custStr);
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

        @BindView(R.id.star)
        protected StarRatingView star;

        @BindView(R.id.tv_cust)
        protected TextView tv_cust;

        @BindView(R.id.tv_dire)
        protected TextView tv_dire;

        public DouBanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
