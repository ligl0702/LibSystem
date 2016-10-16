package com.lll.library.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lll.library.R;
import com.lll.library.entity.QueryConditionObj;
import com.lll.library.util.Constant;

/**
 * 查询条件
 */
public class SearchConditionActivity extends Activity implements View.OnClickListener {
    private Button mQueryBtn;
    private EditText mBookNameEt;
    private EditText mBookPressEt;
    private EditText mBookAuthorEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_condition_layout);

        initView();

    }

    private void initView() {
        mBookNameEt = (EditText) findViewById(R.id.et_book_name);
        mBookAuthorEt = (EditText) findViewById(R.id.et_book_author);
        mBookPressEt = (EditText) findViewById(R.id.et_book_press);

        mQueryBtn = (Button) findViewById(R.id.btn_query);
        mQueryBtn.setOnClickListener(this);
    }

    private void query() {
        QueryConditionObj queryConditionObj = null;
        if (!TextUtils.isEmpty(mBookNameEt.getText().toString().trim()) || !TextUtils.isEmpty(mBookAuthorEt.getText().toString().trim())
                || !TextUtils.isEmpty(mBookPressEt.getText().toString().trim())) {
            queryConditionObj = new QueryConditionObj();

            queryConditionObj.conditionTitle = mBookNameEt.getText().toString().trim();
            queryConditionObj.conditionAuthor = mBookAuthorEt.getText().toString().trim();
            queryConditionObj.conditionPublisher = mBookPressEt.getText().toString().trim();
        }

        startActivity(new Intent(SearchConditionActivity.this, SearchResultActivity.class)
                .putExtra(Constant.EXTRA_QUERY_CONDITION, queryConditionObj));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                query();
                break;

            default:
                break;

        }
    }

}
