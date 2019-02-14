package com.ly.a316.ly_meetingroommanagement.chooseOffice.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.activites.InvitationPeoActivity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;
import com.ly.a316.ly_meetingroommanagement.customView.DatePicker;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeDianFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.dagaishijiandian)
    TextView about_time;
    @BindView(R.id.shichang)
    TextView shichang;
    @BindView(R.id.shebeixuqiu_num)
    TextView shebeixuqiu_num;
    @BindView(R.id.huiyishididian)
    TextView huiyishididian;
    @BindView(R.id.huiyishitype)
    TextView huiyishitype;
    @BindView(R.id.renshu)
    TextView renshu;
    TimePicker time_test;//时刻选择控件
    DatePicker ddm_test;
    View shijiandian_view;
    AlertDialog alertDialog;
    LinearLayout add_kongjian_linearlayout;//用来添加控件的
    TextView shijiandian_biaotil;
    EditText edit_input_shijian;//输入时间段

    int hour, miniute, year, month, day;


    public TimeDianFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_dian, container, false);

        shijiandian_view = LayoutInflater.from(getContext()).inflate(R.layout.shijiandian_item, null);
        ShijiandianClass.WHO = "1";
        Calendar cd = Calendar.getInstance();
        year = cd.get(Calendar.YEAR);
        month = cd.get(Calendar.MONTH) + 1;
        day = cd.get(Calendar.DATE);
        hour = cd.get(Calendar.HOUR_OF_DAY);
        miniute = cd.get(Calendar.MINUTE);
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;
    }

    void initview() {
        ddm_test = new DatePicker(getContext());
        time_test = new TimePicker(getContext());
        edit_input_shijian = new EditText(getContext());

        add_kongjian_linearlayout = shijiandian_view.findViewById(R.id.add_shiketime);
        shijiandian_biaotil = shijiandian_view.findViewById(R.id.shijiandian_biaoti);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.shijaindianclick, R.id.huiyishichang, R.id.shebeixuqiu, R.id.shijiandian_didian, R.id.huiyishi_people_num, R.id.getAllType})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shijaindianclick:
                toast_dia(1);
                break;
            case R.id.huiyishichang:
                toast_dia(2);
                break;
            case R.id.shebeixuqiu:
                getShebeiXuqiu();
                break;
            case R.id.shijiandian_didian:
                getDidian();
                break;
            case R.id.huiyishi_people_num:
                toast_dia(3);
                break;
            case R.id.getAllType:
                getAllType();
                break;
        }
    }

    void toast_dia(final int who) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(getContext(), R.style.style_dialog)
                    .setView(shijiandian_view)//在这里把写好的这个listview的布局加载dialog中.setCancelable(false);
                    .setCancelable(false)
                    .create();
        }
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        if (who == 1) {
            shijiandian_biaotil.setText("时间点选择");
            if (edit_input_shijian != null) {
                add_kongjian_linearlayout.removeView(edit_input_shijian);
            }
            if (time_test != null) {
                add_kongjian_linearlayout.removeView(time_test);
            }
            if (ddm_test != null) {
                add_kongjian_linearlayout.removeView(ddm_test);
            }
            add_kongjian_linearlayout.addView(ddm_test);
            add_kongjian_linearlayout.addView(time_test);


            time_test.setOnChangeListener(new TimePicker.OnChangeListener() {
                @Override
                public void onChange(int hourr, int munite) {
                    hour = hourr;
                    miniute = munite;
                }
            });
            ddm_test.setOnChangeListener(new DatePicker.OnChangeListener() {
                @Override
                public void onChange(int yearr, int monthh, int dayy, int day_of_weekk) {
                    year = yearr;
                    month = monthh;
                    day = dayy;
                }
            });

        } else if (who == 2) {
            edit_input_shijian.setHint("填入时长（分钟为单位）");
            shijiandian_biaotil.setText("会议时长");
            if (edit_input_shijian != null) {
                add_kongjian_linearlayout.removeView(edit_input_shijian);
            }
            if (time_test != null) {
                add_kongjian_linearlayout.removeView(time_test);
            }
            if (ddm_test != null) {
                add_kongjian_linearlayout.removeView(ddm_test);
            }
            add_kongjian_linearlayout.addView(edit_input_shijian);

        } else if (who == 3) {
            edit_input_shijian.setHint("填入人数");
            shijiandian_biaotil.setText("会议人数");
            if (edit_input_shijian != null) {
                add_kongjian_linearlayout.removeView(edit_input_shijian);
            }
            if (time_test != null) {
                add_kongjian_linearlayout.removeView(time_test);
            }
            if (ddm_test != null) {
                add_kongjian_linearlayout.removeView(ddm_test);
            }
            add_kongjian_linearlayout.addView(edit_input_shijian);
        }
        Button btn_sure = shijiandian_view.findViewById(R.id.shijian_sure);//确定
        Button btn_quxiao = shijiandian_view.findViewById(R.id.shijian_quxiao);//取消
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setWindowAnimations(R.style.dialog_anim_shijiandian);//<pre name="code" class="java">然后再Style文件中定义这么一个Style,就是我们的dialog_anim
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = 800;   //设置宽度充满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setBackgroundDrawableResource(R.drawable.shijiandian_layout);
        window.setAttributes(lp);
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (who == 1) {
                    about_time.setText(panduanShijina(year,month,day,hour,miniute));
                    ShijiandianClass.DATESTRING = panduanShijina(year,month,day,hour,miniute);
                }
                if (who == 2) {
                    if (edit_input_shijian.getText().toString() != null) {
                        ShijiandianClass.HUIYISHICHANG_TIME = edit_input_shijian.getText().toString();//开会时长
                        shichang.setText(ShijiandianClass.HUIYISHICHANG_TIME);
                        String shichangg = edit_input_shijian.getText().toString();//开会时长

                        if (miniute >= 30) {
                            if (hour >= 24) {
                                hour = 1;
                            } else {
                                hour += 1;
                            }
                            miniute = miniute + Integer.valueOf(shichangg) - 60;
                        } else {
                            miniute += Integer.valueOf(shichangg);
                        }
                        ShijiandianClass.END_DIAN_TIME = panduanShijina(year,month,day,hour,miniute);
                    }
                }
                if (who == 3) {
                    if (edit_input_shijian.getText().toString() != null) {
                        ShijiandianClass.PEOPLE_NUMBER = edit_input_shijian.getText().toString();//人数
                        renshu.setText(ShijiandianClass.PEOPLE_NUMBER);
                    }
                }
                alertDialog.dismiss();
            }
        });
        btn_quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    String panduanShijina(int t_year,int t_month,int t_day,int t_hour,int t_miniute){
        String time = t_year + "-" ;
        if (t_month / 10 == 0) {
            time += "0" + t_month;
        }else{
            time +=   t_month;
        }
        if (t_day / 10 == 0) {
            time +=  "-0" + t_day + " ";
        }else{
            time += t_day + " ";
        }
        if(t_hour/10 ==0){
            time+="0"+t_hour + ":";
        }else{
            time+= t_hour + ":";
        }
        if(t_miniute/10==0){
            time+="0"+t_miniute + ":00";
        }else{
            time+=t_miniute + ":00";
        }
        return time;
    }

    void getShebeiXuqiu() {
        Intent intent = new Intent(getContext(), InvitationPeoActivity.class);
        intent.putExtra("who", "1");
        startActivityForResult(intent, 100);
    }

    void getDidian() {
        Intent intent = new Intent(getContext(), InvitationPeoActivity.class);
        intent.putExtra("who", "2");
        startActivityForResult(intent, 130);
    }

    void getAllType() {
        Intent intent = new Intent(getContext(), InvitationPeoActivity.class);
        intent.putExtra("who", "3");
        startActivityForResult(intent, 140);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 123) {
                String count_num = data.getStringExtra("qqqq");
                Toast.makeText(getContext(), count_num, Toast.LENGTH_SHORT).show();
                shebeixuqiu_num.setText(count_num);
            }
        }
        if (requestCode == 130) {
            if (resultCode == 124) {
                String count_num = data.getStringExtra("qqqq");
                ShijiandianClass.HUIYI_WHERE = count_num;//地点
                Toast.makeText(getContext(), count_num, Toast.LENGTH_SHORT).show();
                huiyishididian.setText(count_num);
            }
        }
        if (requestCode == 140) {
            if (resultCode == 150) {
                String count_num = data.getStringExtra("qqqq");
                Toast.makeText(getContext(), count_num, Toast.LENGTH_SHORT).show();
                huiyishitype.setText(count_num);
            }
        }
    }
}
