package com.ly.a316.ly_meetingroommanagement.activites;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailMeetingActivity extends BaseActivity {

    @BindView(R.id.customize_bar_rl)
    RelativeLayout customizeBarRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.white).init();
        setContentView(R.layout.activity_order_detail_meeting);
        ButterKnife.bind(this);
        initView();
    }
private void initView(){
        customizeBarRl.setBackgroundColor(getResources().getColor(R.color.white));
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
