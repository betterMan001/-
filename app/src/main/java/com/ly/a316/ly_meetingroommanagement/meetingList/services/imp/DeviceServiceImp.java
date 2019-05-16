package com.ly.a316.ly_meetingroommanagement.meetingList.services.imp;

import com.ly.a316.ly_meetingroommanagement.meetingList.activities.DeviceListActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.adapter.DeviceListAdapter;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Device;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Meeting;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.DeviceService;
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
Date:2019/5/14
Time:17:18
auther:xwd
*/
public class DeviceServiceImp implements DeviceService {
  DeviceListActivity deviceListActivity;
  DeviceListAdapter deviceListAdapter;
    public DeviceServiceImp(DeviceListActivity deviceListActivity) {
        this.deviceListActivity = deviceListActivity;
    }

    public DeviceServiceImp(DeviceListAdapter deviceListAdapter) {
        this.deviceListAdapter = deviceListAdapter;
    }

    @Override
    public void roomDeviceList(String mId) {
        final String URL= Net.HEAD+Net.ROOM_DEVICE_LIST+"?mId="+mId;
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deviceListActivity.subThreadToast(Net.FAIL);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String res = jsonObject.getString("result");
                    if("0".equals(res)){
                        deviceListActivity.subThreadToast("该会议已经失效");
                        return;
                    }
                    JSONArray array = jsonObject.getJSONArray("list");
                    List<Device> list = new ArrayList<>();
                    int length = array.length();
                    for (int i = 0; i < length; i++) {
                        Device temp = MyJSONUtil.toObject(String.valueOf(array.get(i)), Device.class);
                        list.add(temp);
                    }
                    deviceListActivity.deviceCallBack(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
    });
    }

    @Override
        public void kai_led(String devicechoose_id) {
        final String URL= Net.HEAD+Net.KAI_LED+"?devicechoose_id="+devicechoose_id;
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String res = jsonObject.getString("result");

                    deviceListAdapter.deviceSwitchOnCallBack(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void kai_close(String devicechoose_id) {
        final String URL= Net.HEAD+Net.KAI_CLOSE+"?devicechoose_id="+devicechoose_id;
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String res = jsonObject.getString("result");

                    deviceListAdapter.deviceSwitchOFFCallBack(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
