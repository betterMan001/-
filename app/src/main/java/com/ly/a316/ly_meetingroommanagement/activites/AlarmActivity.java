package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.classes.Alarm;
import com.ly.a316.ly_meetingroommanagement.fragments.CalendarFragment;
import com.netease.nim.uikit.business.robot.parser.elements.group.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlarmActivity extends BaseActivity {

    @BindView(R.id.alarm_toolbar)
    Toolbar alarmToolbar;
    @BindView(R.id.alarm_recycleview)
    RecyclerView alarm_recycleview;
    List<Alarm> list = new ArrayList<>();
    Alarm alarm;
    AlarmAdapter alarmAdapter;
    @BindView(R.id.alarm)
    TextView alarmText;
    String choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        choose = intent.getStringExtra("choose");
        alarmText.setText(title);
        alarmToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initdata();
        alarmAdapter = new AlarmAdapter();
        alarm_recycleview.setAdapter(alarmAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        alarm_recycleview.setLayoutManager(linearLayoutManager);
    }

    void initdata() {
        if(choose.equals("2")){
            list.clear();
            alarm = new Alarm("永不", false);
            list.add(alarm);
            alarm = new Alarm("每天", false);
            list.add(alarm);
            alarm = new Alarm("每周", false);
            list.add(alarm);
            alarm = new Alarm("每月", false);
            list.add(alarm);
            alarm = new Alarm("每年", false);
            list.add(alarm);
            alarm = new Alarm("自定义", false);
            list.add(alarm);
        }else if(choose.equals("1")||choose.equals("3")) {
            list.clear();
            alarm = new Alarm("无", false);
            list.add(alarm);
            alarm = new Alarm("日程发生时", false);
            list.add(alarm);
            alarm = new Alarm("5分钟前", false);
            list.add(alarm);
            alarm = new Alarm("15分钟前", false);
            list.add(alarm);
            alarm = new Alarm("30分钟前", false);
            list.add(alarm);
            alarm = new Alarm("1小时前", false);
            list.add(alarm);
            alarm = new Alarm("2小时前", false);
            list.add(alarm);
            alarm = new Alarm("1天前", false);
            list.add(alarm);
            alarm = new Alarm("2天前", false);
            list.add(alarm);
        }
    }

    class AlarmAdapter extends RecyclerView.Adapter {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(AlarmActivity.this).inflate(R.layout.alarm_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
            ((MyViewHolder) viewHolder).textView.setText(list.get(i).getName().toString());

            if (list.get(i).isSign()) {
                ((MyViewHolder) viewHolder).gou.setVisibility(View.VISIBLE);
            }
            ((MyViewHolder) viewHolder).alarm_linearlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(i).isSign()) {
                        ((MyViewHolder) viewHolder).gou.setVisibility(View.GONE);
                        list.get(i).setSign(false);
                    } else {
                        ((MyViewHolder) viewHolder).gou.setVisibility(View.VISIBLE);
                        list.get(i).setSign(true);
                    }
                    if(choose.equals("1")){
                        Intent intent1 = new Intent(AlarmActivity.this, CalendarFragment.class);
                        AlarmActivity.this.setResult(i, intent1);
                        finish();
                    }
                    else if(choose.equals("2")){
                        Intent intent1 = new Intent(AlarmActivity.this, AddSchedule.class);
                        AlarmActivity.this.setResult(i, intent1);
                        finish();
                    }
                    else if(choose.equals("3")){
                        Intent intent1 = new Intent(AlarmActivity.this, AddSchedule.class);
                        AlarmActivity.this.setResult(i, intent1);
                        finish();
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            android.widget.LinearLayout alarm_linearlayout;
            ImageView gou;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.alarm_item);
                alarm_linearlayout = itemView.findViewById(R.id.alarm_linearlayout);
                gou = itemView.findViewById(R.id.alarm_gou);
            }
        }
    }

}
