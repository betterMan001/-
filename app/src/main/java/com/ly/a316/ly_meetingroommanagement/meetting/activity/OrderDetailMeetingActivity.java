package com.ly.a316.ly_meetingroommanagement.meetting.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
     boolean flag=false;
     //日程内容对话框的编辑框
    EditText contextET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_order_detail_meeting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }


    private void showTimeSelect() {
        TimePickerView pvTime = new TimePickerView.Builder(OrderDetailMeetingActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date);
                if(flag==false){
                    beginTime.setText(time);
                }
                else{
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

    @OnClick({R.id.begin_time_ll, R.id.end_time_ll,R.id.content_tv,R.id.meeting_person_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //开始时间
            case R.id.begin_time_ll:
                flag=false;
                showTimeSelect();
                break;
                //结束时间
            case R.id.end_time_ll:
                flag=true;
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
    private void showContentDialog(){
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
