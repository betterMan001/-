package com.ly.a316.ly_meetingroommanagement.meetingList.classes;

/*
Date:2018/12/9
Time:18:53
auther:xwd
*/
public class Meeting {
    //会议标题
    private  String title;
    //会议日期
    private  String date;
    //会议发起人
    private  String sponsor;
    //会议发起后经过的时间
    private  String didTime;
    //会议动态数
    private  String messageNum;
    //会议确认参加人数
    private  String partnerNum;
    //会议状态
    private  String meetingStatus;
    public Meeting(String title, String date, String sponsor, String didTime, String messageNum, String partnerNum, String meetingStatus) {
        this.title = title;
        this.date = date;
        this.sponsor = sponsor;
        this.didTime = didTime;
        this.messageNum = messageNum;
        this.partnerNum = partnerNum;
        this.meetingStatus = meetingStatus;
    }
    public Meeting(){}



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    public String getDidTime() {
        return didTime;
    }

    public void setDidTime(String didTime) {
        this.didTime = didTime;
    }

    public String getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(String messageNum) {
        this.messageNum = messageNum;
    }

    public String getPartnerNum() {
        return partnerNum;
    }

    public void setPartnerNum(String partnerNum) {
        this.partnerNum = partnerNum;
    }

    public String getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(String meetingStatus) {
        this.meetingStatus = meetingStatus;
    }
}
