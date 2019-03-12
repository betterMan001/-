package com.ly.a316.ly_meetingroommanagement.remind_huiyi_end;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.calendar_fra.object.Day_Object;
import com.ly.a316.ly_meetingroommanagement.remind_huiyi_end.service.End_Service;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：余智强
 * 2019/3/12
 */
public class YanChangDaoImp {

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x33) {
                Log.i("zjc", "延长会议失败");
            } else if (msg.what == 0x39) {
                Log.i("zjc", "延长会议成功");
            }
        }
    };

    public void getAllIndormation(String mId) {
        String url = Net.huiyiyangchang + "?mId=" + mId;
        Log.i("zjc", "会议延长：" + url);
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
                String resBody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(resBody);
                    if (jsonObject.getString("result").equals("1")) {
                        handler.sendEmptyMessage(0x39);
                    } else {
                        handler.sendEmptyMessage(0x33);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
