package com.ly.a316.ly_meetingroommanagement.chooseOffice.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.ShijiandianClass;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：余智强
 * 2019/1/23
 */
public class Zhanshi_MyAdapter extends RecyclerView.Adapter {
    List<HuiyiInformation> shijian_list = new ArrayList<>();
    Context context;


    public Zhanshi_MyAdapter(Context context, List<HuiyiInformation> shijian_list) {
        this.shijian_list = shijian_list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_huiyishi, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        Glide.with(context).load(shijian_list.get(i).getmImageUrl()).into(((MyViewHolder) viewHolder).avatarImageView);
        ((MyViewHolder) viewHolder).hy_infor_huiname.setText(shijian_list.get(i).getmName());
        ((MyViewHolder) viewHolder).hy_infor_rongliang.setText(shijian_list.get(i).getmNumber());
        ((MyViewHolder) viewHolder).hy_infor_bianhao.setText(shijian_list.get(i).getmId() + "");
        ((MyViewHolder) viewHolder).hy_infor_didian.setText(shijian_list.get(i).getmAddress());
        ((MyViewHolder) viewHolder).angry_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemClick.click(shijian_list.get(i), i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shijian_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView avatarImageView;
        TextView hy_infor_huiname;//地点
        TextView hy_infor_rongliang;//会议室容量
        TextView hy_infor_bianhao;//会议室编号
        TextView hy_infor_didian;//会议室地点
        Button angry_btn;//查看详情

        MyViewHolder(View itemView) {
            super(itemView);
            avatarImageView = (ImageView) itemView.findViewById(R.id.hy_infor_image);
            hy_infor_huiname = (TextView) itemView.findViewById(R.id.hy_infor_huiname);
            hy_infor_rongliang = (TextView) itemView.findViewById(R.id.hy_infor_rongliang);
            hy_infor_bianhao = (TextView) itemView.findViewById(R.id.hy_infor_bianhao);
            hy_infor_didian = (TextView) itemView.findViewById(R.id.hy_infor_didian);
            angry_btn = itemView.findViewById(R.id.angry_btn);
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
