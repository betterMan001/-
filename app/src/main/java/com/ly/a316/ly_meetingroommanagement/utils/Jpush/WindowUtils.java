package com.ly.a316.ly_meetingroommanagement.utils.Jpush;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetting.services.imp.OrderDetailMeetingServiceImp;

import org.w3c.dom.Text;


/*
Date:2019/1/28
Time:17:19
auther:xwd
*/
public class WindowUtils {
    private static final String LOG_TAG = "WindowUtils";
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;
    public static Boolean isShown = false;
    public static Boolean isReject=false;
    public static String[] spit;
    /*
    *判断这个自定义消息的类型：
    * false:会议邀请
    * true:延长会议
    */
    public static Boolean messageType=false;
    /**
     * 显示弹出框
     *
     */
    private static Handler mhandler=new Handler() {
        @Override
        public void handleMessage(Message msg){
        super.handleMessage(msg);
        //被邀请人做出了自己的决定，并发送给了服务器，然后更新
            if(msg.what==12){
               //弹Toast
                String temp=(isReject==false)?"同意":"拒绝";
                Toast.makeText(MyApplication.getContext(),"您已经"+temp+"该会议!",Toast.LENGTH_LONG).show();
                //关闭悬浮窗
                WindowUtils.hidePopupWindow();
            }else if(msg.what==13){
                Toast.makeText(MyApplication.getContext(),"成功为您延长15分钟",Toast.LENGTH_LONG).show();
                //关闭悬浮窗
                WindowUtils.hidePopupWindow();
            }
        }
    };
    public static void showPopupWindow(final Context context) {
        if (isShown) {
            Log.i(LOG_TAG, "return cause already shown");
            return;
        }
        isShown = true;
        spit=MyReceiver.customString.split(",");
        Log.i(LOG_TAG, "showPopupWindow");
        // 获取应用的Context
        mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);
        mView = setUpView(context);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        //8.0后要适配类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        mWindowManager.addView(mView, params);
        Log.i(LOG_TAG, "add view");
    }
    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        Log.i(LOG_TAG, "hide " + isShown + ", " + mView);
        if (isShown && null != mView) {
            Log.i(LOG_TAG, "hidePopupWindow");
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }
    private static View setUpView(final Context context) {
        Log.i(LOG_TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow,
                null);
        final TextView titleView=(TextView) view.findViewById(R.id.true_title);
       final TextView tintView=(TextView) view.findViewById(R.id.content);
        tintView.setText(spit[0]);
        titleView.setText(MyReceiver.title);
        //通过标题进行类型判断
        if("会议延迟".equals(MyReceiver.title))
            messageType=true;
        else
            messageType=false;
        Button positiveBtn = (Button) view.findViewById(R.id.positiveBtn);
        //隐藏的提示字
        final     TextView hideTint=view.findViewById(R.id.hideTint);
        //包裹写拒绝内容的布局
        final      LinearLayout hideContentll=view.findViewById(R.id.hide_ll);
        final  EditText reject_content=view.findViewById(R.id.reject_content);
        //第一个button布局
       final LinearLayout one_group=view.findViewById(R.id.one_group);
        //隐藏的button布局
     final   LinearLayout second_group=view.findViewById(R.id.second_group);
        //隐藏的取消按钮
        final     Button hideNegativeBtn=view.findViewById(R.id.hideNegativeBtn);
        //隐藏的确定按钮
        final    Button hide_PositiveBtn=view.findViewById(R.id.hide_PositiveBtn);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "ok on click");
                // 打开安装包
                if(messageType)
                    //延迟会议接口
                    new OrderDetailMeetingServiceImp().delayMeeting(spit[1]);
                else{
                    isReject=false;
                    new OrderDetailMeetingServiceImp().optIn(spit[1],MyApplication.getId(),"","1");
                }

            }
        });
        final Button negativeBtn = (Button) view.findViewById(R.id.negativeBtn);
        negativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "cancel on click");
                if(messageType==false){
                    //WindowUtils.hidePopupWindow();
                    //隐藏现在的布局，显示隐藏的拒绝的布局

                    tintView.setVisibility(View.GONE);
                    //显示新的布局
                    hideTint.setVisibility(View.VISIBLE);
                    hideContentll.setVisibility(View.VISIBLE);
                    second_group.setVisibility(View.VISIBLE);
                    one_group.setVisibility(View.GONE);
                }
                else
                    WindowUtils.hidePopupWindow();
            }
        });
        hideNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏现在的布局，显示隐藏的拒绝的布局
                hideTint.setVisibility(View.GONE);
                hideContentll.setVisibility(View.GONE);
                //显示新的布局
                one_group.setVisibility(View.VISIBLE);
                tintView.setVisibility(View.VISIBLE);
                second_group.setVisibility(View.GONE);
            }
        });
        hide_PositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isReject=true;
                new OrderDetailMeetingServiceImp().optIn(spit[1],MyApplication.getId(),reject_content.getText().toString(),"0");
            }
        });
        // 点击窗口外部区域可消除
        // 这点的实现主要将悬浮窗设置为全屏大小，外层有个透明背景，中间一部分视为内容区域
        // 所以点击内容区域外部视为点击悬浮窗外部
        final View popupWindowView = view.findViewById(R.id.popup_window);// 非透明的内容区域
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(LOG_TAG, "onTouch");
                int x = (int) event.getX();
                int y = (int) event.getY();
                Rect rect = new Rect();
                popupWindowView.getGlobalVisibleRect(rect);
                if (!rect.contains(x, y)) {
                    WindowUtils.hidePopupWindow();
                }
                Log.i(LOG_TAG, "onTouch : " + x + ", " + y + ", rect: "
                        + rect);
                return false;
            }
        });
        // 点击back键可消除
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        WindowUtils.hidePopupWindow();
                        return true;
                    default:
                        return false;
                }
            }
        });
        return view;
    }
    public static void inviteCallBack(final String result){
        if("success".equals(result)==true){
            mhandler.sendEmptyMessage(12);
        }else{

        }
    }
    public static void delayCallBack(final String result){
        if("1".equals(result)){
            mhandler.sendEmptyMessage(13);
        }
    }
}
