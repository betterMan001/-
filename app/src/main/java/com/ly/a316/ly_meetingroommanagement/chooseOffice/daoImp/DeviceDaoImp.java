package com.ly.a316.ly_meetingroommanagement.chooseOffice.daoImp;

import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.ly.a316.ly_meetingroommanagement.activites.InvitationPeoActivity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.activity.HuiyiActivity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.activity.HuiyiXiang_Activity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.activity.ZhanshiHuiActivity;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.dao.deviceDao;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Device;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.DeviceType;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Hui_Device;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Hui_Shiyong;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;
import com.ly.a316.ly_meetingroommanagement.endActivity.activity.End_Activity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingDetailActivity;
import com.ly.a316.ly_meetingroommanagement.schedule_room_four.activity.Schedule_Activity_four;
import com.ly.a316.ly_meetingroommanagement.utils.Net;
import com.netease.nim.uikit.common.framework.infra.TaskObservable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：余智强
 * 2019/1/22
 */
public class DeviceDaoImp implements deviceDao {
    InvitationPeoActivity invitationPeoActivity;
    String url;
    Toast toast;
    List<Device> device_list = new ArrayList<>();//存放的设备
    List<DeviceType> deviceType_list = new ArrayList<>();//存放所有的设备类型
    List<HuiyiInformation> list_meet = new ArrayList<>();//經過删选得到的会议室列表
    List<Hui_Device> list_huidevice = new ArrayList<>();//存放摸个会议室的所有设备
    Gson gson = new Gson();
    HuiyiActivity huiyiActivity;
    HuiyiXiang_Activity huiyiXiang_activity;
    ZhanshiHuiActivity zhanshiHuiActivity;
    Schedule_Activity_four schedule_activity_four;
    End_Activity end_activity;


    MeetingDetailActivity meetingDetailActivity;
    String whho = "1";

    public DeviceDaoImp(MeetingDetailActivity meetingDetailActivity, String idd) {
        this.meetingDetailActivity = meetingDetailActivity;
        whho = idd;
    }

    public DeviceDaoImp(End_Activity end_activity) {
        this.end_activity = end_activity;
    }

    public DeviceDaoImp(ZhanshiHuiActivity zhanshiHuiActivity) {
        this.zhanshiHuiActivity = zhanshiHuiActivity;
    }

    public DeviceDaoImp(HuiyiActivity huiyiActivity) {
        this.huiyiActivity = huiyiActivity;
    }

    public DeviceDaoImp(Schedule_Activity_four schedule_activity_four) {
        this.schedule_activity_four = schedule_activity_four;
    }

    public DeviceDaoImp(InvitationPeoActivity invitationPeoActivity) {
        this.invitationPeoActivity = invitationPeoActivity;
    }

    public DeviceDaoImp(HuiyiXiang_Activity huiyiXiang_activity) {
        this.huiyiXiang_activity = huiyiXiang_activity;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                toast("网络请求失败");
                invitationPeoActivity.call_fail();
            } else if (msg.what == 2) {
                toast("网络请求成功");
                invitationPeoActivity.call_success_back(device_list);
            } else if (msg.what == 3) {
                toast("网络请求成功");
                invitationPeoActivity.call_success_dedian(device_list);
            } else if (msg.what == 4) {
                toast("网络请求成功");
                invitationPeoActivity.call_success_type(deviceType_list);
            } else if (msg.what == 5) {
                Toast.makeText(huiyiActivity, "帮你找到了以下会议室", Toast.LENGTH_SHORT).show();
                huiyiActivity.success(list_meet);
            } else if (msg.what == 6) {
                if (sa == 10) {
                    Toast.makeText(schedule_activity_four, "该地址没有找到会议室", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(huiyiActivity, "该地址没有找到会议室", Toast.LENGTH_SHORT).show();
                }
            } else if (msg.what == 7) {
                huiyiXiang_activity.get_success(list_huidevice);
            } else if (msg.what == 8) {
                zhanshiHuiActivity.success_back(list_meet);
            } else if (msg.what == 9) {
                if (sa == 10) {
                    Toast.makeText(schedule_activity_four, "帮你找到了以下会议室", Toast.LENGTH_SHORT).show();
                    schedule_activity_four.success(list_meet);
                }
            } else if (msg.what == 10) {
                //toast_end("网络请求成功");
                end_activity.call_success_back(device_list);
            } else if (msg.what == 11) {
                toast_end("网络请求失败");
            } else if (msg.what == 12) {
                huiyiXiang_activity.get_success_shi(hui_shiyong);
            }else if(msg.what == 50){
                meetingDetailActivity.call_success_back(device_list);
            }
        }
    };

    @Override
    public void getAllDevice() {
        device_list.clear();
        url = Net.getAllShebei;
        Log.i("zjc", "获取所有的设备网址：" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resBody = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resBody);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        Device device = gson.fromJson(String.valueOf(jsonArray.get(i)), Device.class);
                        device_list.add(device);
                    }
                    handler.sendEmptyMessage(2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getAllDiDian() {
        device_list.clear();
        url = Net.getAllDiDian;
        Log.i("zjc", "获取所有的开会地点网址" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resBody = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resBody);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Device device = new Device(jsonArray.get(i).toString());
                        device_list.add(device);
                    }
                    handler.sendEmptyMessage(3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    int sa;

    @Override
    public void getAllType() {
        url = Net.getAllType;
        Log.i("zjc", "获取所有的开会类型的网址：" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String resBody = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resBody);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        DeviceType device_type = gson.fromJson(String.valueOf(jsonArray.get(i)), DeviceType.class);
                        deviceType_list.add(device_type);
                    }
                    handler.sendEmptyMessage(4);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void subbmitHuiyi(final int whooer) {
        sa = whooer;
        if (ShijiandianClass.START_TIME != null) {
            url = Net.subbmit_meetroom + "?address=" + ShijiandianClass.HUIYI_WHERE + "&number=" + ShijiandianClass.PEOPLE_NUMBER
                    + "&dateString=" + "" + "&beginTime=" + ShijiandianClass.START_TIME + "&endTime=" + ShijiandianClass.END_TIME + "&device=" + ShijiandianClass.SHEBEI + "&duration=" + ShijiandianClass.HUIYISHICHANG_TIME +
                    "&types=" + ShijiandianClass.HUIYI_LEIXING;
        } else {
            url = Net.subbmit_meetroom + "?address=" + ShijiandianClass.HUIYI_WHERE + "&number=" + ShijiandianClass.PEOPLE_NUMBER
                    + "&dateString=" + ShijiandianClass.DATESTRING + "&beginTime=" + "" + "&endTime=" + "" + "&device=" + ShijiandianClass.SHEBEI + "&duration=" + ShijiandianClass.HUIYISHICHANG_TIME +
                    "&types=" + ShijiandianClass.HUIYI_LEIXING;
        }

        Log.i("zjc", "寻找会议的网址:" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
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
                        if (whooer == 1) {
                            handler.sendEmptyMessage(5);
                        }
                        if (whooer == 2) {
                            handler.sendEmptyMessage(8);
                        }
                        if (whooer == 10) {
                            handler.sendEmptyMessage(9);
                        }
                    } else {

                        handler.sendEmptyMessage(6);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getOneHuiroom(String id, String type_id) {
        url = Net.getOneHuiroom + "?roomId=" + id + "&mType=" + type_id;
        Log.i("zjc", "查找某个会议室的网址:" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String requestbody = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(requestbody);
                    JSONObject h = jsonObject.getJSONObject("map");
                    JSONArray jsonArray = h.getJSONArray("device");
                    String hui_type = h.getString("type");
                    ShijiandianClass.HUIYI_Leixing = hui_type;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Hui_Device hui_device = new Hui_Device(jsonArray.get(i).toString());
                        list_huidevice.add(hui_device);
                    }
                    handler.sendEmptyMessage(7);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getAllDevice_inEndActivity() {
        device_list.clear();
        url = Net.getAllShebei;
        Log.i("zjc", "获取所有的设备网址：" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(11);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resBody = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resBody);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        Device device = gson.fromJson(String.valueOf(jsonArray.get(i)), Device.class);
                        device_list.add(device);
                    }
                    if(whho.equals("2")){
                        handler.sendEmptyMessage(50);
                    }else {
                        handler.sendEmptyMessage(10);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Hui_Shiyong hui_shiyong = new Hui_Shiyong();

    @Override
    public void getOneHuiShiyong(String roomId, String dates) {
        String url = Net.getShijian + "?roomId=" + roomId + "&dates=" + dates;
        Log.i("zjc", "获取会议室时间使用：" + url);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(11);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resBody = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(resBody);
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String time = jsonObject1.getString(dates);
                    hui_shiyong.setTime(time);
                    handler.sendEmptyMessage(12);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void toast(String neirong) {
        if (toast == null) {
            toast = Toast.makeText(invitationPeoActivity, neirong, Toast.LENGTH_SHORT);
        } else {
            toast.setText(neirong);
        }
        toast.show();
    }


    void toast_end(String neirong) {
        if (toast == null) {
            toast = Toast.makeText(end_activity, neirong, Toast.LENGTH_SHORT);
        } else {
            toast.setText(neirong);
        }
        toast.show();
    }
}
