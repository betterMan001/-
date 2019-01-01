package com.ly.a316.ly_meetingroommanagement.services;

import com.ly.a316.ly_meetingroommanagement.activites.ForgetPWDTwoActivity;
import com.ly.a316.ly_meetingroommanagement.utils.MathUtil;
import com.ly.a316.ly_meetingroommanagement.utils.MyHttpUtil;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
Date:2018/12/29
Time:21:25
auther:xwd
*/
public class ModifiyPWDServiceImp implements ModifiyPWDService {
    private ForgetPWDTwoActivity activity;

    public ModifiyPWDServiceImp(ForgetPWDTwoActivity activity) {
        this.activity = activity;
    }

    @Override
    public void ModifiyPWD(String phoneNumber, String PWD) {
        String URL= Net.HEAD+Net.CHANGE_PWD;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone",phoneNumber);
        builder.add("password", MathUtil.getMd5(PWD));
        RequestBody body=builder.build();
        MyHttpUtil.sendOkhttpPostRequest(URL, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.subThreadToast(Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
             activity.subThreadToast("修改密码成功！");
            }
        });
    }
}
