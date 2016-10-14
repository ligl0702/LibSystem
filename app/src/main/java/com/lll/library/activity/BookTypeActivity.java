package com.lll.library.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lll.library.R;
import com.lll.library.fragment.UpdateFragment;
import com.lll.library.util.DisplayUtils;
import com.lll.library.view.layout.TitleBar;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;


/**
 * 健康贴士
 */
public class BookTypeActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = "BookTypeActivity";
    private IndicatorViewPager indicatorViewPager;
    private TitleBar title_bar;
    private LinearLayout mLlBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_type);

        ViewPager viewPager = (ViewPager) findViewById(R.id.moretab_viewPager);
        ScrollIndicatorView scrollIndicatorView = (ScrollIndicatorView) findViewById(R.id.moretab_indicator);

        float unSelectSize = 12;
        float selectSize = unSelectSize * 1.3f;
        scrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(getResources().getColor(R.color.common_green), getResources().getColor(R.color.common_gray_font)).setSize(selectSize, unSelectSize));

       // scrollIndicatorView.setScrollBar(new ColorBar(this, getResources().getColor(R.color.common_green), 4));

        viewPager.setOffscreenPageLimit(2);
        indicatorViewPager = new IndicatorViewPager(scrollIndicatorView, viewPager);

        initTitle();
        initData();
    }

    private void initData() {
        MyAdapter adapter= new MyAdapter(getSupportFragmentManager());
        indicatorViewPager.setAdapter(adapter);


    }

    // 初始化头布局
    private void initTitle() {
        title_bar.setText("图书分类");
    }

    @Override
    public void onClick(View view) {

    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
         String[] typeID = {"Cupcake", "Donut", "Éclair", "Froyo", "Gingerbread", "Honeycomb", "Ice Cream Sandwich", "Jelly Bean", "KitKat", "Lolipop", "Marshmallow"};
        private String[] names = {"纸杯蛋糕", "甜甜圈", "闪电泡芙", "冻酸奶", "姜饼", "蜂巢", "冰激凌三明治", "果冻豆", "奇巧巧克力棒", "棒棒糖", "棉花糖"};

        private ArrayList<Fragment> list=new ArrayList<>();


//        public MyAdapter(FragmentManager fragmentManager,ArrayList<Fragment> list) {
//            super(fragmentManager);
//            this.list = list;
//        }

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            for(int i=0;i<names.length;i++){
                UpdateFragment fragment =new UpdateFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type_id",typeID[i]);
                fragment.setArguments(bundle);
                list.add(fragment);
            }
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(names[position]);
            int padding = DisplayUtils.dp2px(getApplicationContext(),10);
            textView.setPadding(padding, 0, padding, 0);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return list.get(position);
        }


        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_UNCHANGED;
        }




    }
}
