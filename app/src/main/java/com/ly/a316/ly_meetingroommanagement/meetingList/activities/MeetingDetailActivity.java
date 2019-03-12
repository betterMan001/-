package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.endActivity.activity.End_Activity;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.MeetingDetailModel;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.AttenderServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.MeetingDetailServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetting.activity.ContentDialogActivity;
import com.ly.a316.ly_meetingroommanagement.nim.helper.TeamCreateHelper;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeetingDetailActivity extends BaseActivity {
    @BindView(R.id.meeting_title_tv)
    TextView meetingTitleTv;
    @BindView(R.id.meeting_place_tv)
    TextView meetingPlaceTv;
    @BindView(R.id.meeting_time)
    TextView meetingTime;
    @BindView(R.id.meetin_room_no)
    TextView meetinRoomNo;
    @BindView(R.id.do_something)
    ImageView doSomething;
    @BindView(R.id.begin_meeting)
    TextView beginMeeting;
    @BindView(R.id.meeting_maker)
    TextView meetingMaker;
    private String mId;
    public static MeetingDetailModel model;
    TopRightMenu mToRightMenu;
    public static List<Attendee> attendeeList = new ArrayList<>();
    private boolean isSignInMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_meeting_detail);
        ButterKnife.bind(this);
        mId = getIntent().getStringExtra("mId");
        //获取该页面所需要的数据
        makeData();
    }

    private void makeData() {
        loadingDialog.show();
        new MeetingDetailServiceImp(this, "1").meetDetail(mId);
    }

    public static final void start(Context context, String mId, String duration) {
        Intent intent = new Intent();
        intent.putExtra("mId", mId);
        intent.putExtra("duration", duration);
        intent.setClass(context, MeetingDetailActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        //填充数据
        meetingTitleTv.setText(model.title);
        meetingPlaceTv.setText(model.address);
        meetingTime.setText(model.begin);
        meetingMaker.setText(model.getSender());
        String showPeopleNum = "";
        showPeopleNum += (model.sure + "/" + model.all + "已接受>");
        meetinRoomNo.setText(showPeopleNum);
        mToRightMenu = new TopRightMenu(MeetingDetailActivity.this);
        if ("2".equals(model.getState()))
            beginMeeting.setText("已结束");
        else if ("1".equals(model.getState()))
            beginMeeting.setText("正在进行中");
//        //不是发起人不能开始会议
//        if(MyApplication.getId().equals(model.senderId)==false){
//            beginMeeting.setEnabled(false);
//        }

    }

    @OnClick({R.id.back_ll, R.id.do_something, R.id.meeting_people_ll, R.id.signature_ll, R.id.begin_meeting, R.id.meeting_content_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.do_something:
                showMenu();
                break;
            case R.id.meeting_people_ll:
                AttendeeActivity.start(MeetingDetailActivity.this, mId);
                break;
            case R.id.signature_ll:
                isSignInMode = true;
                new AttenderServiceImp(MeetingDetailActivity.this).attendersForDetailActivity(mId);
                break;
            //开始会议
            case R.id.begin_meeting:
                String start_time = model.begin.substring(11, 13) + "," + model.begin.substring(14, 16);
                String end_time = generate(getIntent().getStringExtra("duration"));

                /* Intent intentet = new Intent(this, Ceshi.class);*/
                Intent intentet = new Intent(this, End_Activity.class);
                intentet.putExtra("end_time", end_time);
                intentet.putExtra("start_time", start_time);
                startActivity(intentet);
                break;
            //会议内容
            case R.id.meeting_content_ll:
                ContentDialogActivity.start(MeetingDetailActivity.this, "2");
        }
    }

    public void startForSignIn() {
        SignInActivity.start(MeetingDetailActivity.this, mId);
    }

    public void showMenu() {
        final List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("一键拉讨论组"));
        mToRightMenu
                .setHeight(150)     //默认高度480
                .setWidth(350)      //默认宽度wrap_content
                .showIcon(false)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //动画样式 默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position) {
                            case 0:
                                break;
                            //一键拉讨论组
                            case 1:
                                new AttenderServiceImp(MeetingDetailActivity.this).attendersForDetailActivity(mId);
                                break;
                        }
                    }
                }).showAsDropDown(doSomething, -225, 0);

    }

    public void meetingDetailCallBack(final MeetingDetailModel model) {
        this.model = model;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                initView();
            }
        });
    }

    public void attenderListCallBack(final List<Attendee> list) {
        attendeeList = list;
        if (isSignInMode == false) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MeetingDetailActivity.this.loadingDialog.dismiss();
                    List<String> list = new ArrayList<String>();
                    for (Attendee temp : attendeeList) {
                        list.add(temp.getId());
                    }
                    TeamCreateHelper.createNormalTeam(MeetingDetailActivity.this, list, false, null);
                }
            });
        } else {
            //重置该值
            isSignInMode = false;
            final Context context = MeetingDetailActivity.this;
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SignInActivity.start(context, mId);
                }
            });
        }

    }

    public String generate(String time) {
        //开始时间
        int start_hour = new Integer(model.begin.substring(11, 13));
        int start_mintue = new Integer(model.begin.substring(14, 16));
        int temp = new Integer(time);
        int hours = temp / 60;
        int minutes = temp % 60;
        //把时间加起来生成结束时间
        minutes += start_mintue;
        hours = (hours + start_hour + (minutes / 60));
        minutes %= 60;
        StringBuilder builder = new StringBuilder();
        // builder.append("持续时间:");
        builder.append(new Integer(hours).toString());
        builder.append(",");
        builder.append(new Integer(minutes).toString());
        return builder.toString();

    }
}
