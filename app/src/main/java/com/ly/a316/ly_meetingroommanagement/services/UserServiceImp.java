package com.ly.a316.ly_meetingroommanagement.services;

import com.ly.a316.ly_meetingroommanagement.login.activities.LoginActivity;
import com.ly.a316.ly_meetingroommanagement.login.models.UserInfoModel;
import com.ly.a316.ly_meetingroommanagement.utils.MathUtil;
import com.ly.a316.ly_meetingroommanagement.utils.MyHttpUtil;
import com.ly.a316.ly_meetingroommanagement.utils.MyJSONUtil;
import com.ly.a316.ly_meetingroommanagement.utils.Net;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
Date:2018/12/28
Time:15:16
auther:xwd
*/
public class UserServiceImp implements UserService {
    LoginActivity loginActivity;
    private static final String TAG = "loginService:";
    public UserServiceImp(LoginActivity loginActivity) {
        this.loginActivity=loginActivity;
    }

    @Override
    public void loginValidate(String username, String password, String loginType) {
        String URL= Net.HEAD+Net.LOGIN;
        Log.d(TAG, "loginValidate: "+username+" "+MathUtil.getMd5(password)+" "+loginType);
        //提交账号信息采用post传输
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("employeeId",username);
        builder.add("password",MathUtil.getMd5(password));
        builder.add("type",loginType);
        RequestBody body=builder.build();
        MyHttpUtil.sendOkhttpPostRequest(URL, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                loginActivity.subThreadToast(Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    Log.d(TAG,"登录获取服务器信息成功");
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    String result=jsonObject.getString("result");
                    UserInfoModel model=new UserInfoModel();
                    //如果map的json对象为空则直接输出结果
                    if(jsonObject.isNull("map")==true){

                    }else{
                        JSONObject jsonObject1=jsonObject.getJSONObject("map");
                        model= MyJSONUtil.toObject(String.valueOf(jsonObject1),UserInfoModel.class);
                    }
                    loginActivity.loginCallBack(result,model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
