package com.lll.library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lll.library.R;
import com.lll.library.adapter.BooksAdapter;


/**
 * Created by guoliangli on 2016/9/9. 借阅图书的列表,读者用
 */
public class BorrowFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mBooksLv;
    private BooksAdapter mBookAdapter;

    private View contentView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            initView(inflater);
        }
        if (contentView.getParent() != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }

        return contentView;
    }

    private void initView(LayoutInflater inflater) {
        contentView = inflater.inflate(R.layout.book_list_fragment_layout, null);
        mBooksLv = (ListView) contentView.findViewById(R.id.lv_books);

        mBookAdapter = new BooksAdapter(getActivity());
        mBooksLv.setAdapter(mBookAdapter);
        mBooksLv.setOnItemClickListener(this);

        queryDataFromBmob();
    }

    private void queryDataFromBmob() {
        
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
    }
}
