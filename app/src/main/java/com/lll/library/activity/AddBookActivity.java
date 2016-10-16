package com.lll.library.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.adapter.CommonAdapter;
import com.lll.library.entity.BookType;
import com.lll.library.entity.Books;
import com.lll.library.util.Constant;
import com.lll.library.util.MyLoadingDialog;
import com.lll.library.view.layout.TitleBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LLL on 2016/10/15.
 */
public class AddBookActivity extends Activity {
    private TitleBar mTitleBar;

    private EditText mISBNEt, mTitleEt, mAuthorEt, mPublisherEt, mPubDateEt, mSummaryEt;
    private Spinner mBookTypeSp;

    private List<BookType> mBookTypeList = new ArrayList<>();
    private MySpinnerAdapter mAdapter;
    private Books mBook;

    private String mISBNFromScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_book_layout);

        initView();
        requestBookType();

        if (!TextUtils.isEmpty(getIntent().getStringExtra(Constant.SCAN_EXTRA_RESULT))) {
            mISBNFromScan = getIntent().getStringExtra(Constant.SCAN_EXTRA_RESULT);

            requestBookInfoFromISBN();
        }
    }

    private void initView() {
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        mTitleBar.setRightTextView(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        mISBNEt = (EditText) findViewById(R.id.et_isbn);
        mTitleEt = (EditText) findViewById(R.id.et_title);
        mAuthorEt = (EditText) findViewById(R.id.et_author);
        mPublisherEt = (EditText) findViewById(R.id.et_publisher);
        mPubDateEt = (EditText) findViewById(R.id.et_pubdate);
        mSummaryEt = (EditText) findViewById(R.id.et_summary);

        mBookTypeSp = (Spinner) findViewById(R.id.sp_book_type);

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

    private void requestBookInfoFromISBN() {
        if (!TextUtils.isEmpty(mISBNFromScan)) {
            OkHttpClient mOkHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("size", "10")
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.douban.com/v2/book/search?q=" + mISBNFromScan +
                            "&fields=id,isbn13,title,images,author,publisher,pubdate,summary")
                    .get() //.post(formBody)
                    .build();
            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("wangshu", e + "");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        if (jsonObject.length() > 0) {
                            JSONArray booksArray = jsonObject.getJSONArray(Constant.BOOK_JSON_NAME);
                            if (booksArray.length() > 0) {
                                mBook = new Books();
                                mBook.bookId = booksArray.getJSONObject(0).getString("id");
                                mBook.ISBN = booksArray.getJSONObject(0).getString("isbn13");
                                mBook.title = booksArray.getJSONObject(0).getString("title");
                                if (booksArray.getJSONObject(0).getJSONArray("author").length() > 0) {
                                    mBook.author = booksArray.getJSONObject(0).getJSONArray("author").getString(0);
                                } else {
                                    mBook.author = "未知";
                                }
                                mBook.publisher = booksArray.getJSONObject(0).getString("publisher");
                                mBook.pubDate = booksArray.getJSONObject(0).getString("pubdate");
                                mBook.summary = booksArray.getJSONObject(0).getString("summary");
                                mBook.image = booksArray.getJSONObject(0).getJSONObject("images").getString("large");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        fillBookInfoFromScanISBN();
                                    }
                                });
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        }
    }

    private void fillBookInfoFromScanISBN() {
        if (mBook != null) {
            mISBNEt.setText(mBook.ISBN);
            mTitleEt.setText(mBook.title);
            mAuthorEt.setText(mBook.author);
            mPublisherEt.setText(mBook.publisher);
            mPubDateEt.setText(mBook.pubDate);
            mSummaryEt.setText(mBook.summary);
        }
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

    private void save() {
        if (validate()) {
            Books book = new Books();
            book.ISBN = mISBNEt.getText().toString().trim();
            book.title = mTitleEt.getText().toString().trim();
            book.author = mAuthorEt.getText().toString().trim();
            book.publisher = mPublisherEt.getText().toString().trim();
            book.pubDate = mPubDateEt.getText().toString().trim();
            book.summary = mSummaryEt.getText().toString().trim();
            book.typeId = ((BookType) mBookTypeSp.getSelectedItem()).typeId;

            book.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        showToast("图书信息保存成功：" + s);

                    } else {
                        showToast(e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
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
