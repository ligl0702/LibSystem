package com.lll.library.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by LLL on 2016/10/12. 图书表
 */
public class Books extends BmobObject {
    public String bookId;
    public String ISBN;
    public String title;
    public String author;
    public String publisher;
    public String pubDate;
    public String summary;
    public String image;
    public String typeId;
    public String borrow = "0";//是否被借阅，1被借阅，0未借阅
}
