package com.ly.a316.ly_meetingroommanagement.popPage.DaoImp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.popPage.Dao.GetTodayHui;
import com.ly.a316.ly_meetingroommanagement.popPage.object.HuiyiClass;
import com.ly.a316.ly_meetingroommanagement.utils.Net;
import com.ly.a316.ly_meetingroommanagement.utils.PopupMenuUtil;

import org.json.JSONArray;
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
 * 2019/3/3
 */
public class GetTodayHuiDaoImp implements GetTodayHui {
   PopupMenuUtil popupMenuUtil;

    public GetTodayHuiDaoImp(PopupMenuUtil popupMenuUtil) {
        this.popupMenuUtil = popupMenuUtil;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x1){
            }
            if(msg.what == 0x2){
                popupMenuUtil.success_back(list);
            }
        }
    };
    Gson gson = new Gson();
    List<HuiyiClass> list = new ArrayList<>();
    @Override
    public void getTodayHui(String tel) {
        OkHttpClient okHttpClient = new OkHttpClient();

        String url = Net.get_today_hui+"?phone="+tel;
        final Request request = new Request.Builder().url(url).build();
        Log.i("zjc",url);
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                list.clear();
                String call_body = response.body().string();
                try {
                    JSONObject jsonObject  = new JSONObject(call_body);
                    String result = jsonObject.getString("result");
                    if(result.equals("1")){
                        JSONArray  jsonArray  = jsonObject.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HuiyiClass device = gson.fromJson(String.valueOf(jsonArray.get(i)), HuiyiClass.class);
                            list.add(device);
                        }
                        handler.sendEmptyMessage(0x2);
                    }else{
                        handler.sendEmptyMessage(0x1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
