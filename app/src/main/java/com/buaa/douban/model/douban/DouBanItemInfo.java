package com.buaa.douban.model.douban;

import java.util.List;

/**
 * Created by Administrator on 2016/10/26.
 */
public class DouBanItemInfo {
    public Rating rating;
    public List<String> genres;
    public String title;
    public int collect_count;
    public List<Cast> casts;
    public List<Directors> directors;
    public Images images;
    public String id;
}
