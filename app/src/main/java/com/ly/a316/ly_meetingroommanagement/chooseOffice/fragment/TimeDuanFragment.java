package com.ly.a316.ly_meetingroommanagement.chooseOffice.fragment;


import android.content.Intent;
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
public class TimeDuanFragment extends Fragment {


    @BindView(R.id.shijian_duan_start_txt)
    TextView shijianDuanStartTxt;
    @BindView(R.id.shijian_duan_end_txt)
    TextView shijianDuanEndTxt;
    @BindView(R.id.shijian_duan_shebei_txt)
    TextView shijianDuanShebeiTxt;
    @BindView(R.id.shijian_duan_didian_txt)
    TextView shijianDuanDidianTxt;
    @BindView(R.id.shijian_duan_leixing_txt)
    TextView shijianDuanLeixingTxt;
    @BindView(R.id.shijian_duan_renshu_txt)
    TextView shijianDuanRenshuTxt;
    Unbinder unbinder;
    TimePicker time_test_start;//时刻选择控件
    DatePicker ddm_test_start;
    TimePicker time_test_end;//时刻选择控件
    DatePicker ddm_test_end;
    View shijianduan_view;
    AlertDialog alertDialog;
    LinearLayout add_kongjian_linearlayout;//用来添加控件的
    TextView shijiandian_biaotil;
    EditText edit_input_shijian;//输入时间段
    int start_hour, start_miniute, start_year, start_month, start_day;
    int end_hour, end_miniute, end_year, end_month, end_day;

    public TimeDuanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_duan, container, false);
        unbinder = ButterKnife.bind(this, view);
        Calendar cd = Calendar.getInstance();
        start_year = cd.get(Calendar.YEAR);
        start_month = cd.get(Calendar.MONTH);
        start_day = cd.get(Calendar.DATE);
        start_hour = cd.get(Calendar.HOUR_OF_DAY);
        start_miniute = cd.get(Calendar.MINUTE);


        end_year = cd.get(Calendar.YEAR);
        end_month = cd.get(Calendar.MONTH);
        end_day = cd.get(Calendar.DATE);
        end_hour = cd.get(Calendar.HOUR_OF_DAY);
        end_miniute = cd.get(Calendar.MINUTE);
        ShijiandianClass.WHO = "2";
        shijianduan_view = LayoutInflater.from(getContext()).inflate(R.layout.shijiandian_item, null);
        initview();
        return view;
    }

    void initview() {
        ddm_test_start = new DatePicker(getContext());
        time_test_start = new TimePicker(getContext());
        time_test_end = new TimePicker(getContext());
        ddm_test_end = new DatePicker(getContext());
        edit_input_shijian = new EditText(getContext());

        add_kongjian_linearlayout = shijianduan_view.findViewById(R.id.add_shiketime);
        shijiandian_biaotil = shijianduan_view.findViewById(R.id.shijiandian_biaoti);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.shijian_duan_start_click, R.id.shijian_duan_end_click , R.id.shijian_duan_shebei_click, R.id.shijian_duan_didian_click, R.id.shijian_duan_leixing_click, R.id.shijian_duan_renshu_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shijian_duan_start_click:
                toast_dia(1);
                break;
            case R.id.shijian_duan_end_click:
                toast_dia(2);
                break;
            case R.id.shijian_duan_shebei_click:
                getShebeiXuqiu();
                break;
            case R.id.shijian_duan_didian_click:
                getDidian();
                break;
            case R.id.shijian_duan_leixing_click:
                getAllType();
                break;
            case R.id.shijian_duan_renshu_click:
                toast_dia(3);
                break;
        }
    }
    void toast_dia(final int who) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(getContext(), R.style.style_dialog)
                    .setView(shijianduan_view)//在这里把写好的这个listview的布局加载dialog中.setCancelable(false);
                    .setCancelable(false)
                    .create();
        }
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        if (who == 1) {
            shijiandian_biaotil.setText("开始时间");
            if (edit_input_shijian != null) {
                add_kongjian_linearlayout.removeView(edit_input_shijian);
            }
            if (time_test_start != null) {
                add_kongjian_linearlayout.removeView(time_test_start);
            }
            if(time_test_end != null){
                add_kongjian_linearlayout.removeView(time_test_end);
            }
            if (ddm_test_start != null) {
                add_kongjian_linearlayout.removeView(ddm_test_start);
            }
            if (ddm_test_end != null) {
                add_kongjian_linearlayout.removeView(ddm_test_end);
            }
            add_kongjian_linearlayout.addView(ddm_test_start);
            add_kongjian_linearlayout.addView(time_test_start);


            time_test_start.setOnChangeListener(new TimePicker.OnChangeListener() {
                @Override
                public void onChange(int hourr, int munite) {
                    start_hour = hourr;
                    start_miniute = munite;
                }
            });
            ddm_test_start.setOnChangeListener(new DatePicker.OnChangeListener() {
                @Override
                public void onChange(int yearr, int monthh, int dayy, int day_of_weekk) {
                    start_year = yearr;
                    start_month = monthh;
                    start_day = dayy;
                }
            });

        } else if (who == 2) {
            edit_input_shijian.setHint("结束时间");
            if (edit_input_shijian != null) {
                add_kongjian_linearlayout.removeView(edit_input_shijian);
            }
            if (time_test_start != null) {
                add_kongjian_linearlayout.removeView(time_test_start);
            }
            if(time_test_end != null){
                add_kongjian_linearlayout.removeView(time_test_end);
            }
            if (ddm_test_start != null) {
                add_kongjian_linearlayout.removeView(ddm_test_start);
            }
            if (ddm_test_end != null) {
                add_kongjian_linearlayout.removeView(ddm_test_end);
            }
            add_kongjian_linearlayout.addView(ddm_test_end);
            add_kongjian_linearlayout.addView(time_test_end);


            time_test_end.setOnChangeListener(new TimePicker.OnChangeListener() {
                @Override
                public void onChange(int hourr, int munite) {
                    end_hour = hourr;
                    end_miniute = munite;
                }
            });
            ddm_test_end.setOnChangeListener(new DatePicker.OnChangeListener() {
                @Override
                public void onChange(int yearr, int monthh, int dayy, int day_of_weekk) {
                    end_year = yearr;
                    end_month = monthh;
                    end_day = dayy;
                }
            });

        } else if (who == 3) {
            edit_input_shijian.setHint("填入人数");
            shijiandian_biaotil.setText("会议人数");
            if (edit_input_shijian != null) {
                add_kongjian_linearlayout.removeView(edit_input_shijian);
            }
            if (edit_input_shijian != null) {
                add_kongjian_linearlayout.removeView(edit_input_shijian);
            }
            if (time_test_start != null) {
                add_kongjian_linearlayout.removeView(time_test_start);
            }
            if(time_test_end != null){
                add_kongjian_linearlayout.removeView(time_test_end);
            }
            if (ddm_test_start != null) {
                add_kongjian_linearlayout.removeView(ddm_test_start);
            }
            if (ddm_test_end != null) {
                add_kongjian_linearlayout.removeView(ddm_test_end);
            }
            add_kongjian_linearlayout.addView(edit_input_shijian);
        }
        Button btn_sure = shijianduan_view.findViewById(R.id.shijian_sure);//确定
        Button btn_quxiao = shijianduan_view.findViewById(R.id.shijian_quxiao);//取消
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
                    String time = start_year+"-"+start_month+"-"+start_day+" "+start_hour + ":" + start_miniute + ":"+"00";
                    shijianDuanStartTxt.setText(time);
                    ShijiandianClass.START_TIME = time;
                }
                if (who == 2) {
                    if (edit_input_shijian.getText().toString() != null) {
                        String time = end_year+"-"+end_month+"-"+end_day+" "+end_hour + ":" + end_miniute + ":"+"00";
                        ShijiandianClass.END_TIME = time;
                        shijianDuanEndTxt.setText(ShijiandianClass.END_TIME);
                    }
                }
                if (who == 3) {
                    if (edit_input_shijian.getText().toString() != null) {
                        ShijiandianClass.PEOPLE_NUMBER = edit_input_shijian.getText().toString();//人数
                        shijianDuanRenshuTxt.setText(ShijiandianClass.PEOPLE_NUMBER);
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
                shijianDuanShebeiTxt.setText(count_num);
            }
        }
        if (requestCode == 130) {
            if (resultCode == 124) {
                String count_num = data.getStringExtra("qqqq");
                ShijiandianClass.HUIYI_WHERE = count_num;//地点
                Toast.makeText(getContext(), count_num, Toast.LENGTH_SHORT).show();
                shijianDuanDidianTxt.setText(count_num);
            }
        }
        if (requestCode == 140) {
            if (resultCode == 150) {
                String count_num = data.getStringExtra("qqqq");
                Toast.makeText(getContext(), count_num, Toast.LENGTH_SHORT).show();
                shijianDuanLeixingTxt.setText(count_num);
            }
        }
    }
}
