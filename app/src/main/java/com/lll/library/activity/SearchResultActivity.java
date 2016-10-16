package com.lll.library.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.adapter.BooksAdapter;
import com.lll.library.entity.Books;
import com.lll.library.entity.QueryConditionObj;
import com.lll.library.util.Constant;
import com.lll.library.util.MyLoadingDialog;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LLL on 2016/10/16.
 */
public class SearchResultActivity extends Activity implements AdapterView.OnItemClickListener {
    private ListView mBooksLv;
    private BooksAdapter mBookAdapter;
    private QueryConditionObj queryConditionObj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_layout);

        initView();
        getConditionFromBundle();
        queryDataFromBmob();
    }

    public void getConditionFromBundle() {
        if (getIntent().getSerializableExtra(Constant.EXTRA_QUERY_CONDITION) != null) {
            queryConditionObj = (QueryConditionObj) getIntent().getSerializableExtra(Constant.EXTRA_QUERY_CONDITION);
        }
    }

    private void initView() {
        mBooksLv = (ListView) findViewById(R.id.lv_books);
        mBookAdapter = new BooksAdapter(this);
        mBooksLv.setAdapter(mBookAdapter);
        mBooksLv.setOnItemClickListener(this);
    }

    private void queryDataFromBmob() {
        MyLoadingDialog.showLoading(this);

        BmobQuery<Books> query = new BmobQuery<>();
        if (queryConditionObj != null) {
            if (!TextUtils.isEmpty(queryConditionObj.conditionTitle)) {
                query.addWhereEqualTo(Constant.QUERY_CONDITION_TITLE, queryConditionObj.conditionTitle);
            }
            if (!TextUtils.isEmpty(queryConditionObj.conditionAuthor)) {
                query.addWhereEqualTo(Constant.QUERY_CONDITION_AUTOR, queryConditionObj.conditionAuthor);
            }
            if (!TextUtils.isEmpty(queryConditionObj.conditionPublisher)) {
                query.addWhereEqualTo(Constant.QUERY_CONDITION_PUBLISHER, queryConditionObj.conditionPublisher);
            }
        }

        query.setLimit(Constant.QUERY_LIMIT);

        query.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                MyLoadingDialog.dismissLoading();

                if (e == null) {
                    if (list.size() > 0) {
                        mBookAdapter.setData(list);
                    } else {
                        showToast("未查询到结果，请重新搜索");
                    }

                } else {
                    showToast(e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Books book = (Books) mBookAdapter.getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(SearchResultActivity.this);
        builder.setTitle(book.title);
        builder.setMessage(book.summary);
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
