package com.ly.a316.ly_meetingroommanagement.services;

import com.ly.a316.ly_meetingroommanagement.login.activities.SignUpLastActivity;
import com.ly.a316.ly_meetingroommanagement.utils.MathUtil;
import com.ly.a316.ly_meetingroommanagement.utils.MyHttpUtil;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
Date:2018/12/30
Time:16:27
auther:xwd
*/
public class SignUpServiceImp implements SignUpService {
    SignUpLastActivity activity;

    public SignUpServiceImp(SignUpLastActivity activity) {
        this.activity = activity;
    }

    @Override
    public void signUp(String account, String nickName, String profile, String password) {
        String URL= Net.HEAD+Net.REGISTER;
        boolean flag=true;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("account",account);
        builder.add("nickName",nickName);
        builder.add("profile",profile);
        builder.add("password", MathUtil.getMd5(password));
        RequestBody body=builder.build();
        MyHttpUtil.sendOkhttpPostRequest(URL, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                activity.subThreadToast(Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                response.close();
                //接受返回的jsonObject
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(result);
                    //JSONObject jsonObject1=jsonObject.getJSONObject("result");
                    String comeOut=jsonObject.getString("result");
                    activity.callBack(comeOut);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
