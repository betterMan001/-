package com.ly.a316.ly_meetingroommanagement.activites.chooseOffice;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * 作者：余智强
 * 2019/1/4
 */
public class MyManager extends LinearLayoutManager {
    public MyManager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }


}
