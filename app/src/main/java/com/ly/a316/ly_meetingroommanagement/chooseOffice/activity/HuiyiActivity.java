package com.ly.a316.ly_meetingroommanagement.chooseOffice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.customview.SlidingTabLayout;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.daoImp.DeviceDaoImp;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.fragment.LiHuiFragment;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.fragment.TimeDianFragment;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.fragment.TimeDuanFragment;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.utils.ViewFindUtils;
import com.ly.a316.ly_meetingroommanagement.customView.LoadingImageView;
import com.ly.a316.ly_meetingroommanagement.meetting.activity.OrderDetailMeetingActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HuiyiActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"   时间点预定   ", "   时间段预定   ", "   例会的预定   "};
    private MyPagerAdapter mAdapter;
    @BindView(R.id.addShike_toolbar)
    Toolbar addShike_toolbar;

    @BindView(R.id.faqiyuding_btn)
    TextView faqiyuding_btn;

    DeviceDaoImp deviceDaoImp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huiyi);
        ButterKnife.bind(this);
        deviceDaoImp = new DeviceDaoImp(this);
        addShike_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        faqiyuding_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//发起会议
                deviceDaoImp.subbmitHuiyi();
            }
        });
        initview();
    }

    Fragment fragment;

    void initview() {
        mFragments.add(new TimeDianFragment());
        mFragments.add(new TimeDuanFragment());
        mFragments.add(new LiHuiFragment());
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        SlidingTabLayout tabLayout_9 = ViewFindUtils.find(decorView, R.id.tl_9);
        tabLayout_9.setViewPager(vp);

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
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            fragment = mFragments.get(position);
            return fragment;
        }
    }
    List<HuiyiInformation> list_meet = new ArrayList<>();
    public void success( List<HuiyiInformation> list_meett){
        list_meet.clear();
        list_meet.addAll(list_meett);
        Intent intent =new Intent(this,ZhanshiHuiActivity.class);
        intent.putExtra("list_meet", (Serializable) list_meet);
        startActivity(intent);
    }
}
