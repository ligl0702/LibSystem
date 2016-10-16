package com.lll.library.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.adapter.BooksAdapter;
import com.lll.library.adapter.BooksBorrowAdapter;
import com.lll.library.entity.Books;
import com.lll.library.entity.BorrowBook;
import com.lll.library.entity.MyUser;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by guoliangli on 2016/9/9. 借阅图书的列表,读者用
 */
public class BorrowFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mBooksLv;
    private BooksBorrowAdapter mBookAdapter;

    private View contentView = null;
    private String[] bookIds;

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

        mBookAdapter = new BooksBorrowAdapter(getActivity());
        mBooksLv.setAdapter(mBookAdapter);
        mBooksLv.setOnItemClickListener(this);

        queryDataFromBmob();
    }

    private void queryDataFromBmob() {
        String userId = BmobUser.getCurrentUser(MyUser.class).getUsername();
        if (!TextUtils.isEmpty(userId)) {
            BmobQuery<BorrowBook> query = new BmobQuery<>();
            query.addWhereEqualTo("userId", userId);
            query.findObjects(new FindListener<BorrowBook>() {
                @Override
                public void done(List<BorrowBook> list, BmobException e) {
                    if (e == null) {
                        int size = list.size();
                        if (size > 0) {
                            bookIds = new String[size];
                            for (int i = 0; i < size; i++) {
                                bookIds[i] = list.get(i).bookId;
                            }
                            queryBorrowBooks();
                        } else {
                            showToast("暂时没有借阅的书，快去借阅吧！");
                        }
                    } else {
                        showToast(e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }

    private void queryBorrowBooks() {
        BmobQuery<Books> query = new BmobQuery<>();
        List<BmobQuery<Books>> queries = new ArrayList<>();
        for (int i = 0; i < bookIds.length; i++) {
            BmobQuery<Books> query0 = new BmobQuery<>();
            query0.addWhereEqualTo("bookId", bookIds[i]);
            queries.add(query0);
        }
        query.or(queries);
//        query.addWhereContainedIn(Constant.QUERY_CONDITION_TITLE, Arrays.asList(bookIds));
        query.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        mBookAdapter.setData(list);
                    } else {
                        showToast("暂时没有借阅的书，快去借阅吧！");
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
