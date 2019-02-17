package com.ly.a316.ly_meetingroommanagement.meetingList.services;

/*
Date:2019/2/12
Time:18:37
auther:xwd
*/
public interface AttenderService {
    public void attenders(String mid);
    public void addAttender(String attender,String mid);
    public void attendersForDetailActivity(String mid);
}
