package com.ly.a316.ly_meetingroommanagement.Schedule.unit.Util;

import java.util.Calendar;
import java.util.List;

/**
 * 作者：余智强
 * 2018/12/23
 */
public interface WeekViewLoader {
    double toWeekViewPeriodIndex(Calendar instance);

    List<? extends WeekViewEvent> onLoad(int periodIndex);
}
