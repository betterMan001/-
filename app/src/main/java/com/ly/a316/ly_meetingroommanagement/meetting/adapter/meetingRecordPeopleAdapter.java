package com.ly.a316.ly_meetingroommanagement.meetting.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetting.activity.OrderDetailMeetingActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/*
Date:2019/1/22
Time:20:07
auther:xwd
*/
public class meetingRecordPeopleAdapter extends RecyclerView.Adapter {
    private Context context;
    List<LevelOne> list;

    public meetingRecordPeopleAdapter(Context context,List<LevelOne> list) {
        this.context=context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.invite_list_item_one,viewGroup,false);
       return new MeetingRecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MeetingRecordViewHolder holder=(MeetingRecordViewHolder) viewHolder;
        LevelOne helper=list.get(i);
        //给每项附上属性
        holder.choose_riv.setImageResource(R.drawable.empty_circle001);
        //设置头像
        //设置glide加载的选项
        RequestOptions requestOptions=new RequestOptions()
                .placeholder(R.drawable.user_default_head)
                .error(R.drawable.user_default_head);
        Glide
                .with(context)
                .load(helper.getHeadURL())
                .apply(requestOptions)
                .into(holder.invite_head);
        holder.Lv1_tv.setText(helper.name);
        holder.invite_job_tv.setText(helper.eJob);
        final String eId=helper.eId;
      final  MeetingRecordViewHolder finalHolder=holder;
      final LevelOne finalHelper=helper;
        holder.choose_riv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将选择的状态保存
                if(OrderDetailMeetingActivity.recordEmployees.containsKey(eId)==false){
                    finalHolder.choose_riv.setImageResource(R.drawable.gou1);
                    OrderDetailMeetingActivity.recordEmployees.put(eId,finalHelper);
                }
                else{
                    finalHolder.choose_riv.setImageResource(R.drawable.empty_circle001);
                    OrderDetailMeetingActivity.recordEmployees.remove(eId);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MeetingRecordViewHolder extends RecyclerView.ViewHolder{
         public RoundedImageView choose_riv;
         public RoundedImageView invite_head;
         public TextView Lv1_tv;
         public TextView invite_job_tv;
        //对应select_item中单项
        public MeetingRecordViewHolder(View itemView) {
            super(itemView);
            choose_riv=itemView.findViewById(R.id.choose_riv);
            invite_head=itemView.findViewById(R.id.invite_head);
            Lv1_tv=itemView.findViewById(R.id.Lv1_tv);
            invite_job_tv=itemView.findViewById(R.id.invite_job_tv);
        }

    }
}
