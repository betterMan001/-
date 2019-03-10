package com.ly.a316.ly_meetingroommanagement.Schedule.Activity;

import android.os.Bundle;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.Schedule.unit.Util.WeekViewEvent;
import com.ly.a316.ly_meetingroommanagement.Schedule.Classes.EventModel;
import com.ly.a316.ly_meetingroommanagement.Schedule.unit.Util.CalanderUtils;
import com.ly.a316.ly_meetingroommanagement.calendar_fra.object.Day_Object;
import com.ly.a316.ly_meetingroommanagement.meetting.models.MettingPeople;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OneDayCountActivity extends BaseActivity {

    /* @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_one_day_count);
     }*/
    int colors[] = {R.color.one, R.color.two, R.color.three, R.color.three, R.color.four, R.color.six, R.color.collu, R.color.miss_blue};
    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        WeekViewEvent event = new WeekViewEvent();
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.set(java.util.Calendar.YEAR, Integer.valueOf(newYear));
        c.set(java.util.Calendar.MONTH, Integer.valueOf(newMonth) - 1);
        int maxDay = c.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
     /*   List<EventModel> calendarEvent = null;
        try {
            calendarEvent = CalanderUtils.getCalendarEvent(this,newYear,newMonth,maxDay);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        List<Day_Object> day_list = new ArrayList<>();

        Bundle MoonBuddle = getIntent().getExtras();
        day_list = (List<Day_Object>) MoonBuddle.getSerializable("list");
        for (int i = 0; i < day_list.size(); i++) {
            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getStartTime().substring(11, 13)))));
            startTime.set(Calendar.MINUTE, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getStartTime().substring(14, 16)))));
            startTime.set(Calendar.MONTH, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getStartTime().substring(5, 7))))-1);
            startTime.set(Calendar.DAY_OF_MONTH, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getStartTime().substring(8, 10)))));
            startTime.set(Calendar.YEAR, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getStartTime().substring(0, 4)))));

            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getEndTime().substring(11, 13)))));
            endTime.set(Calendar.MINUTE, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getEndTime().substring(14, 16)))));
            endTime.set(Calendar.MONTH, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getEndTime().substring(5, 7))))-1);
            endTime.set(Calendar.DAY_OF_MONTH, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getEndTime().substring(8, 10)))));
            endTime.set(Calendar.YEAR, Integer.valueOf(panduan(Integer.valueOf(day_list.get(i).getEndTime().substring(0, 4)))));
            event = new WeekViewEvent(i, getEventTitle(startTime, endTime), startTime, endTime);

            int asa = (int) (0 + Math.random() * (7 - 0 + 1));
            event.setColor(getResources().getColor(colors[asa]));

            events.add(event);
        }



       /*
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, 11);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, 11);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime,endTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, 11);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, 11);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime,endTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, 11);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 8);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, 11);
        event = new WeekViewEvent(10, getEventTitle(startTime,endTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);*/
/*
        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 4);
        startTime.set(Calendar.MINUTE, 20);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 5);
        endTime.set(Calendar.MINUTE, 0);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 2);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(2, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth - 1);
        startTime.set(Calendar.YEAR, newYear);
        startTime.add(Calendar.DATE, 1);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        endTime.set(Calendar.MONTH, newMonth - 1);
        event = new WeekViewEvent(3, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 15);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(4, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH,
                startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.HOUR_OF_DAY, 15);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        //AllDay event
        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 23);
        event = new WeekViewEvent(7, getEventTitle(startTime),null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 8);
        startTime.set(Calendar.HOUR_OF_DAY, 2);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 10);
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        event = new WeekViewEvent(8, getEventTitle(startTime),null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        // All day event until 00:00 next day
        startTime = Calendar.getInstance();

        startTime.set(Calendar.DAY_OF_MONTH, 10);
        startTime.set(Calendar.HOUR_OF_DAY, 0);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.DAY_OF_MONTH, 11);
        event = new WeekViewEvent(8, getEventTitle(startTime), null, startTime, endTime, true);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);*/

        return events;
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
}
