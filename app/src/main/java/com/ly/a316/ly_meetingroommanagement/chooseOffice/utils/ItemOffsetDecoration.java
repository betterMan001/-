package com.ly.a316.ly_meetingroommanagement.chooseOffice.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：余智强
 * 2019/1/3
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int mSpacing;

    public ItemOffsetDecoration(int itemOffset) {
        mSpacing = itemOffset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mSpacing, mSpacing, mSpacing, mSpacing);
    }
}