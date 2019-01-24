package com.ly.a316.ly_meetingroommanagement;

import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit BaseActivity, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    String  url;
    List<HuiyiInformation> list_meet = new ArrayList<>();//經過删选得到的会议室列表
    Gson gson = new Gson();
     android.os.Handler handler = new android.os.Handler(){
         @Override
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             if(msg.what==5){
                 for(int i=0;i<list_meet.size();i++){
                     System.out.println(list_meet.get(i).getmAddress());
                 }
             }
             if(msg.what == 01){
                 System.out.println("dsadasdsa");
             }
         }
     };

    @Test
    public void addition_isCorrect() {
        url = Net.subbmit_meetroom+"?address="+ "3C"+"&number="+"25"
            +"&dateString"+"2019-1-4 12:00:00"+"&beginTime="+""+"&endTime="+""+"&device="+"1,7,8,17,"+"&duration="+"30"+
            "&types="+"1,2,3,4,5,6,7,8,9,";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(01);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String requestbody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(requestbody);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for(int i=0 ;i<jsonArray.length();i++){
                        HuiyiInformation huiyiInformation = gson.fromJson(String.valueOf(jsonArray.get(i)),HuiyiInformation.class);
                        list_meet.add(huiyiInformation);
                    }
                    handler.sendEmptyMessage(5);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}