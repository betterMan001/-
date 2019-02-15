package com.ly.a316.ly_meetingroommanagement.endActivity.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.endActivity.adaper.FilePickerShowAdapter;
import com.ly.a316.ly_meetingroommanagement.endActivity.object.OnFileItemClickListener;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.OpenFile;
import com.ly.a316.ly_meetingroommanagement.endActivity.util.PickerManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class End_Activity extends AppCompatActivity {
    @BindView(R.id.addSchedule_toolbar)
    Toolbar addScheduleToolbar;

    private LinearLayout imageview;
    private int window_width;
    private int window_height;
    private int image_width;
    private LinearLayout wujiaoxing;
    private LinearLayout shop;
    private LinearLayout fans;
    private LinearLayout news;
    private LinearLayout expression;
    private LinearLayout trails;
    private LinearLayout istar;

    int height = RelativeLayout.LayoutParams.WRAP_CONTENT;
    int width = RelativeLayout.LayoutParams.WRAP_CONTENT;

    @BindView(R.id.xianshi_file_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.relay_baoguo)
    RelativeLayout baoguo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_end_, null);
        setContentView(view);
        ButterKnife.bind(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        addScheduleToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AlphaAnimation aa = new AlphaAnimation(0, 1.0f);
        aa.setDuration(400);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                int startangle = 54;
                int length = (int) ((window_width
                        / (Math.sin(72 * Math.PI / 180)) - image_width) / 2 - image_width / 5);
                ValueAnimator expressionanim = zhixian(expression, startangle,
                        length);
                ValueAnimator trailsanim = zhixian(trails, startangle + 72,
                        length);
                ValueAnimator newsanim = zhixian(news, startangle + (72 * 2),
                        length);
                ValueAnimator shopanim = zhixian(shop, startangle + (72 * 3),
                        length);
                ValueAnimator fansanim = zhixian(fans, startangle + (72 * 4),
                        length);
                AnimatorSet animSet = new AnimatorSet();

                ObjectAnimator fadeIn = ObjectAnimator.ofFloat(imageview,
                        "alpha", 0f, 1f);
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(istar, "alpha",
                        1f, 0f);

                animSet.playTogether(fadeIn, fadeOut, shopanim, fansanim,
                        newsanim, expressionanim, trailsanim);
                animSet.setDuration(1000);
                animSet.start();

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        WindowManager wm = getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        window_width = dm.widthPixels;
        window_height = dm.heightPixels - 20;
        ;
        image_width = window_width / 4;

        imageview = (LinearLayout) findViewById(R.id.head);
        RelativeLayout.LayoutParams paras = new RelativeLayout.LayoutParams(
                width, height);
        paras.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageview.setLayoutParams(paras);

        /*wujiaoxing = (ImageView) findViewById(R.id.wujiaoxing);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT); // , 1是可选写的
        lp.setMargins(image_width * 5 / 8, 0, image_width * 5 / 8, 0);
        wujiaoxing.setLayoutParams(lp);*/
        initbuttons();

    }

    /**
     * 沿直线运动。
     *
     * @param view   要移动的对象。
     * @param
     * @param length
     */
    public ValueAnimator zhixian(final View view, final int angle,
                                 final int length) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setObjectValues(new PointF(0, 0));
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {
            // fraction = t / duration
            @Override
            public PointF evaluate(float fraction, PointF startValue,
                                   PointF endValue) {
                Log.v("znz", "znz ---> " + fraction);
                PointF point = new PointF();
                point.x = (float) (fraction * length * Math.cos(angle * Math.PI
                        / 180));
                point.y = (float) (fraction * length * Math.sin(angle * Math.PI
                        / 180));
                return point;
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF point = (PointF) animation.getAnimatedValue();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        width, height);
                params.leftMargin = (int) point.x
                        + (window_width / 2 - imageview.getWidth() / 2); // Your
                // coordinate
                params.topMargin = (int) point.y
                        + (window_height / 4 - (imageview.getHeight() + 20) / 2); // Your Y
                // coordinate
                Log.v("znz", "point.x ---> " + point.x);
                Log.v("znz", "point.y ---> " + point.y);
                view.setLayoutParams(params);
            }
        });
        return valueAnimator;
    }

    /**
     * 初始化五颗按钮。
     */
    private void initbuttons() {
        shop = (LinearLayout) findViewById(R.id.shop);
        fans = (LinearLayout) findViewById(R.id.fans);
        news = (LinearLayout) findViewById(R.id.news);
        expression = (LinearLayout) findViewById(R.id.expression);
        trails = (LinearLayout) findViewById(R.id.trails);
        istar = (LinearLayout) findViewById(R.id.isstartlogo);
        RelativeLayout.LayoutParams paras = new RelativeLayout.LayoutParams(
                width, height);
        paras.addRule(RelativeLayout.CENTER_IN_PARENT);
        shop.setLayoutParams(paras);
        fans.setLayoutParams(paras);
        news.setLayoutParams(paras);
        expression.setLayoutParams(paras);
        trails.setLayoutParams(paras);
        istar.setLayoutParams(paras);
    }

    public void add_file(View view) {
        Intent intent = new Intent(this, FilePickerActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == 101) {
                FilePickerShowAdapter adapter = new FilePickerShowAdapter(this, PickerManager.getInstance().files);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new OnFileItemClickListener() {
                    @Override
                    public void click(int position) {
                        startActivity(Intent.createChooser(OpenFile.openFile(PickerManager.getInstance().files.get(position).getFilePath(), getApplicationContext()), "选择程序"));
                    }
                });
            }
        }
    }
}
