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
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/1.
 */
public class DouBanComingAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<DouBanItemInfo> dataList = new ArrayList<>();
    private static final int NORMAL = 1;
    private static final int LAST = 2;
    private int total;
    public DouBanComingAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<DouBanItemInfo> list, int t) {
        dataList.clear();
        if(list!=null){
            dataList.addAll(list);
        }
        total = t;
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL){
            return new DouBanComingHolder(LayoutInflater.from(context).inflate(R.layout.douban_coming_item,parent,false));
        }else{
            return new DouBanLastHolder(LayoutInflater.from(context).inflate(R.layout.douban_coming_last,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof DouBanComingHolder){
            DouBanComingHolder douBanComingHolder = (DouBanComingHolder)holder;
            DouBanItemInfo douBanItemInfo = getAdapterDataItem(position);
            Glide.with(context).load(douBanItemInfo.images.large).into(douBanComingHolder.iv_hot);
            douBanComingHolder.tv_coming_title.setText(douBanItemInfo.title);
            douBanComingHolder.tv_count.setText(context.getString(R.string.douban_collect,douBanItemInfo.collect_count));
        }else{
            DouBanLastHolder douBanLastHolder = (DouBanLastHolder)holder;
            douBanLastHolder.tv_coming_count.setText(context.getString(R.string.douban_bu,total));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position==dataList.size()){
            return LAST;
        }else{
            return NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size()+1;
    }

    public DouBanItemInfo getAdapterDataItem(int dataListPosition){
        return dataList.get(dataListPosition);
    }

    public class DouBanComingHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_hot)
        protected ImageView iv_hot;

        @BindView(R.id.tv_coming_title)
        protected TextView tv_coming_title;

        @BindView(R.id.tv_count)
        protected TextView tv_count;

        public DouBanComingHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class DouBanLastHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_coming_count)
        protected TextView tv_coming_count;

        public DouBanLastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
