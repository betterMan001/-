package com.ly.a316.ly_meetingroommanagement.meetting.services.imp;

import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.meetting.activity.OrderDetailMeetingActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.services.OrderDetailMeetingService;
import com.ly.a316.ly_meetingroommanagement.utils.Jpush.WindowUtils;
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
Date:2019/1/31
Time:19:09
auther:xwd
*/
public class OrderDetailMeetingServiceImp implements OrderDetailMeetingService {
    OrderDetailMeetingActivity activity;
    private static final String TAG = "OrderDetailMeeting";
    public OrderDetailMeetingServiceImp(OrderDetailMeetingActivity activity) {
        this.activity = activity;
    }

    public OrderDetailMeetingServiceImp() {
    }
    @Override
    public void unlockRoom(String roomId) {
      final String URL= Net.HEAD+Net.UNLOCK_ROOM+"?roomId="+roomId;
      activity.loadingDialog.show();
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.loadingDialog.dismiss();

                    }

                });
                activity.subThreadToast(Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              //成功解锁则回调
                activity.unLookCallBack();
            }
        });
    }

    @Override
    public void bookMeetRoom(String employeeId, String roomId, String beginTime, String endTime, String theme, String content, String attends, String recorder) {
     final String URL=Net.HEAD+Net.BOOK_MEET_ROOM;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("employeeId",employeeId);
        builder.add("roomId",roomId);
        builder.add("beginTime",beginTime);
        builder.add("endTime",endTime);
        builder.add("theme",theme);
        builder.add("content",content);
        builder.add("attends",attends);
        builder.add("recorder",recorder);
        RequestBody body=builder.build();
        activity.loadingDialog.show();
        MyHttpUtil.sendOkhttpPostRequest(URL, body, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.loadingDialog.dismiss();

                    }
                });
                activity.subThreadToast(Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                response.close();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String comeOut=jsonObject.getString("result");
                    activity.orderMeetingRoomCallBack(comeOut);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void optIn(String mId, String phone, String reason, String type) {
         String URL=Net.HEAD+Net.OPTIN;
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("mId",mId);
        builder.add("phone",phone);
        builder.add("reason",reason);
        builder.add("type",type);
        RequestBody body=builder.build();
        MyHttpUtil.sendOkhttpPostRequest(URL, body,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                response.close();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String comeOut=jsonObject.getString("result");
                   WindowUtils.inviteCallBack(comeOut);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
