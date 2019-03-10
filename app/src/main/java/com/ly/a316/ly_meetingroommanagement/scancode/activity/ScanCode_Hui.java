package com.ly.a316.ly_meetingroommanagement.scancode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.necer.calendar.WeekCalendar;
import com.necer.entity.NDate;
import com.necer.listener.OnWeekSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanCode_Hui extends AppCompatActivity {


    @BindView(R.id.weekCalendar)
    WeekCalendar weekCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code__hui);
        ButterKnife.bind(this);
        weekCalendar.setOnWeekSelectListener(new OnWeekSelectListener() {
            @Override
            public void onWeekSelect(NDate date, boolean isClick) {
                String ndsa = String.valueOf(date.localDate.getDayOfWeek());
                Toast.makeText(ScanCode_Hui.this,ndsa,Toast.LENGTH_SHORT).show();
            }
        });

    }


}
