package com.ly.a316.ly_meetingroommanagement.all_hui_room.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ly.a316.ly_meetingroommanagement.R;

import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;

import java.util.List;

/**
 * 作者：余智强
 * 2019/3/9
 */
public class All_Hui_Room_Adapter extends RecyclerView.Adapter {
    Context context;
    List<HuiyiInformation> list_hui;

    /**
     * 筛选的条件
     * 时间显示 地点筛选 容纳人数 类型筛选
     */


    public All_Hui_Room_Adapter(Context context, List<HuiyiInformation> list_hui) {
        this.context = context;
        this.list_hui = list_hui;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_huiyishi, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        setContent(i, (MyViewHolder) viewHolder);
    }

    void setContent(int i, MyViewHolder viewHolder) {
        Glide.with(context).load(list_hui.get(i).getmImageUrl()).into(((MyViewHolder) viewHolder).avatarImageView);
        viewHolder.hy_infor_huiname.setText(list_hui.get(i).getmName());
        viewHolder.hy_infor_rongliang.setText(list_hui.get(i).getmNumber());
        viewHolder.hy_infor_bianhao.setText(list_hui.get(i).getRate()+"%");
        viewHolder.hy_infor_didian.setText(list_hui.get(i).getmAddress());
        viewHolder.jindutaio.setText(list_hui.get(i).getRate()+"%");
        viewHolder.angry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClick.click(list_hui.get(i), i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_hui.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarImageView;
        TextView hy_infor_huiname;//地点
        TextView hy_infor_rongliang;//会议室容量
        TextView hy_infor_bianhao;//会议室编号
        TextView hy_infor_didian;//会议室地点
        Button angry_btn;//查看详情
        TextView jindutaio;

        MyViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.hy_infor_image);
            hy_infor_huiname = (TextView) itemView.findViewById(R.id.hy_infor_huiname);
            hy_infor_rongliang = (TextView) itemView.findViewById(R.id.hy_infor_rongliang);
            hy_infor_bianhao = (TextView) itemView.findViewById(R.id.hy_infor_bianhao);
            hy_infor_didian = (TextView) itemView.findViewById(R.id.hy_infor_didian);
            angry_btn = itemView.findViewById(R.id.angry_btn);
            jindutaio =(TextView) itemView.findViewById(R.id.jindutaio);
        }
    }

    public interface OnitemClick {

        void click(HuiyiInformation huiyiInformation, int position);
    }

    public OnitemClick onitemClick;

    public void setOnitemClick(OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }
}
