package com.ly.a316.ly_meetingroommanagement.Schedule.Activity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.Schedule.Dao.ScheduleDao;
import com.ly.a316.ly_meetingroommanagement.Schedule.DaoImp.ScheduleDaoImp;
import com.ly.a316.ly_meetingroommanagement.customView.DatePicker;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;

import com.ly.a316.ly_meetingroommanagement.Schedule.unit.Util.CalanderUtils;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    LinearLayout add_choosetime_end;
    java.util.Calendar calendar_all;//获取今天的时间

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
    String sc_title, sc_location, sc_allday, sc_remarks;
    String chongfu[] = {"永不", "每天", "每周", "每月", "每年", "自定义"};
    String chongfudui[] = {"0", "DAILY", "WEEKLY", "MONTHLY", "YEARLY"};
    String alarmTime[] = {"无", "日程发生时", "5分钟前", "15分钟前", "30分钟前", "1小时前", "2小时前", "1天前", "2天前", "一周前"};
    int alarmTimeduiying[] = {-1, 0, 5, 15, 30, 60, 120, 1440, 2880, 10080};
    String selectDatae, selectTime;//选择完的年月日   选择完的时分
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private int currentHour, currentMinute, currentDay;
    TextView add_sure;
    //重复名字
    String getSc_remarks = "0";
    int alarmtime;//提醒的时间
    int s_yearr, s_monthr, s_dayr, s_day_of_weekr, s_selectHour, s_selectMiniute;//开始的时间
    int e_yearr, e_monthr, e_dayr, e_day_of_wekkr, e_selectHour, e_selectMiniute;//结束的时间
    ScheduleDao scheduleDao = new ScheduleDaoImp(this);
    String ti = "无";//提醒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
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
                selectDatae = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
                add_startTime.setText(selectDatae + " ");

                e_yearr = year;
                e_monthr = month;
                e_dayr = day;
                e_day_of_wekkr = day_of_week;
                selectDatae = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
                add_endTime.setText(selectDatae + " " + selectTime);

                add_choosetime_end.removeView(dpp_test);
                add_choosetime_end.removeView(tpp_test);
            }
        });
        tp_test.setOnChangeListener(new TimePicker.OnChangeListener() {
            @Override
            public void onChange(int hour, int minute) {
                s_selectHour = hour;//开始的时间
                s_selectMiniute = minute;
                selectTime = hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分";
                add_startTime.setText(selectDatae + " " + selectTime);
                if (hour >= 24) {
                    e_selectHour = hour;
                }
                e_selectMiniute = s_selectMiniute == 30 ? 0 : (s_selectMiniute < 30 ? s_selectMiniute + 30 : s_selectMiniute);
                if (s_selectMiniute >= 30) {
                    if (hour == 24) {
                        e_selectHour = 1;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, s_yearr);
                        calendar.set(Calendar.MONTH, s_monthr);
                        if (s_dayr == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            e_dayr = 1;
                        } else {
                            e_dayr = s_dayr + 1;
                        }
                    } else {
                        e_selectHour = hour + 1;
                    }
                } else {
                    e_selectHour = s_selectHour;
                }
                selectTime = e_selectHour + "点" + ((e_selectMiniute < 10) ? ("0" + e_selectMiniute) : e_selectMiniute) + "分";
                add_endTime.setText(selectDatae + " " + selectTime);

            }
        });
    }

    //动态添加时间选择器
    void dsd(int year, int month, int day, int hour, int mini) {
        add_choosetime_end.removeView(dpp_test);
        add_choosetime_end.removeView(tpp_test);
        dpp_test = new DatePicker(this, year, month, day, hour, mini);
        add_choosetime_end.addView(dpp_test);
        tpp_test = new TimePicker(this, hour, mini);
        add_choosetime_end.addView(tpp_test);


        dpp_test.setOnChangeListener(new DatePicker.OnChangeListener() {
            @Override
            public void onChange(int year, int month, int day, int day_of_week) {
                e_yearr = year;
                e_monthr = month;
                e_dayr = day;
                e_day_of_wekkr = day_of_week;
                selectDatae = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
                add_endTime.setText(selectDatae + " " + selectTime);

            }
        });

        tpp_test.setOnChangeListener(new TimePicker.OnChangeListener() {
            @Override
            public void onChange(int hour, int minute) {
                e_selectHour = hour;//结束的时间
                e_selectMiniute = minute;
                selectTime = hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分";
                add_endTime.setText(selectDatae + " " + selectTime);
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
                    dsd(e_yearr, e_monthr, e_dayr, e_selectHour, e_selectMiniute);
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
                intent.putExtra("alerttime", "0");
                startActivityForResult(intent, 13);
                break;
            case R.id.add_layout_tixing:
                //提醒
                Intent intentt = new Intent(this, AlarmActivity.class);
                intentt.putExtra("title", "提醒");
                intentt.putExtra("choose", "3");
                intentt.putExtra("alerttime", "-1");
                startActivityForResult(intentt, 14);
                break;
            case R.id.add_sure:
                //提交日程
                subbmit();

                String starttime = s_yearr + "-" + panduan(s_monthr) + "-" + panduan(s_dayr) + " " + panduan(s_selectHour) + ":" + panduan(s_selectMiniute) + ":00";
                String endtime = e_yearr + "-" + panduan(e_monthr) + "-" + panduan(e_dayr) + " " + panduan(e_selectHour) + ":" + panduan(e_selectMiniute) + ":00";
                scheduleDao.addSchedule("18248612936", starttime, endtime, sc_title, "日程", sc_remarks, sc_location, ti);
                break;
        }
    }

    String panduan(int time) {
        String s_time = "";
        if (time / 10 == 0) {
            s_time = "0" + time;
        }else{
            s_time = String.valueOf(time);
        }
        return s_time;
    }

    @SuppressLint("MissingPermission")
    void subbmit() {
        sc_title = sc_titleview.getText().toString();//标题
        sc_location = sc_locationview.getText().toString();//地点
       /*
         开始时间add_startTime;
         结束时间add_endTime;
         重复
         提醒
       */
        //备注
        sc_remarks = sc_beizhuview.getText().toString();
        //设置calendar的id。一般都设置为1
        if (CalanderUtils.requestPermission(this)) {
            String calId = "";
            //这是一条查询语句
            Cursor userCursor = getContentResolver().query(Uri.parse(CalanderUtils.calanderURL), null,
                    null, null, null);
            //Uri.parse(CalanderUtils.calanderURL)打印得结果：content://com.android.calendar/calendars
            //CalendarContract.Calendars.CONTENT_URI[打印得结果： content://com.android.calendar/calendars
            if (userCursor.getCount() > 0) {
                userCursor.moveToFirst();
                calId = userCursor.getString(userCursor.getColumnIndex("_id"));
                //打印得结果calld = 1
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
            long start = mCalendar.getTime().getTime();
            //结束时间
            mCalendar.set(Calendar.YEAR, e_yearr);
            mCalendar.set(Calendar.MONTH, e_monthr - 1);
            mCalendar.set(Calendar.DAY_OF_MONTH, e_dayr);
            mCalendar.set(Calendar.HOUR_OF_DAY, e_selectHour);
            mCalendar.set(Calendar.MINUTE, e_selectMiniute);
            long end = mCalendar.getTime().getTime();

            event.put("dtstart", start);
            event.put("dtend", end);
            event.put("hasAlarm", 1);

            if (!getSc_remarks.equals("0")) {
                //设置重复规则
                event.put("rrule", "FREQ=" + getSc_remarks);
            }
            Uri newEvent = getContentResolver().insert(Uri.parse(CalanderUtils.calanderEventURL), event);

            long id = Long.parseLong(newEvent.getLastPathSegment());
            ContentValues values = new ContentValues();
            //插入时间的event_id
            values.put("event_id", id);

            //提前10分钟有提醒
            values.put("minutes", alarmtime);//在事件发生之前多少分钟进行提醒
            getContentResolver().insert(Uri.parse(CalanderUtils.calanderRemiderURL), values);
            //添加会议人
            /*ContentValues valuess = new ContentValues();
            valuess.put("event_id", id);
            valuess.put("event_id", id);
            Log.i("zjc",id+"");
            valuess.put("attendeeName", "张思");
            getContentResolver().insert(CalendarContract.Attendees.CONTENT_URI, valuess);*/

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
            if (requestCode == 0) {
                ti = "无";
            } else {
                ti = "有";
            }
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
        selectDatae = calendar_all.get(java.util.Calendar.YEAR) + "年" + (Integer.valueOf(calendar_all.get(java.util.Calendar.MONTH)) + 1) + "月"
                + calendar_all.get(java.util.Calendar.DAY_OF_MONTH) + "日"
                + DatePicker.getDayOfWeekCN(calendar_all.get(java.util.Calendar.DAY_OF_WEEK));
        s_yearr = calendar_all.get(java.util.Calendar.YEAR);
        s_monthr = (Integer.valueOf(calendar_all.get(java.util.Calendar.MONTH)) + 1);
        s_dayr = calendar_all.get(java.util.Calendar.DAY_OF_MONTH);
        s_day_of_weekr = calendar_all.get(java.util.Calendar.DAY_OF_WEEK);
        s_selectHour = calendar_all.get(Calendar.HOUR_OF_DAY);//开始的时间
        s_selectMiniute = calendar_all.get(Calendar.MINUTE);

        e_yearr = calendar_all.get(java.util.Calendar.YEAR);
        e_monthr = (Integer.valueOf(calendar_all.get(java.util.Calendar.MONTH)) + 1);
        e_dayr = calendar_all.get(java.util.Calendar.DAY_OF_MONTH);
        s_day_of_weekr = calendar_all.get(java.util.Calendar.DAY_OF_WEEK);

        e_selectMiniute = s_selectMiniute == 30 ? 0 : (s_selectMiniute < 30 ? s_selectMiniute + 30 : s_selectMiniute);
        if (s_selectMiniute >= 30) {
            if (s_selectHour == 24) {
                e_selectHour = 1;
            } else {
                e_selectHour = s_selectHour + 1;
            }
        } else {
            e_selectHour = s_selectHour;
        }
        selectTime = s_selectHour + "点" + ((s_selectMiniute < 10) ? ("0" + s_selectMiniute) : s_selectMiniute) + "分";
        add_startTime.setText(selectDatae + " " + selectTime);
        selectTime = e_selectHour + "点" + ((e_selectMiniute < 10) ? ("0" + e_selectMiniute) : e_selectMiniute) + "分";
        add_endTime.setText(selectDatae + " " + selectTime);

        dpp_test = new DatePicker(this, e_yearr, e_monthr, e_dayr, e_selectHour, e_selectMiniute);
        add_choosetime_end.addView(dpp_test);
        tpp_test = new TimePicker(this, e_selectHour, e_selectHour);
        add_choosetime_end.addView(tpp_test);
    }

    public void successback() {
        finish();
    }
}
