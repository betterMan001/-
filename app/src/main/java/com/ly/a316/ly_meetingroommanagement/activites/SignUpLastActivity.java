package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.nim.model.Extras;
import com.ly.a316.ly_meetingroommanagement.utils.PointConst;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpLastActivity extends BaseActivity {

    @BindView(R.id.act_sign_up_name)
    EditText actSignUpName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_last);
        ButterKnife.bind(this);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor("#00A7FF").init();
    }
    public  static  final  void start(Context context, String phoneNumber,String password){
        Intent intent=new Intent();
        intent.putExtra("phoneNumber",phoneNumber);
        intent.putExtra("password",password);
        intent.setClass(context,SignUpLastActivity.class);
        context.startActivity(intent);
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
                register();
                break;
        }
    }
    private void register(){
        //1.先判断昵称是否已经正确输入
        String nickName=actSignUpName.getText().toString();
        if("".equals(nickName)||nickName==null){
             subThreadToast(PointConst.NO_EMPTY_NICK_NAME);
        }
        else{
            //2.获得账号和密码
            Intent intent=getIntent();
            String phoneNumber= intent.getExtras().getString("phoneNumber","");
            String password=intent.getExtras().getString("password","");
            String imageUrl=getImagUrl();
            //发送注册信息到后台服务器

        }
    }
    private String getImagUrl(){
        //测试默认返回本地头像
        return MyApplication.getImageURL();
    }
    private void getPicture(){

    }
    //对注册的结果进行反馈
    public void callBack(String re){
      //对注册结果进行不同的反馈
        final String result=re;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //0不是合法的号码
                if("0".equals(result)){
                    subThreadToast("对不起，该号码不是员工的号码");
                }else if("1".equals(result)){
                    //注册成功
                    subThreadToast("注册成功！");
                }else{
                    //号码已经注册过了
                    subThreadToast("该号码已经被注册！");
                }
            }
        });
    }
}
