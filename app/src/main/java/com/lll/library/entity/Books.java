package com.lll.library.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by LLL on 2016/10/12. 图书表
 */
public class Books extends BmobObject {
    public String ISBN;
    public String title;
    public String author;
    public String publisher;
    public String pubDate;
    public String summary;
    public String image;
    public String typeId;
}
