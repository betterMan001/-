package com.ly.a316.ly_meetingroommanagement.askhttpDaoImp;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.activites.AddSchedule;
import com.ly.a316.ly_meetingroommanagement.askhttpDao.ScheduleDao;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：余智强
 * 2018/12/29
 */
public class ScheduleDaoImp implements ScheduleDao {
    AddSchedule addSchedule;//新建日程的活动类

    public ScheduleDaoImp(AddSchedule addSchedule) {
        this.addSchedule = addSchedule;
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //网络访问失败
                    Toast.makeText(addSchedule,"网络请求失败",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    //新建日程成功
                    Toast.makeText(addSchedule,"网络请求成功",Toast.LENGTH_SHORT).show();
                    addSchedule.successback();
                    break;
            }
        }
    };
    //新建日程
    @Override
    public void addSchedule(String eId, String begin, String end, String theme, String meetType, String context, String address, String remind) {
        String path = Net.addSchedule;

        String url= Net.addSchedule+"?eId="+eId+"&begin="+begin+"&end="+end+"&theme="+theme+"&meetType="+meetType+"&context="+context+"&address="+address+"&remind="+remind;
Log.i("zjc",url);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(11, TimeUnit.MINUTES)
                .readTimeout(11, TimeUnit.MINUTES)
                .build();
        FormBody formBody = new FormBody.Builder()
                .add("eId", eId)
                .add("begin", begin)
                .add("end",end)
                .add("theme",theme)
                .add("meetType",meetType)
                .add("context",context)
                .add("address",address)
                .add("remind",remind)
                .build();
        Request request = new Request.Builder()
                .url(path)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    if(jsonObject.getString("result").equals("success")){
                        handler.sendEmptyMessage(2);
                    }else{
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
