package com.lll.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class QueryConditionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mQueryBtn;
    private EditText mBookNameEt;
    private EditText mBookPressEt;
    private EditText mBookAuthorEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_condition_layout);

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

            queryConditionObj.conditionName = mBookNameEt.getText().toString().trim();
            queryConditionObj.conditionAuthor = mBookAuthorEt.getText().toString().trim();
            queryConditionObj.conditionPress = mBookPressEt.getText().toString().trim();
        }

        setResult(RESULT_OK, new Intent().putExtra(Constant.EXTRA_QUERY_CONDITION, queryConditionObj));
        finish();
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
