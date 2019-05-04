package com.ly.a316.ly_meetingroommanagement.meetting.services;

/*
Date:2019/1/31
Time:19:07
auther:xwd
*/
public interface OrderDetailMeetingService {
    //解锁会议室
    public void unlockRoom(String roomId);
    //预定会议室
    public void bookMeetRoom(String employeeId,String roomId,String beginTime,String endTime,String theme,String content,String attends,String recorder);
    //会议邀请情况反馈
    public void optIn(String mId,String phone,String reason,String type);
    //会议延迟
    public void delayMeeting(String mId);
}
