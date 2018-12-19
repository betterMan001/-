package com.ly.a316.ly_meetingroommanagement.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.classes.Schedule;
import com.ly.a316.ly_meetingroommanagement.R;

import java.util.List;

/**
 * 作者：余智强
 * 2018 12/5
 */
public class Calendar_Adapter extends RecyclerView.Adapter {
    private Context context;
    private List<Schedule> list;

    public Calendar_Adapter(Context context, List<Schedule> list) {
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
        ((MyViewHolder) viewHolder).item_calendar_day.setText(list.get(i).getDateTime());
        if (list.get(i).getContent().equals("")) {
            ((MyViewHolder) viewHolder).item_calendar_todayri.setText("无安排");
        } else {
            ((MyViewHolder) viewHolder).item_calendar_todayri.setText(list.get(i).getContent());
        }

        ((MyViewHolder)viewHolder).item_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onitemClick(i);
            }
        });

        ((MyViewHolder)viewHolder).item_calendar_alerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onitemAlarm(i);
            }
        });
        ((MyViewHolder)viewHolder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onitemDelete(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item_all;
        public TextView item_calendar_day, item_calendar_todayri;
      public   Button delete,item_calendar_alerm;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_calendar_day = itemView.findViewById(R.id.item_calendar_day);
            item_calendar_todayri = itemView.findViewById(R.id.item_calendar_todayri);
            item_all = itemView.findViewById(R.id.item_all);
            delete = itemView.findViewById(R.id.delete);
            item_calendar_alerm = itemView.findViewById(R.id.item_calendar_alerm);
        }
    }

   public interface OnItemClick {
        void onitemClick(int position);
        void onitemDelete(int position);
        void onitemAlarm(int position);
    }

    OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}
