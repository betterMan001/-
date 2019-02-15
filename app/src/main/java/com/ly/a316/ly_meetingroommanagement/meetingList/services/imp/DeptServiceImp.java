package com.ly.a316.ly_meetingroommanagement.meetingList.services.imp;

import com.ly.a316.ly_meetingroommanagement.meetingList.activities.NewInviteActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.MulitemAdapter;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelZero;
import com.ly.a316.ly_meetingroommanagement.meetting.services.DeptService;
import com.ly.a316.ly_meetingroommanagement.utils.MyHttpUtil;
import com.ly.a316.ly_meetingroommanagement.utils.MyJSONUtil;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/*
Date:2019/1/19
Time:16:31
auther:xwd
*/
public class DeptServiceImp implements DeptService {
    NewInviteActivity activity;
    MulitemAdapter adapter;

    public DeptServiceImp(MulitemAdapter adapter) {
        this.adapter = adapter;
    }

    private static final String TAG = "DeptServiceImp:";
    public DeptServiceImp(NewInviteActivity activity) {
        this.activity = activity;
    }

    @Override
    public void getAllDepartemnt() {
       String URL= Net.HEAD+Net.DEPT_INFO;
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                activity.subThreadToast(Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              String result=response.body().string();
              response.close();
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray array=jsonObject.getJSONArray("list");
                    int length=array.length();
                    List<LevelZero> list=new ArrayList<>();
                    for(int i=0;i<length;i++){
                        LevelZero model= MyJSONUtil.toObject(String.valueOf(array.get(i)),LevelZero.class);
                        list.add(model);
                    }
                    activity.levelZeroCallBack(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getAllEmployeeByDepartmentId(String departmentId) {
      String URL=Net.HEAD+Net.DEPT_EMPLOYEE+"?deptId="+departmentId;
      MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
          @Override
          public void onFailure(Call call, IOException e) {
              adapter.activity.subThreadToast(Net.FAIL);
          }

          @Override
          public void onResponse(Call call, Response response) throws IOException {
             String result=response.body().string();
             response.close();
              try {
                  JSONObject jsonObject=new JSONObject(result);
                  JSONArray array=jsonObject.getJSONArray("list");
                  List<LevelOne>list=new ArrayList<>();
                    int length=array.length();
                  for(int i=0;i<length;i++){
                      LevelOne temp= MyJSONUtil.toObject(String.valueOf(array.get(i)),LevelOne.class);
                      list.add(temp);
                  }
                  adapter.leveloneCallBack(list);
              } catch (JSONException e) {

              }

          }
      });
    }
}
