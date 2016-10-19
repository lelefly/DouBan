package com.buaa.douban.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buaa.douban.R;

/**
 * Created by Administrator on 2016/10/18.
 */
public class DouBanComingFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.douban_film_layout,null);
        TextView textView = (TextView) view.findViewById(R.id.tv);
        textView.setText("即将上映");
        return view;
    }
}
