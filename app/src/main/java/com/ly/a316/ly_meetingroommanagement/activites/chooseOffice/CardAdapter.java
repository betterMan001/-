package com.ly.a316.ly_meetingroommanagement.activites.chooseOffice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.netease.nim.uikit.common.framework.infra.TaskObservable;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：余智强
 * 2019/1/3
 */
public class CardAdapter extends RecyclerView.Adapter {

    final static int PROPLE_COUNT = 0;
    final static int EQUIPMENT_COUNT = 1;
    final static int TIME = 2;
    final static int PLACE = 3;

    View view;
    private List<String> list = new ArrayList();
    Context context;

    public CardAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EQUIPMENT_COUNT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_choose_shebei, parent, false);
            EquipmentHolder equipmentHolder = new EquipmentHolder(view);
            return equipmentHolder;
        } else if(viewType == TIME){
            view = LayoutInflater.from(context).inflate(R.layout.item_choose_time, parent, false);
            TimeViewHolder timeViewHolder = new TimeViewHolder(view);
            return timeViewHolder;
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.row_empty_card, parent, false);
            PeopleViewHolder peopleViewHolder = new PeopleViewHolder(view);
            return peopleViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        //设备需求
        if (viewHolder instanceof EquipmentHolder) {
            ((EquipmentHolder) viewHolder).item_choose_shebei_xia.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    onclickItem.OnItem_shebei_Click(i, ((EquipmentHolder) viewHolder));
                }
            });
        } else if( viewHolder instanceof TimeViewHolder){
            ((TimeViewHolder) viewHolder).item_choose_time_xia.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    if (((CardAdapter.TimeViewHolder) viewHolder).item_time_choose.getVisibility() == 8) {
                        ((CardAdapter.TimeViewHolder) viewHolder).item_time_choose.setVisibility(View.VISIBLE);
                    } else {
                        ((CardAdapter.TimeViewHolder) viewHolder).item_time_choose.setVisibility(View.GONE);
                    }
                }
            });

            ((TimeViewHolder) viewHolder).item_choose_shike.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    onclickItem.OnItem_time_Click(((TimeViewHolder) viewHolder),1);
                    Toast.makeText(context,"点击了时刻", Toast.LENGTH_SHORT).show();
                }
            });

            ((TimeViewHolder) viewHolder).item_choose_shijianduan.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("WrongConstant")
                @Override
                public void onClick(View v) {
                    onclickItem.OnItem_time_Click(((TimeViewHolder) viewHolder),2);
                    Toast.makeText(context,"点击了时间段", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            ((PeopleViewHolder) viewHolder).one.setHint(list.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //参会人的样式
    public class PeopleViewHolder extends RecyclerView.ViewHolder {
        EditText one;
        public PeopleViewHolder(View itemView) {
            super(itemView);
            one = itemView.findViewById(R.id.i_one);
        }
    }

    //设备需求的样式
    public class EquipmentHolder extends RecyclerView.ViewHolder {
        public LinearLayout item_choose_shebei_xia, item_choose_she;
        public EquipmentHolder(@NonNull View itemView) {
            super(itemView);
            item_choose_shebei_xia = itemView.findViewById(R.id.item_choose_shebei_xia);
            item_choose_she = itemView.findViewById(R.id.item_choose_she);
        }
    }

    //时间选择
    public class TimeViewHolder extends RecyclerView.ViewHolder{
        LinearLayout item_choose_time_xia,item_time_choose,item_choose_shikeview;
        Button item_choose_shike,item_choose_shijianduan;
        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);
            item_choose_time_xia = itemView.findViewById(R.id.item_choose_time_xia);
            item_choose_shike = itemView.findViewById(R.id.item_choose_shike);
            item_choose_shijianduan = itemView.findViewById(R.id.item_choose_shijianduan);
            item_time_choose = itemView.findViewById(R.id.item_time_choose);
            item_choose_shikeview = itemView.findViewById(R.id.item_choose_shikeview);
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == EQUIPMENT_COUNT) {
            return EQUIPMENT_COUNT;
        } else if(position == TIME){
            return  TIME;
        }else{
            return PROPLE_COUNT;
        }
    }


    public interface  OnclickItem{
        void OnItem_shebei_Click(int position, RecyclerView.ViewHolder viewHolder);//选择设备的时候调用
        void OnItem_ren_Click(RecyclerView.ViewHolder viewHolder);//点击人的时候调用
        void OnItem_time_Click(RecyclerView.ViewHolder viewHolder,int whoclick);//选择时间
    }
    OnclickItem onclickItem;

    public void setOnclickItem(OnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }

}