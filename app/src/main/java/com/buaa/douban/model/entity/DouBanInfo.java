package com.buaa.douban.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2016/10/25.
 */

public class DouBanInfo implements Serializable{
    public int count;
    public int start;
    public int total;
    public List<DouBanItemInfo> subjects;

}
