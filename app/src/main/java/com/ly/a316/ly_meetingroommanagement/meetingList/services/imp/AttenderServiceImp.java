package com.ly.a316.ly_meetingroommanagement.meetingList.services.imp;

import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.meetingList.activities.AttendeeActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.NewInviteActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.AttenderService;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;
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
Date:2019/2/12
Time:18:42
auther:xwd
*/
public class AttenderServiceImp implements AttenderService {
    private static final String TAG = "AttenderService:";
    AttendeeActivity activity;
    NewInviteActivity inviteActivity;
    public AttenderServiceImp(AttendeeActivity activity) {
        this.activity = activity;
    }

    public AttenderServiceImp(NewInviteActivity inviteActivity) {
        this.inviteActivity = inviteActivity;
    }
    //参会人列表
    @Override
    public void attenders(String mid) {
       String URL= Net.HEAD+Net.ATTENDERS+"?mId="+mid;
        Log.d(TAG, URL);
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DisplayUtils.subThreadToast(activity,Net.FAIL);
                        activity.loadingDialog.dismiss();
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
                    List<Attendee> list=new ArrayList<>();
                    int length=array.length();
                    for(int i=0;i<length;i++){
                        Attendee temp= MyJSONUtil.toObject(String.valueOf(array.get(i)),Attendee.class);
                        list.add(temp);
                    }
                    activity.attenderListCallBack(list);
                } catch (JSONException e) {

                }

            }
        });
    }
    //添加参会人
    @Override
    public void addAttender(String attender, String mid) {
     String URL=Net.HEAD+Net.ADD_ATTENDER+"?attender="+attender+"&mId="+mid;
     Log.d(TAG, URL);
     MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
         @Override
         public void onFailure(Call call, IOException e) {
             inviteActivity.runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     DisplayUtils.subThreadToast(inviteActivity,Net.FAIL);
                    inviteActivity.loadingDialog.dismiss();
                 }
             });
         }

         @Override
         public void onResponse(Call call, Response response) throws IOException {
             inviteActivity.runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     DisplayUtils.subThreadToast(inviteActivity,"发送请求成功！");
                 inviteActivity.loadingDialog.dismiss();
                 }
             });
         }
     });
    }
}
