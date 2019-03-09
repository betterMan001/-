package com.ly.a316.ly_meetingroommanagement.Schedule.Activity;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.Schedule.Classes.Schedule;
import com.ly.a316.ly_meetingroommanagement.customView.DatePicker;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;
import com.ly.a316.ly_meetingroommanagement.main.fragment.CalendarFragment;
import com.ly.a316.ly_meetingroommanagement.Schedule.unit.Util.CalanderUtils;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Calendar_infor_activity extends AppCompatActivity {
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
    @BindView(R.id.headddd)
     TextView headddd;
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

    String sc_title, sc_location, sc_allday, sc_remarks;
    //重复名字
    String getSc_remarks = "0";
    int alarmtime = 0;//提醒的时间
    int s_yearr, s_monthr, s_dayr, s_selectHour, s_selectMiniute;//开始的时间
    int e_yearr, e_monthr, e_dayr, e_selectHour, e_selectMiniute;//结束的时间

    String ti = "无";//提醒


    String infor_event_idd;//事件的event id
    Schedule schedule;//存放茶查找的事件的所有信息
    String start_time, end_time;//存放开始时间和结束时间  格式：yyyy-MM-dd hh:mm:ss
    int start_year, start_month, start_day, start_hour, start_miniute, s_day_of_weekr, end_year, end_month, end_day, end_hour, end_miniute, e_day_of_wekkr;
    DatePicker dpp_test;
    TimePicker tpp_test;

    String chongfu[] = {"永不", "每天", "每周", "每月", "每年", "自定义"};
    String chongfudui[] = {"0", "DAILY", "WEEKLY", "MONTHLY", "YEARLY"};
    String alarmTime[] = {"无", "日程发生时", "5分钟前", "15分钟前", "30分钟前", "1小时前", "2小时前", "1天前", "2天前", "一周前"};
    int alarmTimeduiying[] = {-1, 0, 5, 15, 30, 60, 120, 1440, 2880, 10080};

    String time_start,time_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        ButterKnife.bind(this);
        Intent inttent = getIntent();
        infor_event_idd = inttent.getStringExtra("event_id");//获取到事件的id
        try {
            //获取事件的所有信息
            schedule = CalanderUtils.getCalendarEventByEventId(Calendar_infor_activity.this, infor_event_idd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
        dp_test.setOnChangeListener(new DatePicker.OnChangeListener() {
            @Override
            public void onChange(int year, int month, int day, int day_of_week) {
                start_year = year;
                start_month = month;
                start_day = day;
                s_day_of_weekr = day_of_week;
                start_time = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
                add_startTime.setText(start_time + " ");

                end_year = year;
                end_month = month;
                end_day = day;
                e_day_of_wekkr = day_of_week;
                end_time = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
                add_endTime.setText(end_time);

                add_choosetime_end.removeView(dpp_test);
                add_choosetime_end.removeView(tpp_test);
            }
        });
        tp_test.setOnChangeListener(new TimePicker.OnChangeListener() {
            @Override
            public void onChange(int hour, int minute) {
                start_hour = hour;//小时
                start_miniute = minute;//分钟
                time_start = " " + hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分";
                add_startTime.setText(start_time+" "+time_start);

                if (hour >= 24) {
                    end_hour = hour;
                }
                end_miniute = start_miniute == 30 ? 0 : (start_miniute < 30 ? start_miniute + 30 : start_miniute);
                if (start_miniute >= 30) {
                    if (hour == 24) {
                        end_hour = 1;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, start_year);
                        calendar.set(Calendar.MONTH, start_month);
                        if (start_day == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                            end_day = 1;
                        } else {
                            end_day = start_day + 1;
                        }
                    } else {
                        end_hour = hour + 1;
                    }
                } else {
                    end_hour = start_hour;
                }
                time_end ="  " + end_hour + "点" + ((end_miniute < 10) ? ("0" + end_miniute) : end_miniute) + "分";
                add_endTime.setText(start_time+" "+time_end);
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
                end_year = year;
                end_month = month;
                end_day = day;
                e_day_of_wekkr = day_of_week;
                end_time = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
                add_endTime.setText(end_time);

            }
        });

        tpp_test.setOnChangeListener(new TimePicker.OnChangeListener() {
            @Override
            public void onChange(int hour, int minute) {
                end_hour = hour;//结束的时间
                end_miniute = minute;
                time_end =" " + hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分";
                add_endTime.setText(end_time+" "+time_end);
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
                    dsd(end_year, end_month, end_day, end_hour, end_miniute);
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
                 break;
        }
    }



    @SuppressLint("MissingPermission")
    void subbmit() {
        sc_title = sc_titleview.getText().toString();//标题
        sc_location = sc_locationview.getText().toString();//地点
        //备注
        sc_remarks = sc_beizhuview.getText().toString();
        //修改提醒回调的地方
        ContentValues values = new ContentValues();
        //插入时间的event_id
        if(sc_title != null){
            values.put("title", sc_title);//在事件发生之前多少分钟进行提醒
        }
        if(sc_remarks != null){
            values.put("description", sc_remarks);//备注
        }
        if(sc_allday != null){
            values.put("allDay", sc_allday);//是否全天
        }
        if(sc_location != null){
            values.put("eventLocation", sc_location);//地点
        }

        Calendar mCalendar = Calendar.getInstance();
        //起始时间
        mCalendar.set(Calendar.YEAR, start_year);
        mCalendar.set(Calendar.MONTH, start_month - 1);
        mCalendar.set(Calendar.DAY_OF_MONTH, start_day);
        mCalendar.set(Calendar.HOUR_OF_DAY, start_hour);
        mCalendar.set(Calendar.MINUTE, start_miniute);
        long start = mCalendar.getTime().getTime();
        //结束时间
        mCalendar.set(Calendar.YEAR, end_year);
        mCalendar.set(Calendar.MONTH, end_month - 1);
        mCalendar.set(Calendar.DAY_OF_MONTH, end_day);
        mCalendar.set(Calendar.HOUR_OF_DAY, end_hour);
        mCalendar.set(Calendar.MINUTE, end_miniute);
        long end = mCalendar.getTime().getTime();


        values.put("dtstart", start);
        values.put("dtend", end);
        values.put("hasAlarm", 1);
        if (!getSc_remarks.equals("0")) {
            //设置重复规则
            values.put("rrule", "FREQ=" + getSc_remarks);
        }
        Uri updateUri = null;

        updateUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, Integer.valueOf(infor_event_idd));

        int rows = getContentResolver().update(updateUri, values, null, null);



        ContentValues valuess = new ContentValues();
        //插入时间的event_id
        valuess.put("event_id", infor_event_idd);
        //提前10分钟有提醒
        if(alarmtime != 0){
            valuess.put("minutes", alarmtime);//在事件发生之前多少分钟进行提醒
        }
        int rowss = getContentResolver().update(Uri.parse(CalanderUtils.calanderRemiderURL), valuess, CalendarContract.Reminders.EVENT_ID + "=" + Integer.valueOf(infor_event_idd), null);

        if(rows>0&&rowss>0){
            Intent intent1 = new Intent(Calendar_infor_activity.this, CalendarFragment.class);
            Calendar_infor_activity.this.setResult(10, intent1);
            finish();
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
                sc_allday = "1"; // 开
            } else {
                sc_allday = "0";// 关
            }
        }
    }

    void init() {
        sc_titleview.setText(schedule.getAlert_head());//标题
        sc_locationview.setText(schedule.getAlert_difang());
        add_chongfu.setText(schedule.getAlert_people());//重复
        String dsa = schedule.getEvent_idd().toString();
        dsa = panduan(dsa);
        add_tixing.setText(dsa);//提醒
        sc_beizhuview.setText(schedule.getAlert_beizhu());
        headddd.setText("日程详情");
        addSchedule_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toggleButton.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) new MyOnCheckedChangeListener());
        if (schedule.getAttribute().toString().equals("1")) {
            toggleButton.setChecked(true);
        }
        //格式：yyyy-MM-dd hh:mm:ss
        start_time = schedule.getAlert_startTime();//拿到事件的开始时间
        end_time = schedule.getAlert_endtime();//拿到事件的结束时间

        //截取字段
        start_year = Integer.parseInt(start_time.substring(0, 4));
        start_month = Integer.parseInt(start_time.substring(5, 7));
        start_day = Integer.parseInt(start_time.substring(8, 10));
        start_hour = Integer.parseInt(start_time.substring(11, 13));
        start_miniute = Integer.parseInt(start_time.substring(14, 16));

        end_year = Integer.parseInt(end_time.substring(0, 4));
        end_month = Integer.parseInt(end_time.substring(5, 7));
        end_day = Integer.parseInt(end_time.substring(8, 10));
        end_hour = Integer.parseInt(end_time.substring(11, 13));
        end_miniute = Integer.parseInt(end_time.substring(14, 16));

        add_startTime.setText(start_time);
        add_endTime.setText(end_time);

        dpp_test = new DatePicker(this, end_year, end_month, end_day, end_hour, end_miniute);
        add_choosetime_end.addView(dpp_test);
        tpp_test = new TimePicker(this, end_hour, end_miniute);
        add_choosetime_end.addView(tpp_test);
    }



    String panduan(String dsa) {
        switch (dsa) {
            case "-1":
                dsa = "无";
                break;
            case "0":
                dsa = "日程发生时";
                break;
            case "5":
                dsa = "5分钟前";
                break;
            case "10":
                dsa = "10分钟前";
                break;
            case "15":
                dsa = "15分钟前";
                break;
            case "30":
                dsa = "30分钟前";
                break;
            case "60":
                dsa = "一小时前";
                break;
            case "120":
                dsa = "两小时前";
                break;
            case "1440":
                dsa = "一天前";
                break;
            case "2880":
                dsa = "两天前";
                break;
            case "10080":
                dsa = "一周前";
                break;
            default:
                dsa = "无";
                break;
        }
        return dsa;
    }
}
