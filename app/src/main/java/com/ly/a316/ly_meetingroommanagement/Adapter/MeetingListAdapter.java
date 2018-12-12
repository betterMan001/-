package com.ly.a316.ly_meetingroommanagement.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.models.Meeting;

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
        holder1.meeting_list_message_tv.setText(model.getMessageNum());
        holder1.meeting_list_partner.setText(model.getPartnerNum());
        holder1.meeting_list_sponsor_tv.setText(model.getSponsor());
        holder1.meeting_list_time_tv.setText(model.getDate());
        holder1.meeting_list_status_tv.setText(model.getMeetingStatus());
        holder1.meeting_title_tv.setText(model.getTitle());
        holder1.meeting_list_did_time.setText(model.getDidTime());
    }

    @Override
    public int getItemCount() {
        return count;
    }
    public class MeetingListViewHolder extends RecyclerView.ViewHolder{
        public CardView meeting_list_item;
        public TextView meeting_list_sponsor_tv;
        public TextView meeting_title_tv;
        public TextView meeting_list_time_tv;
        public TextView meeting_list_message_tv;
        public TextView meeting_list_partner;
        public TextView meeting_list_status_tv;
        public TextView meeting_list_did_time;
        //对应select_item中单项
        public MeetingListViewHolder(View itemView) {
            super(itemView);
            meeting_list_item=itemView.findViewById(R.id.meeting_list_item);
            meeting_list_sponsor_tv=itemView.findViewById(R.id.meeting_list_sponsor_tv);
            meeting_title_tv=itemView.findViewById(R.id.meeting_title_tv);
            meeting_list_time_tv=itemView.findViewById(R.id.meeting_list_time_tv);
            meeting_list_message_tv=itemView.findViewById(R.id.meeting_list_message_tv);
            meeting_list_partner=itemView.findViewById(R.id.meeting_list_partner);
            meeting_list_status_tv=itemView.findViewById(R.id.meeting_list_status_tv);
            meeting_list_did_time=itemView.findViewById(R.id.meeting_list_did_time);
        }

    }
}
