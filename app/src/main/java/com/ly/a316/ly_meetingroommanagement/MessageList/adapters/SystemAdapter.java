package com.ly.a316.ly_meetingroommanagement.MessageList.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.MessageList.models.MessageModel;
import com.ly.a316.ly_meetingroommanagement.R;

import java.util.List;

/*
Date:2019/2/28
Time:14:34
auther:xwd
*/
public class SystemAdapter extends RecyclerView.Adapter {
    //上下文和数据
    private Context context;
    private List<MessageModel> list;
    public SystemAdapter(Context context, List<MessageModel> list) {
        this.context = context;
        this.list = list;
    }

    //将目标layout渲染成View
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.system_message_item,parent,false);
        return new SysMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel model=list.get(position);
        SysMessageHolder holder1= (SysMessageHolder) holder;
        holder1.textView.setText(model.getContent());
        holder1.message_time.setText(model.getTime());
    }

    @Override
    public int getItemCount() {
        if(list.size()>0)
            return list.size();
        else return 0;
    }
    //写一个Holder继承RecyclerView.ViewHolder
    static class SysMessageHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public TextView message_time;
        public SysMessageHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.system_item_Tv);
            message_time=itemView.findViewById(R.id.message_time);
        }
    }
}
