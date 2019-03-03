package com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_one.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
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
import android.widget.TimePicker;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_one.customview.right_view;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dian_fragemnt extends Fragment {


    @BindView(R.id.click_shijiandian)
    LinearLayout clickShijiandian;
    @BindView(R.id.dian_shijianxuanze)
    LinearLayout dian_shijianxuanze;
    @BindView(R.id.shijian_surer)
    Button shijian_sure;
    @BindView(R.id.shijiandian_date_picker)
    DatePicker shijiandian_date_picker;
    @BindView(R.id.shijiandian_time_picker)
    TimePicker shijiandian_time_picker;
    @BindView(R.id.shijiandian_shichang)
    EditText shijiandian_shichang;
    @BindView(R.id.can_renshu)
    EditText can_renshu;
    Unbinder unbinder;
    View view;
    @BindView(R.id.xiala_one)
    ImageView xialaOne;
    @BindView(R.id.rerererer)
    right_view right_view;
    @BindView(R.id.ren_rererererr)
    right_view ren_rererererr;
    boolean open_close = true;//true为关闭状态
    int year, month, day, hour, miniute;
    private String hui_long_time,hui_renshu;

    public Dian_fragemnt() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dian_fragemnt, container, false);
        unbinder = ButterKnife.bind(this, view);
        initview();
        return view;
    }

    void initview() {
        shijiandian_shichang.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!shijiandian_shichang.getText().toString().trim().equals("")) {
                    right_view.start();
                    hui_long_time = shijiandian_shichang.getText().toString().trim();
                }else{
                    right_view.reset();
                }
            }
        });
        can_renshu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!can_renshu.getText().toString().equals("")){
                    ren_rererererr.start();
                    hui_renshu = can_renshu.getText().toString().trim();
                }else{
                    ren_rererererr.reset();
                }
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.click_shijiandian, R.id.shijian_surer, R.id.shijian_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.click_shijiandian:
                rotation(xialaOne);
                break;
            case R.id.shijian_surer:
                rotation(xialaOne);
                dian_shijianxuanze.setVisibility(View.GONE);
                huoqushijian();
                /*退出动画
                Animation mHiddenAction = AnimationUtils.loadAnimation(getContext(), R.anim.tran_bottom);
                dian_shijianxuanze.startAnimation(mHiddenAction);*/
                break;
            case R.id.shijian_cancel:
                rotation(xialaOne);
                dian_shijianxuanze.setVisibility(View.GONE);
                break;

        }
    }

    void rotation(View ima_view) {
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
                if (!open_close) {
                    dian_shijianxuanze.setVisibility(View.VISIBLE);
                    addLayoutAnimation(dian_shijianxuanze);
                } else {
                    dian_shijianxuanze.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void addLayoutAnimation(ViewGroup view) {
        Animation animation;
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.tran_top);
        LayoutAnimationController layoutAnimationController = new LayoutAnimationController(animation);
        /*layoutAnimationController.setDelay(0.3f);*/
        layoutAnimationController.setOrder(LayoutAnimationController.ORDER_NORMAL);
        view.setLayoutAnimation(layoutAnimationController);

    }

    void huoqushijian() {
        year = shijiandian_date_picker.getYear();
        month = shijiandian_date_picker.getMonth() + 1;
        day = shijiandian_date_picker.getDayOfMonth();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hour = shijiandian_time_picker.getHour();
            miniute = shijiandian_time_picker.getMinute();
        }

    }
}
