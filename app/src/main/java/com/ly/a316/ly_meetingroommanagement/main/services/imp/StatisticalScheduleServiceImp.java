package com.ly.a316.ly_meetingroommanagement.main.services.imp;

import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.Schedule.Classes.Schedule;
import com.ly.a316.ly_meetingroommanagement.main.activites.StatisticalActivity;
import com.ly.a316.ly_meetingroommanagement.main.models.ScheduleModel;
import com.ly.a316.ly_meetingroommanagement.main.services.StatisticalScheduleService;
import com.ly.a316.ly_meetingroommanagement.utils.MyHttpUtil;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
Date:2019/3/10
Time:12:17
auther:xwd
*/
public class StatisticalScheduleServiceImp implements StatisticalScheduleService {
    StatisticalActivity activity;
    private static final String TAG = "staticService:";
    public StatisticalScheduleServiceImp(StatisticalActivity activity) {
        this.activity = activity;
    }

    @Override
    public void statistics(String phone) {
      String URL= Net.HEAD+Net.STATISTICS+"?phone="+phone;
        Log.d(TAG, URL);
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.subThreadToast(Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    response.close();
                    ScheduleModel model=new ScheduleModel();
                    model.setSevenMonthDoneList(jsonObject.getString("sevenMonthDoneList"));
                    model.setSevenWeekDoneList(jsonObject.getString("sevenWeekDoneList"));
                    model.setThisWeekCreateScheduleNumber(jsonObject.getString("thisWeekCreateScheduleNumber"));
                    model.setThisWeekDoneScheduleNumber(jsonObject.getString("thisWeekDoneScheduleNumber"));
                    model.setThisWeekDoScheduleList(jsonObject.getString("thisWeekDoScheduleList"));
                    activity.statisticalCallBack(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
