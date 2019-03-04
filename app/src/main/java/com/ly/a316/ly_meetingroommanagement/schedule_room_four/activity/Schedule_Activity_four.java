package com.ly.a316.ly_meetingroommanagement.schedule_room_four.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.activites.InvitationPeoActivity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.activity.ZhanshiHuiActivity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.daoImp.DeviceDaoImp;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_one.customview.right_view;
import com.ly.a316.ly_meetingroommanagement.schedule_room_four.fragment.MyDialogFragment_four;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Schedule_Activity_four extends AppCompatActivity {
    MyDialogFragment_four myDialogFragment_four;
    @BindView(R.id.four_lt_click_time)
    LinearLayout fourLtClickTime;
    @BindView(R.id.four_ly_choose_time)
    LinearLayout fourLyChooseTime;
    @BindView(R.id.four_ly_time)
    LinearLayout fourLyTime;
    @BindView(R.id.four_huiyishichang)
    LinearLayout four_huiyishichang;
    @BindView(R.id.four_ly_where_hui)
    LinearLayout fourLyWhereHui;
    @BindView(R.id.four_ly_huitype)
    LinearLayout fourLyHuitype;
    @BindView(R.id.four_ly_wriren)
    LinearLayout fourLyWriren;
    @BindView(R.id.four_ly_device)
    LinearLayout fourLyDevice;
    @BindView(R.id.four_ly_shijianduan)
    LinearLayout four_ly_shijianduan;
    @BindView(R.id.four_title_time)
    TextView four_title_time;
    @BindView(R.id.four_text_huiyishi)
    TextView four_text_huiyishi;
    @BindView(R.id.four_toolbar)
    Toolbar four_toolbar;
    @BindView(R.id.four_edit_shichang)
    EditText four_edit_shichang;
    @BindView(R.id.four_edit_renshu)
    EditText four_edit_renshu;
    @BindView(R.id.four_panduan_donghua)
    right_view four_panduan_donghua;
    @BindView(R.id.four_ren_pan_ani)
    right_view four_ren_pan_ani;
    @BindView(R.id.four_img_xiala_one)
    ImageView four_img_xiala_one;
    @BindView(R.id.four_date_picker)
    DatePicker four_date_picker;
    @BindView(R.id.four_time_picker)
    TimePicker four_time_picker;
    @BindView(R.id.four_text_intime)
    TextView four_text_intime;
    @BindView(R.id.four_text_huidi)
    TextView four_text_huidi;
    @BindView(R.id.four_text_type)
    TextView four_text_type;
    @BindView(R.id.four_txt_shebei)
    TextView four_txt_shebei;
    @BindView(R.id.four_ly_choose_duan_start_time)
    LinearLayout four_ly_choose_duan_start_time;
    @BindView(R.id.four_image_start_time)
    ImageView four_image_start_time;
    @BindView(R.id.four_btn_start_cancel)
    Button four_btn_start_cancel;
    @BindView(R.id.four_image_end_time)
            ImageView four_image_end_time;
    @BindView(R.id.four_ly_choose_duan_end_time)
            LinearLayout four_ly_choose_duan_end_time;
    @BindView(R.id.four_end_time)
            TextView four_end_time;
    @BindView(R.id.four_start_time)
            TextView four_start_time;
    DeviceDaoImp deviceDaoImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_);
        ButterKnife.bind(this);
        deviceDaoImp = new DeviceDaoImp(this);
        initview();
        four_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myDialogFragment_four = new MyDialogFragment_four();
        myDialogFragment_four.show(getSupportFragmentManager(), "df");

        myDialogFragment_four.setDiaFragment_interface(new MyDialogFragment_four.DiaFragment_interface() {
            @Override
            public void success(List<String> list) {
                for (String nei : list) {
                    whichone_show(nei);
                }
            }
        });
    }

    void whichone_show(String why_nei) {
        switch (why_nei) {
            case "时间点":
                if (four_title_time.getVisibility() != View.VISIBLE) {
                    four_title_time.setVisibility(View.VISIBLE);
                }
                fourLyTime.setVisibility(View.VISIBLE);
                break;
            case "时间段":
                four_ly_shijianduan.setVisibility(View.VISIBLE);
                break;
            case "参会人数":
                fourLyWriren.setVisibility(View.VISIBLE);
                break;
            case "会议时长":
                if (four_title_time.getVisibility() != View.VISIBLE) {
                    four_title_time.setVisibility(View.VISIBLE);
                }
                four_huiyishichang.setVisibility(View.VISIBLE);
                break;
            case "设备需求":
                fourLyDevice.setVisibility(View.VISIBLE);
                break;
            case "会议室类型":
                if (four_text_huiyishi.getVisibility() != View.VISIBLE) {
                    four_text_huiyishi.setVisibility(View.VISIBLE);
                }
                fourLyHuitype.setVisibility(View.VISIBLE);
                break;
            case "会议室地点":
                if (four_text_huiyishi.getVisibility() != View.VISIBLE) {
                    four_text_huiyishi.setVisibility(View.VISIBLE);
                }
                fourLyWhereHui.setVisibility(View.VISIBLE);
                break;
        }
    }


    @OnClick({R.id.four_btn_start_surer,R.id.four_btn_end_surer,R.id.four_btn_end_cancel,R.id.four_ly_duan_end,R.id.four_btn_start_cancel, R.id.four_ly_duan_start, R.id.four_lt_click_time, R.id.four_btn_cancel, R.id.four_btn_surer, R.id.four_ly_where_hui, R.id.four_ly_huitype, R.id.four_ly_device, R.id.four_faqiyuding_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.four_lt_click_time:
                rotation(four_img_xiala_one, 1);
                break;
            case R.id.four_btn_cancel:
                rotation(four_img_xiala_one, 1);
                fourLyChooseTime.setVisibility(View.GONE);
                break;
            case R.id.four_btn_surer:
                rotation(four_img_xiala_one, 1);
                fourLyChooseTime.setVisibility(View.GONE);
                huoqushijian();
                String time = panduanShijina(year, month, day, hour, miniute);
                ShijiandianClass.DATESTRING = time;
                four_text_intime.setText(time);

                break;
            case R.id.four_ly_where_hui:
                //选择会议室地点
                getDidian();
                break;
            case R.id.four_ly_huitype:
                //选择会议室类型
                getAllType();
                break;
            case R.id.four_ly_device:
                getShebeiXuqiu();
                break;
            case R.id.four_faqiyuding_btn:
                //发起会议
                ShijiandianClass.PEOPLE_NUMBER = four_edit_renshu.getText().toString().trim();
                ShijiandianClass.HUIYISHICHANG_TIME = four_edit_shichang.getText().toString();//开会时长
                dechujieshushijian();
                ShijiandianClass.END_DIAN_TIME = panduanShijina(year, month, day, hour, miniute);
                deviceDaoImp.subbmitHuiyi(10);
                break;
            case R.id.four_ly_duan_start:
                rotation(four_image_start_time, 2);
                if(four_ly_choose_duan_end_time.getVisibility() == View.VISIBLE){
                    four_ly_choose_duan_end_time.setVisibility(View.GONE);
                }
                break;
            case R.id.four_btn_start_cancel:
                rotation(four_image_start_time, 2);
                four_ly_choose_duan_start_time.setVisibility(View.VISIBLE);
                break;
            case R.id.four_btn_start_surer:
                rotation(four_image_start_time, 2);
                four_ly_choose_duan_start_time.setVisibility(View.GONE);
                huoqushijian_duan();
                String time_start = panduanShijina(duan_start_year, duan_start_month, duan_start_day, duan_start_hour, duan_start_miniute);
                ShijiandianClass.START_TIME = time_start;
                four_start_time.setText(time_start);
                break;
            case R.id.four_ly_duan_end:
                rotation(four_image_end_time, 3);
                if(four_ly_choose_duan_start_time.getVisibility() == View.VISIBLE){
                    four_ly_choose_duan_start_time.setVisibility(View.GONE);
                }
                break;
            case R.id.four_btn_end_cancel:
                rotation(four_image_end_time, 3);
                four_ly_choose_duan_end_time.setVisibility(View.VISIBLE);
                break;
            case R.id.four_btn_end_surer:
                rotation(four_image_end_time, 3);
                four_ly_choose_duan_end_time.setVisibility(View.GONE);
                huoqu_end_time();
                String time_end = panduanShijina(duan_end_year, duan_end_month, duan_end_day, duan_end_hour, duan_end_miniute);
                ShijiandianClass.END_TIME = time_end;
                four_end_time.setText(time_end);
                break;
        }

    }

    void dechujieshushijian() {
        if (miniute >= 30) {
            if (hour >= 24) {
                hour = 1;
            } else {
                hour += 1;
            }
            ShijiandianClass.end_hour = hour;
            if(!ShijiandianClass.HUIYISHICHANG_TIME.equals("")){
                miniute = miniute + Integer.valueOf(ShijiandianClass.HUIYISHICHANG_TIME) - 60;
                ShijiandianClass.end_miniute = miniute;
            }
        } else {
            if(!ShijiandianClass.HUIYISHICHANG_TIME.equals("")){
                miniute += Integer.valueOf(ShijiandianClass.HUIYISHICHANG_TIME);
                ShijiandianClass.end_miniute = miniute;
            }

        }

    }

    String panduanShijina(int t_year, int t_month, int t_day, int t_hour, int t_miniute) {
        String time = t_year + "-";
        if (t_month / 10 == 0) {
            time += "0" + t_month;
        } else {
            time += t_month;
        }
        if (t_day / 10 == 0) {
            time += "-0" + t_day + " ";
        } else {
            time += "-" + t_day + " ";
        }
        if (t_hour / 10 == 0) {
            time += "0" + t_hour + ":";
        } else {
            time += t_hour + ":";
        }
        if (t_miniute / 10 == 0) {
            time += "0" + t_miniute + ":00";
        } else {
            time += t_miniute + ":00";
        }
        return time;
    }

    String hui_long_time, hui_renshu;

    void initview() {
        four_edit_shichang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!four_edit_shichang.getText().toString().trim().equals("")) {
                    four_panduan_donghua.start();
                    hui_long_time = four_edit_shichang.getText().toString().trim();
                } else {
                    four_panduan_donghua.reset();
                }
            }
        });
        four_edit_renshu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!four_edit_renshu.getText().toString().equals("")) {
                    four_ren_pan_ani.start();
                    hui_renshu = four_edit_renshu.getText().toString().trim();
                } else {
                    four_ren_pan_ani.reset();
                }
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
                        fourLyChooseTime.setVisibility(View.VISIBLE);
                        addLayoutAnimation(fourLyChooseTime);
                    } else {
                        fourLyChooseTime.setVisibility(View.GONE);
                    }
                } else if (who == 2) {
                    if(!open_close){
                        four_ly_choose_duan_start_time.setVisibility(View.VISIBLE);
                        addLayoutAnimation(four_ly_choose_duan_start_time);
                    }else{
                        four_ly_choose_duan_start_time.setVisibility(View.GONE);
                    }

                }else if(who == 3){
                    if (!open_close) {
                        four_ly_choose_duan_end_time.setVisibility(View.VISIBLE);
                        addLayoutAnimation(four_ly_choose_duan_end_time);
                    }else{
                        four_ly_choose_duan_end_time.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void addLayoutAnimation(ViewGroup view) {
        Animation animation;
        animation = AnimationUtils.loadAnimation(this, R.anim.tran_top);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        /*layoutAnimationController.setDelay(0.3f);*/
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        view.setLayoutAnimation(layoutAnimationController);

    }

    int year, month, day, hour, miniute;

    void huoqushijian() {
        year = four_date_picker.getYear();
        month = four_date_picker.getMonth() + 1;
        day = four_date_picker.getDayOfMonth();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = four_time_picker.getHour();
            miniute = four_time_picker.getMinute();
        }

    }

    @BindView(R.id.four_date_start_picker)
    DatePicker four_date_start_picker;
    @BindView(R.id.four_date_end_picker)
    DatePicker four_date_end_picker;
    @BindView(R.id.four_time_start_picker)
    TimePicker four_time_start_picker;
    @BindView(R.id.four_time_end_picker)
            TimePicker four_time_end_picker;
    int duan_start_year, duan_start_month, duan_start_day, duan_start_hour, duan_start_miniute;
    int duan_end_year, duan_end_month, duan_end_day, duan_end_hour, duan_end_miniute;

    void huoqushijian_duan() {
        duan_start_year = four_date_start_picker.getYear();
        duan_start_month = four_date_start_picker.getMonth() + 1;
        duan_start_day = four_date_start_picker.getDayOfMonth();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            duan_start_hour = four_time_start_picker.getHour();
            duan_start_miniute = four_time_start_picker.getMinute();
        }

    }
    void huoqu_end_time(){
        duan_end_year = four_date_end_picker.getYear();
        duan_end_month = four_date_end_picker.getMonth() + 1;
        duan_end_day = four_date_end_picker.getDayOfMonth();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            duan_end_hour = four_time_end_picker.getHour();
            duan_end_miniute = four_time_end_picker.getMinute();
        }

    }

    void getDidian() {
        Intent intent = new Intent(this, InvitationPeoActivity.class);
        intent.putExtra("who", "2");
        startActivityForResult(intent, 130);
    }

    void getAllType() {
        Intent intent = new Intent(this, InvitationPeoActivity.class);
        intent.putExtra("who", "3");
        startActivityForResult(intent, 140);
    }

    void getShebeiXuqiu() {
        Intent intent = new Intent(this, InvitationPeoActivity.class);
        intent.putExtra("who", "1");
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 123) {
                String count_num = data.getStringExtra("qqqq");
                Toast.makeText(this, count_num, Toast.LENGTH_SHORT).show();
                four_txt_shebei.setText(count_num);
            }
        }
        if (requestCode == 130) {
            if (resultCode == 124) {
                String count_num = data.getStringExtra("qqqq");
                ShijiandianClass.HUIYI_WHERE = count_num;//地点
                Toast.makeText(this, count_num, Toast.LENGTH_SHORT).show();
                four_text_huidi.setText(count_num);
            }
        }
        if (requestCode == 140) {
            if (resultCode == 150) {
                String count_num = data.getStringExtra("qqqq");
                Toast.makeText(this, count_num, Toast.LENGTH_SHORT).show();
                four_text_type.setText(count_num);
            }
        }
    }

    List<HuiyiInformation> list_meet = new ArrayList<>();

    public void success(List<HuiyiInformation> list_meett) {
        list_meet.clear();
        list_meet.addAll(list_meett);
        Intent intent = new Intent(this, ZhanshiHuiActivity.class);
        intent.putExtra("list_meet", (Serializable) list_meet);
        startActivity(intent);
    }
}
