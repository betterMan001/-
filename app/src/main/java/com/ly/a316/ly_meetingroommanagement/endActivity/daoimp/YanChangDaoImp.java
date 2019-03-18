package com.ly.a316.ly_meetingroommanagement.endActivity.daoimp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.endActivity.activity.End_Activity;
import com.ly.a316.ly_meetingroommanagement.endActivity.dao.YanChangDao;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingDetailActivity;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：余智强
 * 2019/3/12
 */
public class YanChangDaoImp implements YanChangDao {
    End_Activity end_activity;
    List<String> tel_list = new ArrayList<>();
    String whooo = "1";

    public YanChangDaoImp(End_Activity end_activity) {
        this.end_activity = end_activity;
    }

    MeetingDetailActivity meetingDetailActivity;

    public YanChangDaoImp(MeetingDetailActivity meetingDetailActivity, String idd) {
        this.meetingDetailActivity = meetingDetailActivity;
        this.whooo = idd;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x33) {
                Toast.makeText(end_activity, "网络访问失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 0x36) {
                Toast.makeText(end_activity, "会议延迟15分钟成功", Toast.LENGTH_LONG).show();
            } else if (msg.what == 0x38) {
                end_activity.baoxiu("已将你的建议发送给后台，谢谢你为会议室做的贡献");
                //  Toast.makeText(end_activity, "已将你的建议发送给后台，谢谢你为会议室做的贡献", Toast.LENGTH_LONG).show();
            } else if (msg.what == 0x39) {
                end_activity.getAllRen(tel_list);
            } else if (msg.what == 0x40) {
                meetingDetailActivity.baoxiu("已将你的建议发送给后台，谢谢你为会议室做的贡献");
            }
        }
    };

    @Override
    public void yanchang(String mId) {
        String url = Net.huiyiyangchang + "?mId=" + mId;
        Log.i("zjc", "延长会议：" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x33);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                handler.sendEmptyMessage(0x36);
            }
        });
    }

    @Override
    public void baoxiu(String phone, String roomId, String deviceId, String info) {
        String url = Net.devicebaoxiu + "?phone=" + phone + "&roomId=" + roomId + "&deviceId=" + deviceId + "&info=" + info;
        Log.i("zjc", "设备报修：" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x33);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (whooo.equals("2")) {
                    handler.sendEmptyMessage(0x40);
                } else {
                    handler.sendEmptyMessage(0x38);
                }

            }
        });
    }


    @Override
    public void getAllpeopleTel(String mid) {
        String url = Net.getAllCanhuiren + "?mId=" + mid;
        Log.i("zjc", "所有人会人的电话：" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x33);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                tel_list.clear();
                String requestBody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(requestBody);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        tel_list.add(jsonObject1.getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x39);
            }
        });
    }

    @Override
    public void endMeet(String id) {
        String url = Net.getAllCanhuiren + "?id=" + id+"&role=1";
        Log.i("zjc", "结束会议：" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
