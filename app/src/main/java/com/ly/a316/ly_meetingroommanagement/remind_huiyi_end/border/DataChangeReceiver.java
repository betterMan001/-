package com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.border;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service.End_Service;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 作者：余智强
 * 2019/2/21
 */
public class DataChangeReceiver extends BroadcastReceiver {
    String time;

    @Override
    public void onReceive(Context context, Intent intent) {
       if (intent.ACTION_TIME_TICK.equals(intent.getAction())) {
            get_alarm_bain(context);
        } else if (intent.ACTION_TIME_CHANGED.equals(intent.getAction())) {

        }
    }

    public String updateTime() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        boolean is24hFormart = true;
        if (!is24hFormart && hour >= 12) {
            hour = hour - 12;
        }

        String time = "";
        if (hour >= 10) {
            time += Integer.toString(hour);
        } else {
            time += "0" + Integer.toString(hour);
        }
        time += ":";

        if (minute >= 10) {
            time += Integer.toString(minute);
        } else {
            time += "0" + Integer.toString(minute);
        }

        return time;
    }


    void get_alarm_bain(Context context) {
        time = updateTime();//每一分钟更新时间
        Log.i("zjc", time);
        String CHANNEL_ID = "my_channel_01";

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notification = new Notification.Builder(context)
                    //设置通知的标题
                    .setContentTitle("This is content title")
                    // 设置通知的详细信息
                    .setContentText("打到皇家马德里")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setChannelId(CHANNEL_ID)
                    .build();
        }
        manager.notify(1, notification);

    }

}
