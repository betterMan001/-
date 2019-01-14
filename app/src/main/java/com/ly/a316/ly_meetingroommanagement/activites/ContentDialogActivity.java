package com.ly.a316.ly_meetingroommanagement.activites;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.NoteEditor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContentDialogActivity extends AppCompatActivity {

    @BindView(R.id.note_edit_et)
    NoteEditor noteEditEt;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //关闭自带的标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.customize_dialog);

        ButterKnife.bind(this);
        initView();
    }

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,ContentDialogActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        noteEditEt.setNotesMinLines(5);
    }

    @OnClick(R.id.close_iv)
    public void onViewClicked() {
         finish();
    }
}
