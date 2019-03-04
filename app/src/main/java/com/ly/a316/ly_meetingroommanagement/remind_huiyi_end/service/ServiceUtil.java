package com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 作者：余智强
 * 2019/2/23
 */
public class ServiceUtil {
    private final static String ServiceNname = "com.example.h2.TimerService";
    public static void startAMService(Context context){
        Log.i("zjc", "invokeTimerPOIService wac called.." );
        PendingIntent alarmSender = null;
        Intent startIntent = new Intent(context, End_Service.class);
        startIntent.setAction(ServiceNname);

        try {

            alarmSender = PendingIntent.getService(context, 0, startIntent, 0);
        } catch (Exception e) {
            Log.i("zjc", "failed to start " + e.toString());
        }
        AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 5*1000, alarmSender);
    }
}
