package com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.support.v4.app.NotificationCompat.Action;
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
import android.graphics.drawable.Icon;
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
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.border.YesReceiver;

import java.util.Calendar;

/**
 * 作者：余智强
 * 2019/2/21
 */
public class End_Service extends Service {

    //通过handler方式实现的定时任务
    private String TAG = "zjc";
    private String time;
    private String title_huiyi;
    int hour, minute;
    String channelId,channelName,start_time,end_time;
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
    NotificationManager manager;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("zjc", "执行了onStartCommand方法");
        Log.i("zjc", hour+" : "+ minute);
        title_huiyi = intent.getStringExtra("title_huiyi");
        start_time = intent.getStringExtra("start_time");
        end_time = intent.getStringExtra("end_time");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = "channelId";
            channelName = "这是用来测试的";

            createNotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH);
        }
        String ho;
        if(hour/10 == 0){
              ho = "0"+String.valueOf(hour);
        }else{
            ho = String.valueOf(hour);
        }
        String min ;
        if(minute/10 == 0){
            min = "0"+String.valueOf(minute);
        }else{
            min = String.valueOf(minute);
        }
        if(ho.equals(start_time) && min.equals(end_time)){
            sendActionNotification(this,manager);
        }




      /*  //if(hour == 17){
            //这里要进行判断，是否是android8.0以上的系统，只有android8.0以上才有这个 渠道设置
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  channelId = "channelId";
                  channelName = "这是用来测试的notification";
                  *//*第三个参数是设置提示等级的，NotificationManagerCompat.IMPORTANCE_HIGH这时最高级，会有系统提示音
                 *//*
                createNotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            builder.setContentTitle("会议结束提醒");
            builder.setContentText("你的会议："+title_huiyi+"将在15分钟后结束，是否延长时间？默认不延长");

            //builder.setNumber(4);//添加未读取通知条数
            //builder.setTimeoutAfter(long timeOut); //通知超时，当超时的时候，系统会自动清除
            Notification notification = builder.build();


        mManager.notify(1, notification);*/
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
         manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    public void sendActionNotification(Context context,NotificationManager nm) {

        //创建通知
        Notification.Builder nb = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nb = new Notification.Builder(context,channelId)
                    //设置通知左侧的小图标
                    .setSmallIcon(R.mipmap.ic_launcher)
                    //设置通知标题
                    .setContentTitle("会议结束提醒")
                    //设置通知内容
                    .setContentText("你的会议："+title_huiyi+"将在15分钟后结束，是否延长时间？\n默认不延长!")
                    //设置点击通知后自动删除通知
                    .setAutoCancel(true)
                    //设置显示时长
                   // .setTimeoutAfter(6000)
                    //设置显示通知时间
                    .setShowWhen(true);

        }
    //创建点击通知 YES 按钮时发送的广播
        Intent yesIntent = new Intent(context, YesReceiver.class);
        yesIntent.setAction("YES");
        PendingIntent yesPendingIntent = PendingIntent.getBroadcast(context,0,yesIntent,0);
        Notification.Action yesActionBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            yesActionBuilder = new Notification.Action.Builder(
                    Icon.createWithResource("", R.mipmap.ic_yes),
                    "YES",
                    yesPendingIntent)
                    .build();
        }
        //创建点击通知 NO 按钮时发送的广播
        Intent noIntent = new Intent(context,YesReceiver.class);
        noIntent.setAction("NO");
        PendingIntent noPendingIntent = PendingIntent.getBroadcast(context,0,noIntent,0);
        Notification.Action noActionBuilder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            noActionBuilder = new Notification.Action.Builder(
                    Icon.createWithResource("", R.drawable.ic_no),
                    "NO",
                    noPendingIntent)
                    .build();
        }
        //为通知添加按钮
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            nb.setActions(yesActionBuilder,noActionBuilder);
        }
        //发送通知
        nm.notify(1,nb.build());
    }
}
