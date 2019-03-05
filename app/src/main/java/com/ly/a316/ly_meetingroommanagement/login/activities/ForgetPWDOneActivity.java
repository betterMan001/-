package com.ly.a316.ly_meetingroommanagement.login.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.CountDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPWDOneActivity extends BaseActivity {

    @BindView(R.id.act_forget_pwd_phone_et)
    EditText actForgetPwdPhoneEt;
    @BindView(R.id.act_forget_pwd_identifying_code_et)
    EditText actForgetPwdIdentifyingCodeEt;
    private static final String TAG = "forgetPWD1:";
    private final String NO_EMPTY_ACCOUNT = "账号不能为空！";
    private final String NO_EMPTY_PWD = "密码不能为空！";
    private final String NO_EMPTY_VWD = "验证码不能为空！";
    String phone = "";
    //mob短信验证的监听事件
    EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            Log.d(TAG, "handleMessage: 成功发送了短信验证码");
                        } else {
                            // TODO 处理错误的结果
                            ((Throwable) data).printStackTrace();
                            Log.d(TAG, "handleMessage: 发送短信验证码失败");
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                                ForgetPWDTwoActivity.start(ForgetPWDOneActivity.this,phone);
                                finish();
                        } else {
                            // TODO 处理错误的结果
                            Log.d(TAG, "handleMessage: 短信验证失败");
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };
    public static final void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ForgetPWDOneActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor(R.color.classical_blue).init();
        setContentView(R.layout.activity_forget_pwdone);
        ButterKnife.bind(this);
        initMobMessage();
        SMSSDK.setAskPermisionOnReadContact(true);

    }
    private void initMobMessage() {
        SMSSDK.registerEventHandler(eventHandler);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
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
                getIdentifyCode();
                break;
                //跳转到重置密码界面
            case R.id.act_forget_pwdOne_bt:
                turnNext();
                break;
        }
    }
    private void getIdentifyCode(){
        phone += this.actForgetPwdPhoneEt.getText().toString();
        CountDownButton button=findViewById(R.id.act_forget_pwdone_get_identifying_code);
        if (button.isFinish()) {
            //发送验证码请求成功后调用
            button.start();
        }
        String pwd = "";
        if (!("".equals(phone)))
            SMSSDK.getVerificationCode("86", phone);
        else
            subThreadToast(NO_EMPTY_ACCOUNT);

    }
    private void turnNext(){
        String identifyCode=this.actForgetPwdIdentifyingCodeEt.getText().toString();
        if(identifyCode==null||"".equals(identifyCode)){
            subThreadToast(NO_EMPTY_VWD);
        }
        else
        SMSSDK.submitVerificationCode("86", phone,identifyCode);
    }
}
