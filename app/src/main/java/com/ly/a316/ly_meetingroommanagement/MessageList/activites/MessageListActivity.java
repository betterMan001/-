package com.ly.a316.ly_meetingroommanagement.MessageList.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MessageList.adapters.MeetingAdapter;
import com.ly.a316.ly_meetingroommanagement.MessageList.adapters.MyPagerAdapter;
import com.ly.a316.ly_meetingroommanagement.MessageList.adapters.SystemAdapter;
import com.ly.a316.ly_meetingroommanagement.MessageList.models.MessageModel;
import com.ly.a316.ly_meetingroommanagement.MessageList.services.imp.MessageServiceImp;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.activity.MeetingRecordPeopleActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageListActivity extends BaseActivity {
    //控件
    @BindView(R.id.message_tl)
    TabLayout messageTl;
    @BindView(R.id.message_vp)
    ViewPager messageVp;
    //View
    private View viewPager_meeting;
    private View viewPager_system;
    //list
    private List<String> titleList;//title标题列表
    private List<View> viewList;//ViewPage列表
    //RecycleVoew
    private RecyclerView vp_meeting_rv;
    private RecyclerView vp_system_rv;
    //page
    private int meeting_page = 0;
    private int system_page = 0;
    //消息推送的数据列表
    List<MessageModel> sysList=new ArrayList<>();
    List<MessageModel> meetingList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        makeData();
    }
    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MessageListActivity.class);
        context.startActivity(intent);
    }
    private void makeData() {
        //开启加载框
        loadingDialog.show();
        new MessageServiceImp(this).userJpush(MyApplication.getId());
    }

    private void initView() {
        //init View
        viewPager_meeting = getLayoutInflater().from(this).inflate(R.layout.viewpager_meeting, null);
        viewPager_system = getLayoutInflater().from(this).inflate(R.layout.viewpager_system, null);
        /*init recView*/
        //2.1会议相关消息
        vp_meeting_rv = viewPager_meeting.findViewById(R.id.vp_meeting_rv);
        vp_meeting_rv.setLayoutManager(new LinearLayoutManager(this));
        vp_meeting_rv.setAdapter(new MeetingAdapter(this, meetingList));
        //2.2系统消息
        vp_system_rv = viewPager_system.findViewById(R.id.vp_system_rv);
        vp_system_rv.setLayoutManager(new LinearLayoutManager(this));
        SystemAdapter systemAdapter = new SystemAdapter(this, sysList);
        vp_system_rv.setAdapter(systemAdapter);
        //init 列表
        titleList = new ArrayList<String>();
        viewList = new ArrayList<View>();
        titleList.add("会议通知");
        titleList.add("系统通知");
        viewList.add(viewPager_meeting);
        viewList.add(viewPager_system);
        //setting tab模式
        messageTl.setTabMode(TabLayout.MODE_FIXED);
        messageTl.addTab(messageTl.newTab().setText(titleList.get(0)));
        messageTl.addTab(messageTl.newTab().setText(titleList.get(1)));
        //viewPager适配
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(viewList, titleList);
        messageVp.setAdapter(myPagerAdapter);
        //tabLayout联动viewPage
        messageTl.setupWithViewPager(messageVp);
    }

    public void messageCallBack(final List<MessageModel> list) {
        meetingList = list;
        loadingDialog.dismiss();
        new MessageServiceImp(this).adminJpush();
    }

    public void sysMessageCallBack(final List<MessageModel> list) {
        sysList = list;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });
    }
    @OnClick(R.id.back_ll)
    public void onViewClicked() {
        finish();
    }
}
