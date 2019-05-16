package com.ly.a316.ly_meetingroommanagement.meetingList.services.imp;

import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.main.fragment.ConversationListFragment;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingListActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.SearchViewActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Meeting;
import com.ly.a316.ly_meetingroommanagement.meetingList.services.MeetingListService;
import com.ly.a316.ly_meetingroommanagement.utils.DisplayUtils;
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
Date:2019/2/10
Time:13:49
auther:xwd
*/
public class MeetingListServiceImp implements MeetingListService {
    MeetingListActivity activity;
    private static final String TAG = "MeetingListService";
    SearchViewActivity searchViewActivity;
    private String type="1";

    public MeetingListServiceImp(SearchViewActivity searchViewActivity, String type) {
        this.searchViewActivity = searchViewActivity;
        this.type = type;
    }

    public MeetingListServiceImp(MeetingListActivity activity) {
        this.activity = activity;
    }
    //获取账号相关的会议
    @Override
    public void selectMeetingBySEmployeeId(String sEmployeeId) {
        final String URL= Net.HEAD+Net.SELECT_MEETING_ByS_EmployeeId+"?sEmployeeId="+sEmployeeId;
        Log.i(TAG, URL);
        MyHttpUtil.sendOkhttpGetRequest(URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if("1".equals(type)){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.loadingDialog.dismiss();
                        }
                    });
                }
                else{

                }

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray array=jsonObject.getJSONArray("list");
                    List<Meeting> list=new ArrayList<>();
                    int length=array.length();
                    for(int i=0;i<length;i++){
                        Meeting temp= MyJSONUtil.toObject(String.valueOf(array.get(i)),Meeting.class);
                        list.add(temp);
                    }
                    if("1".equals(type))
                    activity.callBack(list);
                    else
                        searchViewActivity.callBack(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
