package com.ly.a316.ly_meetingroommanagement.MessageList.services.imp;

import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.MessageList.activites.MessageListActivity;
import com.ly.a316.ly_meetingroommanagement.MessageList.models.MessageModel;
import com.ly.a316.ly_meetingroommanagement.MessageList.services.MessageService;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.SearchViewActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Meeting;
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
Date:2019/3/3
Time:11:47
auther:xwd
*/
public class MessageServiceImp implements MessageService {
    MessageListActivity activity;
    private static final String TAG = "MessageService:";

    public MessageServiceImp(MessageListActivity activity) {
        this.activity = activity;
    }

    @Override
    public void userJpush(String phone) {
        final String URL= Net.HEAD+Net.USER_JPUSH+"?phone="+phone;
        Log.i(TAG, URL);
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DisplayUtils.subThreadToast(activity,Net.FAIL);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.loadingDialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray array=jsonObject.getJSONArray("list");
                    List<MessageModel> list=new ArrayList<>();
                    int length=array.length();
                    for(int i=0;i<length;i++){
                        MessageModel temp= MyJSONUtil.toObject(String.valueOf(array.get(i)),MessageModel.class);
                        list.add(temp);
                    }
                    activity.messageCallBack(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void adminJpush() {
        final String URL= Net.HEAD+Net.ADMIN_JPUSH;
        Log.i(TAG, URL);
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                DisplayUtils.subThreadToast(activity,Net.FAIL);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.loadingDialog.dismiss();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray array=jsonObject.getJSONArray("list");
                    List<MessageModel> list=new ArrayList<>();
                    int length=array.length();
                    for(int i=0;i<length;i++){
                        MessageModel temp= MyJSONUtil.toObject(String.valueOf(array.get(i)),MessageModel.class);
                        list.add(temp);
                    }
                    activity.sysMessageCallBack(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
