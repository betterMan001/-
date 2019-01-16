package com.ly.a316.ly_meetingroommanagement.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.FacePack.DetecterActivity;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingListActivity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.WriteconditionActivity;

public class PopupMenuUtil {
    public static PopupMenuUtil getInstance() {
        return MenuUtilHolder.INSTANCE;
    }

    private static class MenuUtilHolder {
        public static PopupMenuUtil INSTANCE = new PopupMenuUtil();
    }

    private View rootVew;
    private PopupWindow popupWindow;
    private RelativeLayout rlClick;
    private ImageView ivBtn;
    private LinearLayout pop_reserve, pop_notice, pop_liebiao,pop_openDoor;
    float animatorProperty[] = null;
    int top = 0;
    int bottom = 0;

    private void _createView(final Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootVew = layoutInflater.inflate(R.layout.popup_menu, null);
        popupWindow = new PopupWindow(rootVew,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        rootVew.setSelected(true);

        popupWindow.setFocusable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());


        popupWindow.setOutsideTouchable(true);

        if (animatorProperty == null) {
            top = dip2px(context, 310);
            bottom = dip2px(context, 210);
            animatorProperty = new float[]{bottom, 60, -30, -20 - 10, 0};
        }

        initLayout(context);
    }

    private void initLayout(Context context) {
        rlClick = (RelativeLayout) rootVew.findViewById(R.id.pop_rl_click);
        ivBtn = (ImageView) rootVew.findViewById(R.id.pop_iv_img);//那个十字架
        pop_reserve = (LinearLayout) rootVew.findViewById(R.id.pop_reserve);
        pop_notice = (LinearLayout) rootVew.findViewById(R.id.pop_notice);
        pop_liebiao = (LinearLayout) rootVew.findViewById(R.id.pop_liebiao);
        pop_openDoor = rootVew.findViewById(R.id.pop_openDoor);

        rlClick.setOnClickListener(new MViewClick(0, context));

        pop_reserve.setOnClickListener(new MViewClick(1, context));
        pop_notice.setOnClickListener(new MViewClick(2, context));
        pop_liebiao.setOnClickListener(new MViewClick(3, context));
        pop_openDoor.setOnClickListener(new MViewClick(4,context));
    }

    private class MViewClick implements View.OnClickListener {

        public int index;
        public Context context;

        public MViewClick(int index, Context context) {
            this.index = index;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if (index == 0) {
                //加号按钮点击之后的执行
                _rlClickAction();
            } else {
                showToast(context,  index);
            }
        }
    }

    public void _rlClickAction() {
        if (ivBtn != null && rlClick != null) {

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivBtn, "rotation", 135f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.start();

            _closeAnimation(pop_reserve, 300, top);
            _closeAnimation(pop_notice, 200, top);
            _closeAnimation(pop_liebiao, 200, top);

            _closeAnimation(pop_openDoor, 300, top);
            rlClick.postDelayed(new Runnable() {
                @Override
                public void run() {
                    _close();
                }
            }, 300);

        }
    }

    public void _close() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    private void _closeAnimation(View view, int duration, int next) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", 0f, next);
        anim.setDuration(duration);
        anim.start();
    }

    Toast toast = null;

    private void showToast(Context context, int str) {
        Intent intent = new Intent();
        //点击相应项跳转到相应的界面
        switch (str) {
            //人脸注册
            case 4:
                intent = new Intent(context, DetecterActivity.class);
                break;
            //预订会议
            case 1:
                intent = new Intent(context, WriteconditionActivity.class);
                break;
            //查看历史会议
            case 3:
                intent=new Intent(context,MeetingListActivity.class);
        }
        context.startActivity(intent);
        rlClick.performClick();
    }
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void _show(Context context, View parent) {

        _createView(context);

        if (popupWindow != null && !popupWindow.isShowing()) {

            popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);

            _openPopupWindowAction();
        }
    }

    private void _openPopupWindowAction() {

        //属性动画中旋转动画中rotation,rotationX和rotationY的区别
        //第一个参数是执行动画的参数 第二个是指定控件属性 第三个是被拉升的长度
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivBtn, "rotation", 0f, 135f);
        objectAnimator.setDuration(200);
        objectAnimator.start();

        _startAnimation(pop_reserve, 500, animatorProperty);
        _startAnimation(pop_notice, 430, animatorProperty);
        _startAnimation(pop_liebiao, 430, animatorProperty);
        _startAnimation(pop_openDoor, 500, animatorProperty);

    }

    private void _startAnimation(View view, int duration, float[] distance) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", distance);
        anim.setDuration(duration);
        anim.start();
    }

}
