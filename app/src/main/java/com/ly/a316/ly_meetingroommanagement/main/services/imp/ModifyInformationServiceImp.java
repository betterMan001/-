package com.ly.a316.ly_meetingroommanagement.main.services.imp;

import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.main.fragment.MineFragment;
import com.ly.a316.ly_meetingroommanagement.main.services.ModifyInformationService;
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
Date:2019/3/5
Time:18:05
auther:xwd
*/
public class ModifyInformationServiceImp  implements ModifyInformationService {
    MineFragment fragment;

    public ModifyInformationServiceImp(MineFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void changeProfile(String phone, String image) {

        String URL= Net.HEAD+Net.CHANGE_PROFILE;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("phone",phone);
        builder.add("image",image);
        RequestBody body=builder.build();
        MyHttpUtil.sendOkhttpPostRequest(URL,body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                fragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(),Net.FAIL,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                fragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(),"修改头像成功!",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void changeNickname(String phone, String nickname) {
        String URL= Net.HEAD+Net.CHANGE_NICKNAME+"?phone="+phone+"&nickname="+nickname;
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                fragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(),Net.FAIL,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                fragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(),"修改名称成功!",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
