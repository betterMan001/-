package com.ly.a316.ly_meetingroommanagement.meetting.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.MulitemAdapter;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelZero;
import com.ly.a316.ly_meetingroommanagement.meetting.services.imp.DeptServiceImp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InviteActivity extends BaseActivity {
    ArrayList<MultiItemEntity> res;
    List<LevelZero> lv0 = new ArrayList<>();
    @BindView(R.id.invite_rv)
    RecyclerView inviteRv;
    public static  OrderDetailMeetingActivity activity;
    public static final void start(Context context) {
        OrderDetailMeetingActivity.selectedEmployees.clear();
        //获得预定会议详情活动的对象来使用它的属性
        activity=(OrderDetailMeetingActivity) context;
        Intent intent = new Intent();
        intent.setClass(context, InviteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_invite);
        ButterKnife.bind(this);
        //获取一级列表部门的数据
        new DeptServiceImp(InviteActivity.this).getAllDepartemnt();

    }

    private void initView() {
        addData();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        inviteRv.setAdapter(new MulitemAdapter(res,InviteActivity.this));
        inviteRv.setLayoutManager(manager);
    }

    private void addData() {
        res = new ArrayList<>();
        for (int j = 0; j < lv0.size(); j++) {
            res.add(lv0.get(j));
        }
    }

    //获取一级列表后的回调函数
    public void levelZeroCallBack(final List<LevelZero> list) {
        this.lv0 = list;
        //获得数据后初始化视图
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });
    }

    @OnClick({R.id.back_ll, R.id.invite_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //退回到上一个界面
            case R.id.back_ll:
                OrderDetailMeetingActivity.selectedEmployees.clear();
                break;
                //提交参会人的列表
            case R.id.invite_tv:
                //弹出是否要补充会议纪录人的对话框
                showIsAddRecordingMeetingPeople();
                break;
        }
    }
    private void showIsAddRecordingMeetingPeople(){
        //显示对话框,d
        //第一个对话框提示是否添加会议记录人，
        //第二个对话框显示是否已经编辑完成，

        final AlertDialog.Builder dialog= new AlertDialog.Builder(InviteActivity.this);
        dialog.setMessage("是否要继续添加会议记录人？");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            //如果需要继续添加会议人则跳转到会议记录人的界面
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.isFinishMeetingPeople=true;
              MeetingRecordPeopleActivity.start(InviteActivity.this);
                InviteActivity.this.finish();
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                final AlertDialog.Builder dialog1= new AlertDialog.Builder(InviteActivity.this);
                dialog1.setMessage("是否已经确定参会人？");
                dialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subThreadToast("选择成功！");
                        activity.isFinishMeetingPeople=true;
                        InviteActivity.this.finish();
                    }
                });
                dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog1.show();

            }
        });
        dialog.show();
    }
}
