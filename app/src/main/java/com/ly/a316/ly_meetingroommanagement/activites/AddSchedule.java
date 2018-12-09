package com.ly.a316.ly_meetingroommanagement.activites;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.classes.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddSchedule extends BaseActivity {

    @BindView(R.id.addSchedule_back)
    ImageView addScheduleBack;
    @BindView(R.id.ac_sche_content)
    EditText acScheContent;
    @BindView(R.id.ac_sche_startTex)
    TextView acScheStartTex;
    @BindView(R.id.ac_sche_endTex)
    TextView acScheEndTex;
    @BindView(R.id.ac_sche_remind)
    Button acScheRemind;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.add_schedule)
    Button add_schedule;
    @BindView(R.id.spinner_lei)
    Spinner spinner_lei;
    String body;//复选框选中的值
    String leibie;//日程属性
    String starttime,endtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        ButterKnife.bind(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                body = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_lei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leibie = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick({R.id.addSchedule_back, R.id.ac_sche_startTex, R.id.ac_sche_endTex, R.id.ac_sche_remind, R.id.add_schedule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addSchedule_back:
                finish();
                break;
            case R.id.ac_sche_startTex:
                chooseTime(1);
                break;
            case R.id.ac_sche_endTex:
                chooseTime(2);
                break;
            case R.id.ac_sche_remind:
                Toast.makeText(AddSchedule.this, body, Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_schedule:
                //发布日程信息
                Schedule schedule = new Schedule(starttime, acScheContent.getText().toString(),starttime,endtime,leibie);
                Schedule.list.add(schedule);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addScheduleBack.performClick();
                    }
                },500);
                break;

        }
    }

    void chooseTime(final int pan) {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (pan == 1) {
                    starttime = getTime(date);
                    acScheStartTex.setText(starttime);
                } else if (pan == 2) {
                    endtime = getTime(date);
                    acScheEndTex.setText(endtime);
                }
            }
        }).setType(TimePickerView.Type.MONTH_DAY_HOUR_MIN)//默认全部显示
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
                //  .setLabel("年","月","日","时","分","秒")
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(java.util.Calendar.getInstance());
        pvTime.show();
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日aHH:mm");
        return format.format(date);
    }
}
