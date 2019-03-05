package com.ly.a316.ly_meetingroommanagement.popPage.DaoImp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ly.a316.ly_meetingroommanagement.popPage.Dao.GetInformationDao;
import com.ly.a316.ly_meetingroommanagement.popPage.activity.Information_meet;
import com.ly.a316.ly_meetingroommanagement.popPage.object.HuiyiClass;
import com.ly.a316.ly_meetingroommanagement.popPage.object.Information_Hui;
import com.ly.a316.ly_meetingroommanagement.popPage.object.LiuYan_class;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

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
 * 2019/3/5
 */
public class GetInformationDaoImp implements GetInformationDao {
    Context context;
    Information_meet information_meet ;
    Gson gson = new Gson();
    List<LiuYan_class> liuYan_classes = new ArrayList<>();
    public GetInformationDaoImp(Information_meet information_meet) {
        this.information_meet = information_meet;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x1){
                Toast.makeText(context,"网络出现问题",Toast.LENGTH_SHORT).show();
            }
            if(msg.what == 0x2){
                information_meet.success_back(information_hui);
            }if(msg.what == 0x3){
                information_meet.success_send();
            }if(msg.what == 0x4){
                information_meet.success_get(liuYan_classes);
            }
        }
    };
    Information_Hui information_hui;
    @Override
    public void getAllinformation(String mId) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Net.getdaiban_huiinformation+"?mId="+mId;
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

                String call_body = response.body().string();
                try {
                    JSONObject jsonObject  = new JSONObject(call_body);
                    String result = jsonObject.getString("result");
                    if(result.equals("success")){
                        JSONArray jsonArray  = jsonObject.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                              information_hui = gson.fromJson(String.valueOf(jsonArray.get(i)), Information_Hui.class);
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

    @Override
    public void send_liuyan(String phone, String mId, String comment) {
        OkHttpClient okHttpClient = new OkHttpClient();

        String url = Net.send_liuyan+"?phone="+phone+"&mId="+mId+"&comment="+comment;
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

                String call_body = response.body().string();
                try {
                    JSONObject jsonObject  = new JSONObject(call_body);
                    String result = jsonObject.getString("result");
                    if(result.equals("1")){
                        handler.sendEmptyMessage(0x3);
                    }else{
                        handler.sendEmptyMessage(0x1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void get_AllLiuyan(String mId) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Net.get_liuyan+"?mId="+mId;
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
                liuYan_classes.clear();
                String call_body = response.body().string();
                try {
                    JSONObject jsonObject  = new JSONObject(call_body);
                    String result = jsonObject.getString("result");
                    if(result.equals("1")){
                        JSONArray jsonArray  = jsonObject.getJSONArray("list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            LiuYan_class liuYan_class = gson.fromJson(String.valueOf(jsonArray.get(i)), LiuYan_class.class);
                            liuYan_classes.add(liuYan_class);
                        }
                        handler.sendEmptyMessage(0x4);
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
