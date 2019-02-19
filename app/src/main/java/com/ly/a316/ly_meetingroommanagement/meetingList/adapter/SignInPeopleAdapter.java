package com.ly.a316.ly_meetingroommanagement.meetingList.adapter;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
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
import com.ly.a316.ly_meetingroommanagement.meetingList.models.Attendee;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/*
Date:2019/2/18
Time:21:16
auther:xwd
*/
public class SignInPeopleAdapter extends RecyclerView.Adapter {
    private Context context;
    List<Attendee> list;

    public SignInPeopleAdapter(Context context, List<Attendee> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int pos) {
        View view= LayoutInflater.from(context).inflate(R.layout.attendee_item,parent,false);
        return new SignInPeopleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
     SignInPeopleHolder holder=(SignInPeopleHolder)viewHolder;
        Attendee attendee=list.get(position);
       holder.attendee_tv.setText(attendee.getName());
        ImageView headView=holder.head_rv;
        //设置glide加载的选项
        RequestOptions requestOptions=new RequestOptions()
                .placeholder(R.drawable.user_default_head)
                .error(R.drawable.user_default_head);
        Glide
                .with(context)
                .load(attendee.getImage())
                .apply(requestOptions)
                .into(headView);
        /** 方法1：
         * ColorMatrix类有一个内置的方法可用于改变饱和度。
         * 传入一个大于1的数字将增加饱和度，而传入一个0～1之间的数字会减少饱和度。0值将产生一幅灰度图像。
         */
        if(position==3){
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            headView.setColorFilter(filter);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class SignInPeopleHolder extends RecyclerView.ViewHolder{
        public RoundedImageView head_rv;
        public TextView attendee_tv;
        public SignInPeopleHolder( View itemView) {
            super(itemView);
            head_rv=itemView.findViewById(R.id.head_rv);
            attendee_tv=itemView.findViewById(R.id.attendee_tv);
        }
    }
}
