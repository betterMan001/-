package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPWDOneActivity extends AppCompatActivity {

    @BindView(R.id.act_forget_pwd_phone_et)
    EditText actForgetPwdPhoneEt;
    @BindView(R.id.act_forget_pwd_identifying_code_et)
    EditText actForgetPwdIdentifyingCodeEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwdone);
        ButterKnife.bind(this);
        //状态栏沉浸效果
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor("#00A7FF").init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @OnClick({R.id.act_forget_pwd_back_ll, R.id.act_forget_pwdone_get_identifying_code, R.id.act_forget_pwdOne_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回上一个页面
            case R.id.act_forget_pwd_back_ll:
                finish();
                break;
                //获取验证码
            case R.id.act_forget_pwdone_get_identifying_code:
                break;
                //跳转到重置密码界面
            case R.id.act_forget_pwdOne_bt:
                turnNext();
                break;
        }
    }
    private void turnNext(){
        Intent intent=new Intent(ForgetPWDOneActivity.this,ForgetPWDTwoActivity.class);
        startActivity(intent);
    }
}
