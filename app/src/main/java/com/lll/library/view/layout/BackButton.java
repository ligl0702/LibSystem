package com.lll.library.view.layout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.lll.library.R;


public class BackButton extends ImageView
{
    public BackButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        init();
    }

    private void init()
    {
        setImageResource(R.drawable.back_button);
        setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((Activity) getContext()).onBackPressed();
            }
        });
    }
}
