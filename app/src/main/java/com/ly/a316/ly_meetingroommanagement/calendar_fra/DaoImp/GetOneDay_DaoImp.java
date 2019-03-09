package com.ly.a316.ly_meetingroommanagement.calendar_fra.DaoImp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ly.a316.ly_meetingroommanagement.calendar_fra.Dao.GteOneDay_RC;
import com.ly.a316.ly_meetingroommanagement.calendar_fra.object.Day_Object;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Device;
import com.ly.a316.ly_meetingroommanagement.main.fragment.CalendarFragment;
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
public class GetOneDay_DaoImp implements GteOneDay_RC {
    CalendarFragment calendarFragment;
    Context context;
    Gson gson = new Gson();
    public GetOneDay_DaoImp(CalendarFragment calendarFragment, Context context) {
        this.calendarFragment = calendarFragment;
        this.context = context;
    }

    List<Day_Object> list = new ArrayList<>();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x33) {
                toast("网络请求失败");
            } else if (msg.what == 0x34) {
                calendarFragment.success_callback(list);
            }
        }
    };

    @Override
    public void getOneDayinformation(String leadersid, String date) {
        String url = Net.get_allHuiyi + "?leadersId=" + leadersid + "&date=" + date;
        Log.i("zjc", "获取日程：" + url);
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
                list.clear();
                String resBody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(resBody);
                    if(jsonObject.getString("result").equals("success")){
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        for(int i=0;i<jsonArray.length();i++){
                            Day_Object day_object = gson.fromJson(String.valueOf(jsonArray.get(i)), Day_Object.class);
                            list.add(day_object);
                        }
                    }else{
                        handler.sendEmptyMessage(0x33);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Toast toast;

    void toast(String neirong) {
        if (toast == null) {
            toast = Toast.makeText(context, neirong, Toast.LENGTH_SHORT);
        } else {
            toast.setText(neirong);
        }
        toast.show();
    }

}
