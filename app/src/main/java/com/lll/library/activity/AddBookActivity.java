package com.lll.library.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.adapter.CommonAdapter;
import com.lll.library.entity.BookType;
import com.lll.library.util.MyLoadingDialog;
import com.lll.library.view.layout.TitleBar;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

/**
 * Created by LLL on 2016/10/15.
 */
public class AddBookActivity extends Activity implements View.OnClickListener {
    private EditText mISBNEt, mTitleEt, mAuthorEt, mPublisherEt, mPubDateEt, mSummaryEt;
    private Spinner mBookTypeSp;
    private Button mSaveBtn;

    private List<BookType> mBookTypeList = new ArrayList<>();
    private MySpinnerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book_layout);

        initView();
        requestBookType();
    }

    private void initView() {
        mISBNEt = (EditText) findViewById(R.id.et_isbn);
        mTitleEt = (EditText) findViewById(R.id.et_title);
        mAuthorEt = (EditText) findViewById(R.id.et_author);
        mPublisherEt = (EditText) findViewById(R.id.et_publisher);
        mPubDateEt = (EditText) findViewById(R.id.et_pubdate);
        mSummaryEt = (EditText) findViewById(R.id.et_summary);

        mBookTypeSp = (Spinner) findViewById(R.id.sp_book_type);
        mSaveBtn = (Button) findViewById(R.id.btn_save);

        mSaveBtn.setOnClickListener(this);

        mAdapter = new MySpinnerAdapter();
        mBookTypeSp.setAdapter(mAdapter);
    }

    private void requestBookType() {
        MyLoadingDialog.showLoading(this);

        String bql = "select * from tb_book_type";
        BmobQuery<BookType> bookTypeBmobQuery = new BmobQuery<>();
        bookTypeBmobQuery.doSQLQuery(bql, new SQLQueryListener<BookType>() {
            @Override
            public void done(BmobQueryResult<BookType> bmobQueryResult, BmobException e) {
                MyLoadingDialog.dismissLoading();

                if (e == null) {
                    List<BookType> list = bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {
                        BookType type0 = new BookType();
                        type0.typeId = "0";
                        type0.typeName = "推荐";
                        mBookTypeList.add(type0);
                        for (BookType type : list) {
                            mBookTypeList.add(type);
                        }
                        mAdapter.setData(mBookTypeList);
                    }
                } else {
                    showToast(e.getMessage());
                }
            }
        });
    }

    private boolean validate() {
        if (TextUtils.isEmpty(mISBNEt.getText().toString().trim()) || mISBNEt.getText().toString().trim().length() != 13) {
            showToast("请输入13位ISBN号");
            mISBNEt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mTitleEt.getText().toString().trim())) {
            showToast("请输入图书名");
            mTitleEt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mAuthorEt.getText().toString().trim())) {
            showToast("请输入作者");
            mAuthorEt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mPublisherEt.getText().toString().trim())) {
            showToast("请输入出版社名称");
            mPublisherEt.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(mPubDateEt.getText().toString().trim())) {
            showToast("请输入出版日期");
            mPubDateEt.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                save();
                break;
        }
    }

    private void save() {
        if (validate()) {

        }
    }

    private void showToast(String msg) {
        Toast.makeText(AddBookActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private class MySpinnerAdapter extends CommonAdapter<BookType> {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(AddBookActivity.this, android.R.layout.simple_spinner_item, null);
            }

            BookType bookType = getItem(position);
            TextView type = (TextView) convertView.findViewById(android.R.id.text1);
            type.setText(bookType.typeName);

            return convertView;
        }
    }

}
