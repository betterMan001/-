package com.ly.a316.ly_meetingroommanagement.meetingList.adapter;

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
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetingList.activities.NewInviteActivity;
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.ly.a316.ly_meetingroommanagement.meetting.models.LevelOne;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/*
Date:2019/2/9
Time:19:21
auther:xwd
*/
public class AttendeeAdapter extends RecyclerView.Adapter {
    private Context context;
    private int count=50;
    List<Attendee> list;
    String mId;
    public AttendeeAdapter(Context context, List<Attendee> list, String mId) {
        this.context = context;
        this.list=list;
        count=list.size()+1;
        this.mId=mId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.attendee_item,parent,false);
        return new AttendeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int pos) {
        AttendeeViewHolder holder=(AttendeeViewHolder) viewHolder;
        Attendee temp=null;

         if(pos==count-1){
             holder.head_rv.setImageResource(R.drawable.add001);
             holder.attendee_tv.setText("");
             holder.head_rv.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     NewInviteActivity.start(context,mId);
                 }
             });
         }
         else{
             temp=list.get(pos);
             //设置头像
             ImageView headView=holder.head_rv;
             //设置glide加载的选项
             RequestOptions requestOptions=new RequestOptions()
                     .placeholder(R.drawable.user_default_head)
                     .error(R.drawable.user_default_head);
             Glide
                     .with(context)
                     .load(temp.getImage())
                     .apply(requestOptions)
                     .into(headView);
             holder.attendee_tv.setText(temp.getName());
         }
    }

    @Override
    public int getItemCount() {
        return count;
    }
    public class AttendeeViewHolder extends RecyclerView.ViewHolder{
        public RoundedImageView head_rv;
        public TextView attendee_tv;
        public AttendeeViewHolder( View itemView) {
            super(itemView);
            head_rv=itemView.findViewById(R.id.head_rv);
            attendee_tv=itemView.findViewById(R.id.attendee_tv);
        }
    }
}
