package com.ly.a316.ly_meetingroommanagement.fragments;


import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ddz.floatingactionbutton.FloatingActionButton;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.ly.a316.ly_meetingroommanagement.Adapter.Calendar_Adapter;
import com.ly.a316.ly_meetingroommanagement.activites.AddSchedule;
import com.ly.a316.ly_meetingroommanagement.calendarActivity.OneDayCountActivity;
import com.ly.a316.ly_meetingroommanagement.classes.Schedule;
import com.ly.a316.ly_meetingroommanagement.classes.jilei;
import com.ly.a316.ly_meetingroommanagement.customView.DatePicker;
import com.ly.a316.ly_meetingroommanagement.customView.SwipeItemLayout;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
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
    @BindView(R.id.more)
    TextView more;
    Unbinder unbinder;
    private int mYear;
    String t_year, t_month, t_day;
    List<Schedule> list = new ArrayList<>();
    Intent intent1;//跳转统计的页面

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

    TopRightMenu mToRightMenu;//右上角的菜单栏

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
        Schedule schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);

        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);
        schedule = new Schedule("12:50", "13:50", "会议", "3c", "余智强", "大会", "很重要");
        Schedule.list.add(schedule);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        calRecycleview.setLayoutManager(linearLayoutManager);
        calendar_adapter = new Calendar_Adapter(getContext(), Schedule.list);
        calRecycleview.setAdapter(calendar_adapter);
        calendar_adapter.setOnItemClick(new Calendar_Adapter.OnItemClick() {
            @Override
            public void onitemClick(int position) {
                Toast.makeText(getContext(), "all被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onitemDelete(int position) {
                Toast.makeText(getContext(), "删除按钮被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onitemAlarm(int po) {
                RecyclerView.LayoutManager manager = calRecycleview.getLayoutManager();
                View vieww = manager.findViewByPosition(po);
                Calendar_Adapter.MyViewHolder holder = (Calendar_Adapter.MyViewHolder) calRecycleview.getChildViewHolder(vieww);
                holder.item_calendar_alerm.setBackgroundColor(R.drawable.btn_delete);
                Toast.makeText(getContext(), "提醒按钮被点击", Toast.LENGTH_SHORT).show();
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
    //日历被点击
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


    @OnClick({R.id.fl_addday, R.id.fl_schedule, R.id.tv_month_dayyy, R.id.tv_current_day, R.id.more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_schedule:
                View vieww = View.inflate(getContext(), R.layout.choosetime, null);
                selectDatae = calendar_all.get(java.util.Calendar.YEAR) + "年" + calendar_all.get(java.util.Calendar.MONTH) + "月"
                        + calendar_all.get(java.util.Calendar.DAY_OF_MONTH) + "日"
                        + DatePicker.getDayOfWeekCN(calendar_all.get(java.util.Calendar.DAY_OF_WEEK));
                //选择时间与当前时间的初始化，用于判断用户选择的是否是以前的时间，如果是，弹出toss提示不能选择过去的时间
                selectDay = currentDay = calendar_all.get(java.util.Calendar.DAY_OF_MONTH);
                selectMinute = currentMinute = calendar_all.get(java.util.Calendar.MINUTE);
                selectHour = currentHour = calendar_all.get(java.util.Calendar.HOUR_OF_DAY);
                selectTime = currentHour + "点" + ((currentMinute < 10) ? ("0" + currentMinute) : currentMinute) + "分";
                dp_test = (DatePicker) vieww.findViewById(R.id.dp_test);
                tp_test = (TimePicker) vieww.findViewById(R.id.tp_test);
                tv_ok = (TextView) vieww.findViewById(R.id.tv_ok);
                tv_cancel = (TextView) vieww.findViewById(R.id.tv_cancel);
                //设置滑动改变监听器
                //listeners
                dp_test.setOnChangeListener(dp_onchanghelistener);
                tp_test.setOnChangeListener(tp_onchanghelistener);
                pw = new PopupWindow(vieww, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//              //设置这2个使得点击pop以外区域可以去除pop
//              pw.setOutsideTouchable(true);
//              pw.setBackgroundDrawable(new BitmapDrawable());
                //出现在布局底端
                pw.showAtLocation(fra, 0, 0, Gravity.END);
                //点击确定
                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if (selectDay == currentDay) {   //在当前日期情况下可能出现选中过去时间的情况
                            if (selectHour < currentHour) {
                                Toast.makeText(getContext(), "不能选择过去的时间\n        请重新选择", Toast.LENGTH_SHORT).show();
                            } else if ((selectHour == currentHour) && (selectMinute < currentMinute)) {
                                Toast.makeText(getContext(), "不能选择过去的时间\n        请重新选择", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), selectDatae + selectTime, Toast.LENGTH_SHORT).show();
                                pw.dismiss();
                            }
                        } else {
                            Toast.makeText(getContext(), selectDatae + selectTime, Toast.LENGTH_SHORT).show();
                            pw.dismiss();
                        }
                    }
                });

                //点击取消
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        pw.dismiss();
                    }
                });
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
            case R.id.more:
                 intent1 =new Intent(getActivity(),OneDayCountActivity.class);
                 startActivity(intent1);
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
}
