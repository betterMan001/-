package com.ly.a316.ly_meetingroommanagement.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.customview.LoadingDialog;
import com.ly.a316.ly_meetingroommanagement.utils.Jpush.MyReceiver;

/**
 *  描述：基本类 实现沉浸式效果
 *  作者： 余智强
 *  创建时间：12/3 14:13
 */
public class BaseActivity  extends AppCompatActivity{
    public LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        }
        // ImmersionBar.with(this).init();
        loadingDialog= LoadingDialog.getInstance(this);
        //添加到活动列表
        MyApplication.addActivity(this);
    }
    protected void initImmersionBar() {
        //在BaseActivity里初始化
        ImmersionBar.with(this).navigationBarColor(R.color.white).init();
    }
    protected boolean isImmersionBarEnabled() {
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        //移除活动
        MyApplication.removeActivity(this);
    }
    //用于在回调函数中显示Toast
    public  void subThreadToast(final String message){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getContext(),message,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
