package com.ly.a316.ly_meetingroommanagement.meetting.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetting.models.MettingPeople;

import java.util.List;

/**
 * 作者：余智强
 * 2018/12/11
 */
public class MettingPeopleAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<MettingPeople> list;
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;
    public MettingPeopleAdapter(Context context, List<MettingPeople> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mettingpeople, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if (mEditMode == MYLIVE_MODE_CHECK) {
            ((MyViewHolder) viewHolder).check_box.setVisibility(View.GONE);
        } else {
            ((MyViewHolder) viewHolder).check_box.setVisibility(View.VISIBLE);
        }
        if (list.get(i).getChoose().equals("0")) {
            ((MyViewHolder) viewHolder).check_box.setImageResource(R.mipmap.ic_checked);
        } else {
            ((MyViewHolder) viewHolder).check_box.setImageResource(R.mipmap.ic_uncheck);
        }
        ((MyViewHolder) viewHolder).pan.setText(list.get(i).getChoose());
        ((MyViewHolder) viewHolder).metting_name.setText(list.get(i).getName());
        ((MyViewHolder) viewHolder).metting_tel.setText(list.get(i).getPeotel());
        ((MyViewHolder) viewHolder).item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickk.onItemclick(  ((MyViewHolder) viewHolder),i);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       public ImageView check_box;
        public TextView metting_name, metting_tel,pan ;
        LinearLayout item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check_box = itemView.findViewById(R.id.check_box);
            metting_name = itemView.findViewById(R.id.metting_name);
            metting_tel = itemView.findViewById(R.id.metting_tel);
            item  = itemView.findViewById(R.id.item);
            pan = itemView.findViewById(R.id.pan);
        }
    }

    public interface OnItemClickk {
        void onItemclick(MyViewHolder myViewHolder,int position);
    }

    OnItemClickk onItemClickk;

    public void setOnItemClick(OnItemClickk onItemClickk) {
        this.onItemClickk = onItemClickk;
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }
}
