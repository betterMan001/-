package com.ly.a316.ly_meetingroommanagement.meetingList.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.AttendeeActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.MeetingDetailActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Meeting;

import java.util.List;

/*
Date:2018/12/9
Time:19:08
auther:xwd
*/
public class MeetingListAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Meeting> list;
    //用于筛选下拉框
    public static int[] truePositon;
    public int count=0;

    public void setCount(int count) {
        this.count = count;
    }

    public MeetingListAdapter(Context context, List<Meeting> list,int count) {
        this.context = context;
        this.list = list;
        truePositon=new int[list.size()];
        this.count=count;
        for(int i=0;i<count;i++)
            truePositon[i]=i;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.meeting_list_item,parent,false);
        return new MeetingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MeetingListViewHolder holder1= (MeetingListViewHolder) holder;
        //根据truePostion指针来获取被筛选的model
        Meeting model=list.get(truePositon[position]);
        //设置相应的属性和监听事件
        holder1.meeting_list_time_tv1.setText("时间:"+model.getBegin());
        holder1.meeting_list_time_tv2.setText(generate(model.getDuration()));
        holder1.meeting_title_tv.setText(model.getName());
        holder1.meeting_list_sponsor_tv.setText(model.getInitiator());
        holder1.meeting_list_message_tv.setText(model.getAddress());
        holder1.meeting_list_partner.setText(model.getRatio()+"人确认参加");
        String meetingStatus="";
        switch (model.getState()){
            case "未开始":
                meetingStatus="未开始";
                break;
            case "正在":
                meetingStatus="正在进行中";
                break;
            case "已结束":
                meetingStatus="已结束";
                break;
        }
        final String mId=model.getmId();
        holder1.meeting_list_status_tv.setText(meetingStatus);
        holder1.meeting_title_tv.setText(model.getName());
        holder1.meeting_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeetingDetailActivity.start(context,mId);
            }
        });
    }
   public String generate(String time){
        int temp=new Integer(time);
        Integer hours= temp/60;
        Integer minutes=temp%60;
        StringBuilder builder=new StringBuilder();
       // builder.append("持续时间:");
        builder.append(hours.toString());
        builder.append("小时");
        builder.append(minutes.toString());
        builder.append("分钟");
        return builder.toString();

   }
    @Override
    public int getItemCount() {
        return count;
    }
    public class MeetingListViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout meeting_list_item;
        public TextView meeting_list_sponsor_tv;//发起人
        public TextView meeting_title_tv;//会议名称
        public TextView meeting_list_time_tv1;//开始时间
        public TextView meeting_list_time_tv2;//持续时间
        public TextView meeting_list_message_tv;//会议室地点
        public TextView meeting_list_partner;//参会情况
        public TextView meeting_list_status_tv;//会议状态
        //对应select_item中单项
        public MeetingListViewHolder(View itemView) {
            super(itemView);
            meeting_list_item=itemView.findViewById(R.id.meeting_list_item);
            meeting_list_sponsor_tv=itemView.findViewById(R.id.meeting_list_sponsor_tv);
            meeting_title_tv=itemView.findViewById(R.id.meeting_title_tv);
            meeting_list_time_tv1=itemView.findViewById(R.id.meeting_list_time_tv1);
            meeting_list_time_tv2=itemView.findViewById(R.id.meeting_list_time_tv2);
            meeting_list_message_tv=itemView.findViewById(R.id.meeting_list_message_tv);
            meeting_list_partner=itemView.findViewById(R.id.meeting_list_partner);
            meeting_list_status_tv=itemView.findViewById(R.id.meeting_list_status_tv);
        }

    }
}
