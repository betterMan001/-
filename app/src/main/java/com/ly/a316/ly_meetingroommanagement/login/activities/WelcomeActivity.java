package com.ly.a316.ly_meetingroommanagement.login.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.MainActivity;
import com.ly.a316.ly_meetingroommanagement.nim.DemoCache;

public class WelcomeActivity extends BaseActivity {
    //本地保存
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        DemoCache.setMainTaskLaunching(true);
        MyApplication.removeActivity(this);
        MyApplication.finishAllActivities();
        //开线程,延迟跳转
        pref= PreferenceManager.getDefaultSharedPreferences(getApplication());
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if(TextUtils.isEmpty(pref.getString("token",""))){
                    intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                }else {
                    MyApplication.setId((pref.getString("id","")));
                    intent=new Intent(WelcomeActivity.this,MainActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}