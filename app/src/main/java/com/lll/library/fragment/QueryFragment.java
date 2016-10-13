package com.lll.library.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.activity.QueryConditionActivity;
import com.lll.library.adapter.BooksAdapter;
import com.lll.library.entity.Books;
import com.lll.library.entity.QueryConditionObj;
import com.lll.library.util.Constant;
import com.lll.library.view.layout.TitleBar;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;


/**
 * Created by guoliangli on 2016/9/9.
 */
public class QueryFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private TextView mSearchTv;
    private ListView mBooksLv;
    private BooksAdapter mBookAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.query_fragment_layout, container, false);

        mSearchTv = (TextView) view.findViewById(R.id.tv_search);
        mSearchTv.setOnClickListener(this);
        mBooksLv = (ListView) view.findViewById(R.id.lv_books);

        mBookAdapter = new BooksAdapter(getActivity());
        mBooksLv.setAdapter(mBookAdapter);
        mBooksLv.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent(getActivity(), QueryConditionActivity.class);
                startActivityForResult(intent, Constant.QUERY_REQUEST_CODE);
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.QUERY_REQUEST_CODE) {
            StringBuilder bql = new StringBuilder();
            bql.append("select * from Books book ");
            if (data != null) {
                QueryConditionObj obj = (QueryConditionObj) data.getSerializableExtra(Constant.EXTRA_QUERY_CONDITION);
                if (obj != null) {
                    if (!TextUtils.isEmpty(obj.conditionName)) {
                        bql.append("where book.bookName like % " + obj.conditionName);
                    }

                    if (!TextUtils.isEmpty(obj.conditionAuthor)) {
                        bql.append(" and ");
                        bql.append(" book.bookName like % " + obj.conditionAuthor);
                    }

                    if (!TextUtils.isEmpty(obj.conditionPress)) {
                        bql.append(" and ");
                        bql.append(" book.bookName like % " + obj.conditionPress);
                    }
                }

            }

            BmobQuery<Books> booksBmobQuery = new BmobQuery<>();
            booksBmobQuery.setSQL(bql.toString());
            booksBmobQuery.doSQLQuery(new SQLQueryListener<Books>() {
                @Override
                public void done(BmobQueryResult<Books> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        List<Books> booksList = bmobQueryResult.getResults();
                        if (booksList != null && booksList.size() > 0) {
                            mBookAdapter.setData(booksList);
                        }
                    } else {
                        showToast(e.getMessage());
                    }
                }
            });
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Books book = (Books) mBookAdapter.getItem(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.book_introduction));
        builder.setMessage(book.introduction);
        builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
