package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.customview.LoadingDialog;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.ListDropDownAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.MeetingListAdapter;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Meeting;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.MeetingListServiceImp;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeetingListActivity extends BaseActivity {

    @BindView(R.id.meeting_list_rv)
    RecyclerView meetingListRv;
    @BindView(R.id.act_meeting_list_menu)
    DropDownMenu actMeetingListMenu;
    //下拉标题
    private String headers[] = {"会议状态"};
    //下拉listView
    private List<View> popupViews = new ArrayList<>();
    private ListDropDownAdapter meetingAdapter;
    private MeetingListAdapter meetingListAdapter;
    private String meetings[] = {"不限", "未开始", "正在进行中","已结束"};
    private View statusBarView;
    public static List<Meeting> meetingList;
    public static List<Meeting> searchMeetingList;
    public LoadingDialog loadingDialog;
    public static String mIdForSearch="";
    public static String duration="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_meeting_list);
        ButterKnife.bind(this);
        loadingDialog= LoadingDialog.getInstance(this);
        //延时加载数据
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if(isStatusBar()){
                    initStatusBar();
                    getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                            initStatusBar();
                        }
                    });
                }
                return false;
            }
        });
        makeData();
    }
   //在活动被唤醒的时候更新数据，显示搜索目标的活动

    @Override
    protected void onRestart() {
        super.onRestart();
//        //1.修改指针数组
//        int count=0;
//        int length=meetingList.size();
//        for(int i=0;i<length;i++){
//            if(mIdForSearch.equals(meetingList.get(i).getmId())==true){
//                MeetingListAdapter.truePositon[count++]=i;
//                break;
//            }
//
//        }
//        //2.修改适配器的信息
//        meetingListAdapter.setCount(count);
//        //3.通知适配器刷新数据
//        meetingListAdapter.notifyDataSetChanged();
    }

    private void initView() {
        //1.设置RecycleView适配器
        initRecycleView();
        //2.设置下拉框
        initDropDownMenu();
    }

    private void initDropDownMenu() {
        //1.设置下拉listView
        final ListView meetingView = new ListView(this);
        meetingView.setDividerHeight(0);
        meetingAdapter = new ListDropDownAdapter(this, Arrays.asList(meetings));
        meetingView.setAdapter(meetingAdapter);
        popupViews.add(meetingView);
        //2.设置内容view
        //init context view
        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        contentView.setText("");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 0);
        //3.添加ListView监听事件
        meetingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //1.选择要筛选的条件
                meetingAdapter.setCheckItem(position);
                //2.设置菜单标题
                actMeetingListMenu.setTabText(position == 0 ? headers[0] : meetings[position]);
                //3.设置指针数组
                int length = meetingList.size();
                int count = 0;
                //如果选择不限则显示所有
                if ("不限".equals(meetings[position])) {
                    for (int i = 0; i < length; i++) {
                        MeetingListAdapter.truePositon[i] = i;
                    }
                    count = length;
                } else {
                    //根据条件筛选，并刷新RecycleView
                    for (int i = 0; i < length; i++) {
                        if (meetings[position].equals(meetingList.get(i).getState())||"正在进行".equals(meetingList.get(i).getState())) {
                            MeetingListAdapter.truePositon[count++] = i;
                        }
                    }
                }

                meetingListAdapter.setCount(count);
                //刷新RecycleView视图实现

                meetingListAdapter.notifyDataSetChanged();
                actMeetingListMenu.closeMenu();
            }
        });
        //3.绑定下拉菜单
        actMeetingListMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    private void makeData() {
        loadingDialog.show();
       new MeetingListServiceImp(this).selectMeetingBySEmployeeId(MyApplication.getId());
    }

    private void initRecycleView() {
        meetingListRv.setLayoutManager(new LinearLayoutManager(this));
        meetingListAdapter = new MeetingListAdapter(this, meetingList, meetingList.size());
        meetingListRv.setAdapter(meetingListAdapter);

    }

    @OnClick({R.id.act_meeting_list_back_ll, R.id.act_meeting_iv, R.id.meeting_list_rv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回上一级界面
            case R.id.act_meeting_list_back_ll:
                finish();
                break;
            //搜索框
            case R.id.act_meeting_iv:
                initSearch();

                break;
            //进入下一层界面
            case R.id.meeting_list_rv:
                break;
        }
    }

    private  void initSearch(){
        SearchViewActivity.start(MeetingListActivity.this,"2");
    }
    private void initStatusBar() {
        if (statusBarView == null) {
        int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
        statusBarView = getWindow().findViewById(identifier); }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.title_bar_color);
    }
    }
        protected boolean isStatusBar() { return true; }

    public void callBack(final List<Meeting> list){
        meetingList=list;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                initView();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        if(actMeetingListMenu.isShowing()){
            actMeetingListMenu.closeMenu();
        }
    }
}
