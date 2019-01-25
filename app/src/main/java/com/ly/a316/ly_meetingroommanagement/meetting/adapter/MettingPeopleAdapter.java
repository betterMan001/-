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
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.Device;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.DeviceType;

import java.util.List;

/**
 * 作者：余智强
 * 2018/12/11
 */
public class MettingPeopleAdapter extends RecyclerView.Adapter {
    private Context context;

    private List<Device> list;
    private List<DeviceType> list_type;
    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;
    String whoo = "1";

    public MettingPeopleAdapter(Context context, List<Device> list) {
        this.context = context;
        this.list = list;
    }

    public MettingPeopleAdapter(Context context, List<DeviceType> list, String whoo) {
        this.context = context;
        this.list_type = list;
        this.whoo = whoo;
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
        if (whoo.equals("3")) {
            if (list_type.get(i).getChoose().equals("0")) {
                ((MyViewHolder) viewHolder).check_box.setImageResource(R.mipmap.ic_checked);
            } else {
                ((MyViewHolder) viewHolder).check_box.setImageResource(R.mipmap.ic_uncheck);
            }
            ((MyViewHolder) viewHolder).pan.setText(list_type.get(i).getChoose());
            ((MyViewHolder) viewHolder).metting_name.setText(list_type.get(i).getName());
            ((MyViewHolder) viewHolder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickk.onItemclick(((MyViewHolder) viewHolder), i);
                }
            });
        } else {
            if (list.get(i).getChoose().equals("0")) {
                ((MyViewHolder) viewHolder).check_box.setImageResource(R.mipmap.ic_checked);
            } else {
                ((MyViewHolder) viewHolder).check_box.setImageResource(R.mipmap.ic_uncheck);
            }
            ((MyViewHolder) viewHolder).pan.setText(list.get(i).getChoose());
            ((MyViewHolder) viewHolder).metting_name.setText(list.get(i).getdName());
            ((MyViewHolder) viewHolder).item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickk.onItemclick(((MyViewHolder) viewHolder), i);
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (whoo.equals("3")) {
            return list_type.size();
        } else {
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView check_box;
        public TextView metting_name, metting_tel, pan;
        LinearLayout item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            check_box = itemView.findViewById(R.id.check_box);
            metting_name = itemView.findViewById(R.id.metting_name);
            //  metting_tel = itemView.findViewById(R.id.metting_tel);
            item = itemView.findViewById(R.id.item);
            pan = itemView.findViewById(R.id.pan);
        }
    }

    public interface OnItemClickk {
        void onItemclick(MyViewHolder myViewHolder, int position);
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
