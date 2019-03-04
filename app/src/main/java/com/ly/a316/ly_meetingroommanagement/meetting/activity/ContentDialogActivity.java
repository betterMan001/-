package com.ly.a316.ly_meetingroommanagement.meetting.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingDetailActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.widgets.NoteEditor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentDialogActivity extends AppCompatActivity {

    @BindView(R.id.note_edit_et)
    NoteEditor noteEditEt;
    ActionBar actionBar;
    //表示要显示的东西的类型
    /*
    * 1是预订会议详情界面
    * 2是会议详情界面
    * */
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //关闭自带的标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customize_dialog);
        ButterKnife.bind(this);
        //获得对话框类型(1.预定会议详情的会议纪要2.会议详情界面的会议纪要)
        type=getIntent().getStringExtra("type");
        initView();
    }

    public static final void start(Context context,String type) {
        Intent intent = new Intent();
        intent.putExtra("type",type);
        intent.setClass(context,ContentDialogActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        noteEditEt.setNotesMinLines(7);
        //预订会议详情界面
        if("1".equals(type))
          noteEditEt.setText(OrderDetailMeetingActivity.meeting_content);
        //会议详情界面
        else
          noteEditEt.setText(MeetingDetailActivity.model.content);
    }

    @OnClick(R.id.close_iv)
    public void onViewClicked() {
        if("1".equals(type))
        OrderDetailMeetingActivity.meeting_content=noteEditEt.getText().toString();
        finish();
    }
}
