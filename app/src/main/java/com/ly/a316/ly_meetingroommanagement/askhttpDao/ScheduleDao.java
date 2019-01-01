package com.ly.a316.ly_meetingroommanagement.askhttpDao;

/**
 * 作者：余智强
 * 2018/12/29
 */
public interface ScheduleDao {
    //新建日程


    void addSchedule(String eId, String begin, String end, String theme, String meetType, String context, String address, String remind);


}
