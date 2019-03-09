package com.ly.a316.ly_meetingroommanagement.all_hui_room.DaoImp;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ly.a316.ly_meetingroommanagement.all_hui_room.Dao.FindAll_room;
import com.ly.a316.ly_meetingroommanagement.all_hui_room.activity.All_Hui_Room_Activity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Device;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;
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
 * 2019/3/9
 */
public class FindAllRoom_DaoImp implements FindAll_room {
    All_Hui_Room_Activity all_hui_room_activity;

    public FindAllRoom_DaoImp(All_Hui_Room_Activity all_hui_room_activity) {
        this.all_hui_room_activity = all_hui_room_activity;
    }

    Handler handler = new Handler(){
     @Override
     public void handleMessage(Message msg) {
         super.handleMessage(msg);
         if (msg.what == 0x1){
             toast("网络请求失败");
         }else if(msg.what == 0x2){
             toast("找到以下会议室");
             all_hui_room_activity.callBack(list_meet);
         }
     }
 };
    List<HuiyiInformation> list_meet = new ArrayList<>();//經過删选得到的会议室列表
    Gson gson = new Gson();

    @Override
    public void find_all_room() {
        String url = Net.get_allroom;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(0x1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                list_meet.clear();
                String requestbody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(requestbody);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    String boolean_request = jsonObject.getString("result").toString();
                    if (boolean_request.equals("success")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HuiyiInformation huiyiInformation = gson.fromJson(String.valueOf(jsonArray.get(i)), HuiyiInformation.class);
                            list_meet.add(huiyiInformation);
                        }
                    }
                    handler.sendEmptyMessage(0x2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    Toast toast;
    void toast(String neirong) {
        if (toast == null) {
            toast = Toast.makeText(all_hui_room_activity, neirong, Toast.LENGTH_SHORT);
        } else {
            toast.setText(neirong);
        }
        toast.show();
    }
}
