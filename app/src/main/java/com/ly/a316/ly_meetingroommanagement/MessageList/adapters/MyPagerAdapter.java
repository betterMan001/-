package com.ly.a316.ly_meetingroommanagement.MessageList.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/*
Date:2019/2/28
Time:15:02
auther:xwd
*/
public class MyPagerAdapter extends PagerAdapter {
    private List<View> viewList;
    private List<String> titleList;

    public MyPagerAdapter(List<View> viewList, List<String> titleList) {
        this.viewList = viewList;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));//添加页卡
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));//删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);//页卡标题
    }
}
