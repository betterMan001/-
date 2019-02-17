package com.ly.a316.ly_meetingroommanagement.meetingList.services.imp;

import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingDetailActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.MeetingDetailModel;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.MeetingDetailService;
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
Date:2019/2/16
Time:19:59
auther:xwd
*/
public class MeetingDetailServiceImp implements MeetingDetailService {
    MeetingDetailActivity activity;
    private static final String TAG = "MeetingDetail:";
    public MeetingDetailServiceImp(MeetingDetailActivity activity) {
        this.activity = activity;
    }

    @Override
    public void meetDetail(String mId) {
     String URL= Net.HEAD+Net.MEET_DETAIL+"?mId="+mId;
        Log.d(TAG,URL);
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.loadingDialog.dismiss();
                        activity.subThreadToast(Net.FAIL);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                response.close();
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray array=jsonObject.getJSONArray("list");
                    int length=array.length();
                    MeetingDetailModel model= MyJSONUtil.toObject(String.valueOf(array.get(0)),MeetingDetailModel.class);
                    activity.meetingDetailCallBack(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
