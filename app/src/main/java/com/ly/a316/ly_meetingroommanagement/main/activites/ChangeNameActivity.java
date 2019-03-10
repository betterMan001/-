package com.ly.a316.ly_meetingroommanagement.main.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.fragment.MineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeNameActivity extends AppCompatActivity {

    @BindView(R.id.change_name)
    EditText changeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关闭自带的标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_name);
        ButterKnife.bind(this);
    }

    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ChangeNameActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.hideNegativeBtn, R.id.hide_PositiveBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hideNegativeBtn:
                MineFragment.type="1";
                finish();
                break;
            case R.id.hide_PositiveBtn:
                MineFragment.type="2";
                MineFragment.newName=changeName.getText().toString();
                finish();
                break;
        }
    }
}
