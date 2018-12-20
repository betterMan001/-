package com.ly.a316.ly_meetingroommanagement.fragments;


import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.ddz.floatingactionbutton.FloatingActionButton;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.ly.a316.ly_meetingroommanagement.Adapter.Calendar_Adapter;
import com.ly.a316.ly_meetingroommanagement.activites.AddSchedule;
import com.ly.a316.ly_meetingroommanagement.classes.Schedule;
import com.ly.a316.ly_meetingroommanagement.classes.jilei;
import com.ly.a316.ly_meetingroommanagement.customView.SwipeItemLayout;
import com.ly.a316.ly_meetingroommanagement.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 描述：日历
 * 作者： 余智强
 * 创建时间：12/4 21：06 第一次提交
 */
public class CalendarFragment extends jilei implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, View.OnClickListener {
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
    Unbinder unbinder;
    private int mYear;
    String t_year, t_month, t_day;

    List<Schedule> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fr_calendar;
    }

    @Override
    protected void initView() {
        initview();
    }

    @Override
    protected void initData() {
    }

    void init() {




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
        tvMonthDay.setOnClickListener(this);
        tvCurrentDay.setOnClickListener(this);
        ibCalendarview.setOnCalendarSelectListener(this);
        ibCalendarview.setOnYearChangeListener(this);


    }

    void moren() {
        tvYear.setText(String.valueOf(ibCalendarview.getCurYear()));
        mYear = ibCalendarview.getCurYear();
        tv_today.setText((String.valueOf(ibCalendarview.getCurMonth()) + "月" + String.valueOf(ibCalendarview.getCurDay()) + "日"));
        tvMonthDay.setText((String.valueOf(ibCalendarview.getCurMonth()) + "月" + String.valueOf(ibCalendarview.getCurDay()) + "日"));
        tvLunar.setText("今天");
        tvCurrentDay.setText(String.valueOf(ibCalendarview.getCurDay()));
    }


    void initview() {
        setStatusBarDarkMode();
        init();
        /**
         * 伪造数据
         */
        Schedule schedule = new Schedule("12:50","13:50","会议","3c", "余智强","大会","很重要");
        Schedule.list.add(schedule);

         schedule = new Schedule("12:50","13:50","会议","3c", "余智强","大会","很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50","13:50","会议","3c", "余智强","大会","很重要");
        Schedule.list.add(schedule); schedule = new Schedule("12:50","13:50","会议","3c", "余智强","大会","很重要");
        Schedule.list.add(schedule); schedule = new Schedule("12:50","13:50","会议","3c", "余智强","大会","很重要");
        Schedule.list.add(schedule); schedule = new Schedule("12:50","13:50","会议","3c", "余智强","大会","很重要");
        Schedule.list.add(schedule); schedule = new Schedule("12:50","13:50","会议","3c", "余智强","大会","很重要");
        Schedule.list.add(schedule);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calRecycleview.setLayoutManager(linearLayoutManager);
        calendar_adapter = new Calendar_Adapter(getContext(), Schedule.list);
        calRecycleview.setAdapter(calendar_adapter);
        calendar_adapter.setOnItemClick(new Calendar_Adapter.OnItemClick() {
           @Override
           public void onitemClick(int position) {
               Toast.makeText(getContext(),"all被点击",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onitemDelete(int position) {
                Toast.makeText(getContext(),"删除按钮被点击",Toast.LENGTH_SHORT).show();
           }
           @Override
            public  void onitemAlarm(int po) {
               RecyclerView.LayoutManager manager = calRecycleview.getLayoutManager();
               View vieww = manager.findViewByPosition(po);
               Calendar_Adapter.MyViewHolder holder = (Calendar_Adapter.MyViewHolder) calRecycleview.getChildViewHolder(vieww);
               holder.item_calendar_alerm.setBackgroundColor(R.drawable.btn_delete);
               Toast.makeText(getContext(),"提醒按钮被点击",Toast.LENGTH_SHORT).show();
           }
       });
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

        moren();
        int year = ibCalendarview.getCurYear();
        int month = ibCalendarview.getCurMonth();
        //伪造数据
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFFdf1356, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFFdf1356, "假"));
        ibCalendarview.setSchemeDate(map);
        //*********************************************
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

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
    }

    @Override
    public void onYearChange(int year) {
        tvMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick({R.id.fl_addday,R.id.fl_schedule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_schedule:

                TimePickerView pvTime = new TimePickerView.Builder(getContext(),new TimePickerView.OnTimeSelectListener(){
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        String time = getTime(date);
                        Toast.makeText(getContext(),time,Toast.LENGTH_SHORT).show();
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
                pvTime.setDate(java.util.Calendar.getInstance());
                pvTime.show();

               break;
            case R.id.fl_addday:
                //添加日程
                Intent intent = new Intent(getContext(), AddSchedule.class);
                startActivity(intent);
                break;
        }
    }

    public String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }
}
