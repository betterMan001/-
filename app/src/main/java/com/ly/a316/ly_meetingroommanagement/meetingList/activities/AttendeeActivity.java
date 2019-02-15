package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.AttendeeAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.AttenderServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;
import com.ly.a316.ly_meetingroommanagement.nim.helper.TeamCreateHelper;
import com.ly.a316.ly_meetingroommanagement.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AttendeeActivity extends BaseActivity {

    @BindView(R.id.attendee_rv)
    RecyclerView attendeeRv;
    private  List<Attendee> attendeeList =new ArrayList<>();
    private String mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_attendee);
        ButterKnife.bind(this);
         mId=getIntent().getStringExtra("mId");
       //向后台获取参会人列表
        this.loadingDialog.show();
        new AttenderServiceImp(this).attenders(mId);
    }
    public static final void start(Context context,String mId) {
        Intent intent = new Intent();
        intent.putExtra("mId",mId);
        intent.setClass(context, AttendeeActivity.class);
        context.startActivity(intent);
    }
    private void initView() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        attendeeRv.setLayoutManager(layoutManager);
        attendeeRv.setAdapter(new AttendeeAdapter(this, attendeeList,mId));
    }

    @OnClick({R.id.back_ll, R.id.finish_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.finish_tv:
                createNormalTeam();
                break;
        }
    }
    //一键拉讨论组，创建云信的讨论组
    public void createNormalTeam(){
        List<String> list=new ArrayList<String>();
        for(Attendee temp: attendeeList){
            list.add(temp.getId());
        }
        TeamCreateHelper.createNormalTeam(AttendeeActivity.this,list,false, null);
    }
    public void attenderListCallBack(final List<Attendee> list){
        attendeeList =list;
         this.runOnUiThread(new Runnable() {
             @Override
             public void run() {
                AttendeeActivity.this.loadingDialog.dismiss();
                 initView();
             }
         });
    }

}
