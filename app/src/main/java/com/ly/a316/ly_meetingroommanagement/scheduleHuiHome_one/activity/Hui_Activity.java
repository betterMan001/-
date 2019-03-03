package com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_one.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_one.fragment.Dian_fragemnt;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_one.fragment.Duan_fragment;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_one.fragment.Lihui_fragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Hui_Activity extends AppCompatActivity {

    @BindView(R.id.hui_two_title)
    TextView huiTwoTitle;
    @BindView(R.id.hui_two_fabtn)
    TextView huiTwoFabtn;
    @BindView(R.id.hui_two_toolbar)
    Toolbar huiTwoToolbar;
    @BindView(R.id.hui_two_vp)
    ViewPager huiTwoVp;
    private final String[] mTitles = {"时间点预定", "时间段预定", "例会的预定"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hui_);
        ButterKnife.bind(this);
        huiTwoToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initview();

    }
    void initview() {
        mFragments.add(new Dian_fragemnt());
        mFragments.add(new Duan_fragment());
        mFragments.add(new Lihui_fragment());
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        huiTwoVp.setAdapter(mAdapter);
        huiTwoVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                huiTwoTitle.setText(mTitles[i]);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public Fragment getItem(int position) {

            fragment = mFragments.get(position);
            return fragment;
        }
    }
    @OnClick(R.id.hui_two_fabtn)
    public void onViewClicked() {

    }
}
