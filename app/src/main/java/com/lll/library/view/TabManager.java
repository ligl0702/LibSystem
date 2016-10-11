package com.lll.library.view;

import android.text.TextUtils;

/**
 * 用于管理当前选中的是哪个tab，dac日志新增Tab字段
 * 
 * @author vivili
 * @version [版本号, 2016年4月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TabManager
{
    private static TabManager mInstance = null;

    private String mCurrentTab = TAB_HOME;

    /**
     * 首页
     */
    public static final String TAB_HOME = "home";



    /**
     * 发现
     */
    public static final String TAB_DISCOVER = "discover";

    /**
     * 我的
     */
    public static final String TAB_MINE = "mine";

    private TabManager()
    {

    }

    public static TabManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (TabManager.class)
            {
                if (mInstance == null)
                {
                    mInstance = new TabManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 保存当前tab
     * 
     * @param tabName
     * @see [类、类#方法、类#成员]
     */
    public void setCurrentTab(String tabName)
    {
        mCurrentTab = tabName;
    }

    /**
     * 返回当前tab，使用dac日志里的tab名称
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String getCurrentTab()
    {
        return getDacTab();
    }

    /**
     * 将当前tab名称转换为dac日志里使用的tab名称
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String getDacTab()
    {
        if (TextUtils.isEmpty(mCurrentTab))
        {
            mCurrentTab = TAB_HOME;
        }
        if ("find".equals(mCurrentTab))
        {
            mCurrentTab = TAB_DISCOVER;
        }
        else if ("user".equals(mCurrentTab))
        {
            mCurrentTab = TAB_MINE;
        }
        return mCurrentTab;
    }
}
