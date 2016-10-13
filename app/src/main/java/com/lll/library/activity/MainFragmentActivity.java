package com.lll.library.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.lll.library.R;
import com.lll.library.fragment.AddFragment;
import com.lll.library.fragment.DeleteFragment;
import com.lll.library.fragment.QueryFragment;
import com.lll.library.fragment.UpdateFragment;
import com.lll.library.view.MainFragmentTabHost;
import com.lll.library.view.TabManager;
import com.lll.library.view.layout.TitleBar;


/**
 * Created by guoliangli on 2016/8/3.
 */
public class MainFragmentActivity extends FragmentActivity {
    private static final String TAG = "MainFragmentActivity";
    private MainFragmentTabHost mTabHost;
    /**
     * 增加数据
     */
    public static final String TAB_ADD = "add";

    /**
     * 删除数据
     */
    public static final String TAB_DELETE = "delete";

    /**
     * 修改数据
     */
    public static final String TAB_UPDATE = "update";

    /**
     * 查询数据
     */
    public static final String TAB_QUERY = "query";

    private TitleBar titleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragemet);
        initViews();

    }

    private void initViews() {
        titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setText("图书管理系统");
        titleBar.setBackViewVisibility(View.GONE);
        mTabHost = (MainFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View menuAdd = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuAdd.findViewById(R.id.text)).setText(R.string.home_add);
        ((ImageView) menuAdd.findViewById(R.id.img)).setImageResource(R.drawable.tab_home);

        View menuDelete = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuDelete.findViewById(R.id.text)).setText(R.string.home_delete);
        ((ImageView) menuDelete.findViewById(R.id.img)).setImageResource(R.drawable.tab_ask);

        View menuUpdate = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuUpdate.findViewById(R.id.text)).setText(R.string.home_update);
        ((ImageView) menuUpdate.findViewById(R.id.img)).setImageResource(R.drawable.tab_test);

        View menuQuery = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuQuery.findViewById(R.id.text)).setText(R.string.home_query);
        ((ImageView) menuQuery.findViewById(R.id.img)).setImageResource(R.drawable.tab_mine);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_QUERY).setIndicator(menuQuery), QueryFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_ADD).setIndicator(menuAdd), AddFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_UPDATE).setIndicator(menuUpdate), UpdateFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_DELETE).setIndicator(menuDelete), DeleteFragment.class, null);

        //去掉分隔的竖线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCommitEnabled(true);
        mTabHost.initFragments();

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case TAB_ADD:
                        titleBar.setText("增加图书");
                        break;
                    case TAB_DELETE:
                        titleBar.setText("删除");
                        break;
                    case TAB_UPDATE:
                        titleBar.setText("修改");
                        break;
                    case TAB_QUERY:
                        titleBar.setText("图书管理系统");
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
