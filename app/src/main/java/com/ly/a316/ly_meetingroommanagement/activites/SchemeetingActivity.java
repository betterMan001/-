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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.Adapter.FlipViewPaper;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.LoadingImageView;
import com.ly.a316.ly_meetingroommanagement.utils.CommonUtils;
import com.ly.a316.ly_meetingroommanagement.utils.OsUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchemeetingActivity extends BaseActivity {
    ArrayList<String> list = new ArrayList<>();
    ArrayList<View> views = new ArrayList<>();
    int a[] = {R.drawable.ali, R.drawable.alii, R.drawable.aliiii, R.drawable.ali, R.drawable.alii, R.drawable.aliiii};
    int b[] = {R.color.one, R.color.two, R.color.three, R.color.four, R.color.five, R.color.six};
 String c[]={

         "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505151721118&di=649c9a43aed72fbc4d99ec1a031510c6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015c7d574b9f8f6ac72525aee98351.jpg",
         "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505151956771&di=0eb6f306991d24b67a13ceb336f80102&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F00613def3f1beb7a35ae136341be2b589bc46a2d.jpg_320x200.jpg",
         "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505151825129&di=70bf74b87d8a15cb91a2d79f15ed0eaf&imgtype=0&src=http%3A%2F%2Fattimg.dospy.com%2Fimg%2Fday_081016%2F20081016_fee215664d5740e56c13E2YB8giERFEX.jpg",

         "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505151721118&di=649c9a43aed72fbc4d99ec1a031510c6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015c7d574b9f8f6ac72525aee98351.jpg",
         "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505151956771&di=0eb6f306991d24b67a13ceb336f80102&imgtype=0&src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farchive%2F00613def3f1beb7a35ae136341be2b589bc46a2d.jpg_320x200.jpg",
         "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505151825129&di=70bf74b87d8a15cb91a2d79f15ed0eaf&imgtype=0&src=http%3A%2F%2Fattimg.dospy.com%2Fimg%2Fday_081016%2F20081016_fee215664d5740e56c13E2YB8giERFEX.jpg",
 };


    int sa;//翻页生成的随机数
    ImageView im_back;
    CoordinatorLayout coordinatorLayout;
    LinearLayout head_hui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemeeting);
        coordinatorLayout = findViewById(R.id.coor);
        head_hui = findViewById(R.id.head_hui);
        im_back = findViewById(R.id.sc_back);
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list.add("2A 316");
        list.add("3C 112");
        list.add("1b 201");
        list.add("2A 316");
        list.add("3C 112");
        list.add("1b 201");
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        final CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) viewPager.getLayoutParams();
        params.width = CommonUtils.getScreenWidth(this) - CommonUtils.dp2px(this, 80);
        params.setMargins(CommonUtils.dp2px(this, 16), CommonUtils.dp2px(this, 20 + 16), CommonUtils.dp2px(this, 16), CommonUtils.dp2px(this, 20 + 16));
        viewPager.setLayoutParams(params);
        viewPager.setPageMargin(CommonUtils.dp2px(this, 8));
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        for (int i = 0; i < list.size(); i++) {
            CardView cardView = (CardView) LayoutInflater.from(this).inflate(R.layout.cardview_item, null, false);
            final TextView textView = (TextView) cardView.findViewById(R.id.tv_name);
            textView.setText(list.get(i));
            cardView.setTag(textView);
            LoadingImageView imageView = cardView.findViewById(R.id.tv_image);
            imageView.setImageURL(c[i]);
            views.add(cardView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SchemeetingActivity.this, DetailsMettingActivity.class);
                    intent.putExtra("hui", textView.getText().toString());
                    startActivity(intent);

                }
            });
        }

        FlipViewPaper flipViewPaper = new FlipViewPaper(views);
        viewPager.setAdapter(flipViewPaper);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sa = (int) (1 + Math.random() * (6 - 1 + 1)) - 1;
                coordinatorLayout.setBackgroundResource(b[sa]);
                head_hui.setBackgroundColor(b[sa]);
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
            page.setPivotY(page.getHeight() / 2);
            float maxRotate = 35f;
            float minScale = 0.8f;
            float maxTranslationX = page.getWidth() / 5;
            if (position <= -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                page.setRotationY(maxRotate);
                page.setPivotX(0);
                page.setScaleX(minScale);
                page.setScaleY(minScale);
                page.setTranslationX(maxTranslationX);
            } else if (position < 1) { // [-1,1]
                page.setRotationY(-position * maxRotate);
                if (position < 0)//[0,-1]
                {
                    page.setPivotX(0);
                    page.setScaleX(1 + position * (1 - minScale));
                    page.setScaleY(1 + position * (1 - minScale));
                    page.setTranslationX(-position * maxTranslationX);
                } else//[1,0]
                {
                    page.setPivotX(page.getWidth());
                    page.setScaleX(1 - position * (1 - minScale));
                    page.setScaleY(1 - position * (1 - minScale));
                    page.setTranslationX(-position * maxTranslationX);
                }
            } else { // (1,+Infinity]
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
