package com.ly.a316.ly_meetingroommanagement.meetting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.meetingRecordPeopleAdapter;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeetingRecordPeopleActivity extends BaseActivity {
    List<LevelOne> selectedList = new ArrayList();
    @BindView(R.id.invite_rv)
    RecyclerView inviteRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_meeting_record_people);
        ButterKnife.bind(this);
        initView();

    }

    //初始化列表
    private void initView() {
        //将集合中的数据转化为list
       getData();
       //初始化RecycleView
        this.inviteRv.setLayoutManager(new LinearLayoutManager(this));
        inviteRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));//加分隔线
        inviteRv.setAdapter(new meetingRecordPeopleAdapter(MeetingRecordPeopleActivity.this,this.selectedList));
    }
     private void getData(){
        //遍历集合从中获取数据
         Set<Map.Entry<String, LevelOne>> set =OrderDetailMeetingActivity.selectedEmployees.entrySet();
        //
         for(Map.Entry<String,LevelOne> me:set){
             this.selectedList.add(me.getValue());
         }
    }
    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MeetingRecordPeopleActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.back_ll, R.id.finish_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                break;
            case R.id.finish_tv:
                InviteActivity.activity.isFinishRecordMeetingPeople=true;
                finish();
                break;
        }
    }
}
