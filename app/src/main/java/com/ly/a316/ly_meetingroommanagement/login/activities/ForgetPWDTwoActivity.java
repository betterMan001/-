package com.ly.a316.ly_meetingroommanagement.login.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.login.services.ModifiyPWDServiceImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPWDTwoActivity extends BaseActivity {

    @BindView(R.id.act_forget_pwdtwo_et)
    EditText actForgetPwdtwoEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor("#00A7FF").init();
        setContentView(R.layout.activity_forget_pwdtwo);
        ButterKnife.bind(this);

    }
    public  static  final  void start(Context context,String phoneNumber){
        Intent intent=new Intent();
        intent.putExtra("phoneNumber",phoneNumber);
        intent.setClass(context,ForgetPWDTwoActivity.class);
        context.startActivity(intent);
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
                finish();
                break;
                //提交新的密码
            case R.id.act_forget_pwdtwo_bt:
                register();
                break;
        }
    }
    private void register(){
        //1先判断是否已经输入密码
        String pwd=actForgetPwdtwoEt.getText().toString();
        if(pwd==null||"".equals(pwd)){
            Toast.makeText(this,"密码不能为空!",Toast.LENGTH_SHORT).show();
        }
        else{
            //2.获得输入的密码和来自上一个活动的电话号码
            Intent intent=getIntent();
            String phoneNum=intent.getExtras().getString("phoneNumber");
            //3.向服务器发送修改密码请求
             new ModifiyPWDServiceImp(ForgetPWDTwoActivity.this).ModifiyPWD(phoneNum,pwd);

        }


    }
}
