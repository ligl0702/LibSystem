package com.lll.library.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.lll.library.R;
import com.lll.library.fragment.CircleFragment;
import com.lll.library.view.MainFragmentTabHost;
import com.lll.library.view.TabManager;


/**
 * Created by guoliangli on 2016/8/3.
 */
public class MainFragmentActivity extends FragmentActivity {
    private static final String TAG ="MainFragmentActivity" ;
    private MainFragmentTabHost mTabHost;
    /**
     * 首页Tab
     */
    public static final String TAB_HOME = "home";

    /**
     * 问诊Tab
     */
    public static final String TAB_ASK = "ask";

    /**
     * 体检Tab
     */
    public static final String TAB_PHY = "phy";


    /**
     * 个人中心Tab
     */
    public static final String TAB_USERCENTER = "user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragemet);
        initViews();

    }

    private void initViews() {
        mTabHost = (MainFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        View menuHome = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuHome.findViewById(R.id.text)).setText(R.string.home_circle);
        ((ImageView) menuHome.findViewById(R.id.img)).setImageResource(R.drawable.tab_home);

        View menuCate = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuCate.findViewById(R.id.text)).setText(R.string.home_message);
        ((ImageView) menuCate.findViewById(R.id.img)).setImageResource(R.drawable.tab_ask);

        View menuFind = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuFind.findViewById(R.id.text)).setText(R.string.home_find);
        ((ImageView) menuFind.findViewById(R.id.img)).setImageResource(R.drawable.tab_test);

        View menuUser = getLayoutInflater().inflate(R.layout.main_tab_item, null);
        ((TextView) menuUser.findViewById(R.id.text)).setText(R.string.home_user_center);
        ((ImageView) menuUser.findViewById(R.id.img)).setImageResource(R.drawable.tab_mine);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME).setIndicator(menuHome), CircleFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_ASK).setIndicator(menuCate), CircleFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_PHY).setIndicator(menuFind), CircleFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_USERCENTER).setIndicator(menuUser), CircleFragment.class, null);
        //去掉分隔的竖线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCommitEnabled(true);
        mTabHost.initFragments();



        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {

            @Override
            public void onTabChanged(String tabId)
            {
                TabManager.getInstance().setCurrentTab(tabId);


            }

        });

        if (mTabHost.getTabWidget() != null)
        {
            if (mTabHost.getTabWidget().getChildAt(0) != null)
            {
                mTabHost.getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
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

            if (mTabHost.getTabWidget().getChildAt(2) != null)
            {
                mTabHost.getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {

                        mTabHost.setCurrentTab(2);
                    }
                });
            }

        }
        TabManager.getInstance().setCurrentTab(mTabHost.getCurrentTabTag());
    }


}
