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

public class ForgetPWDTwoActivity extends AppCompatActivity {

    @BindView(R.id.act_forget_pwdtwo_et)
    EditText actForgetPwdtwoEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwdtwo);
        ButterKnife.bind(this);
        //状态栏沉浸效果
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor("#00A7FF").init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @OnClick({R.id.act_forget_pwdtwo_back_ll, R.id.act_forget_pwdtwo_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回上一级界面
            case R.id.act_forget_pwdtwo_back_ll:
                break;
                //提交新的密码
            case R.id.act_forget_pwdtwo_bt:
                break;
        }
    }
}
