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
 * Created by guoliangli on 2016/9/9.
 */
public class DeleteFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_fragment_layout, container, false);
        return view;
    }
}
