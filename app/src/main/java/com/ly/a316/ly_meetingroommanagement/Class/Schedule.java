package com.ly.a316.ly_meetingroommanagement.Class;

/**
 * 作者：余智强
 * ${Date}
 */
public class Schedule {
    private String dateTime;//日期
    private String content;//内容
    private String remind;//提醒
    public Schedule(String dateTime, String content,String remind) {
        this.dateTime = dateTime;
        this.content = content;
        this.remind = remind;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
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
