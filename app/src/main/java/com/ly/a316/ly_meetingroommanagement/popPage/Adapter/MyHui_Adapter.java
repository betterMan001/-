package com.ly.a316.ly_meetingroommanagement.popPage.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.popPage.object.LiuYan_class;

import java.util.List;

/**
 * 作者：余智强
 * 2019/3/5
 */
public class MyHui_Adapter extends RecyclerView.Adapter {
    private Context context;
    List<LiuYan_class> liuYan_classes;

    public MyHui_Adapter(Context context, List<LiuYan_class> liuYan_classes) {
        this.context = context;
        this.liuYan_classes = liuYan_classes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.information_item_class,viewGroup,false);
        MyviewHoder myviewHoder = new MyviewHoder(view);
        return myviewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(!liuYan_classes.get(i).getImage().equals(null)){
            Uri uri = Uri.parse(liuYan_classes.get(i).getImage());
            ((MyviewHoder)viewHolder).simpleDraweeView.setImageURI(uri);
       }
        ((MyviewHoder)viewHolder).TextView_content.setText(liuYan_classes.get(i).getComment());
        ((MyviewHoder)viewHolder).textView_name.setText(liuYan_classes.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return liuYan_classes.size();
    }
    class MyviewHoder extends RecyclerView.ViewHolder{
        SimpleDraweeView simpleDraweeView;
        TextView textView_name;
        TextView TextView_content;
        public MyviewHoder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView = itemView.findViewById(R.id.information_img_rentou);
            textView_name = itemView.findViewById(R.id.information_txt_pinglunrenmingzi);
            TextView_content = itemView.findViewById(R.id.information_txt_liuyan);
        }
    }
}
