package com.ly.a316.ly_meetingroommanagement.calendar_fra.Dao;

/**
 * 作者：余智强
 * 2019/3/9
 */
public interface GteOneDay_RC {
    void getOneDayinformation(String leadersid,String date);//具体某天的
    void getAllIndormation(String leadersid);//所以的
    void deleteSch(String sid);
    void finish(String event_id);
}
