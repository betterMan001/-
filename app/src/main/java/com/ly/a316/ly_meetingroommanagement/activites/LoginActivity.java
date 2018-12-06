package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.act_hint_title)
    TextView actHintTitle;
    @BindView(R.id.login_s_userID_et)
    EditText loginSUserIDEt;
    @BindView(R.id.login_s_pwd_et)
    EditText loginSPwdEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
//监听事件
    @OnClick({R.id.act_login_sign_up, R.id.login_button, R.id.act_message_verification, R.id.act_forget_password})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            //跳转到注册界面
            case R.id.act_login_sign_up:
                intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                break;
                //登录
            case R.id.login_button:
                login();
                break;
                //短信验证登录
            case R.id.act_message_verification:
                break;
                //找回密码
            case R.id.act_forget_password:
                intent=new Intent(LoginActivity.this,ForgetPWDOneActivity.class);
                startActivity(intent);
                break;
        }

    }
    void login(){
        /*
        测试：不登陆账号
        */
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
