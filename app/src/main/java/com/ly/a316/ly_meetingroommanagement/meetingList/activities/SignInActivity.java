package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.activites.DetailsMettingActivity;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.ListDropDownAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.MeetingListAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.SignInPeopleAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.SignInServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetting.activity.OrderDetailMeetingActivity;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {
    List<Attendee> list = new ArrayList<Attendee>();
    @BindView(R.id.act_sign_in_menu)
    DropDownMenu actSignInMenu;
    @BindView(R.id.sign_in_rv)
    RecyclerView signInRv;
    @BindView(R.id.sign_in_status)
    TextView signInStatus;
    private ListDropDownAdapter meetingAdapter;
    //下拉标题
    private String headers[] = {"签到情况"};
    private String meetings[] = {"不限", "已签到", "未签到"};
    //下拉listView
    private List<View> popupViews = new ArrayList<>();
    //签到人数统计情况
    private String stateResult = "";
    private String mId;
    private SignInPeopleAdapter signInPeopleAdapter;
    private int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        mId = getIntent().getStringExtra("mId");
        makeData();
    }

    public static final void start(Context context, String mId) {
        Intent intent = new Intent();
        intent.putExtra("mId", mId);
        intent.setClass(context, SignInActivity.class);
        context.startActivity(intent);
    }

    public void makeData() {

        //为了测试前台功能是否正常注释掉了这一行
        loadingDialog.show();
        new SignInServiceImp(this).signInCase(mId);
        //测试用本地数据
//        List<Attendee> list=MeetingDetailActivity.attendeeList;
//        Attendee temp=new Attendee();
//        temp.setName("已经签到的帅哥哥");
//        temp.setSign(true);
//        list.add(temp);
//        initView();
    }

    private void initView() {
        //初始化RecycleView
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        signInRv.setLayoutManager(layoutManager);
        signInPeopleAdapter = new SignInPeopleAdapter(this, MeetingDetailActivity.attendeeList);
        signInRv.setAdapter(signInPeopleAdapter);
        signInStatus.setText("签到情况"+stateResult+"人");
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
                /*这里有数据再操作*/
                actSignInMenu.setTabText(position == 0 ? headers[0] : meetings[position]);
                //3.设置指针数组
                int length = MeetingDetailActivity.attendeeList.size();
                int count = 0;
                //如果选择不限则显示所有
                if ("不限".equals(meetings[position])) {
                    for (int i = 0; i < length; i++) {
                        SignInPeopleAdapter.truePositon[i] = i;
                    }
                        signInPeopleAdapter.setCount(MeetingDetailActivity.attendeeList.size());
                }else if ("已签到".equals(meetings[position])) {
                    for (int i = 0; i < length; i++) {
                        if (MeetingDetailActivity.attendeeList.get(i).isSign()==true) {
                            SignInPeopleAdapter.truePositon[count++] = i;
                        }
                    }
                    signInPeopleAdapter.setCount(count);
                } else {
                    for (int i = 0; i < length; i++) {
                        if (MeetingDetailActivity.attendeeList.get(i).isSign()==false) {
                            SignInPeopleAdapter.truePositon[count++] = i;
                        }
                    }
                    signInPeopleAdapter.setCount(count);
                }
                //刷新RecycleView视图实现
                signInPeopleAdapter.notifyDataSetChanged();
                actSignInMenu.closeMenu();
            }
        });
        //3.绑定下拉菜单
        actSignInMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    @OnClick(R.id.back_ll)
    public void onViewClicked() {
        finish();
    }

    public void signInCallBack(String stateResult, final List<Attendee> list) {
        //1.先保存下签到情况的总结
        this.stateResult=stateResult;
        //2.做区分签到和普通参会人区别的处理
        //..这里就直接暴力遍历了。。卡了我再换orz
        for (Attendee temp : MeetingDetailActivity.attendeeList) {
            for (Attendee compare : list) {
                //如果是签到的人则标记已经签到
                if (compare.getId().equals(temp.getId()) == true) {
                    temp.setSign(true);
                }
            }
        }
        //3.记录签到人数
        count=list.size();
        //4.初始化视图
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                initView();
            }
        });
    }
}
