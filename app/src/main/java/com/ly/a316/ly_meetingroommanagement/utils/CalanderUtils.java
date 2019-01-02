package com.ly.a316.ly_meetingroommanagement.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.classes.EventModel;
import com.ly.a316.ly_meetingroommanagement.classes.Schedule;
import com.netease.nim.uikit.common.util.sys.TimeUtil;

import java.lang.ref.WeakReference;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：余智强
 * 2018/12/28
 */
public class CalanderUtils {
    //这些对应的都是android手机里的数据库
    public static String calanderURL = "content://com.android.calendar/calendars";
    public static String calanderEventURL = "content://com.android.calendar/events";
    public static String calanderRemiderURL = "content://com.android.calendar/reminders";
    public static String calanderattendsURL = "content://com.android.calendar/attendees";
    public final static int EXTERNAL_CALENDAR_REQ_CODE = 10;
    public final static String EVENT = "EVENT";

    private static CalanderUtils sUtils;
    private static String mepeople ="2";
    private Map<String, int[]> sAllHolidays = new HashMap<>();
    private Map<String, List<Integer>> sMonthTaskHint = new HashMap<>();

    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:

                return -1;
        }
    }

    /**
     * android 6.0 以上申请权限
     */
    public static boolean requestPermission(Activity activity) {
        WeakReference<Activity> activityWeakReference = new WeakReference<Activity>(activity);
        Activity useActivity = activityWeakReference.get();
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(activity.getApplicationContext(), Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(useActivity,
                    Manifest.permission.READ_CALENDAR) || ActivityCompat.shouldShowRequestPermissionRationale(useActivity,
                    Manifest.permission.WRITE_CALENDAR)) {
                Toast.makeText(useActivity, "请求权限", Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions(useActivity,
                        new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                        EXTERNAL_CALENDAR_REQ_CODE);
            }
            return false;
        } else {
            //申请成功，进行相应操作
            return true;
        }

    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得两个日期距离几周
     *
     * @return
     */
    public static int getWeeksAgo(int lastYear, int lastMonth, int lastDay, int year, int month, int day) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(lastYear, lastMonth, lastDay);
        end.set(year, month, day);
        int week = start.get(Calendar.DAY_OF_WEEK);
        start.add(Calendar.DATE, -week);
        week = end.get(Calendar.DAY_OF_WEEK);
        end.add(Calendar.DATE, 7 - week);
        float v = (end.getTimeInMillis() - start.getTimeInMillis()) / (3600 * 1000 * 24 * 7 * 1.0f);
        return (int) (v - 1);
    }

    /**
     * 获得两个日期距离几个月
     *
     * @return
     */
    public static int getMonthsAgo(int lastYear, int lastMonth, int year, int month) {
        return (year - lastYear) * 12 + (month - lastMonth);
    }

    public static int getWeekRow(int year, int month, int day) {
        int week = getFirstDayWeek(year, month);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int lastWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (lastWeek == 7)
            day--;
        return (day + week - 1) / 7;
    }


    public static int getMonthRows(int year, int month) {
        int size = getFirstDayWeek(year, month) + getMonthDays(year, month) - 1;
        return size % 7 == 0 ? size / 7 : (size / 7) + 1;
    }

    /**
     * 得到上一天的时间
     */
 /*   @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate getYesterday(int year, int month, int day) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.set(year, month - 1, day);//月份是从0开始的，所以11表示12月

        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int inDay = ca.get(Calendar.DATE);
        ca.set(Calendar.DATE, inDay - 1);
        return new LocalDate(ca);
    }*/


    /**
     * 获取某月某日的日历
     *
     * @param year
     * @param month
     */
    public static List<EventModel> getCalendarEvent(Context context, int year, int month,int maxday) throws Exception {
        List<EventModel> eventModels = new ArrayList<>();
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month-1 , 1, 0, 0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month-1 , maxday, 0, 0);
        String selection = "((dtstart >= " + beginTime.getTimeInMillis() + ") AND (dtend <= " + endTime.getTimeInMillis() + "))";
        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CalanderUtils.calanderEventURL), null,
                selection, null, null);
        if (eventCursor.getCount() > 0) {
            if (eventCursor.moveToFirst()) {
                do {
                    String dtstart = eventCursor.getString(eventCursor.getColumnIndex("dtstart"));//日程事件开始时间，是13位字符串
                    String timeStart = TimeUtil.timeFormatStr(dtstart);//将日程时间改成yyyy-MM-dd hh:mm:ss形式
                    String dtend = eventCursor.getString(eventCursor.getColumnIndex("dtend"));//日程事件结束时间
                    String timeEnd = TimeUtil.timeFormatStr(dtend);//将日程时间改成yyyy-MM-dd hh:mm:ss形式
                    //30 29 12 12 2018 2018

                    int startday = Integer.parseInt(timeStart.substring(8, 10));//截取日程事件的开始时间的 day， dd
                    int endday = Integer.parseInt(timeEnd.substring(8, 10));//截取日程事件的结束时间的 day， dd
                    int startMonth = Integer.parseInt(timeStart.substring(5, 7));//截取日程事件的开始时间的 月， mm
                    int endMonth = Integer.parseInt(timeEnd.substring(5, 7));//截取日程事件的结束时间的 月， mm
                    int startYear = Integer.parseInt(timeStart.substring(0, 4));//截取日程事件的开始时间的 年， yyyy
                    int endYear = Integer.parseInt(timeEnd.substring(0, 4));//截取日程事件的结束时间的 年， yyyy

                    int sthour = Integer.parseInt(timeStart.substring(11, 13));//截取日程事件的开始时间的 day， dd
                    int stmini = Integer.parseInt(timeStart.substring(14, 16));//截取日程事件的开始时间的 day， dd
                    int ehour =  Integer.parseInt(timeEnd.substring(11, 13));//截取日程事件的开始时间的 day， dd
                    int etmini = Integer.parseInt(timeEnd.substring(14, 16));//截取日程事件的开始时间的 day， dd
                    int day = TimeUtil.DateCompareDiffDay(timeEnd, timeStart);//比较日程事件开始和结束时间，看看是否跨日了，跨日的那些天都需要特殊处理*/
                    EventModel eventModel = new EventModel(startYear,startMonth,startday,endYear,endMonth,endday,sthour,stmini,ehour,etmini);
                    eventModels.add(eventModel);
                } while (eventCursor.moveToNext());
            }

        }
        eventCursor.close();
        return eventModels;
    }

    String s;
    static String dsd = "sss";
    public static List<Schedule> getCalendarEventByDay(Context context, int year, int month, int dayy) throws Exception {
        List<Schedule> eventlist = new ArrayList<>();
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month - 1, dayy - 1, 24, 0, 0);
        Calendar endTime = Calendar.getInstance();
        endTime.set(year, month - 1, dayy + 1, 0, 0, 0);
        String selection = "( (dtstart > " + beginTime.getTimeInMillis() + ") AND (dtend <" + endTime.getTimeInMillis() + "))";

        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CalanderUtils.calanderEventURL), null,
                selection, null, null);
        if (eventCursor.getCount() > 0) {
            if (eventCursor.moveToFirst()) {
                do {
                    String calendar_id = eventCursor.getString(eventCursor.getColumnIndex("calendar_id"));//每个事件特有的id
                    String location = eventCursor.getString(eventCursor.getColumnIndex("eventLocation"));//地点
                    String event_idd = eventCursor.getString(eventCursor.getColumnIndex("_id"));//每个事件特有的id
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));//日程事件标题
                    String description = eventCursor.getString(eventCursor.getColumnIndex("description"));//日程事件描术
                    String dtstart = eventCursor.getString(eventCursor.getColumnIndex("dtstart"));//日程事件开始时间，是13位字符串
                    String eventLOCATION = eventCursor.getString(eventCursor.getColumnIndex("allday"));//日程事件的位置，发现没有这个字段
                    String address = eventCursor.getString(eventCursor.getColumnIndex("eventlocation"));//日程事件的位置，发现没有这个字段
                    String duration = eventCursor.getString(eventCursor.getColumnIndex("duration"));//持续时间
                    String allday = eventCursor.getString(eventCursor.getColumnIndex("allday"));//是否全天提醒

                    String alerT_time = "-1";//代表永不
                    String timeStart = TimeUtil.timeFormatStr(dtstart);//将日程时间改成yyyy-MM-dd hh:mm:ss形式
                    String dtend = eventCursor.getString(eventCursor.getColumnIndex("dtend"));//日程事件结束时间
                    String timeEnd = TimeUtil.timeFormatStr(dtend);//将日程时间改成yyyy-MM-dd hh:mm:ss形式
                    String startTime = timeStart.substring(11, 16);//截取日程事件的开始时间的 时和分， hh:mm
                    String endtime = timeEnd.substring(11, 16);//截取日程事件的结束时间的 时和分， hh:mm
                    try {
                        String selections = "((event_id == " + Integer.valueOf(eventCursor.getString(eventCursor.getColumnIndex("_id"))) + "))";
                        @SuppressLint("MissingPermission")
                        Cursor eventCursors = context.getContentResolver().query(CalendarContract.Attendees.CONTENT_URI, null,
                                selections, null, null);
                        if (eventCursors.getCount() > 0) {
                            //参会人员
                            mepeople = eventCursors.getString(eventCursors.getColumnIndex("attendeeName"));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        String selections = "((event_id == " + Integer.valueOf(eventCursor.getString(eventCursor.getColumnIndex("_id"))) + "))";
                        @SuppressLint("MissingPermission")
                        Cursor eventCursorss = context.getContentResolver().query(CalendarContract.Reminders.CONTENT_URI, null,
                                selections, null, null);
                        if (eventCursorss.getCount() > 0) {
                            if (eventCursorss.moveToFirst()) {
                                do{
                                    alerT_time = eventCursorss.getString(eventCursorss.getColumnIndex("minutes"));//在事件发生之前多少分钟进行提醒
                                    Log.i("zjc", "alerT_time= " + alerT_time);
                                } while (eventCursorss.moveToNext());
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    //30 29 12 12 2018 2018

                    int startday = Integer.parseInt(timeStart.substring(8, 10));//截取日程事件的开始时间的 day， dd
                    int endday = Integer.parseInt(timeEnd.substring(8, 10));//截取日程事件的结束时间的 day， dd
                    int startMonth = Integer.parseInt(timeStart.substring(5, 7));//截取日程事件的开始时间的 月， mm
                    int endMonth = Integer.parseInt(timeEnd.substring(5, 7));//截取日程事件的结束时间的 月， mm
                    int startYear = Integer.parseInt(timeStart.substring(0, 4));//截取日程事件的开始时间的 年， yyyy
                    int endYear = Integer.parseInt(timeEnd.substring(0, 4));//截取日程事件的结束时间的 年， yyyy
                    int day = TimeUtil.DateCompareDiffDay(timeEnd, timeStart);//比较日程事件开始和结束时间，看看是否跨日了，跨日的那些天都需要特殊处理*/

                    if (mepeople.equals("2")) {
                        Schedule schedule = new Schedule(startTime, endtime, "[日程]", location, "自己", eventTitle, description, event_idd,startYear,startMonth,startday,alerT_time);
                        eventlist.add(schedule);
                    } else {
                        Schedule schedule = new Schedule(startTime, endtime, "[会议]", location, mepeople, eventTitle, description, event_idd,startYear,startMonth,startday,alerT_time);
                        eventlist.add(schedule);
                    }
                } while (eventCursor.moveToNext());
            }

        }
        eventCursor.close();
        return eventlist;
    }

}