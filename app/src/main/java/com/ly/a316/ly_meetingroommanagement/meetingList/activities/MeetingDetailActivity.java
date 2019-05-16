package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.daoImp.DeviceDaoImp;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Device;
import com.ly.a316.ly_meetingroommanagement.endActivity.activity.End_Activity;
import com.ly.a316.ly_meetingroommanagement.endActivity.daoimp.YanChangDaoImp;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.MeetingDetailModel;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.AttenderServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.imp.MeetingDetailServiceImp;
import com.ly.a316.ly_meetingroommanagement.meetting.activity.ContentDialogActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.MettingPeopleAdapter;
import com.ly.a316.ly_meetingroommanagement.nim.helper.TeamCreateHelper;
import com.zaaach.toprightmenu.MenuItem;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeetingDetailActivity extends BaseActivity {
    @BindView(R.id.meeting_title_tv)
    TextView meetingTitleTv;
    @BindView(R.id.meeting_place_tv)
    TextView meetingPlaceTv;
    @BindView(R.id.meeting_time)
    TextView meetingTime;
    @BindView(R.id.meetin_room_no)
    TextView meetinRoomNo;
    @BindView(R.id.do_something)
    ImageView doSomething;
    @BindView(R.id.begin_meeting)
    TextView beginMeeting;
    @BindView(R.id.meeting_maker)
    TextView meetingMaker;
    private String mId;
    public static MeetingDetailModel model;
    TopRightMenu mToRightMenu;
    public static List<Attendee> attendeeList = new ArrayList<>();
    private boolean isSignInMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_meeting_detail);
        ButterKnife.bind(this);
        mId = getIntent().getStringExtra("mId");
        //获取该页面所需要的数据
        makeData();
    }

    private void makeData() {
        loadingDialog.show();
        new MeetingDetailServiceImp(this, "1").meetDetail(mId);
    }

    public static final void start(Context context, String mId, String duration) {
        Intent intent = new Intent();
        intent.putExtra("mId", mId);
        intent.putExtra("duration", duration);
        intent.setClass(context, MeetingDetailActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        //填充数据
        meetingTitleTv.setText(model.title);
        meetingPlaceTv.setText(model.address);
        meetingTime.setText(model.begin);
        meetingMaker.setText(model.getSender());
        String showPeopleNum = "";
        showPeopleNum += (model.sure + "/" + model.all + "已接受>");
        meetinRoomNo.setText(showPeopleNum);
        mToRightMenu = new TopRightMenu(MeetingDetailActivity.this);
        if ("2".equals(model.getState())) {
            beginMeeting.setText("已结束");
            beginMeeting.setEnabled(false);
        } else if ("1".equals(model.getState()))
            beginMeeting.setText("正在进行中");
        //不是发起人不能开始会议
        if (MyApplication.getId().equals(model.senderId) == false) {
            beginMeeting.setEnabled(false);
            beginMeeting.setBackgroundColor(getResources().getColor(R.color.gray));
        }
        initviewwww();
    }

    @OnClick({R.id.back_ll, R.id.do_something, R.id.meeting_people_ll, R.id.signature_ll, R.id.begin_meeting, R.id.meeting_content_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                finish();
                break;
            case R.id.do_something:
                showMenu();
                break;
            case R.id.meeting_people_ll:
                AttendeeActivity.start(MeetingDetailActivity.this, mId);
                break;
            case R.id.signature_ll:
                isSignInMode = true;
                //该方法获取的数据已经废弃，有点懒先放着以后再删233
                new AttenderServiceImp(MeetingDetailActivity.this).attendersForDetailActivity(mId);
                break;
            //开始会议
            /*
            * 如果未开始会议就弹出框
            * 开始了直接进去
            * */
            case R.id.begin_meeting:
               if("0".equals(model.getState()))
                showBeginMeetingDialog();
               else
                   prepareForBegingMeeting();
                break;
            //会议内容
            case R.id.meeting_content_ll:
                ContentDialogActivity.start(MeetingDetailActivity.this, "2");
        }
    }
    private void showBeginMeetingDialog(){
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(MeetingDetailActivity.this);
        normalDialog.setTitle("开会提醒");
        normalDialog.setMessage("确定要开始会议?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      new MeetingDetailServiceImp(MeetingDetailActivity.this,"1").beginMeeting(mId);
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();

    }
    //为跳转页面做准备
    private void prepareForBegingMeeting(){
        String start_time = model.begin.substring(11, 13) + "," + model.begin.substring(14, 16);
        String end_time = "";
        String type = getIntent().getStringExtra("type");
        end_time = generate(getIntent().getStringExtra("duration"));
        /* Intent intentet = new Intent(this, Ceshi.class);*/
        Intent intentet = new Intent(this, End_Activity.class);
        intentet.putExtra("end_time", end_time);
        intentet.putExtra("start_time", start_time);
        int all = new Integer(model.all);
        int sure = new Integer(model.sure);
        intentet.putExtra("weidao", new Integer(all - sure).toString());
        intentet.putExtra("mId", mId);
        intentet.putExtra("duration", getIntent().getStringExtra("duration"));
        intentet.putExtra("mId", mId);
        startActivity(intentet);
    }
    public void startForSignIn() {
        SignInActivity.start(MeetingDetailActivity.this, mId);
    }

    public void showMenu() {
        final List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(R.drawable.qun001, "一键拉讨论组"));
        mToRightMenu
                .setHeight(150)     //默认高度480
                .setWidth(350)      //默认宽度wrap_content
                .showIcon(true)     //显示菜单图标，默认为true
                .dimBackground(true)           //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //动画样式 默认为R.style.TRM_ANIM_STYLE
                .addMenuList(menuItems)
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        switch (position) {
                            //一键拉讨论组
                            case 0:
                                new AttenderServiceImp(MeetingDetailActivity.this).attendersForDetailActivity(mId);
                                break;
                        }
                    }
                }).showAsDropDown(doSomething, -225, 0);

    }

    public void meetingDetailCallBack(final MeetingDetailModel model) {
        this.model = model;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismiss();
                initView();
            }
        });
    }

    public void attenderListCallBack(final List<Attendee> list) {
        attendeeList = list;
        if (isSignInMode == false) {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MeetingDetailActivity.this.loadingDialog.dismiss();
                    List<String> list = new ArrayList<String>();
                    for (Attendee temp : attendeeList) {
                        list.add(temp.getId());
                    }
                    TeamCreateHelper.createNormalTeam(MeetingDetailActivity.this, list, false, null);
                }
            });
        } else {
            //重置该值
            isSignInMode = false;
            final Context context = MeetingDetailActivity.this;
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SignInActivity.start(context, mId);
                }
            });
        }

    }

    public String generate(String time) {
        //开始时间
        int start_hour = new Integer(model.begin.substring(11, 13));
        int start_mintue = new Integer(model.begin.substring(14, 16));
        int temp = new Integer(time);
        int hours = temp / 60;
        int minutes = temp % 60;
        //把时间加起来生成结束时间
        minutes += start_mintue;
        hours = (hours + start_hour + (minutes / 60));
        minutes %= 60;
        StringBuilder builder = new StringBuilder();
        // builder.append("持续时间:");
        builder.append(new Integer(hours).toString());
        builder.append(",");
        builder.append(new Integer(minutes).toString());
        return builder.toString();

    }

    AlertDialog.Builder builder;
    AlertDialog dialog;
    View dialogView;
    Button canel_device, sure_device, sure_five, cannel_five;
    YanChangDaoImp yanChangDaoImp = new YanChangDaoImp(this, "2");

    LinearLayout four_lt_click_time, baoxiu_ly;
    ImageView baoxiu_img_device;
    TextView baoxiu_txt_device;
    RecyclerView baoxiu_recy_device;
    MettingPeopleAdapter mettingPeopleAdapter;
    TextView text_beizhu;
    String choose_device_id;
    List<Device> device_list = new ArrayList<>();//存放的设备
    DeviceDaoImp deviceDaoImp = new DeviceDaoImp(this, "2");
    int chooseNum = 0;

    void initviewwww() {
        deviceDaoImp.getAllDevice_inEndActivity();

        builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        dialogView = View.inflate(this, R.layout.baoxiu, null);
        //设置对话框布局
        dialog.setView(dialogView);
        four_lt_click_time = dialogView.findViewById(R.id.four_lt_click_time);
        baoxiu_img_device = dialogView.findViewById(R.id.baoxiu_img_device);
        baoxiu_recy_device = dialogView.findViewById(R.id.baoxiu_recy_device);
        canel_device = dialogView.findViewById(R.id.canel_device);
        sure_device = dialogView.findViewById(R.id.sure_device);
        text_beizhu = dialogView.findViewById(R.id.text_beizhu);
        sure_five = dialogView.findViewById(R.id.sure_five);
        cannel_five = dialogView.findViewById(R.id.cannel_five);
        baoxiu_ly = dialogView.findViewById(R.id.baoxiu_ly);
        baoxiu_txt_device = dialogView.findViewById(R.id.baoxiu_txt_device);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_back);
        mettingPeopleAdapter = new MettingPeopleAdapter(this, device_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        baoxiu_recy_device.setLayoutManager(linearLayoutManager);
        baoxiu_recy_device.setAdapter(mettingPeopleAdapter);
        mettingPeopleAdapter.setEditMode(1);
        sure_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yanChangDaoImp.baoxiu(MyApplication.getId(), mId, choose_device_id, text_beizhu.getText().toString());
            }
        });
        cannel_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        mettingPeopleAdapter.setOnItemClick(new MettingPeopleAdapter.OnItemClickk() {
            @Override
            public void onItemclick(MettingPeopleAdapter.MyViewHolder viewHolder, int position) {
                String a = viewHolder.pan.getText().toString();
                if (a.equals("1")) {
                    chooseNum++;
                    viewHolder.check_box.setImageResource(R.mipmap.ic_checked);
                    viewHolder.pan.setText("0");
                    device_list.get(position).setChoose("0");
                } else if (a.equals("0")) {
                    chooseNum--;
                    viewHolder.check_box.setImageResource(R.mipmap.ic_uncheck);
                    viewHolder.pan.setText("1");
                    device_list.get(position).setChoose("1");
                }
            }
        });
        canel_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation(baoxiu_img_device, 3);
                baoxiu_ly.setVisibility(View.VISIBLE);
                Toast.makeText(MeetingDetailActivity.this, chooseNum + "", Toast.LENGTH_SHORT).show();
            }
        });
        sure_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation(baoxiu_img_device, 2);
                baoxiu_ly.setVisibility(View.GONE);
                String nei = "";
                int a = 1;
                for (int i = 0; i < device_list.size(); i++) {
                    if (device_list.get(i).getChoose().equals("0")) {
                        if (a == 1) {
                            choose_device_id = String.valueOf(device_list.get(i).getdId());
                            nei = device_list.get(i).getdName();
                        } else {
                            nei += "," + device_list.get(i).getdName();
                            choose_device_id += "," + device_list.get(i).getdId();
                        }
                        a = 0;
                    }
                }
                baoxiu_txt_device.setText(nei);
            }
        });
    }

    @OnClick(R.id.meeting_fix_ll)
    public void onViewClicked() {
        DeviceListActivity.start(MeetingDetailActivity.this,mId);

    }

    boolean open_close = true;//true为关闭状态

    void rotation(View ima_view, final int who) {
        Animation rotate;
        if (open_close) {
            rotate = new RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            open_close = false;
        } else {
            open_close = true;
            rotate = new RotateAnimation(90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        LinearInterpolator lin = new LinearInterpolator();//为匀速
        rotate.setInterpolator(lin);//设置插值器
        rotate.setDuration(100);//设置动画持续周期
        rotate.setRepeatCount(0);//重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        ima_view.setAnimation(rotate);
        ima_view.startAnimation(rotate);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (who == 1) {
                    if (!open_close) {
                        baoxiu_ly.setVisibility(View.VISIBLE);
                    } else {
                        baoxiu_ly.setVisibility(View.GONE);
                    }
                } else if (who == 3) {
                    if (!open_close) {
                        baoxiu_ly.setVisibility(View.VISIBLE);
                    } else {
                        baoxiu_ly.setVisibility(View.GONE);
                    }
                } else if (who == 2) {
                    if (!open_close) {
                        baoxiu_ly.setVisibility(View.VISIBLE);

                    } else {
                        baoxiu_ly.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void baoxiu(String content) {
        AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                .setMessage(content)//内容
                .create();
        alertDialog1.show();
        dialog.dismiss();
    }

    public void call_success_back(List<Device> list) {
        device_list.clear();
        device_list.addAll(list);
        mettingPeopleAdapter.notifyDataSetChanged();
    }

    public void beginCallBack(){
         this.runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 prepareForBegingMeeting();
             }
         });
    }
}
