package com.tongcheng.lib.serv.ui.adapter;

/**
 * 改版履历
 * ============================================================================
 * Time          Author        Version        Content
 * 2015.05.13    liuyun        v7.3.2         新建
 * ============================================================================
 */

import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Adapter公共类
 * 出境首页、列表页中使用
 * 用于一般的列表显示
 * 仅需继承该类，重写getView方法，用setData()方法绑定数据就能实现列表刷新，更新更多的效果
 * 建议放入公共目录
 */
public abstract class CommonAdapter<DataType> extends BaseAdapter {
    
    public static final int TYPE_COVER = 0;
    public static final int TYPE_ADD = 1;

    /**
     * 数据列表
     */
    public ArrayList<DataType> mData = new ArrayList<DataType>();

    public void setData(ArrayList<DataType> data) {
        mData = data;
        notifyDataSetChanged();
    }
    
    public void addData(DataType data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<DataType>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }
    
    /**
     * 设置数据
     * @param data
     * @param type TYPE_COVER TYPE_ADD
     */
    public void setData(ArrayList<DataType> data, int type) {
        if (type == TYPE_COVER) {
            setData(data);
        } else {
            if (data == null) {
                return;
            }
            if (mData == null) {
                mData = new ArrayList<DataType>();
            }
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取数据列表
     * @return 数据列表
     */
    public ArrayList<DataType> getData() {
        return mData;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public DataType getItem(int position) {
        if (mData == null || position < 0 || position >= mData.size()) {
            return null;
        }
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
