package com.ly.a316.ly_meetingroommanagement.Fragment;



import android.support.v7.widget.RecyclerView;

import android.view.View;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.ly.a316.ly_meetingroommanagement.Class.jilei;
import com.ly.a316.ly_meetingroommanagement.R;

import java.util.HashMap;
import java.util.Map;
/**
 *  描述：日历
 *  作者： 余智强
 *  创建时间：12/4 21：06 第一次提交
*/
public class Fr_calendar extends jilei implements CalendarView.OnCalendarSelectListener, CalendarView.OnYearChangeListener, View.OnClickListener {
    View view;
    TextView tvMonthDay,tvYear,tvLunar,tvCurrentDay;
    ImageView ibCalendar;
    FrameLayout flCurrent;
    RelativeLayout rlTool;
    CalendarView ibCalendarview;
    CalendarLayout calendarLayout;
    RecyclerView calRecycleview;

    private int mYear;

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
    void initview() {
        setStatusBarDarkMode();
        view = jilei.view;
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
        tvYear.setText(String.valueOf(ibCalendarview.getCurYear()));
        mYear = ibCalendarview.getCurYear();
        tvMonthDay.setText((String.valueOf(ibCalendarview.getCurMonth()) + "月" + String.valueOf(ibCalendarview.getCurDay()) + "日"));
        tvLunar.setText("今天");
        tvCurrentDay.setText(String.valueOf(ibCalendarview.getCurDay()));

        int year = ibCalendarview.getCurYear();
        int month = ibCalendarview.getCurMonth();

        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
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
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tvLunar.setVisibility(View.VISIBLE);
        tvYear.setVisibility(View.VISIBLE);
        tvMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        tvYear.setText(String.valueOf(calendar.getYear()));
        tvLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
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
}
