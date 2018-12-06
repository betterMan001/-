package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.act_sign_up_phonenum)
    EditText actSignUpPhonenum;
    @BindView(R.id.act_sign_up_identifying_code)
    EditText actSignUpIdentifyingCode;
    @BindView(R.id.get_identifying_code)
    TextView getIdentifyingCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        //状态栏沉浸效果
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor("#00A7FF").init();
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
            case R.id.get_identifying_code:
                break;
        }
    }
    private void turnNext(){
        Intent intent=new Intent(SignUpActivity.this,SignUpDetailActivty.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
