package com.ly.a316.ly_meetingroommanagement;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

/*
Date:2018/12/4
Time:17:40
auther:xwd
*/
public class MyApplication extends Application {

    //上下文环境
    private static Context context;
    //登录相关信息
    private static String id="";
    private static String token="";
    //本地保存数据
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static List<Activity> activityList=new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        context=getApplicationContext();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();
    }
    //添加活动
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    //关闭所有活动
    public static void finishAllActivities(){
        for(Activity activity:activityList){
            if(!activity.isFinishing()){
                activity.finish();
            }
        }
    }

    public static Context getContext() {
        return context;
    }

    public static String getId() {

        return id;
    }

    public static void setId(String id) {
        MyApplication.id = id;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MyApplication.token = token;
    }
}
