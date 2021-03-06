package com.ly.a316.ly_meetingroommanagement.login.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.CountDownButton;
import com.ly.a316.ly_meetingroommanagement.utils.PointConst;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.act_sign_up_phonenum)
    EditText actSignUpPhonenum;
    @BindView(R.id.act_sign_up_identifying_code)
    EditText actSignUpIdentifyingCode;
    @BindView(R.id.get_identifying_code)
    CountDownButton getIdentifyingCode;
    String phone="";
    private static final String TAG = "SignUpActivity:";
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
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            Log.d(TAG, "handleMessage: 成功发送了短信验证码");
                        } else {
                            Log.d(TAG, "handleMessage: 发送短信验证码失败");
                            ((Throwable) data).printStackTrace();

                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            Toast.makeText(SignUpActivity.this,"验证码通过！",Toast.LENGTH_SHORT).show();
                            SignUpDetailActivty.start(SignUpActivity.this,phone);
                            finish();
                        } else {
                            subThreadToast("验证码验证失败");
                            Log.d(TAG, "handleMessage: 短信验证失败");
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    return false;
                }
            }).sendMessage(msg);
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        //初始化验证码设置
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor("#00A7FF").init();
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        //初始化视图
        initView();
    }

    private void initView() {
        //添加验证码下划线
        getIdentifyingCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }


    @OnClick({R.id.act_sign_up_back_ll, R.id.act_sign_up_bt,R.id.get_identifying_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.act_sign_up_back_ll:
                finish();
                break;
            case R.id.act_sign_up_bt:
                turnNext();
                break;
                //获取验证码
            case R.id.get_identifying_code:
                getIdentifyCode();
                break;
        }
    }
    private void getIdentifyCode(){
        //1.点击后先判断账号是否为空
        phone += this.actSignUpPhonenum.getText().toString();
        if (!("".equals(phone))){
            //2.开启倒计时的动画
            if (getIdentifyingCode.isFinish()) {
                //发送验证码请求成功后调用
                getIdentifyingCode.start();
            }
            //3.调用mod短信验证接口发送短信
            SMSSDK.getVerificationCode("86", phone);
        }
        else
            Toast.makeText(this,PointConst.NO_EMPTY_ACCOUNT,Toast.LENGTH_SHORT).show();
    }
    private void turnNext(){
        phone="";
        phone += this.actSignUpPhonenum.getText().toString();
        String identifyCode=this.actSignUpIdentifyingCode.getText().toString();
        //1.先判断账号是否为空
        if (!("".equals(phone))){
            //2.判断验证码是否为空
            if(identifyCode==null||"".equals(identifyCode)){
                subThreadToast(PointConst.NO_EMPTY_VWD);
            }
            else{
                //测试接口不
                  SignUpDetailActivty.start(SignUpActivity.this,"18248618633");
                //SMSSDK.submitVerificationCode("86", phone,identifyCode);
                //Log.d(TAG, "turnNext: test");

            }
        } else{
            subThreadToast(PointConst.NO_EMPTY_ACCOUNT);
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!getIdentifyingCode.isFinish()) {
            getIdentifyingCode.cancel();
        }
    }
}
