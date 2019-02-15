package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.MulitemAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.AttenderServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.DeptServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelZero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewInviteActivity extends BaseActivity {
    ArrayList<MultiItemEntity> res;
    List<LevelZero> lv0 = new ArrayList<>();
    @BindView(R.id.invite_rv)
    RecyclerView inviteRv;
    String mId="";
    //存放被选中的职工的Map
    public static Map<String, LevelOne> selectedEmployees = new HashMap();
    public static final void start(Context context,String mId) {
        Intent intent = new Intent();
        intent.putExtra("mId",mId);
        intent.setClass(context, NewInviteActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_new_invite);
        ButterKnife.bind(this);
        mId=getIntent().getStringExtra("mId");
        //获取一级列表部门的数据
        new DeptServiceImp(NewInviteActivity.this).getAllDepartemnt();
    }

    private void initView() {
        addData();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        inviteRv.setAdapter(new MulitemAdapter(res, NewInviteActivity.this));
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

    @OnClick({R.id.back_ll, R.id.invite_tv, R.id.invite_rv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.invite_tv:
              addAttender();
                break;
        }
    }
    private  void addAttender(){
        //吧map中的数据提取出来用逗号隔开连接成string再发送
        String attends="";
        String recordPeoples="";
        //遍历集合从中获取数据
        for(String temp:selectedEmployees.keySet()){
            attends+=temp;
            attends+=",";
        }
        //访问服务器
        this.loadingDialog.show();
        new AttenderServiceImp(this).addAttender(attends,mId);
    }
}
