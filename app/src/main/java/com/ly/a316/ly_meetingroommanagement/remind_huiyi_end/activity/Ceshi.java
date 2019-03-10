package com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service.End_Service;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service.ServiceUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Ceshi extends AppCompatActivity {

    @BindView(R.id.start_service)
    Button startService;
    @BindView(R.id.end_service)
    Button endService;

    private final int TIME = 5*1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ceshi2);
        ButterKnife.bind(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick({R.id.start_service, R.id.end_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_service:
                /*
                  通过注册广播的方式监听事件变化
                  不足：当app处于后台的时候，android8.0的手机无法继续监听广播
                  startService(intent);
                 */
                //通过Handler实现定时任务
                Log.i("zjc","点击了start");
                sendTimeService(true);
                break;
            case R.id.end_service:
                Log.i("zjc","点击了end");
                handler.removeCallbacks(runnable);
                break;
        }
    }




  // 通过Handler实现定时任务
   Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(Ceshi.this,End_Service.class);
            startService(intent);
            handler.postDelayed(runnable,TIME);
        }
    };
    private void sendTimeService(boolean idHandler){
        if(idHandler){
            handler.postDelayed(runnable,TIME);
        }else{
            ServiceUtil.startAMService(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

}
