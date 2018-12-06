package com.ly.a316.ly_meetingroommanagement.activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpLastActivity extends AppCompatActivity {

    @BindView(R.id.act_sign_up_name)
    EditText actSignUpName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_last);
        ButterKnife.bind(this);
        //状态栏沉浸效果
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor("#00A7FF").init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @OnClick({R.id.act_sign_up_last_back_ll, R.id.act_sign_up_head, R.id.act_sign_up_last_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //退回到上一个界面
            case R.id.act_sign_up_last_back_ll:
                finish();
                break;
                //点击选择头像
            case R.id.act_sign_up_head:
                getPicture();
                break;
                //提交账号信息
            case R.id.act_sign_up_last_bt:
                break;
        }
    }
    private void getPicture(){

    }

}
