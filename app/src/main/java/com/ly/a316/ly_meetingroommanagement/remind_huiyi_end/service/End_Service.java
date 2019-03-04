package com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.customview.LoadingDialog;
import com.ly.a316.ly_meetingroommanagement.endActivity.activity.End_Activity;
import com.ly.a316.ly_meetingroommanagement.main.MainActivity;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.activity.Ceshi;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.border.DataChangeReceiver;

import java.util.Calendar;

/**
 * 作者：余智强
 * 2019/2/21
 */
public class End_Service extends Service {

    //通过handler方式实现的定时任务
    private String TAG = "zjc";
    private String time;
    int hour, minute;
    String channelId,channelName;
    private NotificationManager mManager;
    @Override
    public void onCreate() {
        super.onCreate();
        time = getTime();
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Log.i(TAG, time);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "UploadPOIService onDestroy here.... ");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("zjc", "执行了onStartCommand方法");
        Log.i("zjc", hour+" : "+ minute);

        //if(hour == 17){
            //这里要进行判断，是否是android8.0以上的系统，只有android8.0以上才有这个 渠道设置
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  channelId = "channelId";
                  channelName = "这是用来测试的notification";
                  /*第三个参数是设置提示等级的，NotificationManagerCompat.IMPORTANCE_HIGH这时最高级，会有系统提示音
                 */
                createNotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            builder.setContentTitle("title");
            builder.setContentText("contentText");
            //builder.setNumber(4);//添加未读取通知条数
            //builder.setTimeoutAfter(long timeOut); //通知超时，当超时的时候，系统会自动清除
            Notification notification = builder.build();

            mManager.notify(1, notification);
      //s  }
        return super.onStartCommand(intent, flags, startId);
    }

    public String getTime() {
        final Calendar c = Calendar.getInstance();
          hour = c.get(Calendar.HOUR_OF_DAY);
          minute = c.get(Calendar.MINUTE);

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
        stopSelf();
        return time;
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);//设置是否显示未读取的通知数目，true显示 这个也是只有8.0的系统才有的功能
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

}
