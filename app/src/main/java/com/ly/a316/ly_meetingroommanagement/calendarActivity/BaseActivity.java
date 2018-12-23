package com.ly.a316.ly_meetingroommanagement.calendarActivity;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.calendarActivity.Util.DateTimeInterpreter;
import com.ly.a316.ly_meetingroommanagement.calendarActivity.Util.MonthLoader;
import com.ly.a316.ly_meetingroommanagement.calendarActivity.Util.WeekView;
import com.ly.a316.ly_meetingroommanagement.calendarActivity.Util.WeekViewEvent;
import com.zaaach.toprightmenu.TopRightMenu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：余智强
 * 2018/12/23
 */
public abstract class BaseActivity extends AppCompatActivity implements WeekView.EventClickListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener, MonthLoader.MonthChangeListener {

    private WeekView mWeekView;
    private static final int TYPE_DAY_VIEW = 1;//日视图
    private static final int TYPE_THREE_DAY_VIEW = 2;//三日周视图
    private static final int TYPE_WEEK_VIEW = 3;
    TopRightMenu mToRightMenu;//右上角的菜单栏
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;//默认显示日视图
    @BindView(R.id.morethen)
    ImageView morethen;
@BindView(R.id.today)
    TextView today;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);


        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
    }



    @OnClick({R.id.morethen,R.id.today})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.morethen:
                morethen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mToRightMenu = new TopRightMenu(BaseActivity.this);
                        final List<com.zaaach.toprightmenu.MenuItem> menuItems = new ArrayList<>();
                        menuItems.add(new com.zaaach.toprightmenu.MenuItem("日统计"));
                        menuItems.add(new com.zaaach.toprightmenu.MenuItem("三日统计"));
                        menuItems.add(new com.zaaach.toprightmenu.MenuItem("周统计"));
                        mToRightMenu
                                .setHeight(400)     //默认高度480
                                .setWidth(400)      //默认宽度wrap_content
                                .showIcon(false)     //显示菜单图标，默认为true
                                .dimBackground(true)           //背景变暗，默认为true
                                .needAnimationStyle(true)   //显示动画，默认为true
                                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //动画样式 默认为R.style.TRM_ANIM_STYLE
                                .addMenuList(menuItems)
                                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                                    @Override
                                    public void onMenuItemClick(int position) {
                                        switch (position) {
                                            case 0:
                                                if (mWeekViewType != TYPE_DAY_VIEW) {

                                                    mWeekViewType = TYPE_DAY_VIEW;//更改选中类型
                                                    mWeekView.setNumberOfVisibleDays(1);//让日历只显示一列  通过这个属性更改日历显示的列数

                                                    // Lets change some dimensions to best fit the view.
                                                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                                                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                                }
                                                break;
                                            case 1:
                                                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {

                                                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                                                    mWeekView.setNumberOfVisibleDays(3);

                                                    // Lets change some dimensions to best fit the view.
                                                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                                                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                                                }
                                                break;
                                            case 2:
                                                if (mWeekViewType != TYPE_WEEK_VIEW) {

                                                    mWeekViewType = TYPE_WEEK_VIEW;
                                                    mWeekView.setNumberOfVisibleDays(7);

                                                    // Lets change some dimensions to best fit the view.
                                                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                                                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                                                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                                                }
                                                break;
                                        }

                                    }
                                })
                                .showAsDropDown(morethen, -225, 0);
                    }
                });
                break;
            case R.id.today:
                mWeekView.goToToday();
                break;

        }
    }
    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.main, menu);
          return true;
      }*/
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id) {
            case R.id.action_today://点击了今天的按钮
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view://日视图

                return true;
            case R.id.action_three_day_view://三日视图

                return true;
            case R.id.action_week_view://周视图

                return true;
          *//*  case R.id.action_month_view://月视图
                startActivity(new Intent(this, MonthScheduleActivity.class));
                return true;

            case R.id.action_calendar_view://日程列表
                startActivity(new Intent(this, CalendarScheduleActivity.class));
                return true;*//*
        }

        return super.onOptionsItemSelected(item);
    }
*/
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));//weekday打印得都是 周一 周二这些信息

                return weekday.toUpperCase() + "\n" + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? hour + " PM" : (hour == 0 ? "12 AM" : hour + " AM");//可以更改右边时间的显示
                /*  return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");*/
            }

        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }
}
