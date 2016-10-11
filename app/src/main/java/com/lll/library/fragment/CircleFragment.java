package com.lll.library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lll.library.R;
import com.lll.library.view.layout.TitleBar;


/**
 * 首页
 * 圈子-注册用户发表的动态列表
 * Created by guoliangli on 2016/9/9.
 */
public class CircleFragment extends Fragment {

    private TitleBar titleBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.circle_fragment_layout, container, false);
        titleBar= (TitleBar) view.findViewById(R.id.title_bar);
        titleBar.setText("图书管理系统");
        titleBar.setBackViewVisibility(View.GONE);
        return view;
    }
}
