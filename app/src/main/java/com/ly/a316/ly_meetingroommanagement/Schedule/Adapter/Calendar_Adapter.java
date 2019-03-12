package com.ly.a316.ly_meetingroommanagement.Schedule.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.Schedule.Classes.Schedule;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.calendar_fra.object.Day_Object;

import java.util.List;

/**
 * 作者：余智强
 * 2018 12/5
 */
public class Calendar_Adapter extends RecyclerView.Adapter {
    private Context context;
    private List<Day_Object> list;

    public Calendar_Adapter(Context context, List<Day_Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_calendar, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        String start_time = list.get(i).getStartTime().substring(11,16);
        String end_time = list.get(i).getEndTime().substring(11,16);
        ((MyViewHolder) viewHolder).item_mettingstart.setText(start_time);
        ((MyViewHolder) viewHolder).item_mettingend.setText(end_time);
        ((MyViewHolder) viewHolder).item_mettinghead.setText(list.get(i).getContent());
        ((MyViewHolder) viewHolder).item_mettingpeople.setText(list.get(i).getLeaders());
        ((MyViewHolder) viewHolder).item_mettingwhere.setText(list.get(i).getAddress());
        ((MyViewHolder) viewHolder).item_mettingplan.setText(list.get(i).getRemark());
        ((MyViewHolder) viewHolder).item_calendar_day.setText("["+list.get(i).getMeetType()+"]");
      //   ((MyViewHolder) viewHolder).item_alerttime.setText(list.get(i).getAlert_time());
        ((MyViewHolder)viewHolder).item_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onitemClick(i,list.get(i).getmId(),list.get(i).getStartTime(),list.get(i).getEndTime());
            }
        });

        ((MyViewHolder)viewHolder).item_calendar_alerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onitemAlarm(i,list.get(i).getNoId());
            }
        });
        ((MyViewHolder)viewHolder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onitemDelete(list.get(i).getNoId(),i);
            }
        });
        if(list.get(i).getState().equals("1")){
            ((MyViewHolder)viewHolder).finish.setText("未完成");
        }else{
            ((MyViewHolder)viewHolder).finish.setText("已完成");
        }
        ((MyViewHolder)viewHolder).finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onitenFinish(i,list.get(i).getNoId(),  ((MyViewHolder) viewHolder));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item_all;
        public TextView item_mettingstart, item_mettingend,item_mettinghead,item_mettingpeople,item_mettingwhere,item_mettingplan,item_calendar_day,item_alerttime;
        public   Button delete,item_calendar_alerm,finish;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_mettingstart = itemView.findViewById(R.id.item_mettingstart);
            item_mettingend = itemView.findViewById(R.id.item_mettingend);
            item_mettinghead = itemView.findViewById(R.id.item_mettinghead);
            item_mettingpeople = itemView.findViewById(R.id.item_mettingpeople);
            item_mettingwhere = itemView.findViewById(R.id.item_mettingwhere);
            item_mettingplan = itemView.findViewById(R.id.item_mettingplan);
            item_all = itemView.findViewById(R.id.item_all);
            delete = itemView.findViewById(R.id.delete);
            finish = itemView.findViewById(R.id.finish);
            item_calendar_alerm = itemView.findViewById(R.id.item_calendar_alerm);
            item_calendar_day = itemView.findViewById(R.id.item_calendar_day);
            item_alerttime =  itemView.findViewById(R.id.item_alerttime);
        }
    }

   public interface OnItemClick {
        void onitemClick(int position,String event_idd,String startime,String endtime);
        void onitemDelete(String position,int weizhi);
        void onitemAlarm(int position,String weizzhi);
        void onitenFinish(int position,String eveniIdd,MyViewHolder viewHolder);
    }

    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}
