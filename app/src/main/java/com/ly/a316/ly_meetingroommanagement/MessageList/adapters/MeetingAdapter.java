package com.ly.a316.ly_meetingroommanagement.MessageList.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ly.a316.ly_meetingroommanagement.MessageList.models.MessageModel;
import com.ly.a316.ly_meetingroommanagement.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/*
Date:2019/2/28
Time:14:49
auther:xwd
*/
public class MeetingAdapter extends RecyclerView.Adapter {
    //上下文和数据
    private Context context;
    private List<MessageModel> list;
    public MeetingAdapter(Context context, List<MessageModel> list) {
        this.context = context;
        this.list = list;
    }

    //将目标layout渲染成View
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.meeting_message_item,parent,false);
    return new MeetingMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel model=list.get(position);
       MeetingMessageHolder holder1=(MeetingMessageHolder) holder;
       if("1".equals(model.type)==true){
           holder1.meeting_message_type.setText("会议提醒");
           holder1.invite_type_tv.setText("");
       }
      else{
           holder1.meeting_message_type.setText("会议邀请");
           holder1.invite_type_tv.setText("已拒绝");

       }
        //设置头像
        RoundedImageView headView=holder1.invite_head;
        //设置glide加载的选项
        RequestOptions requestOptions=new RequestOptions()
                .placeholder(R.drawable.beaty_head001)
                .error(R.drawable.beaty_head001);
        Glide
                .with(context)
                .load(model.getImage())
                .apply(requestOptions)
                .into(headView);
       holder1.meeting_item_Tv.setText(model.getSender());
       holder1.meeting_time.setText(model.getTime());
       holder1.meeting_message_content.setText(model.getMessage());
    }

    @Override
    public int getItemCount() {
        if(list.size()>0)
            return list.size();
        else return 0;
    }
    //写一个Holder继承RecyclerView.ViewHolder
    static class MeetingMessageHolder extends RecyclerView.ViewHolder{
        public TextView meeting_message_type;
        public TextView meeting_time;
        public TextView meeting_item_Tv;
        public TextView meeting_message_content;
        public TextView invite_type_tv;
        public RoundedImageView invite_head;public MeetingMessageHolder(View itemView) {
            super(itemView);
            meeting_message_type=itemView.findViewById(R.id.meeting_message_type);
            meeting_time=itemView.findViewById(R.id.meeting_time);
            meeting_item_Tv=itemView.findViewById(R.id.meeting_item_Tv);
            meeting_message_content=itemView.findViewById(R.id.meeting_message_content);
            invite_type_tv=itemView.findViewById(R.id.invite_type_tv);
            invite_head=itemView.findViewById(R.id.invite_head);

        }
    }
}
