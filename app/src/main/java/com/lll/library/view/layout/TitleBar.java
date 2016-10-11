package com.lll.library.view.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lll.library.R;
import com.lll.library.utils.DisplayUtil;


/**
 * 标题栏封装
 * 
 * @author wentaoli
 * @version [版本号, 2016年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TitleBar extends RelativeLayout
{

    /**
     * 解析text属性
     */
    public static int[] TEXT_ATTR = new int[] {android.R.attr.text};

    /**
     * 标题textView
     */
    private TextView mTitleTv = null;

    /**
     * 右侧按钮, 默认不显示
     */
    private ImageView mRightIv = null;

    public TitleBar(Context context)
    {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView();
        try
        {
            TypedArray ta = context.obtainStyledAttributes(attrs, TEXT_ATTR);
            setText(ta.getText(0));
            ta.recycle();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 初始化界面元素
     * 
     * @see [类、类#方法、类#成员]
     */
    private void initView()
    {
        if (isInEditMode())
        {
            return;
        }
        View root = LayoutInflater.from(getContext()).inflate(R.layout.title_bar_layout, this, false);
        mTitleTv = (TextView) root.findViewById(R.id.title_bar_title);
        mRightIv = (ImageView) root.findViewById(R.id.title_bar_right_iv);
        mRightIv.setVisibility(View.GONE);
        mTitleTv.setSelected(true);

        addView(root, LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(getContext(), 44));
    }

    /**
     * 设置标题
     * 
     * @param title
     * @see [类、类#方法、类#成员]
     */
    public void setText(CharSequence title)
    {
        if (mTitleTv != null)
        {
            mTitleTv.setText(title);
        }
    }

    /**
     * 设置标题
     * 
     * @param resId
     * @see [类、类#方法、类#成员]
     */
    public void setText(int resId)
    {
        if (mTitleTv != null)
        {
            if (resId == 0)
            {
                mTitleTv.setText("");
                return;
            }
            mTitleTv.setText(resId);
        }
    }

    /**
     * 设置返回按钮的可见性
     * 
     * @param visibility
     * @see [类、类#方法、类#成员]
     */
    public void setBackViewVisibility(int visibility)
    {
        View back = findViewById(R.id.title_bar_back);
        if (back != null)
        {
            back.setVisibility(visibility);
        }
    }
    public void setHomeLogoVisibility(int visibility)
    {
        View logo = findViewById(R.id.title_bar_logo);
        if (logo != null)
        {
            logo.setVisibility(visibility);
        }
    }

    /**
     * 设置右侧搜索按钮的可见性
     * 
     * @param visibility
     * @see [类、类#方法、类#成员]
     */
    public void setRightImageView(int icon, OnClickListener l)
    {
        if (mRightIv == null)
        {
            return;
        }
        mRightIv.setVisibility(View.VISIBLE);
        mRightIv.setImageResource(icon);
        mRightIv.setOnClickListener(l);
    }


}
