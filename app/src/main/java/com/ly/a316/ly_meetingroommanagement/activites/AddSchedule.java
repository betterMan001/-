package com.ly.a316.ly_meetingroommanagement.activites;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.DatePicker;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;
import com.ly.a316.ly_meetingroommanagement.internet.URL;
import com.ly.a316.ly_meetingroommanagement.utils.CalanderUtils;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class AddSchedule extends BaseActivity {
    @BindView(R.id.sc_title)
    TextView sc_titleview;
    @BindView(R.id.sc_beizhu)
    EditText sc_beizhuview;
    @BindView(R.id.sc_location)
    TextView sc_locationview;
    @BindView(R.id.addSchedule_toolbar)
    Toolbar addSchedule_toolbar;
    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;
    @BindView(R.id.add_startTime)
    TextView add_startTime;
    @BindView(R.id.add_endTime)
    TextView add_endTime;
    @BindView(R.id.add_choosetime_start)
    View layput_choosetime;
    @BindView(R.id.add_choosetime_end)
    View add_choosetime_end;
    java.util.Calendar calendar_all;//获取今天的时间
    String selectDatae, selectTime;
    @BindView(R.id.dp_test)
    DatePicker dp_test;//时间选择的控件
    DatePicker dpp_test;
    @BindView(R.id.add_layout_chongfu)
    LinearLayout add_layout_chongfu;
    @BindView(R.id.add_layout_tixing)
    LinearLayout add_layout_tixing;
    @BindView(R.id.tp_test)
    TimePicker tp_test;
    @BindView(R.id.add_chongfu)
    TextView add_chongfu;
    @BindView(R.id.add_tixing)
    TextView add_tixing;
    TimePicker tpp_test;
    String sc_title, sc_location, sc_allday, sc_startTime, sc_endTime, sc_repeat, sc_tiixng, sc_remarks;
    String chongfu[] = {"永不", "每天", "每周", "每月", "每年", "自定义"};
    String chongfudui[] = {"0", "DAILY", "WEEKLY", "MONTHLY", "YEARLY"};
    String alarmTime[] = {"无", "日程发生时", "5分钟前", "15分钟前", "30分钟前", "1小时前", "2小时前", "1天前", "2天前"};
    int alarmTimeduiying[] = {-1, 0, 5, 15, 30, 60, 120, 1440, 2880};
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private int currentHour, currentMinute, currentDay, selectHour, selectMinute, selectDay;
    TextView add_sure;
    //重复名字
    String getSc_remarks = "0";
    int alarmtime;//提醒的时间
    int s_yearr, s_monthr, s_dayr, s_day_of_weekr, s_selectHour, s_selectMiniute;
    int e_yearr, e_monthr, e_dayr, e_day_of_wekkr, e_selectHour, e_selectMiniute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        ButterKnife.bind(this);
        calendar_all = java.util.Calendar.getInstance();
        init();
        addSchedule_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toggleButton.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) new MyOnCheckedChangeListener());
        dp_test.setOnChangeListener(new DatePicker.OnChangeListener() {
            @Override
            public void onChange(int year, int month, int day, int day_of_week) {
                s_yearr = year;
                s_monthr = month;
                s_dayr = day;
                s_day_of_weekr = day_of_week;
                selectDay = day;
                selectDatae = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
                add_startTime.setText(selectDatae + " ");

            }
        });
        dpp_test = findViewById(R.id.add_choosetime_end).findViewById(R.id.dp_test);
        dpp_test.setOnChangeListener(new DatePicker.OnChangeListener() {
            @Override
            public void onChange(int year, int month, int day, int day_of_week) {
                e_yearr = year;
                e_monthr = month;
                e_dayr = day;
                e_day_of_wekkr = day_of_week;
                selectDay = day;
                selectDatae = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
                add_endTime.setText(selectDatae + " " + selectTime);

            }
        });
        tpp_test = findViewById(R.id.add_choosetime_end).findViewById(R.id.tp_test);
        tpp_test.setOnChangeListener(new TimePicker.OnChangeListener() {
            @Override
            public void onChange(int hour, int minute) {
                s_selectHour = hour;
                s_selectMiniute = minute;
                selectTime = hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分";
                selectHour = hour;
                selectMinute = minute;
                add_endTime.setText(selectDatae + " " + selectTime);
            }
        });
        tp_test.setOnChangeListener(new TimePicker.OnChangeListener() {
            @Override
            public void onChange(int hour, int minute) {
                e_selectHour = hour;
                e_selectMiniute = minute;

                selectTime = hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分";
                selectHour = hour;
                selectMinute = minute;
                add_startTime.setText(selectDatae + " " + selectTime);
            }
        });
    }

    int a = 3, c = 3;

    @OnClick({R.id.add_startTime, R.id.add_endTime, R.id.add_layout_chongfu, R.id.add_layout_tixing, R.id.add_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_startTime:
                if (a == 3) {
                    layput_choosetime.setVisibility(View.VISIBLE);
                    add_choosetime_end.setVisibility(View.GONE);
                    a--;
                } else {
                    layput_choosetime.setVisibility(View.GONE);
                    add_choosetime_end.setVisibility(View.GONE);
                    a++;
                }
                break;
            case R.id.add_endTime:
                if (c == 3) {
                    add_choosetime_end.setVisibility(View.VISIBLE);
                    layput_choosetime.setVisibility(View.GONE);
                    c--;
                } else {
                    layput_choosetime.setVisibility(View.GONE);
                    add_choosetime_end.setVisibility(View.GONE);
                    c++;
                }
                break;
            case R.id.add_layout_chongfu:
                //重复
                Intent intent = new Intent(this, AlarmActivity.class);
                intent.putExtra("title", "重复");
                intent.putExtra("choose", "2");
                startActivityForResult(intent, 13);
                break;
            case R.id.add_layout_tixing:
                //提醒
                Intent intentt = new Intent(this, AlarmActivity.class);
                intentt.putExtra("title", "提醒");
                intentt.putExtra("choose", "3");
                startActivityForResult(intentt, 14);
                break;
            case R.id.add_sure:
                //提交日程
                subbmit();

                break;
        }
    }

    void subbmit() {
        sc_title = sc_titleview.getText().toString();//标题
        sc_location = sc_locationview.getText().toString();//地点
        //开始时间add_startTime;
        //结束时间add_endTime;
        //重复
        //提醒
        sc_remarks = sc_beizhuview.getText().toString();//备注
        //获取要出入的gmail账户的id
        if (CalanderUtils.requestPermission(this)) {
            String calId = "";
            //这时一条查询语句
            Cursor userCursor = getContentResolver().query(Uri.parse(CalanderUtils.calanderURL), null,
                    null, null, null);
            if (userCursor.getCount() > 0) {
                userCursor.moveToFirst();
                calId = userCursor.getString(userCursor.getColumnIndex("_id"));
            }


            ContentValues event = new ContentValues();
            event.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());
            event.put("title", sc_title);//插入标题
            event.put("description", sc_remarks);//备注
            event.put("calendar_id", calId);//插入hoohbood@gmail.com这个账户
            event.put("allDay", sc_allday);//是否全天
            event.put("eventLocation", sc_location);//地点
            Calendar mCalendar = Calendar.getInstance();
            //起始时间
            mCalendar.set(Calendar.YEAR, s_yearr);
            mCalendar.set(Calendar.MONTH, s_monthr - 1);
            mCalendar.set(Calendar.DAY_OF_MONTH, s_dayr);
            mCalendar.set(Calendar.HOUR_OF_DAY, s_selectHour);
            mCalendar.set(Calendar.MINUTE, s_selectMiniute);

            Log.i("zjc", s_yearr + "  " + s_monthr + "  " + s_selectHour + " " + s_selectMiniute);
            //结束时间
            //   mCalendar.set(2018, 12, 27, 7, 30);
            long start = mCalendar.getTime().getTime();
            mCalendar.set(Calendar.YEAR, e_yearr);
            mCalendar.set(Calendar.MONTH, e_monthr - 1);
            mCalendar.set(Calendar.DAY_OF_MONTH, e_dayr);
            mCalendar.set(Calendar.HOUR_OF_DAY, e_selectHour);
            mCalendar.set(Calendar.MINUTE, e_selectMiniute);
            Log.i("zjc", e_yearr + "  " + e_monthr + "  " + e_selectHour + " " + e_selectMiniute);
            // mCalendar.set(2018, 12, 27, 10, 30);
            long end = mCalendar.getTime().getTime();
            event.put("dtstart", start);
            event.put("dtend", end);
            event.put("hasAlarm", 1);
            if (!getSc_remarks.equals("0")) {
                event.put("rrule", "FREQ=" + getSc_remarks);
            }
            Uri newEvent = getContentResolver().insert(Uri.parse(CalanderUtils.calanderEventURL), event);
            long id = Long.parseLong(newEvent.getLastPathSegment());
            ContentValues values = new ContentValues();
            values.put("event_id", id);
            //提前10分钟有提醒
            values.put("minutes", alarmtime);//在事件发生之前多少分钟进行提醒
            getContentResolver().insert(Uri.parse(CalanderUtils.calanderRemiderURL), values);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13) {
            //重复
            getSc_remarks = chongfudui[resultCode];
            add_chongfu.setText(chongfu[resultCode]);
        } else if (requestCode == 14) {
            //提醒
            add_tixing.setText(alarmTime[resultCode]);
            alarmtime = alarmTimeduiying[resultCode];
        }
    }

    class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

            if (arg1) {
                // 开
                sc_allday = "1";
                Toast.makeText(AddSchedule.this, "kai", Toast.LENGTH_SHORT).show();
            } else {
                // 关
                sc_allday = "0";
                Toast.makeText(AddSchedule.this, "guan", Toast.LENGTH_SHORT).show();
            }

        }

    }

    void init() {
        selectDatae = calendar_all.get(java.util.Calendar.YEAR) + "年" + calendar_all.get(java.util.Calendar.MONTH) + "月"
                + calendar_all.get(java.util.Calendar.DAY_OF_MONTH) + "日"
                + DatePicker.getDayOfWeekCN(calendar_all.get(java.util.Calendar.DAY_OF_WEEK));

        //选择时间与当前时间的初始化，用于判断用户选择的是否是以前的时间，如果是，弹出toss提示不能选择过去的时间
        selectDay = currentDay = calendar_all.get(java.util.Calendar.DAY_OF_MONTH);
        selectMinute = currentMinute = calendar_all.get(java.util.Calendar.MINUTE);
        selectHour = currentHour = calendar_all.get(java.util.Calendar.HOUR_OF_DAY);
        selectTime = currentHour + "点" + ((currentMinute < 10) ? ("0" + currentMinute) : currentMinute) + "分";

        add_startTime.setText(selectDatae + " " + selectTime);
        add_endTime.setText(selectDatae + " " + selectTime);
    }
    /*void chooseTime(final int pan) {
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
    }*/
}
