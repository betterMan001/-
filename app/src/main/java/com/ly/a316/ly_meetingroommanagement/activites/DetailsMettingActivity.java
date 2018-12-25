package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.classes.MettingPeople;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsMettingActivity extends BaseActivity {

    TimePickerView.Type[] type = new TimePickerView.Type[2];
    String time = "";//开会时间
    @BindView(R.id.detail_reserve_sure)
    TextView detailReserveSure;
    @BindView(R.id.detail_launch_sure)
    Button detailLaunchSure;//发起会议
    @BindView(R.id.detail_place)
    TextView detailPlace;//会议地点
    @BindView(R.id.detail_ptheme)
    EditText detailPtheme;//会议主题
    @BindView(R.id.detail_data)
    TextView detailData;//会议日期
    @BindView(R.id.detail_time)
    TextView detailTime;//会议日期
    @BindView(R.id.detail_people)
    EditText detailPeople;//发起会议的人
    @BindView(R.id.detail_detail)
    EditText detailDetail;//会议内容
    @BindView(R.id.detail_Participant)
    Button detailParticipant;//参与人员
    @BindView(R.id.Sche_back)
    ImageView Sche_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_metting);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        detailPlace.setText(intent.getStringExtra("hui"));
    }


    @OnClick({R.id.detail_launch_sure, R.id.detail_data, R.id.detail_time, R.id.detail_Participant, R.id.Sche_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.detail_launch_sure:
                break;
            case R.id.detail_data:
                chooseTime(1);
                break;
            case R.id.detail_time:
                chooseTime(2);
                break;
            case R.id.detail_Participant:
                Intent intent = new Intent(DetailsMettingActivity.this, InvitationPeoActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.Sche_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 123) {

            Bundle MoonBuddle = data.getExtras();
            List<MettingPeople> l = (List<MettingPeople>) MoonBuddle.getSerializable("qqqq");

            Toast.makeText(DetailsMettingActivity.this, l.size() + "fgdsgd", Toast.LENGTH_SHORT).show();
        }
    }

    public void chooseTime(final int a) {
        if (a == 1) {
            type[0] = TimePickerView.Type.YEAR_MONTH_DAY;
        } else {
            type[0] = TimePickerView.Type.HOURS_MINS;
        }

        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (a == 1) {
                    SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
                    String time = format.format(date);
                    detailData.setText(time);
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("aHH:mm");
                    if (!time.contains("-")) {
                        time += format.format(date) + "-";
                    } else {
                        time += format.format(date);
                    }
                    detailTime.performLongClick();
                    detailTime.setText(time);
                }
            }
        }).setType(type[0])//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.setDate(java.util.Calendar.getInstance());
        pvTime.show();
    }

}
