package com.ly.a316.ly_meetingroommanagement.meetingList.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ly.a316.ly_meetingroommanagement.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeetingDetailActivity extends AppCompatActivity {
    private String mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_detail);
        ButterKnife.bind(this);
        mId=getIntent().getStringExtra("mId");
    }
    public static final void start(Context context, String mId) {
        Intent intent = new Intent();
        intent.putExtra("mId",mId);
        intent.setClass(context, MeetingDetailActivity.class);
        context.startActivity(intent);
    }
    @OnClick({R.id.back_ll, R.id.do_something, R.id.meeting_people_ll, R.id.signature_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_ll:
                break;
            case R.id.do_something:
                break;
            case R.id.meeting_people_ll:
                AttendeeActivity.start(MeetingDetailActivity.this,mId);
                break;
            case R.id.signature_ll:
                break;
        }
    }
}
