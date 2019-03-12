package com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.border;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.YanChangDaoImp;

/**
 * 作者：余智强
 * 2019/3/8
 */
public class YesReceiver extends BroadcastReceiver {
    YanChangDaoImp yanChangDaoImp = new YanChangDaoImp();
    String mId;
    // TODO: 2019/3/8 会议延长的网络申请还没做
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("NO")){
            Log.i("zjc","接收到no");
        }else if(intent.getAction().equals("YES")){
            mId = intent.getStringExtra("mId");
            yanChangDaoImp.getAllIndormation(mId);
            Log.i("zjc","接收到yes");
        }else{
            Log.i("zjc","接收到yes");
        }
    }

}
