package com.ly.a316.ly_meetingroommanagement.calendar_fra.object;

/**
 * 作者：余智强
 * 2019/3/9
 */
public class Day_Object {
    String address;
    String mId;//会议发起人的id
    String remark;//备注
    String leaders;//发起人姓名

    String content;//内容
    String noId;//日程id
    String remind;//是否设置了提醒
    String meetType;//是日程还是会议
    String startTime;
    String endTime;

    String leadersId;//发起人电话

    public Day_Object(String address, String mId, String remark, String leaders , String content, String noId, String remind, String meetType, String startTime, String endTime , String leadersId) {
        this.address = address;
        this.mId = mId;
        this.remark = remark;
        this.leaders = leaders;


        this.content = content;
        this.noId = noId;
        this.remind = remind;
        this.meetType = meetType;
        this.startTime = startTime;
        this.endTime = endTime;

        this.leadersId = leadersId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLeaders() {
        return leaders;
    }

    public void setLeaders(String leaders) {
        this.leaders = leaders;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNoId() {
        return noId;
    }

    public void setNoId(String noId) {
        this.noId = noId;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getMeetType() {
        return meetType;
    }

    public void setMeetType(String meetType) {
        this.meetType = meetType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getLeadersId() {
        return leadersId;
    }

    public void setLeadersId(String leadersId) {
        this.leadersId = leadersId;
    }
}
