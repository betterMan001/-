package com.ly.a316.ly_meetingroommanagement.meetingList.services.imp;

import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingDetailActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.SearchViewActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.MeetingDetailModel;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.MeetingDetailService;
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
Date:2019/2/16
Time:19:59
auther:xwd
*/
public class MeetingDetailServiceImp implements MeetingDetailService {
    MeetingDetailActivity activity;
    SearchViewActivity searchActivity;
    /*
    *1.标志从内个活动访问的，好判断回调的方向
    *2. type=1则是会议详情 type=2则是搜索活动
    */
    String type="";
    private static final String TAG = "MeetingDetail:";
    public MeetingDetailServiceImp(MeetingDetailActivity activity,String type) {
        this.activity = activity;
        this.type=type;
    }

    public MeetingDetailServiceImp(SearchViewActivity searchActivity,String type) {
        this.searchActivity = searchActivity;
        this.type=type;
    }

    @Override
    public void meetDetail(String mId) {
     String URL= Net.HEAD+Net.MEET_DETAIL+"?mId="+mId;
        Log.d(TAG,URL);
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                 BaseActivity baseActivity=null;
                //从会议详情调用
                if("1".equals(type)){
                    baseActivity=activity;

                }else{
                  baseActivity=searchActivity;
                }
                final BaseActivity temp=baseActivity;
                baseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        temp.loadingDialog.dismiss();
                        temp.subThreadToast(Net.FAIL);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                response.close();
                try {
                    JSONObject jsonObject=new JSONObject(result);
                    JSONArray array=jsonObject.getJSONArray("list");
                    int length=array.length();
                    MeetingDetailModel model= MyJSONUtil.toObject(String.valueOf(array.get(0)),MeetingDetailModel.class);
                    if("1".equals(type)){
                        activity.meetingDetailCallBack(model);
                    }
                  else{
                        searchActivity.meetingDetailCallBack(model);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
