package com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.main.BaseActivity;
import com.ly.a316.ly_meetingroommanagement.meetting.adapter.choosemettingAdapter;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.object.Item;

import java.util.List;

/**
 * 作者：余智强
 * 2019/2/28
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Item> liet;
    private View view;

    public MyAdapter(Context context, List<Item> liet) {
        this.context = context;
        this.liet = liet;
    }

    @Override
    public int getCount() {
        return liet.size();
    }

    @Override
    public Object getItem(int position) {
        Log.i("zjc",position+" ");
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    ViewHoder hoder = null;
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.the_one_item, parent, false);
            hoder = new ViewHoder();
            hoder.all_kong = (RelativeLayout) convertView.findViewById(R.id.all_kong);
            hoder.textView = (TextView) convertView.findViewById(R.id.item_title_tou);
            hoder.imageView = (ImageView) convertView.findViewById(R.id.item_title_chacha);
            convertView.setTag(hoder);
        } else {
            hoder = (ViewHoder) convertView.getTag();
        }
        hoder.textView.setText(liet.get(position).getTitle_name());
        hoder.imageView.setImageResource(liet.get(position).getImade_id());
        hoder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    v.setSelected(false);
                } else {
                    onclick.text_click(v,liet.get(position).getTitle_name(),hoder.imageView);
                    v.setSelected(true);
                }
            }
        });
        hoder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.img_click(v,position);
            }
        });
        return convertView;
    }
    private class ViewHoder{
        RelativeLayout all_kong;
        TextView textView;
        ImageView imageView;
    }
    public interface Onclick{
        void text_click(View view,String  neirong,ImageView imageView);
        void img_click(View view,int position);
    }
    Onclick onclick;

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }
}
