package com.lll.library.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.entity.Books;
import com.lll.library.entity.BorrowBook;
import com.lll.library.entity.MyUser;
import com.lll.library.util.Constant;
import com.lll.library.util.DisplayUtils;
import com.lll.library.util.MyLoadingDialog;
import com.lll.library.util.PicassoImageLoader;
import com.lll.library.view.layout.TitleBar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by liguoliang on 16/10/17.
 */
public class BookDetailsActivity extends BaseActivity {

    Books books;
    @Bind(R.id.tv_book_name)
    TextView tv_book_name;
    @Bind(R.id.tv_book_author)
    TextView tv_book_author;
    @Bind(R.id.tv_book_press)
    TextView tv_book_press;
    @Bind(R.id.tv_book_press_date)
    TextView tv_book_press_date;
    @Bind(R.id.tv_book_summary)
    TextView tv_book_summary;
    @Bind(R.id.iv_book)
    ImageView iv_book;
    @Bind(R.id.tv_isbn)
    TextView tv_isbn;
    @Bind(R.id.btn_borrow)
    Button btn_borrow;

    @Bind(R.id.title_bar)
    TitleBar title_bar;

    PicassoImageLoader mImageLoader;
    private Context context;
    private boolean isBorrowed=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details_activity_layout);
        context=this;
        ButterKnife.bind(this);
        title_bar.setText("图书详情");
        mImageLoader=new PicassoImageLoader(this);
        getBook();
        addListener();
        queryDataFromBmob();
    }

    private void addListener() {
        btn_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBorrowed){
                    showToast("已借阅");
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(books.title);
                builder.setMessage(getString(R.string.book_confirm_borrow_tip));
                builder.setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        borrowBookToBmob(books);
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    public void getBook() {
        Intent intent = getIntent();
        books= (Books) intent.getSerializableExtra(Constant.BOOK_OBJ);
        if(books!=null){
            mImageLoader.fitImage(books.image,iv_book);
            tv_book_author.setText(books.author);
            tv_book_name.setText(books.title);
            tv_book_press.setText(books.publisher);
            tv_book_press_date.setText(books.pubDate);
            tv_book_summary.setText(books.summary);
            tv_isbn.setText("ISBN:"+books.ISBN);
        }
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
                        for(BorrowBook bbk:list){
                            if(bbk.bookId.equals(books.bookId)){
                                btn_borrow.setText("已借阅");
                                isBorrowed=true;
                                return;
                            }
                        }
                    } else {
                        showToast(e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        }
    }

    private void borrowBookToBmob(Books book) {

        MyLoadingDialog.showLoading(context);

        String userId = BmobUser.getCurrentUser(MyUser.class).getUsername();
        BorrowBook borrowBook = new BorrowBook();
        borrowBook.userId = userId;
        borrowBook.bookId = book.bookId;

        borrowBook.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                MyLoadingDialog.dismissLoading();

                if (e == null) {
                    if (!TextUtils.isEmpty(s)) {
                        showToast(s + "借阅成功！");
                        btn_borrow.setText("已借阅");
                        isBorrowed=true;
                    }
                } else {
                    if(e.getErrorCode()==401){
                        showToast("您已经借过了");
                    }else{
                        showToast(e.getMessage() + "   ,   " + e.getErrorCode());
                    }
                }


            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
