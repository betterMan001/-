package com.ly.a316.ly_meetingroommanagement.endActivity.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.daoImp.DeviceDaoImp;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Device;
import com.ly.a316.ly_meetingroommanagement.endActivity.adaper.FilePickerShowAdapter;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.FileEntity;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.OnFileItemClickListener;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.OpenFile;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.PickerManager;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.TransDoucument;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.MettingPeopleAdapter;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service.End_Service;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service.ServiceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class End_Activity extends AppCompatActivity {
    @BindView(R.id.addSchedule_toolbar)
    Toolbar addScheduleToolbar;
    @BindView(R.id.weidaorenshu)
    TextView weidaorenshu;
    @BindView(R.id.kaihuishichang)
    TextView kaihuishichang;

    private LinearLayout imageview;
    private int window_width;
    private int window_height;
    private int image_width;
    private LinearLayout wujiaoxing;
    private LinearLayout shop;
    private LinearLayout fans;
    private LinearLayout news;
    private LinearLayout expression;
    private LinearLayout trails;
    private LinearLayout istar;

    int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
    int width = RelativeLayout.LayoutParams.WRAP_CONTENT;

    TransDoucument transDoucument;
    @BindView(R.id.xianshi_file_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.relay_baoguo)
    RelativeLayout baoguo;
    Button canel_device, sure_device;
    DeviceDaoImp deviceDaoImp = new DeviceDaoImp(this);
    AlertDialog.Builder builder;
    AlertDialog dialog;
    View dialogView;
    LinearLayout four_lt_click_time, baoxiu_ly;
    ImageView baoxiu_img_device;
    TextView baoxiu_txt_device;
    RecyclerView baoxiu_recy_device;
    MettingPeopleAdapter mettingPeopleAdapter;
    List<Device> device_list = new ArrayList<>();//存放的设备

    Intent service_intent;
    String title_huiyi, start_time, end_time;
    private final int TIME = 60 * 1000;
    String weidao_number, kaihuishichang_txt;
    String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_end_, null);
        setContentView(view);
        ButterKnife.bind(this);
        Toast.makeText(this, "会议开始进行,会议结束前的15分钟我们将以\n通知的方式提醒你", Toast.LENGTH_LONG).show();
        title_huiyi = getIntent().getStringExtra("meeting_title_tv");
        start_time = getIntent().getStringExtra("start_time");
        end_time = getIntent().getStringExtra("end_time");
        kaihuishichang_txt = getIntent().getStringExtra("duration");
        weidao_number = getIntent().getStringExtra("weidao");
        mId = getIntent().getStringExtra("mId");
        kaihuishichang.setText("会议持续时长: " + zhuanhuanshijian(kaihuishichang_txt));
        sendTimeService(true);
        weidaorenshu.setText(weidao_number);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        init_Alert();
        deviceDaoImp.getAllDevice_inEndActivity();
        recyclerView.setLayoutManager(manager);
        addScheduleToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AlphaAnimation aa = new AlphaAnimation(0, 1.0f);
        aa.setDuration(400);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                int startangle = 54;
                int length = (int) ((window_width
                        / (Math.sin(72 * Math.PI / 180)) - image_width) / 2 - image_width / 5);
                ValueAnimator expressionanim = zhixian(expression, startangle,
                        length);
                ValueAnimator trailsanim = zhixian(trails, startangle + 72,
                        length);
                ValueAnimator newsanim = zhixian(news, startangle + (72 * 2),
                        length);
                ValueAnimator shopanim = zhixian(shop, startangle + (72 * 3),
                        length);
                ValueAnimator fansanim = zhixian(fans, startangle + (72 * 4),
                        length);
                AnimatorSet animSet = new AnimatorSet();

                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imageview,
                        "alpha", 0f, 1f);
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(istar, "alpha",
                        1f, 0f);

                animSet.playTogether(fadeIn, fadeOut, shopanim, fansanim,
                        newsanim, expressionanim, trailsanim);
                animSet.setDuration(1000);
                animSet.start();

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        WindowManager wm = getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        window_width = dm.widthPixels;
        window_height = dm.heightPixels - 20;
        image_width = window_width / 4;

        imageview = (LinearLayout) findViewById(R.id.head);
        RelativeLayout.LayoutParams paras = new RelativeLayout.LayoutParams(
                width, height);
        paras.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageview.setLayoutParams(paras);
        initbuttons();

    }

    /**
     * 沿直线运动。
     *
     * @param view   要移动的对象。
     * @param
     * @param length
     */
    public ValueAnimator zhixian(final View view, final int angle,
                                 final int length) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                Log.v("znz", "znz ---> " + fraction);
                PointF point = new PointF();
                point.x = (float) (fraction * length * Math.cos(angle * Math.PI
                        / 180));
                point.y = (float) (fraction * length * Math.sin(angle * Math.PI
                        / 180));
                return point;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        width, height);
                params.leftMargin = (int) point.x
                        + (window_width / 2 - imageview.getWidth() / 2); // Your
                // coordinate
                params.topMargin = (int) point.y
                        + (window_height / 4 - (imageview.getHeight() + 20) / 2); // Your Y
                // coordinate
                Log.v("znz", "point.x ---> " + point.x);
                Log.v("znz", "point.y ---> " + point.y);
                view.setLayoutParams(params);
            }
        });
        return valueAnimator;
    }

    /**
     * 初始化五颗按钮。
     */
    private void initbuttons() {
        shop = (LinearLayout) findViewById(R.id.shop);
        fans = (LinearLayout) findViewById(R.id.fans);
        news = (LinearLayout) findViewById(R.id.news);
        expression = (LinearLayout) findViewById(R.id.expression);
        trails = (LinearLayout) findViewById(R.id.trails);
        istar = (LinearLayout) findViewById(R.id.isstartlogo);
        RelativeLayout.LayoutParams paras = new RelativeLayout.LayoutParams(
                width, height);
        paras.addRule(RelativeLayout.CENTER_IN_PARENT);
        shop.setLayoutParams(paras);
        fans.setLayoutParams(paras);
        news.setLayoutParams(paras);
        expression.setLayoutParams(paras);
        trails.setLayoutParams(paras);
        istar.setLayoutParams(paras);
    }

    //添加文件
    public void add_file(View view) {
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 101) {
                FilePickerShowAdapter adapter = new FilePickerShowAdapter(this, PickerManager.getInstance().files);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnFileItemClickListener() {
                    @Override
                    public void click(int position) {
                        startActivity(Intent.createChooser(OpenFile.openFile(PickerManager.getInstance().files.get(position).getFilePath(), getApplicationContext()), "选择程序"));
                    }
                });
            }
        }
    }

    List<String> filepath_list = new ArrayList<>();//保存所有文件的路径

    //結束会议
    public void end_huiyi(View view) {
        handler.removeCallbacks(runnable);
        for (int i = 0; i < PickerManager.getInstance().files.size(); i++) {
            FileEntity fileEntity = PickerManager.getInstance().files.get(i);
            filepath_list.add(fileEntity.getFilePath());
        }
        transDoucument = new TransDoucument(filepath_list, this, this);
        transDoucument.transDocument();
    }

    String com, file_path;

    public void success_back(final String content, String filepath) {
        showDialog(content);
        com = content;
        file_path = filepath;
    }

    private void showDialog(String mess) {
        new AlertDialog.Builder(this).setTitle("发送会议记录信息")
                .setMessage(mess)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String tels = MyApplication.getId();              //获取电话
                        String contents = "复制链接在浏览器中打开进行查看：" + file_path;      //获取短信内容
                        Intent intent = new Intent();                        //创建 Intent 实例
                        intent.setAction(Intent.ACTION_SENDTO);             //设置动作为发送短信
                        intent.setData(Uri.parse("smsto:" + tels));           //设置发送的号码
                        intent.putExtra("sms_body", contents);              //设置发送的内容
                        startActivity(intent);                               //启动 Activity
                    }
                }).show();
    }


    void init_Alert() {
        //设备报修
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
        baoxiu_ly = dialogView.findViewById(R.id.baoxiu_ly);
        baoxiu_txt_device = dialogView.findViewById(R.id.baoxiu_txt_device);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_back);
        mettingPeopleAdapter = new MettingPeopleAdapter(this, device_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        baoxiu_recy_device.setLayoutManager(linearLayoutManager);
        baoxiu_recy_device.setAdapter(mettingPeopleAdapter);
        mettingPeopleAdapter.setEditMode(1);

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
                Toast.makeText(End_Activity.this, chooseNum + "", Toast.LENGTH_SHORT).show();
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
                            nei = device_list.get(i).getdName();
                        } else {
                            nei += "," + device_list.get(i).getdName();
                        }
                        a = 0;
                    }
                }
                baoxiu_txt_device.setText(nei);
            }
        });

    }


        /*NiceSpinner niceSpinner = dialogView.findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        niceSpinner.attachDataSource(dataset);*/

    int chooseNum = 0;

    public void device_baoxiu(View view) {
        dialog.show();
        four_lt_click_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotation(baoxiu_img_device, 1);
            }
        });

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

    public void call_success_back(List<Device> list) {
        device_list.clear();
        device_list.addAll(list);
        mettingPeopleAdapter.notifyDataSetChanged();

    }


    // 通过Handler实现定时任务
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(End_Activity.this, End_Service.class);
            //这个界面需要的是结束的小时和分
            intent.putExtra("title_huiyi", title_huiyi);
            String hui_time = panduan(end_time);
            String ds[] = hui_time.split(",");
            intent.putExtra("end_time", ds[1]);//传结束分
            intent.putExtra("start_time", ds[0]);//传结束的小时
            intent.putExtra("mId", mId);//会议id
            startService(intent);
            handler.postDelayed(runnable, TIME);
        }
    };

    private void sendTimeService(boolean idHandler) {
        if (idHandler) {
            handler.postDelayed(runnable, TIME);
        } else {
            ServiceUtil.startAMService(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);

    }

    String zhuanhuanshijian(String time_mini) {
        int hour = Integer.valueOf(time_mini) / 60;
        int mini = Integer.valueOf(time_mini) % 60;
        String result = hour + "小时" + mini + "分";
        return result;
    }

    String panduan(String time) {
        int huimin, hui_hour;
        String dd[] = time.split(",");
        int mini = Integer.valueOf(dd[1]);
        int hour = Integer.valueOf(dd[0]);
        if (mini < 15) {
            hui_hour = hour - 1;
            mini = mini + 60;
            huimin = mini - 15;
        } else {
            huimin = mini - 15;
            hui_hour = hour;
        }
        return hui_hour + "," + huimin;
    }
}
