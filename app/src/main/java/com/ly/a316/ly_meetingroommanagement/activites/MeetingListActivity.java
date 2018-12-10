package com.ly.a316.ly_meetingroommanagement.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.Adapter.ListDropDownAdapter;
import com.ly.a316.ly_meetingroommanagement.Adapter.MeetingListAdapter;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.models.Meeting;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeetingListActivity extends AppCompatActivity {

    List<Meeting> list;
    @BindView(R.id.meeting_list_rv)
    RecyclerView meetingListRv;
    @BindView(R.id.act_meeting_list_menu)
    DropDownMenu actMeetingListMenu;
    //下拉标题
    private String headers[] = {"会议状态"};
    //下拉listView
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter meetingAdapter;
    private String meetings[] = {"不限", "结束的会议", "预订的会议", "正在进行中的会议", "拒绝的会议"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_list);
        ButterKnife.bind(this);
        //状态栏沉浸效果
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor("#00A7FF").init();
        //初始化视图
        initView();
    }

    private void initView() {
        //1.获取数据
        makeData();
        //2.设置RecycleView适配器
        initRecycleView();
        //3.设置下拉框
        initDropDownMenu();
    }
    private void initDropDownMenu(){
        //1.设置下拉listView
        final ListView meetingView = new ListView(this);
        meetingView.setDividerHeight(0);
        meetingAdapter = new ListDropDownAdapter(this, Arrays.asList(meetings));
        meetingView.setAdapter(meetingAdapter);
        popupViews.add(meetingView);
        //2.设置内容view
        //init context view
        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentView.setText("");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        //3.添加ListView监听事件
        meetingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                meetingAdapter.setCheckItem(position);
                actMeetingListMenu.setTabText(position == 0 ? headers[0] : meetings[position]);
                actMeetingListMenu.closeMenu();
            }
        });
        //3.绑定下拉菜单
        actMeetingListMenu.setDropDownMenu(Arrays.asList(headers), popupViews,contentView);
    }

    private void makeData() {
        list = new ArrayList<Meeting>();
        //模拟数据
        Meeting meeting;
        for (int i = 0; i < 6; i++) {
            meeting = new Meeting();
            meeting.setTitle("发展大会");
            meeting.setDate("时间：2018-10-16 05:55:14.0");
            meeting.setSponsor("我");
            meeting.setDidTime("刚刚");
            meeting.setMeetingStatus("正在进行中");
            meeting.setMessageNum("0条动态");
            meeting.setPartnerNum("1/4人确认参加");
            list.add(meeting);
        }
    }

    private void initRecycleView() {
        meetingListRv.setLayoutManager(new LinearLayoutManager(this));
        meetingListRv.setAdapter(new MeetingListAdapter(this, list));

    }

    @OnClick({R.id.act_meeting_list_back_ll, R.id.act_meeting_iv, R.id.meeting_list_rv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回上一级界面
            case R.id.act_meeting_list_back_ll:
                finish();
                break;
            //搜索框
            case R.id.act_meeting_iv:
                break;
            //进入下一层界面
            case R.id.meeting_list_rv:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
