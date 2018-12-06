package com.ly.a316.ly_meetingroommanagement.classes;

/**
 * 作者：余智强
 * ${Date}
 * 日程类
 */
public class Schedule {
    private String dateTime;//日期
    private String content;//内容

    public Schedule(String dateTime, String content) {
        this.dateTime = dateTime;
        this.content = content;

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
