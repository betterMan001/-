package com.ly.a316.ly_meetingroommanagement.meetting.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailMeetingActivity extends BaseActivity {

    @BindView(R.id.customize_bar_rl)
    RelativeLayout customizeBarRl;
    @BindView(R.id.begin_time)
    TextView beginTime;
    @BindView(R.id.end_time_tv)
    TextView endTimeTv;
    boolean flag = false;
    //日程内容对话框的编辑框
    EditText contextET;
    //存放被选中的职工的Map
    public static Map<String, LevelOne> selectedEmployees = new HashMap();
    public static Map<String, LevelOne> recordEmployees = new HashMap();
    public boolean isFinishMeetingPeople = false;
    public boolean isFinishRecordMeetingPeople = false;
    @BindView(R.id.meeting_maker)
    TextView meetingMaker;
    @BindView(R.id.meeting_people_tv)
    TextView meetingPeopleTv;
    @BindView(R.id.meeting_record_people_tv)
    TextView meetingRecordPeopleTv;
    //预定会议相关数据源
    String beginTime_s;
    String endTime_s;
    int peopleNum_i;
    String deviceLists;
    String place;
    String meetingRoomNO;
    @BindView(R.id.peopleNum)
    TextView peopleNum;
    @BindView(R.id.meeting_theme)
    EditText meetingTheme;
    @BindView(R.id.meeting_place)
    TextView meetingPlace;
    @BindView(R.id.meetin_room_no)
    TextView meetinRoomNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_order_detail_meeting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        //设置会议发起人名称，为该账号
        meetingMaker.setText(MyApplication.getUserName());
        //设置会议开始时间
        beginTime_s = intent.getStringExtra("beginTime");
        //设置会议结束时间
        endTime_s = intent.getStringExtra("endTime");
        //人数
        peopleNum_i = intent.getIntExtra("peopleNum", 0);
        //设备列表
        deviceLists = intent.getStringExtra("deviceLists");
        //地点
        place = intent.getStringExtra("place");
        //会议室号
        meetingRoomNO = intent.getStringExtra("meetingRoomNO");
        //初始化各个视图
        beginTime.setText(beginTime_s);
        if(endTime_s==null||endTime_s.equals(""))
            endTimeTv.setText("时间点选择");
        else
            endTimeTv.setText(endTime_s);
        this.peopleNum.setText(new Integer(peopleNum_i).toString());
        meetingPlace.setText(place);
        this.meetinRoomNo.setText(this.meetingRoomNO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    public static final void start(Context context, String beginTime, String endTime, int peopleNum, String deviceLists, String place, String meetingRoomNO) {

        Intent intent = new Intent();
        intent.putExtra("beginTime", beginTime);
        intent.putExtra("endTime", endTime);
        intent.putExtra("peopleNum", peopleNum);
        intent.putExtra("deviceLists", deviceLists);
        intent.putExtra("place", place);
        intent.putExtra("meetingRoomNO", meetingRoomNO);
        intent.setClass(context, OrderDetailMeetingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (this.isFinishMeetingPeople == true) {
            this.meetingPeopleTv.setText("已选择");
            this.meetingPeopleTv.setBackgroundColor(getResources().getColor(R.color.classical_blue));
        }
        if (this.isFinishRecordMeetingPeople == true) {
            this.meetingRecordPeopleTv.setText("");
            meetingRecordPeopleTv.setBackground(getResources().getDrawable(R.drawable.bookstall001));
        }
    }

    private void showTimeSelect() {
        TimePickerView pvTime = new TimePickerView.Builder(OrderDetailMeetingActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date);
                if (flag == false) {
                    beginTime.setText(time);
                } else {
                    endTimeTv.setText(time);
                }

            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
//                        .setTitleText("请选择时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                        .setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                        .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                        .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间
//                        .setRangDate(startDate,endDate)//起始终止年月日设定
                // .setLabel("年","月","日","时","分","秒")

                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(Calendar.getInstance());
        pvTime.show();
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    @OnClick({R.id.begin_time_ll, R.id.end_time_ll, R.id.content_tv, R.id.meeting_person_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //开始时间
            case R.id.begin_time_ll:
                flag = false;
                showTimeSelect();
                break;
            //结束时间
            case R.id.end_time_ll:
                flag = true;
                showTimeSelect();
                break;
            case R.id.content_tv:
                showContentDialog();
                break;
            //跳转到选择界面
            case R.id.meeting_person_ll:
                InviteActivity.start(OrderDetailMeetingActivity.this);
        }
    }

    private void showContentDialog() {
        ContentDialogActivity.start(OrderDetailMeetingActivity.this);
//        AlertDialog.Builder customizeDialog= new AlertDialog.Builder(OrderDetailMeetingActivity.this);
//        final View dialogView= LayoutInflater
//                                .from(OrderDetailMeetingActivity.this)
//                                 .inflate(R.layout.customize_dialog,null);
//         contextET=dialogView.findViewById(R.id.content_tv);
//         // contextET.setMinLines(5);
//         customizeDialog.setTitle("");
//         customizeDialog.setView(dialogView);
//         //customizeDialog.setPositiveButton()
//        customizeDialog.show();
    }
}
