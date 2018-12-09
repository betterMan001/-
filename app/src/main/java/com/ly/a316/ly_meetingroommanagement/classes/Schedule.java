package com.ly.a316.ly_meetingroommanagement.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：余智强
 * ${Date}
 * 日程类
 */
public class Schedule {
    static public List<Schedule> list=new ArrayList<>();
    private String dateTime;//日期
    private String content;//内容

    public Schedule(String dateTime, String content) {
        this.dateTime = dateTime;
        this.content = content;

    }

    private String alert_startTime;//提醒的开始时间
    private String alert_endtime;//结束的时间
    private String attribute;//事物属性

    public Schedule(String dateTime, String content,String alert_startTime,String alert_endtime,String attribute) {
        this.dateTime = dateTime;
        this.content = content;
        this.alert_startTime = alert_startTime;
        this.alert_endtime = alert_endtime;
        this.attribute = attribute;
    }

    public String getAlert_startTime() {
        return alert_startTime;
    }

    public void setAlert_startTime(String alert_startTime) {
        this.alert_startTime = alert_startTime;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getAlert_endtime() {
        return alert_endtime;
    }

    public void setAlert_endtime(String alert_endtime) {
        this.alert_endtime = alert_endtime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
