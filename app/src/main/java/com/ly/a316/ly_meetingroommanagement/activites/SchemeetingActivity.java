package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.Adapter.FlipViewPaper;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.utils.CommonUtils;
import com.ly.a316.ly_meetingroommanagement.utils.OsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchemeetingActivity extends BaseActivity {
    ArrayList<String> list=new ArrayList<>();
    ArrayList<View> views=new ArrayList<>();
    int a[] ={R.drawable.ali,R.drawable.alii,R.drawable.aliiii};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemeeting);
       list.add("2A 316");
       list.add("3C 112");
       list.add("1b 201");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) viewPager.getLayoutParams();
        params.width= CommonUtils.getScreenWidth(this)-CommonUtils.dp2px(this,80);
        params.setMargins(CommonUtils.dp2px(this,16),CommonUtils.dp2px(this,20+16),CommonUtils.dp2px(this,16),CommonUtils.dp2px(this,20+16));
        viewPager.setLayoutParams(params);
        viewPager.setPageMargin(CommonUtils.dp2px(this,8));
        viewPager.setPageTransformer(true,new DepthPageTransformer());
        for(int i=0;i<list.size(); i++){
            CardView cardView= (CardView) LayoutInflater.from(this).inflate(R.layout.cardview_item,null,false);
            final TextView textView=(TextView) cardView.findViewById(R.id.tv_name);
            textView.setText(list.get(i));
            cardView.setTag(textView);
            ImageView imageView = cardView.findViewById(R.id.tv_image);
            imageView.setImageResource(a[i]);
            views.add(cardView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(SchemeetingActivity.this,DetailsMettingActivity.class);
                    intent.putExtra("hui",textView.getText().toString());
                    startActivity(intent);

                }
            });
        }
        FlipViewPaper flipViewPaper=new FlipViewPaper(views);
        viewPager.setAdapter(flipViewPaper);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        //这个方法是专门修改viewpager切换效果的
        public void transformPage(View page, float position) {
            page.setPivotY(page.getHeight()/2);
            float maxRotate = 35f;
            float minScale = 0.8f;
            float maxTranslationX = page.getWidth()/5;
            if (position <= -1)
            { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setRotationY(maxRotate);
                page.setPivotX(0);
                page.setScaleX(minScale);
                page.setScaleY(minScale);
                page.setTranslationX(maxTranslationX);
            } else if (position < 1)
            { // [-1,1]
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

}
