package com.ly.a316.ly_meetingroommanagement.calendarActivity.Util;

import java.util.Calendar;

/**
 * 作者：余智强
 * 2018/12/23
 */
public interface DateTimeInterpreter {
    String interpretDate(Calendar date);
    String interpretTime(int hour);
}
