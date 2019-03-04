package com.ly.a316.ly_meetingroommanagement.meetingList.models;

/*
Date:2018/12/9
Time:18:53
auther:xwd
*/
public class Meeting {
    //持续时间
    private  String duration;
    //会议日期
    private  String image;
    //会议发起人
    private  String initiator;
    //会议名称
    private  String name;
    //会议状态
    private  String state;
    //会议的开始时间
    private  String begin;
    //会议参加人的比例
    private  String ratio;
    private String address;
    //会议内容
    public String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    //会议Id
    private String mId;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
