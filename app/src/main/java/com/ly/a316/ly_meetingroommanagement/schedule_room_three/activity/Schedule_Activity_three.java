package com.ly.a316.ly_meetingroommanagement.schedule_room_three.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.fragment.MyDialog;
import com.ly.a316.ly_meetingroommanagement.schedule_room_three.fragment.MyDialogment;

public class Schedule_Activity_three extends AppCompatActivity {
    MyDialogment myDialogment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__two);
        myDialogment = new MyDialogment();
        myDialogment.show(getSupportFragmentManager(),"ma");
    }
}
