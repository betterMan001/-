package com.ly.a316.ly_meetingroommanagement.meetingList.services.imp;

import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.meetingList.activities.SignInActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.SignInService;
import com.ly.a316.ly_meetingroommanagement.utils.DisplayUtils;
import com.ly.a316.ly_meetingroommanagement.utils.MyHttpUtil;
import com.ly.a316.ly_meetingroommanagement.utils.MyJSONUtil;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
Date:2019/2/22
Time:19:11
auther:xwd
*/
public class SignInServiceImp implements SignInService {
    SignInActivity activity;
    private static final String TAG = "SignInServiceImp";
    public SignInServiceImp(SignInActivity activity) {
        this.activity = activity;
    }

    @Override
    public void signInCase(String mId) {
     String URL= Net.HEAD+Net.SIGN_INCASE+"?mId="+mId;
        Log.d(TAG, URL);
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DisplayUtils.subThreadToast(activity,Net.FAIL);
                activity.loadingDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                response.close();
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    String comeOut=jsonObject.getString("result");
                    JSONArray array=jsonObject.getJSONArray("list");
                    List<Attendee> list=new ArrayList<>();
                    int length=array.length();
                    //吧接收到的数据做下标记处理
                    for(int i=0;i<length;i++){
                        Attendee temp= MyJSONUtil.toObject(String.valueOf(array.get(i)),Attendee.class);
                        list.add(temp);
                    }
                    activity.signInCallBack(comeOut,list);
                } catch (JSONException e) {

                }
            }
        });
    }
}
