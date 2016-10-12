package com.lll.library.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lll.library.R;
import com.lll.library.entity.Books;

/**
 * Created by LLL on 2016/10/12.
 */
public class BooksAdapter extends CommonAdapter {
    private Context context;

    public BooksAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.query_book_item, null);
        }

        Books book = (Books) getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.tv_book_name);
        TextView author = (TextView) convertView.findViewById(R.id.tv_book_author);
        TextView press = (TextView) convertView.findViewById(R.id.tv_book_press);
        TextView pressDate = (TextView) convertView.findViewById(R.id.tv_book_press_date);

        name.setText(book.bookName);
        author.setText(book.author);
        press.setText(book.press);
        pressDate.setText(book.publicationDate);

        return convertView;
    }
}
