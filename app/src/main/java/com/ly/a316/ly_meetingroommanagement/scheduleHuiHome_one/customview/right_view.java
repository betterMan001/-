package com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_one.customview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：余智强
 * 2019/2/27
 */
public class right_view extends View {
    /**
     * view的宽度
     *
     * @param context
     */
    private int width;
    /**
     * view的高度
     */
    private int height;

    /**
     * 背景颜色
     *
     * @param context
     */
    private int bg_color = Color.WHITE;//0xffbc7d53;
    /**
     * 按钮文字字符串
     */
    private String buttonString = "X";
    /**
     * 动画执行时间
     */
    private int duration = 1000;

    /**
     * 绘制圆角矩形的画笔
     *
     * @param context
     */
    private Paint paint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 对勾（√）画笔
     */
    private Paint okPaint;
    /**
     * 文字绘制所在矩形
     */
    private Rect textRect = new Rect();
    /**
     * 绘制对勾（√）的动画
     */
    private ValueAnimator animator_draw_ok;
      /**
     * 矩形到正方形过度的动画
     */
    private ValueAnimator animator_rect_to_square;
    /**
     * 是否开始绘制对勾
     */
    private boolean startDrawOk = false;
    /**
     * 根据view的大小设置成矩形
     */
    private RectF rectf = new RectF();
    /**
     * 路径--用来获取对勾的路径
     */
    private Path path = new Path();
    /**
     * 取路径的长度
     */
    private PathMeasure pathMeasure;
    /**
     * 对路径处理实现绘制动画效果
     */
    private PathEffect effect;
    /**
     * 默认两圆圆心之间的距离=需要移动的距离
     */
    private int default_two_circle_distance;
    /**
     * 点击事件及动画事件2完成回调
     */
    private AnimationButtonListener animationButtonListener;
    /**
     * 动画集
     */
    private AnimatorSet animatorSet = new AnimatorSet();

    public void setAnimationButtonListener(AnimationButtonListener listener) {
        animationButtonListener = listener;
    }


    public right_view(Context context) {
        this(context, null);
    }

    public right_view(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public right_view(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationButtonListener != null) {
                    animationButtonListener.onClickListener();
                }
            }
        });
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationButtonListener != null) {
                    animationButtonListener.animationFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 第一步初始化画笔
     */
    private void initPaint() {

        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(bg_color);//背景颜色

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(25);
        textPaint.setColor(Color.RED);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);//文字画笔

        okPaint = new Paint();
        okPaint.setStrokeWidth(3);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.GREEN);//ok画笔
    }

    /**
     * 第二步重载方法onSizeChanged
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        default_two_circle_distance = (w - h) / 2;//默认两圆圆心之间的距离=需要移动的距离
        initOk();
        initAnimation();
    }
    /**
     * 第三部：绘制路径
     */
    void initOk(){
        //对勾的路径
        //对勾的路径
        path.moveTo(default_two_circle_distance + height / 8 * 3, height / 2);
        path.lineTo(default_two_circle_distance + height / 2, height / 5 * 3);
        path.lineTo(default_two_circle_distance + height / 3 * 2, height / 5 * 2);

        pathMeasure = new PathMeasure(path, true);
    }
    /**
     * 第四部重载ondraw方法
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        draw_circle(canvas);
         drawText(canvas);
        if (startDrawOk) {
            canvas.drawPath(path, okPaint);
        }
    }

    // TODO: 2019/2/27 明天开始画圆圈里面的勾勾
    void draw_circle(Canvas canvas){
        //画圆角矩形
        canvas.drawCircle(width/2, height/2, height/3, paint);
    }
    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        textRect.left = 0;
        textRect.top = 0;
        textRect.right = width;
        textRect.bottom = height;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(buttonString, textRect.centerX(), baseline, textPaint);
    }
    /**
     * 借口回调
     */
    public interface AnimationButtonListener {
        /**
         * 按钮点击事件
         */
        void onClickListener();

        /**
         * 动画完成回调
         */
        void animationFinish();
    }
    /**
     * 启动动画
     */
    public void start() {
        animatorSet.start();
    }
    /**
     * 动画还原
     */
    public void reset() {
        startDrawOk = false;
        default_two_circle_distance = (width - height) / 2;
        textPaint.setAlpha(255);
        invalidate();

    }
    /**
     * 初始化所有动画
     */
    private void initAnimation() {

        set_draw_ok_animation();
        set_rect_to_circle_animation();
        animatorSet.play(animator_draw_ok)
        .after(animator_rect_to_square);

    }
    /**
     * 绘制对勾的动画
     */
    private void set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(duration);
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk = true;
                float value = (Float) animation.getAnimatedValue();

                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();
            }
        });
    }
    /**
     * 两圆圆心之间的距离
     */
    private int two_circle_distance;
    /**
     * 设置圆角矩形过度到圆的动画
     */
    private void set_rect_to_circle_animation() {
        animator_rect_to_square = ValueAnimator.ofInt(0, default_two_circle_distance);
        animator_rect_to_square.setDuration(duration);
        animator_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                two_circle_distance = (int) animation.getAnimatedValue();

                int alpha = 255 - (two_circle_distance * 255) / default_two_circle_distance;

                textPaint.setAlpha(alpha);

                invalidate();
            }
        });
    }
}
