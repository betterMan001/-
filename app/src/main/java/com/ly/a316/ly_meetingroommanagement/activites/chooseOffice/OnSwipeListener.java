package com.ly.a316.ly_meetingroommanagement.activites.chooseOffice;

import android.support.v7.widget.RecyclerView;

/**
 * 作者：余智强
 * 2019/1/3
 */
public interface OnSwipeListener<T> {
    /**
     * 卡片还在滑动的时候调用
     * @param viewHolder 该滑动卡片的viewholder
     * @param ratio 滑动进度的比例
     * @param direction  卡片滑动方向
       CardConfig.SWIPING_LEFT  向左滑，
       CardConfig.SWIPING_RIGHT 向右滑，
       CardConfig.SWIPING_NONE  为不偏左也不偏右
 */
    void onSwiping(RecyclerView.ViewHolder viewHolder,float ratio,int direction);

    /**
     * 卡片完全滑出时的回调
     * @param viewHolder 该滑出卡片的viewholder
     * @param t          该滑出卡片的数据
     * @param direction  卡片滑出的方向，同上
     */
    void onSwiped(RecyclerView.ViewHolder viewHolder,T t,int direction);

    /**
     * 所有卡pain全部滑出的回调
     */
    void onSwipedClear();

}
