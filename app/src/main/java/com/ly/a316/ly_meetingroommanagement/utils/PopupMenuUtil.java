package com.ly.a316.ly_meetingroommanagement.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.FacePack.DetecterActivity;
import com.ly.a316.ly_meetingroommanagement.MessageList.activites.MessageListActivity;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.all_hui_room.activity.All_Hui_Room_Activity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.activity.HuiyiActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingListActivity;
import com.ly.a316.ly_meetingroommanagement.popPage.Adapter.MyAdapter;
import com.ly.a316.ly_meetingroommanagement.popPage.DaoImp.GetTodayHuiDaoImp;
import com.ly.a316.ly_meetingroommanagement.popPage.activity.Information_meet;
import com.ly.a316.ly_meetingroommanagement.popPage.object.HuiyiClass;
import com.ly.a316.ly_meetingroommanagement.schedule_room_four.activity.Choose_Activity;
import com.ly.a316.ly_meetingroommanagement.schedule_room_four.activity.Schedule_Activity_four;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
    private LinearLayout pop_reserve, pop_notice, pop_liebiao, pop_openDoor, item_pop_ly;
    private RecyclerView pop_recycle;
    float animatorProperty[] = null;
    int top = 0;
    int bottom = 0;
    MyAdapter myAdapter;
    List<HuiyiClass> list_huiyi = new ArrayList<>();
    GetTodayHuiDaoImp getTodayHuiDaoImp = new GetTodayHuiDaoImp(this);

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


        myAdapter = new MyAdapter(context, list_huiyi);
        initLayout(context);


        /* item_pop_ly.setBackgroundResource(R.drawable.item_pop_beijing);*/
        getTodayHuiDaoImp.getTodayHui(MyApplication.getId());


    }

    private void initLayout(final Context context) {
        pop_recycle = (RecyclerView) rootVew.findViewById(R.id.pop_recycle);
        item_pop_ly = (LinearLayout) rootVew.findViewById(R.id.item_pop_ly);
        rlClick = (RelativeLayout) rootVew.findViewById(R.id.pop_rl_click);
        ivBtn = (ImageView) rootVew.findViewById(R.id.pop_iv_img);//那个十字架
        pop_reserve = (LinearLayout) rootVew.findViewById(R.id.pop_reserve);
        pop_notice = (LinearLayout) rootVew.findViewById(R.id.pop_notice);
        pop_liebiao = (LinearLayout) rootVew.findViewById(R.id.pop_liebiao);
        pop_openDoor = rootVew.findViewById(R.id.pop_openDoor);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(context, resId);
        pop_recycle.setLayoutAnimation(animation);

        LinearLayoutManager ms = new LinearLayoutManager(context);
        pop_recycle.setLayoutManager(ms);
        pop_recycle.setAdapter(myAdapter);

        rlClick.setOnClickListener(new MViewClick(0, context));
        pop_reserve.setOnClickListener(new MViewClick(1, context));
        pop_notice.setOnClickListener(new MViewClick(2, context));
        pop_liebiao.setOnClickListener(new MViewClick(3, context));
        pop_openDoor.setOnClickListener(new MViewClick(4, context));
        myAdapter.setClick(new MyAdapter.Click() {
            @Override
            public void onCcick(String id, String url) {
                Intent intent = new Intent(context, Information_meet.class);
                intent.putExtra("mId", id);
                intent.putExtra("img", url);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context).toBundle());
                }
            }
        });
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
                showToast(context, index);
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
            //签到开门
            case 4:
                intent = new Intent(context, DetecterActivity.class);
                break;
            //预订会议
            case 1:
                /* intent = new Intent(context,Hui_Activity.class);第一种*/
                /* intent = new Intent(context, Schedule_Activity.class);第二种*/
                /* intent = new Intent(context, Schedule_Activity_three.class);第三种*/
                /* intent = new Intent(context, Schedule_Activity_four.class);第四种*/
                intent = new Intent(context, Choose_Activity.class);
                break;
            //查看会议列表
            case 3:
                intent = new Intent(context, MeetingListActivity.class);
                break;
            //所有会议室列表
            case 2:
                //  intent=new Intent(context, MessageListActivity.class);
                intent = new Intent(context, All_Hui_Room_Activity.class);
                break;
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

    public void success_back(List<HuiyiClass> list_fan) {
        list_huiyi.clear();
        list_huiyi.addAll(list_fan);
        myAdapter.notifyDataSetChanged();
        if (list_huiyi.size() == 0) {
            item_pop_ly.setBackgroundResource(R.drawable.item_pop_beijing);
        } else {
            item_pop_ly.setBackgroundResource(R.drawable.item_pop_beijingtwo);
        }
    }
}
