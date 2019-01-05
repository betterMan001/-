package com.ly.a316.ly_meetingroommanagement.activites.chooseOffice;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ly.a316.ly_meetingroommanagement.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：余智强
 * 2019/1/5
 */
public class shebeiAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> list;
    View view;
    int qwe;
    public shebeiAdapter(Context context, List<String> list,int whoshui) {
        this.context = context;
        this.list = list;
        this.qwe = whoshui;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(context).inflate(R.layout.item_shebei, viewGroup, false);
        ShebeiViewHolder s = new ShebeiViewHolder(view);
        return s;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        ((ShebeiViewHolder) viewHolder).item_shebei_check.setText(list.get(i).toString());
        ((ShebeiViewHolder) viewHolder).item_shebei_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked){
                    if(qwe == 1){
                        WriteconditionActivity.map.put(i, ((ShebeiViewHolder) viewHolder).item_shebei_check.getText().toString());
                    }else{
                        WriteconditionActivity.mapwhere.put(i, ((ShebeiViewHolder) viewHolder).item_shebei_check.getText().toString());

                    }
                }else{
                    if(qwe == 1){
                        WriteconditionActivity.map.put(i, "");
                    }else{
                        WriteconditionActivity.mapwhere.put(i, "");
                    }

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ShebeiViewHolder extends RecyclerView.ViewHolder {
        CheckBox item_shebei_check;

        public ShebeiViewHolder(@NonNull View itemView) {
            super(itemView);
            item_shebei_check = itemView.findViewById(R.id.item_shebei_check);
        }
    }


    public interface OnclickItem {
        void OnItem_shebei_Click(int position, RecyclerView.ViewHolder viewHolder);//选择设备的时候调用
    }

   OnclickItem onclickItem;

    public void setOnclickItem(OnclickItem onclickItem) {
        this.onclickItem = onclickItem;
    }
}
