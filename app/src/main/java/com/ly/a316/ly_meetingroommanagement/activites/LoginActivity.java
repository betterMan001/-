package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.nim.DemoCache;
import com.ly.a316.ly_meetingroommanagement.nim.user_info.UserPreferences;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;

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
    private static final String TAG = "LoginActivity";
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_identifying_code)
    TextView getIdentifyingCode;
    @BindView(R.id.verification_ll)
    LinearLayout verificationLl;
    //切换登录模式的标记
    boolean flag = false;
    @BindView(R.id.password_tv)
    TextView passwordTv;
    @BindView(R.id.act_message_verification)
    TextView actMessageVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    //监听事件
    @OnClick({R.id.act_login_sign_up, R.id.login_button, R.id.act_message_verification, R.id.act_forget_password})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //跳转到注册界面
            case R.id.act_login_sign_up:
                intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            //登录
            case R.id.login_button:
                login();
                break;
            //短信验证登录
            case R.id.act_message_verification:
                changeLoginMod();
                break;
            //找回密码
            case R.id.act_forget_password:
                intent = new Intent(LoginActivity.this, ForgetPWDOneActivity.class);
                startActivity(intent);
                break;
        }

    }

    private void changeLoginMod() {
        //正常账号登录转短信验证
        if (flag == false) {
            flag = true;
            this.loginSPwdEt.setVisibility(View.GONE);
            this.passwordTv.setText("验证码");
            this.actMessageVerification.setText("密码登录");
            this.verificationLl.setVisibility(View.VISIBLE);
        } else {
            flag = false;
            this.verificationLl.setVisibility(View.GONE);
            this.passwordTv.setText("密码");
            this.actMessageVerification.setText("验证码登录");
            this.loginSPwdEt.setVisibility(View.VISIBLE);
        }

    }

    void login() {
        /*
        测试：不登陆账号
        */
        final String account = "badMan";
        String token = "123456";
        LoginInfo info = new LoginInfo(account, token); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用

                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        Log.d(TAG, "onSuccess: 登录成功！");

                        DemoCache.setAccount(account);
                        //自己管理不用云信的
//                        saveLoginInfo(account, token);
                        // 初始化消息提醒配置
                        initNotificationConfig();
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.d(TAG, "onFailed:登录失败了！");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.d(TAG, "onException: 登录异常！");
                    }
                };
        NimUIKit.login(info, callback);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initNotificationConfig() {
        // 初始化消息提醒（先默认开启）
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }

    @OnClick(R.id.act_message_verification)
    public void onViewClicked() {
    }
}
