package com.lll.library.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lll.library.R;
import com.lll.library.entity.Books;
import com.lll.library.util.DisplayUtils;
import com.lll.library.util.PicassoImageLoader;

/**
 * Created by LLL on 2016/10/12.
 */
public class BooksBorrowAdapter extends CommonAdapter {
    private Context context;
    private PicassoImageLoader imageLoader;

    public BooksBorrowAdapter(Context context) {
        this.context = context;
        imageLoader = new PicassoImageLoader(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.book_borrow_item, null);
        }

        final Books book = (Books) getItem(position);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_book);
        TextView name = (TextView) convertView.findViewById(R.id.tv_book_name);
        TextView author = (TextView) convertView.findViewById(R.id.tv_book_author);
        TextView press = (TextView) convertView.findViewById(R.id.tv_book_press);
        TextView pressDate = (TextView) convertView.findViewById(R.id.tv_book_press_date);

        imageLoader.displayImage(book.image, imageView, DisplayUtils.dp2px(context, 70), DisplayUtils.dp2px(context, 100));
        name.setText(book.title);
        author.setText(book.author);
        press.setText(book.publisher);
        pressDate.setText(book.pubDate);

        return convertView;
    }

}
