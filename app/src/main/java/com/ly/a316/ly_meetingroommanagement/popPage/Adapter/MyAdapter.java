package com.ly.a316.ly_meetingroommanagement.popPage.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.popPage.object.HuiyiClass;
import com.netease.nim.uikit.common.ui.recyclerview.holder.RecyclerViewHolder;

import java.util.List;

/**
 * 作者：余智强
 * 2019/3/3
 */
public class MyAdapter extends RecyclerView.Adapter {
    Context context;
    List<HuiyiClass> list;

    public MyAdapter(Context context, List<HuiyiClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_item_hui_information, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        ((MyHolder) viewHolder).item_txt_huiname.setText(list.get(i).getConference_name());
        ((MyHolder) viewHolder).item_txt_huiwhere.setText(list.get(i).getConference_where());
        ((MyHolder) viewHolder).item_txt_hui_time.setText(list.get(i).getConference_time());
        Uri uri = Uri.parse(list.get(i).getConference_img());
        ((MyHolder) viewHolder).simpleDraweeView.setImageURI(uri);
        ((MyHolder) viewHolder).item_pop_lymain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onCcick(list.get(i).getMeetingId(),list.get(i).getConference_img());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView item_txt_huiname;
        TextView item_txt_huiwhere;
        TextView item_txt_hui_time;
        LinearLayout item_pop_lymain;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.item_img_hui);
            item_txt_hui_time = itemView.findViewById(R.id.item_txt_time);
            item_txt_huiwhere = itemView.findViewById(R.id.item_tet_where);
            item_txt_huiname = itemView.findViewById(R.id.item_tet_title);
            item_pop_lymain=itemView.findViewById(R.id.item_pop_lymain);
        }
    }
    public interface Click{
        void onCcick(String id,String url);
    }
    Click click;

    public void setClick(Click click) {
        this.click = click;
    }
}
