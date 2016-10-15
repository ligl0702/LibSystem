package com.lll.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lll.library.R;
import com.squareup.picasso.Picasso;

/**
 * Created by guoliangli on 2016/6/21.
 */
public class PicassoImageLoader {

    private Context mContext;

    public PicassoImageLoader(Context mContext) {
        this.mContext = mContext;
    }

    public void displayImage(String path, ImageView imageView, int width, int height) {
        Picasso.with(mContext)
                .load(path)
                .placeholder(R.drawable.icon_book)
                .error(R.drawable.icon_book_error)
                .config(Bitmap.Config.RGB_565)
                .resize(width, height)
                .centerCrop()
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(imageView);
    }

}
