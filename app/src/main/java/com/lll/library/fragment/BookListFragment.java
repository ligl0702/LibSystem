package com.lll.library.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.adapter.BooksAdapter;
import com.lll.library.entity.BookType;
import com.lll.library.entity.Books;
import com.lll.library.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by guoliangli on 2016/9/9.
 */
public class BookListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mBooksLv;
    private BooksAdapter mBookAdapter;
    private BookType mBookType;
    private ArrayList<Books> bookList;

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

        getDataFromBundle();
        queryDataFromBmob();
//        requestBookInfo();
    }

    private void getDataFromBundle() {
        Bundle bundle = getArguments();
        mBookType = (BookType) bundle.getSerializable(Constant.BOOK_TYPE);
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

    private void queryDataFromBmob() {
        BmobQuery<Books> query = new BmobQuery<>();
        query.addWhereEqualTo(Constant.TYPE_ID, mBookType.typeId);
        query.setLimit(Constant.QUERY_LIMIT);
        query.findObjects(new FindListener<Books>() {
            @Override
            public void done(List<Books> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        mBookAdapter.setData(list);
                    }
                } else {
                    showToast(e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void requestBookInfo() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = new Request.Builder()
                .url("https://api.douban.com/v2/book/search?tag=" + mBookType.typeName + "&count=50" +
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
                            int size = booksArray.length();
                            bookList = new ArrayList<>(size);
                            for (int i = 0; i < size; i++) {
                                Books book = new Books();
                                book.bookId = booksArray.getJSONObject(i).getString("id");
                                book.ISBN = booksArray.getJSONObject(i).getString("isbn13");
                                book.title = booksArray.getJSONObject(i).getString("title");
                                if (booksArray.getJSONObject(i).getJSONArray("author").length() > 0) {
                                    book.author = booksArray.getJSONObject(i).getJSONArray("author").getString(0);
                                } else {
                                    book.author = "未知";
                                }
                                book.publisher = booksArray.getJSONObject(i).getString("publisher");
                                book.pubDate = booksArray.getJSONObject(i).getString("pubdate");
                                book.summary = booksArray.getJSONObject(i).getString("summary");
                                book.image = booksArray.getJSONObject(i).getJSONObject("images").getString("large");
                                book.typeId = mBookType.typeId;

                                bookList.add(book);
                            }

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mBookAdapter.setData(bookList);

                                    addBookDataToBmob(bookList);
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

    private void addBookDataToBmob(List<Books> bookList) {
        List<BmobObject> books = new ArrayList<>();
        for (Books book : bookList) {
            books.add(book);
        }
        BmobBatch batch = new BmobBatch();
        batch.insertBatch(books);
        batch.doBatch(new QueryListListener<BatchResult>() {//批量操作每次只支持最大50条记录的操作。
            @Override
            public void done(List<BatchResult> results, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < results.size(); i++) {
                        BatchResult result = results.get(i);
                        if (result.isSuccess()) {//只有批量添加才返回objectId
                            showToast("第" + i + "个成功：" + result.getObjectId() + "," + result.getUpdatedAt());
                        } else {
                            BmobException error = result.getError();
                            showToast("第" + i + "个失败：" + error.getErrorCode() + "," + error.getMessage());
                        }
                    }
                }
            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
