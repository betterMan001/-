package com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.fragment.MyDialog;

public class Schedule_Activity extends AppCompatActivity {
    MyDialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_);
        myDialog = new MyDialog();
        myDialog.show(getSupportFragmentManager(),"mydialog");
    }
}
