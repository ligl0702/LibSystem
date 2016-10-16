package com.lll.library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lll.library.R;
import com.lll.library.entity.BookType;
import com.lll.library.util.Constant;
import com.lll.library.util.DisplayUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;


/**
 * Created by guoliangli on 2016/9/9. 首页，主要是显示图片列表，查询的数据来源于豆瓣
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private IndicatorViewPager mIndicatorViewPager;
    private ViewPager mViewPager;
    private ScrollIndicatorView mScrollIndicatorView;

    private List<BookType> mBookTypeList = new ArrayList<>();

    private View contentView = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            initView(inflater);
        }
        if (contentView.getParent() != null) {
            ((ViewGroup) contentView.getParent()).removeView(contentView);
        }

        return contentView;
    }

    private void initView(LayoutInflater inflater) {
        contentView = inflater.inflate(R.layout.home_fragment_layout, null);

        mViewPager = (ViewPager) contentView.findViewById(R.id.moretab_viewPager);
        mScrollIndicatorView = (ScrollIndicatorView) contentView.findViewById(R.id.moretab_indicator);

        float unSelectSize = 12;
        float selectSize = unSelectSize * 1.3f;
        mScrollIndicatorView.setOnTransitionListener(new OnTransitionTextListener().setColor(getResources().getColor(R.color.common_green),
                getResources().getColor(R.color.common_gray_font)).setSize(selectSize, unSelectSize));

        mViewPager.setOffscreenPageLimit(2);
        mIndicatorViewPager = new IndicatorViewPager(mScrollIndicatorView, mViewPager);

        requestBookType();
    }

    private void requestBookType() {
        String bql = "select * from tb_book_type";
        BmobQuery<BookType> bookTypeBmobQuery = new BmobQuery<>();
        bookTypeBmobQuery.doSQLQuery(bql, new SQLQueryListener<BookType>() {
            @Override
            public void done(BmobQueryResult<BookType> bmobQueryResult, BmobException e) {
                if (e == null) {
                    List<BookType> list = bmobQueryResult.getResults();
                    if (list != null && list.size() > 0) {
                        BookType type0 = new BookType();
                        type0.typeId = "0";
                        type0.typeName = "推荐";
                        mBookTypeList.add(type0);
                        for (BookType type : list) {
                            mBookTypeList.add(type);
                        }

                        initData();
                    }
                } else {
                    showToast(e.getMessage());
                }
            }
        });
    }

    private void initData() {
        MyAdapter adapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mIndicatorViewPager.setAdapter(adapter);
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private ArrayList<Fragment> list = new ArrayList<>();

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            for (int i = 0; i < mBookTypeList.size(); i++) {
                BookListFragment fragment = new BookListFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.BOOK_TYPE, mBookTypeList.get(i));
                fragment.setArguments(bundle);
                list.add(fragment);
            }
        }

        @Override
        public int getCount() {
            return mBookTypeList.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(mBookTypeList.get(position).typeName);
            int padding = DisplayUtils.dp2px(getActivity(), 10);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tv_search:
//                Intent intent = new Intent(getActivity(), SearchConditionActivity.class);
//                startActivityForResult(intent, Constant.QUERY_REQUEST_CODE);
//                break;

            default:
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

}
