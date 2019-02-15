package com.ly.a316.ly_meetingroommanagement.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.customview.LoadingDialog;

/*
Date:2019/2/10
Time:13:58
auther:xwd
*/
public class DisplayUtils {

    public  static void subThreadToast(Context context, final String message){
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getContext(),message,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
