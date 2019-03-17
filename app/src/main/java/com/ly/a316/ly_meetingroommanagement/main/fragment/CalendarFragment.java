package com.ly.a316.ly_meetingroommanagement.main.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ddz.floatingactionbutton.FloatingActionButton;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.ly.a316.ly_meetingroommanagement.MessageList.activites.MessageListActivity;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.Schedule.Adapter.Calendar_Adapter;
import com.ly.a316.ly_meetingroommanagement.Schedule.Activity.AddSchedule;
import com.ly.a316.ly_meetingroommanagement.Schedule.Activity.AlarmActivity;
import com.ly.a316.ly_meetingroommanagement.Schedule.Activity.Calendar_infor_activity;
import com.ly.a316.ly_meetingroommanagement.Schedule.Activity.OneDayCountActivity;
import com.ly.a316.ly_meetingroommanagement.Schedule.Classes.EventModel;
import com.ly.a316.ly_meetingroommanagement.Schedule.Classes.Schedule;
import com.ly.a316.ly_meetingroommanagement.Schedule.Classes.jilei;
import com.ly.a316.ly_meetingroommanagement.calendar_fra.DaoImp.GetOneDay_DaoImp;
import com.ly.a316.ly_meetingroommanagement.calendar_fra.object.Day_Object;
import com.ly.a316.ly_meetingroommanagement.endActivity.activity.End_Activity;
import com.ly.a316.ly_meetingroommanagement.customView.DatePicker;
import com.ly.a316.ly_meetingroommanagement.customView.SwipeItemLayout;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;
import com.ly.a316.ly_meetingroommanagement.Schedule.unit.Util.CalanderUtils;
import com.ly.a316.ly_meetingroommanagement.login.activities.LoginActivity;
import com.ly.a316.ly_meetingroommanagement.main.MainActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingDetailActivity;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.activity.Ceshi;
import com.ly.a316.ly_meetingroommanagement.scancode.activity.ScanCode_Hui;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;
import com.yanzhenjie.permission.Setting;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.zaaach.toprightmenu.TopRightMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * 描述：日历
 * 作者： 余智强
 * 创建时间：12/4 21：06 第一次提交
 */
//onitemClick
public class CalendarFragment extends jilei implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener {
    View view;
    TextView tvMonthDay, tvYear, tvLunar, tvCurrentDay, tv_today;
    ImageView ibCalendar;
    FrameLayout flCurrent;
    RelativeLayout rlTool;
    CalendarView ibCalendarview;
    CalendarLayout calendarLayout;
    RecyclerView calRecycleview;
    Calendar_Adapter calendar_adapter;
    @BindView(R.id.fl_addday)
    com.ddz.floatingactionbutton.FloatingActionButton flAddday;
    @BindView(R.id.fl_schedule)
    FloatingActionButton fl_schedule;



    ImageView xialacainan;
    Unbinder unbinder;
    private int mYear;
    String t_year, t_month, t_day;
    List<Schedule> list = new ArrayList<>();
    Intent intent1;//跳转统计的页面
    int alarmyear, alarmmonth, alarmday;
    private TextView tv_ok, tv_cancel;//确定、取消button
    java.util.Calendar calendar_all;//获取今天的时间
    private DatePicker dp_test;//时间选择的控件
    private TimePicker tp_test;
    PopupWindow pw;
    String selectDatae, selectTime;
    //选择时间与当前时间，用于判断用户选择的是否是以前的时间
    private int currentHour, currentMinute, currentDay, selectHour, selectMinute, selectDay;
    @BindView(R.id.fra)
    FrameLayout fra;
    DatePicker.OnChangeListener dp_onchanghelistener;
    TimePicker.OnChangeListener tp_onchanghelistener;
    Map<String, Calendar> map = new HashMap<>();
    //0代表无 1代表日程发生时 5-120代表分钟 2代表一天前 3代表2天前
    int time_result[] = {0, 1, 5, 15, 30, 60, 120, 2, 3};
    int alarmTimeduiying[] = {-1, 0, 5, 15, 30, 60, 120, 1440, 2880, 10080};
    String alarm_item_eventid = "0";
    int alarm_choose_item;


    Day_Object day_object;
    GetOneDay_DaoImp getOneDay_daoImp = new GetOneDay_DaoImp(this, getContext());

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fr_calendar;

    }

    @Override
    protected void initView() {
        Schedule.list = new ArrayList<>();
        initview();
    }

    @Override
    protected void initData() {
    }

    void initview() {
        setStatusBarDarkMode();
        init();//初始化各种控件

        getOneDay_daoImp.getOneDayinformation(MyApplication.getId(), ibCalendarview.getCurYear() + "-" + panduan(ibCalendarview.getCurMonth()) + "-" + panduan(ibCalendarview.getCurDay()));
        getOneDay_daoImp.getAllIndormation(MyApplication.getId());
        calendar_adapter.setOnItemClick(new Calendar_Adapter.OnItemClick() {
            @Override
            public void onitemClick(int position, String event_idd, String starttime, String endtime) {
               /* //日程详情
               Intent intent = new Intent(getActivity(), Calendar_infor_activity.class);
                Log.i("zjc", event_idd);
                intent.putExtra("event_id", event_idd);
                startActivityForResult(intent, 14);*/
                //会议详情
                //   Intent intent = new Intent(getActivity(), MeetingDetailActivity.class);
                int start_hour = Integer.valueOf(starttime.substring(11, 13));
                int start_mini = Integer.valueOf(starttime.substring(14, 16));
                int end_hour = Integer.valueOf(endtime.substring(11, 13));
                int end_mini = Integer.valueOf(endtime.substring(14, 16));
                int durationmini = suanshijian(start_hour, start_mini, end_hour, end_mini);
                MeetingDetailActivity.start(getContext(), event_idd, String.valueOf(durationmini));
                //  Log.i("zjc", event_idd);
                ///   intent.putExtra("mId", event_idd);
                // startActivityForResult(intent, 14);
            }

            @Override
            //删除操作
            public void onitemDelete(String event_idd, int position) {
                getOneDay_daoImp.deleteSch(day_list.get(position).getNoId());
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onitemAlarm(int po, String event_idd) {

              /*  alarm_choose_item = po;
                alarmyear = Schedule.list.get(po).getYear();
                alarmmonth = Schedule.list.get(po).getMonth();
                alarmday = Schedule.list.get(po).getDay();
                alarm_item_eventid = event_idd;
                RecyclerView.LayoutManager manager = calRecycleview.getLayoutManager();
                View vieww = manager.findViewByPosition(po);
                Calendar_Adapter.MyViewHolder holder = (Calendar_Adapter.MyViewHolder) calRecycleview.getChildViewHolder(vieww);
                holder.item_calendar_alerm.setBackgroundColor(R.drawable.btn_delete);
*/
                Intent i = new Intent(getActivity(), AlarmActivity.class);
                i.putExtra("title", "新建提醒");
                i.putExtra("choose", "1");
                i.putExtra("alerttime", "1");
                day_object = day_list.get(po);
                startActivityForResult(i, 12);
            }

            @Override
            public void onitenFinish(int position, String eveniIdd, Calendar_Adapter.MyViewHolder viewHolder) {
                viewHolder.finish.setText("已完成");
                getOneDay_daoImp.finish(eveniIdd);
            }
        });
        //让recycleview具有侧滑的功能
        calRecycleview.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        calRecycleview.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        calRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    calendarLayout.shrink();
                } else {
                    calendarLayout.expand();
                }
            }
        });


        /*int year = ibCalendarview.getCurYear();
        int month = ibCalendarview.getCurMonth();
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year,month, 3, 0xFFdf1356, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFFdf1356, "假"));
        ibCalendarview.setSchemeDate(map);*/
    }

    int suanshijian(int s_h, int s_m, int e_h, int e_m) {
        int start_m = s_h * 60 + s_m;
        int end_m = e_h * 60 + e_m;

        int finalmin = end_m - start_m;

        return finalmin;
    }

    void init() {
        calendar_all = java.util.Calendar.getInstance();
        view = jilei.view;
        tv_today = view.findViewById(R.id.tv_today);
        tvMonthDay = view.findViewById(R.id.tv_month_dayyy);
        tvYear = view.findViewById(R.id.tv_year);
        tvLunar = view.findViewById(R.id.tv_lunar);
        ibCalendar = view.findViewById(R.id.ib_calendar);
        tvCurrentDay = view.findViewById(R.id.tv_current_day);
        flCurrent = view.findViewById(R.id.fl_current);
        rlTool = view.findViewById(R.id.rl_tool);
        ibCalendarview = view.findViewById(R.id.ib_calendarview);
        calendarLayout = view.findViewById(R.id.calendarLayout);
        calRecycleview = view.findViewById(R.id.cal_recycleview);
        xialacainan = view.findViewById(R.id.xialacainan);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calRecycleview.setLayoutManager(linearLayoutManager);
        calendar_adapter = new Calendar_Adapter(getContext(), day_list);
        calRecycleview.setAdapter(calendar_adapter);
        ibCalendarview.setOnCalendarSelectListener(this);
        ibCalendarview.setOnYearChangeListener(this);

        //标题栏最上面显示的今天的信息
        tvYear.setText(String.valueOf(ibCalendarview.getCurYear()));
        mYear = ibCalendarview.getCurYear();
        tv_today.setText((String.valueOf(ibCalendarview.getCurMonth()) + "月" + String.valueOf(ibCalendarview.getCurDay()) + "日"));
        tvMonthDay.setText((String.valueOf(ibCalendarview.getCurMonth()) + "月" + String.valueOf(ibCalendarview.getCurDay()) + "日"));
        tvLunar.setText("今天");
        tvCurrentDay.setText(String.valueOf(ibCalendarview.getCurDay()));

        TopRightMenu mToRightMenu = new TopRightMenu(getActivity());
        xialacainan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<com.zaaach.toprightmenu.MenuItem> menuItems = new ArrayList<>();
                menuItems.add(new com.zaaach.toprightmenu.MenuItem(R.drawable.tongjitwo, "统计"));
                menuItems.add(new com.zaaach.toprightmenu.MenuItem(R.drawable.xaioxitwo, "消息"));
                mToRightMenu
                        .setHeight(300)     //默认高度480
                        //默认宽度wrap_content
                        .showIcon(true)     //显示菜单图标，默认为true 为true的时候才能添加图标
                        .dimBackground(true)           //背景变暗，默认为true
                        .needAnimationStyle(true)   //显示动画，默认为true
                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //动画样式 默认为R.style.TRM_ANIM_STYLE
                        .addMenuList(menuItems)
                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                            @Override
                            public void onMenuItemClick(int position) {
                                if (position == 0) {
                                    intent1 = new Intent(getActivity(), OneDayCountActivity.class);
                                    intent1.putExtra("list", (Serializable) day_list);
                                    startActivity(intent1);
                                } else if (position == 1) {
                                    MessageListActivity.start(getActivity());
                                }
                             }
                        })
                        .showAsDropDown(xialacainan, -180, 0);
            }
        });
    }

    String panduan(int timee) {
        String times;
        if (timee / 10 == 0) {
            times = "0" + timee;
        } else {
            times = String.valueOf(timee);
        }
        return times;
    }


    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
         calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());

      /*  calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");*/
        return calendar;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 12:
                //修改提醒回调的地方
              /*  ContentValues values = new ContentValues();
                //插入时间的event_id
                values.put("minutes", alarmTimeduiying[resultCode]);//在事件发生之前多少分钟进行提醒
                int rows = getActivity().getContentResolver().update(Uri.parse(CalanderUtils.calanderRemiderURL), values, CalendarContract.Reminders.EVENT_ID + "=" + Integer.valueOf(alarm_item_eventid), null);
                //rows大于零就表示修改成功。
                if (rows > 0) {
                    Schedule.list.clear();
                    //获取这个时间段的所有信息
                    List<Schedule> calendarEvent = null;
                    try {
                        calendarEvent = CalanderUtils.getCalendarEventByDay(getActivity(), alarmyear, alarmmonth, alarmday);
                        Schedule.list.addAll(calendarEvent);
                        calendar_adapter.notifyItemChanged(alarm_choose_item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                ceshi_huiyi(day_object, alarmTimeduiying[resultCode]);
                break;
            case 14:
                if (resultCode == 10) {
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    //修改日程
                    Schedule.list.clear();
                    //获取这个时间段的所有信息
                    List<Schedule> calendarEvent = null;
                    try {
                        calendarEvent = CalanderUtils.getCalendarEventByDay(getActivity(), alarmyear, alarmmonth, alarmday);
                        Schedule.list.addAll(calendarEvent);
                        calendar_adapter.notifyItemChanged(alarm_choose_item);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 101:
                if (resultCode == RESULT_OK) {
                    String content = data.getStringExtra(Constant.CODED_CONTENT);
                    Toast.makeText(getContext(), "扫描结果为：" + content, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    //日历被点击的时候调用
    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tv_today.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        tvLunar.setVisibility(View.VISIBLE);
        tvYear.setVisibility(View.VISIBLE);
        tvMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        tvYear.setText(String.valueOf(calendar.getYear()));
        tvLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
        t_year = calendar.getYear() + "";
        t_day = calendar.getDay() + "";
        t_month = calendar.getMonth() + "";

        getOneDay_daoImp.getOneDayinformation(MyApplication.getId(), panduan(calendar.getYear()) + "-" + panduan(calendar.getMonth()) + "-" + panduan(calendar.getDay()));

       /* try {
            Schedule.list.clear();
            //获取这个时间段的所有信息
            List<Schedule> calendarEvent = CalanderUtils.getCalendarEventByDay(getActivity(), calendar.getYear(), calendar.getMonth(), calendar.getDay());
            Schedule.list.addAll(calendarEvent);
            calendar_adapter.notifyDataSetChanged();
            getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onYearChange(int year) {
        tvMonthDay.setText(String.valueOf(year));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view

        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        unbinder = ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);
        dp_onchanghelistener = new DatePicker.OnChangeListener() {
            @Override
            public void onChange(int year, int month, int day, int day_of_week) {
                selectDay = day;
                selectDatae = year + "年" + month + "月" + day + "日" + DatePicker.getDayOfWeekCN(day_of_week);
            }
        };
        tp_onchanghelistener = new TimePicker.OnChangeListener() {
            @Override
            public void onChange(int hour, int minute) {
                selectTime = hour + "点" + ((minute < 10) ? ("0" + minute) : minute) + "分";
                selectHour = hour;
                selectMinute = minute;
            }
        };

        return rootView;
    }


    @SuppressLint("MissingPermission")
    @OnClick({R.id.fl_addday, R.id.fl_schedule, R.id.tv_month_dayyy, R.id.tv_current_day})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_schedule:
              /*
                    测试插入会议日程
                 ceshi_huiyi();*/


               /*   //  测试结束会议
                Intent intentttt = new Intent(getContext(), End_Activity.class);
                startActivity(intentttt);
                */

                //测试服务
               /* Intent intentet = new Intent(getContext(), Ceshi.class);
                startActivity(intentet);*/

                /*
              請求權限
                 */
                // scanCode();
                /*
                周日历
                 */

                /*Intent intentttt = new Intent(getContext(), ScanCode_Hui.class);
                startActivity(intentttt);*/

                ibCalendarview.scrollToCurrent();////滚动到当前日期
                break;
            case R.id.fl_addday:
                //添加日程
                Intent intent = new Intent(getContext(), AddSchedule.class);
                startActivity(intent);
                break;
            case R.id.tv_month_dayyy:
                if (!calendarLayout.isExpand()) {
                    ibCalendarview.showYearSelectLayout(mYear);////快速弹出年份选择月份
                    return;
                }
                ibCalendarview.showYearSelectLayout(mYear);
                tvLunar.setVisibility(View.GONE);
                tvYear.setVisibility(View.GONE);
                tvMonthDay.setText(String.valueOf(mYear));
                break;

            case R.id.tv_current_day:
                ibCalendarview.scrollToCurrent();////滚动到当前日期
                break;



        }
    }


    @Override
    public void initImmersionBar() {

    }

    void getAll() {
     /*   try {
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.set(java.util.Calendar.YEAR, Integer.valueOf(t_year));
 g           c.set(java.util.Calendar.MONTH, Integer.valueOf(t_month) - 1);
            int maxDay = c.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
            List<EventModel> calendarEvent = CalanderUtils.getCalendarEvent(getActivity(), Integer.valueOf(t_year), Integer.valueOf(t_month), maxDay);*/
        for (int i = 0; i < list_all.size(); i++) {
            map.put(getSchemeCalendar(Integer.valueOf(panduan(Integer.valueOf(list_all.get(i).getStartTime().substring(0, 4)))), Integer.valueOf(panduan(Integer.valueOf(list_all.get(i).getStartTime().substring(5, 7)))), Integer.valueOf(panduan(Integer.valueOf(list_all.get(i).getStartTime().substring(8, 10)))), 0xFFdf1356, "").toString(),
                    getSchemeCalendar(Integer.valueOf(panduan(Integer.valueOf(list_all.get(i).getStartTime().substring(0, 4)))), Integer.valueOf(panduan(Integer.valueOf(list_all.get(i).getStartTime().substring(5, 7)))), Integer.valueOf(panduan(Integer.valueOf(list_all.get(i).getStartTime().substring(8, 10)))), 0xFFdf1356, ""));
            ibCalendarview.setSchemeDate(map);
        }
       /* } catch (Exception e) {
            e.printStackTrace();
        }*/

    }


    /**
     * 往日历里面插入带有参会人的日程信息
     */
    @SuppressLint("MissingPermission")
    void ceshi_huiyi(Day_Object day_object, int alarmtime) {
        if (CalanderUtils.requestPermission(getActivity())) {
            String calId = "";
            //这是一条查询语句
            Cursor userCursor = getContext().getContentResolver().query(Uri.parse(CalanderUtils.calanderURL), null,
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
            event.put("title", day_object.getContent());//插入标题
            event.put("description", day_object.getRemark());//备注
            event.put("calendar_id", calId);//插入hoohbood@gmail.com这个账户
            event.put("eventLocation", day_object.getAddress());//地点
            java.util.Calendar mCalendar = java.util.Calendar.getInstance();

            //起始时间
            mCalendar.set(java.util.Calendar.YEAR, Integer.valueOf(panduan(Integer.valueOf(day_object.getStartTime().substring(0, 4)))));
            mCalendar.set(java.util.Calendar.MONTH, Integer.valueOf(panduan(Integer.valueOf(day_object.getStartTime().substring(5, 7)))) - 1);
            mCalendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.valueOf(panduan(Integer.valueOf(day_object.getStartTime().substring(8, 10)))));
            mCalendar.set(java.util.Calendar.HOUR_OF_DAY, Integer.valueOf(panduan(Integer.valueOf(day_object.getStartTime().substring(11, 13)))));
            mCalendar.set(java.util.Calendar.MINUTE, Integer.valueOf(panduan(Integer.valueOf(day_object.getStartTime().substring(14, 16)))));
            long start = mCalendar.getTime().getTime();
            //结束时间
            mCalendar.set(java.util.Calendar.YEAR, Integer.valueOf(panduan(Integer.valueOf(day_object.getEndTime().substring(0, 4)))));
            mCalendar.set(java.util.Calendar.MONTH, Integer.valueOf(panduan(Integer.valueOf(day_object.getEndTime().substring(5, 7)))) - 1);
            mCalendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.valueOf(panduan(Integer.valueOf(day_object.getEndTime().substring(8, 10)))));
            mCalendar.set(java.util.Calendar.HOUR_OF_DAY, Integer.valueOf(panduan(Integer.valueOf(day_object.getEndTime().substring(11, 13)))));
            mCalendar.set(java.util.Calendar.MINUTE, Integer.valueOf(panduan(Integer.valueOf(day_object.getEndTime().substring(14, 16)))));
            long end = mCalendar.getTime().getTime();
            event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
            event.put("dtstart", start);
            event.put("dtend", end);
            event.put("hasAlarm", 1);

            Uri newEvent = getContext().getContentResolver().insert(Uri.parse(CalanderUtils.calanderEventURL), event);

            long id = Long.parseLong(newEvent.getLastPathSegment());
            ContentValues values = new ContentValues();
            //插入时间的event_id
            values.put("event_id", id);

            values.put(CalendarContract.Reminders.MINUTES, alarmtime);//在事件发生之前多少分钟进行提醒
            values.put(CalendarContract.Reminders.METHOD, 1);//添加提醒这个必须有
            getContext().getContentResolver().insert(Uri.parse(CalanderUtils.calanderRemiderURL), values);
            //添加会议人
            ContentValues valuess = new ContentValues();
            valuess.put(CalendarContract.Attendees.EVENT_ID, id);
            valuess.put("attendeeName", day_object.getLeaders());

            getContext().getContentResolver().insert(CalendarContract.Attendees.CONTENT_URI, valuess);


        }
    }

    /**
     * 扫码功能
     */
    void scanCode() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
         * 也可以不传这个参数
         * 不传的话  默认都为默认不震动  其他都为true
         * */

        ZxingConfig config = new ZxingConfig();
        config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
        //config.setPlayBeep(true);//是否播放提示音
        //config.setShake(true);//是否震动
        config.setShowAlbum(true);//是否显示相册
        //config.setShowFlashLight(true);//是否显示闪光灯
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        startActivityForResult(intent, 101);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    List<Day_Object> day_list = new ArrayList<>();

    public void success_callback(List<Day_Object> list) {
        day_list.clear();
        day_list.addAll(list);
        calendar_adapter.notifyDataSetChanged();
        getAll();
    }

    List<Day_Object> list_all = new ArrayList<>();

    public void success_call_all(List<Day_Object> list) {
        list_all.clear();
        list_all.addAll(list);
        getAll();
    }

    public void success_delete() {
        getOneDay_daoImp.getOneDayinformation(MyApplication.getId(), panduan(Integer.valueOf(t_year)) + "-" + panduan(Integer.valueOf(t_month)) + "-" + panduan(Integer.valueOf(t_day)));
    }
}
