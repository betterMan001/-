package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.AttendeeAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.ListDropDownAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.MeetingListAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.SignInPeopleAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {
    List<Attendee> list = new ArrayList<Attendee>();
    @BindView(R.id.act_sign_in_menu)
    DropDownMenu actSignInMenu;
    @BindView(R.id.sign_in_rv)
    RecyclerView signInRv;
    private ListDropDownAdapter meetingAdapter;
    //下拉标题
    private String headers[] = {"签到情况"};
    private String meetings[] = {"不限", "已签到", "未签到"};
    //下拉listView
    private List<View> popupViews = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        makeData();
    }
    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SignInActivity.class);
        context.startActivity(intent);
    }
    public void makeData() {
        for (int i = 0; i < 10; i++) {
            Attendee attendee = new Attendee();
            attendee.setName("小松韬，全身变！");
            attendee.setImage("http://zhazhatao.oss-cn-hangzhou.aliyuncs.com/e6fcbe91-920f-4f2b-8514-ef202f3c60ff.png?Expires=1861692137&OSSAccessKeyId=LTAIwIu7qTchB1TQ&Signature=V1t3Q57vAfC0iW9HqQbbHX%2FvHMU%3D");
            list.add(attendee);
        }

        initView();
    }

    private void initView() {
        //初始化RecycleView
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        signInRv.setLayoutManager(layoutManager);
        signInRv.setAdapter(new SignInPeopleAdapter(this, list));
        initDropDownMenu();
    }
    private void initDropDownMenu() {
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
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0);
        //3.添加ListView监听事件
        meetingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //1.选择要筛选的条件
                meetingAdapter.setCheckItem(position);
                //2.设置菜单标题
                /*这里有数据再操作*/
//                actSignInMenu.setTabText(position == 0 ? headers[0] : meetings[position]);
//                //3.设置指针数组
//                int length = meetingList.size();
//                int count = 0;
//                //如果选择不限则显示所有
//                if ("不限".equals(meetings[position])) {
//                    for (int i = 0; i < length; i++) {
//                        MeetingListAdapter.truePositon[i] = i;
//                    }
//                    count = length;
//                } else {
//                    //根据条件筛选，并刷新RecycleView
//                    for (int i = 0; i < length; i++) {
//                        if (meetings[position].equals(meetingList.get(i).getState())||"正在".equals(meetingList.get(i).getState())) {
//                            MeetingListAdapter.truePositon[count++] = i;
//                        }
//                    }
//                }
//
//                meetingListAdapter.setCount(count);
//                //刷新RecycleView视图实现
//
//                meetingListAdapter.notifyDataSetChanged();
                actSignInMenu.closeMenu();
            }
        });
        //3.绑定下拉菜单
        actSignInMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }
    @OnClick(R.id.back_ll)
    public void onViewClicked() {
        finish();
    }
}
