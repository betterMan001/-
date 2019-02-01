package com.ly.a316.ly_meetingroommanagement.meetting.services;

/*
Date:2019/1/31
Time:19:07
auther:xwd
*/
public interface OrderDetailMeetingService {
    public void unlockRoom(String roomId);
    public void bookMeetRoom(String employeeId,String roomId,String beginTime,String endTime,String theme,String content,String attends,String recorder);
    public void optIn(String mId,String phone,String reason,String type);
}
