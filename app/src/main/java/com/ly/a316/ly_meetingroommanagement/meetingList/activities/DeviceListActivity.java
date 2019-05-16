package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.DeviceListAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.MeetingListAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Device;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.DeviceServiceImp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceListActivity extends BaseActivity {

    @BindView(R.id.device_list_rv)
    RecyclerView deviceListRv;
    List<Device> deviceList;
    String mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);
        mId=getIntent().getStringExtra("mId");
        makeData();
    }

    public static final void start(Context context, String mId) {
        Intent intent = new Intent();
        intent.putExtra("mId", mId);
        intent.setClass(context, DeviceListActivity.class);
        context.startActivity(intent);
    }

    void initRecycleView() {
        deviceListRv.setLayoutManager(new LinearLayoutManager(this));
        deviceListRv.setAdapter(new DeviceListAdapter(this, deviceList));
    }
    void makeData(){
      //从服务器获取数据
        new DeviceServiceImp(this).roomDeviceList(mId);
    }
    public void deviceCallBack(final List<Device> list){
       this.runOnUiThread(new Runnable() {
           @Override
           public void run() {
               //1.获取列表
              DeviceListActivity.this.deviceList=list;
              //2.初始化RecycleView
               initRecycleView();
           }
       });
    }
}
