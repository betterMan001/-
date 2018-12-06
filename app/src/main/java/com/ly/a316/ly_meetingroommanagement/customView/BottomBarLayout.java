package com.ly.a316.ly_meetingroommanagement.customView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.classes.TabEntity;
import com.ly.a316.ly_meetingroommanagement.R;

import java.util.ArrayList;
import java.util.List;

/**
 *  描述：自定义底部导航栏
 *  作者： 余智强
 *  创建时间：12/4 14：13
*/
public class BottomBarLayout extends LinearLayout implements View.OnClickListener {
    private  OnItemClickListener onItemClickListener;
    private int normalTextColor;
    private int selectTextColor;
    private LinearLayout linearLayout;
    private List<TabEntity> tablist = new ArrayList<>();
   public interface OnItemClickListener{
        void onItemCLick(int position,View view);
    }
    public  void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public BottomBarLayout(Context context) {
        super(context);
        init(context);
    }

    public BottomBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        linearLayout = (LinearLayout) View.inflate(context, R.layout.container_layout,null);
        addView(linearLayout);
    }

    public void setNormalTextColor(int color){
        this.normalTextColor = color;
    }

    public void setSelectTextColor(int color){
        this.selectTextColor = color;
    }
    public void setTabList(List<TabEntity> tabs){
        if(tabs == null || tabs.size() == 0){
            return ;
        }
        this.tablist = tabs;
        for(int i = 0;i < tabs.size();i++){
            View itemview = View.inflate(getContext(),R.layout.item_tab_layout,null);
            itemview.setId(i);
            itemview.setOnClickListener(this);

            TextView text = (TextView) itemview.findViewById(R.id.tv_title);
            ImageView icon = (ImageView) itemview.findViewById(R.id.iv_icon);
            View redPoint = itemview.findViewById(R.id.red_point);
            TextView number = (TextView) itemview.findViewById(R.id.tv_count);

            TabEntity itemTab = tablist.get(i);
            text.setText(itemTab.getText());
            text.setTextColor(normalTextColor);
            icon.setImageResource(itemTab.getNormalIconId());

            if(itemTab.isShowPoint()){
                redPoint.setVisibility(View.VISIBLE);
            }else{
                redPoint.setVisibility(View.GONE);
            }
            if(itemTab.getNewsCount() == 0){
                number.setVisibility(View.GONE);
            }else if(itemTab.getNewsCount()>99){
                number.setVisibility(View.VISIBLE);
                number.setText("99+");
            }else {
                number.setVisibility(View.VISIBLE);
                number.setText(String.format("%d",itemTab.getNewsCount()));
            }

            linearLayout.addView(itemview);
            if(i==0){
                showTab(0,itemview);
            }
        }
    }

    public void showTab(int position,View view){
        clearStatus();//回复默然样式
        TextView text = (TextView) view.findViewById(R.id.tv_title);
        text.setTextColor(selectTextColor);
        ImageView icon = (ImageView) view.findViewById(R.id.iv_icon);

        icon.setImageResource(tablist.get(position).getSelectIconId());

    }
    private void clearStatus() {
        for (int i=0;i<linearLayout.getChildCount();i++){
            View itemView = linearLayout.getChildAt(i);
            ImageView icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            TextView text = (TextView) itemView.findViewById(R.id.tv_title);
            text.setTextColor(normalTextColor);
            icon.setImageResource(tablist.get(i).getNormalIconId());
        }
    }
    @Override
    public void onClick(View v) {
        if(onItemClickListener == null){
            return;
        }
        switch(v.getId()){

            case 0:
                onItemClickListener.onItemCLick(0,v);
                showTab(0,v);
                break;
            case 1:
                onItemClickListener.onItemCLick(1,v);
                showTab(1,v);
                break;
            case 2:
                onItemClickListener.onItemCLick(2,v);
                showTab(2,v);
                break;
            case 3:
                onItemClickListener.onItemCLick(3,v);
                showTab(3,v);
                break;
            case 4:
                onItemClickListener.onItemCLick(4,v);
                showTab(4,v);
                break;
        }
    }
}
