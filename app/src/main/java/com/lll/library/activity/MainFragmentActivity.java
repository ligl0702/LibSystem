package com.lll.library.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.lll.library.R;
import com.lll.library.fragment.AddFragment;
import com.lll.library.fragment.BorrowFragment;
import com.lll.library.fragment.HomeFragment;
import com.lll.library.fragment.MineFragment;
import com.lll.library.util.Constant;
import com.lll.library.util.SpUtil;
import com.lll.library.view.MainFragmentTabHost;
import com.lll.library.view.SearchTitleBar;
import com.lll.library.view.TabManager;


/**
 * Created by guoliangli on 2016/8/3.
 */
public class MainFragmentActivity extends FragmentActivity {
    private MainFragmentTabHost mTabHost;

    /**
     * 首页
     */
    public static final String TAB_HOME = "home";

    /**
     * 增加数据
     */
    public static final String TAB_ADD = "add";

    /**
     * Borrow 借阅
     */
    public static final String TAB_BORROW = "borrow";

    /**
     * 我的
     */
    public static final String TAB_MINE = "mine";


    private SearchTitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragemet);

        initViews();
    }

    private void initViews() {
        titleBar = (SearchTitleBar) findViewById(R.id.title_bar);
        titleBar.setText("图书浏览");
        titleBar.setRightImageView(R.drawable.icon_search, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainFragmentActivity.this, SearchConditionActivity.class);
                startActivity(intent);
            }
        });
        titleBar.setBackViewVisibility(View.GONE);

        mTabHost = (MainFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View menuHome = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuHome.findViewById(R.id.text)).setText(R.string.home_home);
        ((ImageView) menuHome.findViewById(R.id.img)).setImageResource(R.drawable.tab_home);

        View menuAdd = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuAdd.findViewById(R.id.text)).setText(R.string.home_add);
        ((ImageView) menuAdd.findViewById(R.id.img)).setImageResource(R.drawable.tab_ask);

        View menuBorrow = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuBorrow.findViewById(R.id.text)).setText(R.string.home_borrow);
        ((ImageView) menuBorrow.findViewById(R.id.img)).setImageResource(R.drawable.tab_test);

        View menuMine = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuMine.findViewById(R.id.text)).setText(R.string.home_mine);
        ((ImageView) menuMine.findViewById(R.id.img)).setImageResource(R.drawable.tab_mine);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME).setIndicator(menuHome), HomeFragment.class, null);
        if (TextUtils.equals(Constant.ROLE_ADMIN, SpUtil.getString(this, Constant.USER_ROLE, "1"))) {//管理员
            mTabHost.addTab(mTabHost.newTabSpec(TAB_ADD).setIndicator(menuAdd), AddFragment.class, null);
        } else {
            mTabHost.addTab(mTabHost.newTabSpec(TAB_BORROW).setIndicator(menuBorrow), BorrowFragment.class, null);
        }

        mTabHost.addTab(mTabHost.newTabSpec(TAB_MINE).setIndicator(menuMine), MineFragment.class, null);

        //去掉分隔的竖线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCommitEnabled(true);
        mTabHost.initFragments();

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                switch (tabId) {
                    case TAB_HOME:
                        titleBar.setRightImageViewGone(true);
                        titleBar.setText("浏览图书");
                        break;

                    case TAB_ADD:
                        titleBar.setRightImageViewGone(false);
                        titleBar.setText("录入图书");

                        break;

                    case TAB_BORROW:
                        titleBar.setRightImageViewGone(false);
                        titleBar.setText("借阅图书");
                        break;

                    case TAB_MINE:
                        titleBar.setRightImageViewGone(false);
                        titleBar.setText("我的");
                        break;

                }
                TabManager.getInstance().setCurrentTab(tabId);
            }
        });

        if (mTabHost.getTabWidget() != null) {
            if (mTabHost.getTabWidget().getChildAt(0) != null) {
                mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* // 首页再次点击回到顶端
                        if (TAB_HOME.equals(mTabHost.getCurrentTabTag()))
                        {
                            HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(
                                    TAB_HOME);
                            if (fragment != null)
                            {
                                fragment.scrollToTop();
                            }
                        }
*/
                        mTabHost.setCurrentTab(0);
                    }
                });
            }

            if (mTabHost.getTabWidget().getChildAt(2) != null) {
                mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mTabHost.setCurrentTab(2);
                    }
                });
            }

        }
        TabManager.getInstance().setCurrentTab(mTabHost.getCurrentTabTag());
    }

}
