package com.ly.a316.ly_meetingroommanagement.customView;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 作者：余智强
 * 2018/12/8
 */
public class DepthPageTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.75f;
    //这个方法是专门修改viewpager切换效果的
    public void transformPage(View page, float position) {
        page.setPivotY(page.getHeight()/2);
        float maxRotate = 35f;
        float minScale = 0.8f;
        float maxTranslationX = page.getWidth()/5;
        if (position <= -1)
        {   // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setRotationY(maxRotate);
            page.setPivotX(0);
            page.setScaleX(minScale);
            page.setScaleY(minScale);
            page.setTranslationX(maxTranslationX);
        } else if (position < 1)
        {
            // [-1,1]
            page.setRotationY(-position * maxRotate);
            if (position < 0)//[0,-1]
            {
                page.setPivotX(0);
                page.setScaleX(1 + position * (1-minScale));
                page.setScaleY(1 + position * (1-minScale));
                page.setTranslationX(-position * maxTranslationX);
            } else//[1,0]
            {
                page.setPivotX(page.getWidth());
                page.setScaleX(1 - position * (1-minScale));
                page.setScaleY(1 - position * (1-minScale));
                page.setTranslationX(-position * maxTranslationX);
            }
        } else
        { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setRotationY(-1 * maxRotate);
            page.setPivotX(page.getWidth());
            page.setScaleX(minScale);
            page.setScaleY(minScale);
            page.setTranslationX(-maxTranslationX);
        }

    }
}

