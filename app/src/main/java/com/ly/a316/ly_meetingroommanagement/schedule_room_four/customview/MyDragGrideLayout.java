package com.ly.a316.ly_meetingroommanagement.schedule_room_four.customview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.widget.GridLayout.LayoutParams.*;

/**
 * 作者：余智强
 * 2019/3/1
 */
public class MyDragGrideLayout extends GridLayout {

    private List<String> items;
    private int margin = 6;
    //是否可以拖拽
    private boolean isCanDrag;
    //是否显示叉叉
    private boolean show_cha;//默认为false 不显示
    //记录被拖拽的view
    private View dragView;
    //存放所有的
    private List<Rect> rects;

    public void setItems(List<String> items) {
        this.items = items;
        for (int i = 0; i < items.size(); i++) {
            addGridItem(items.get(i));
        }
    }

    public void setCanDrag(boolean canDrag) {
        isCanDrag = canDrag;
        if (isCanDrag) {
            setOnDragListener(onDragListener);
        }
    }

    public void setShow_cha(boolean show_cha) {
        this.show_cha = show_cha;
    }

    public MyDragGrideLayout(Context context) {
        super(context, null);
    }

    public MyDragGrideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDragGrideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addGridItem(String content) {
       /* TextView tv = new TextView(getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        tv.setBackgroundResource(R.drawable.dialog_label);
        tv.setGravity(Gravity.CENTER);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 4 - margin * 2;
        params.setMargins(margin, margin, margin, margin);
        tv.setLayoutParams(params);

        tv.setText(content);*/
        ///
        // TODO: 2019/3/1 总结代码创建视图和学习gridlayout
        TextView txt1 = new TextView(getContext());
        txt1.setText(content);
        txt1.setId(R.id.id_add_file);
        txt1.setGravity(Gravity.CENTER);
        txt1.setBackgroundResource(R.drawable.dialog_label);
        txt1.setPadding(10,10,10,10);
        RelativeLayout.LayoutParams params_re = new RelativeLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT);
        params_re.width = 180;
        params_re.height =  90;;
        params_re.setMargins(margin,margin,margin,margin);

        txt1.setLayoutParams(params_re);


        RelativeLayout.LayoutParams params_re_tw = new RelativeLayout.LayoutParams(WRAP_CONTENT,
                WRAP_CONTENT);
        params_re_tw.setMargins(0,5,0,0);
        params_re_tw.width =20;params_re_tw.height = (int)dipToPx(getContext(),15);
        params_re_tw.addRule(RelativeLayout.RIGHT_OF, R.id.id_add_file);




        RelativeLayout layout1 = new RelativeLayout(getContext());
        layout1.setLayoutParams(new  RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        layout1.addView(txt1);



        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels / 4 - margin * 2;
        params.setMargins(margin, margin, margin, margin);

        layout1.setLayoutParams(params);
        addView(layout1);

        ///



        if (isCanDrag) {
            layout1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    dragView = view;
                    // 产生浮动的阴影效果
                    // 只有第二个参数有用，其他传空即可
                    view.startDrag(null, new View.DragShadowBuilder(view), null, 0);
                    // true 响应长按事件
                    return true;
                }
            });
        }
        // 设置条目的点击事件
        layout1.setOnClickListener(onClickListener);

    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onDragItemClickListener != null) {
                onDragItemClickListener.onDragItemClick((RelativeLayout) view,(TextView)findTextView2((RelativeLayout)view));
            }
        }
    };

    //接口
    public interface OnDragItemClickListener {
        void onDragItemClick(RelativeLayout tv,TextView vi);
    }

    private OnDragItemClickListener onDragItemClickListener;

    public void setOnDragItemClickListener(OnDragItemClickListener onDragItemClickListener) {
        this.onDragItemClickListener = onDragItemClickListener;
    }

    private OnDragListener onDragListener = new OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                //按下
                case DragEvent.ACTION_DRAG_STARTED:
                    dragView.setEnabled(true);
                    initRects();
                    break;
                // 移动
                case DragEvent.ACTION_DRAG_LOCATION:
                    int exchangeIndex = getExchangeIndex(event);
                    if (exchangeIndex > -1 && dragView != getChildAt(exchangeIndex)) {
                        removeView(dragView);
                        addView(dragView, exchangeIndex);
                    }

                    break;
                // 弹起
                case DragEvent.ACTION_DRAG_ENDED:
                    dragView.setEnabled(true);
                    break;

            }
            //// true 响应拖拽事件
            return true;
        }
    };

    private void initRects() {
        rects = new ArrayList<>();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            // 创建矩形
            Rect rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            rects.add(rect);
        }
    }

    private int getExchangeIndex(DragEvent dragEvent) {
        for (int i = 0; i < rects.size(); i++) {
            Rect rect = rects.get(i);
            if (rect.contains((int) dragEvent.getX(), (int) dragEvent.getY())) {
                return i;
            }
        }
        return -1;
    }
    //dp转px
    private float dipToPx(Context context, float dip) {
        return dip * getDeviceDensity(context) + 0.5f;
    }
    //获取屏幕密度
    private float getDeviceDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }



    private TextView findTextView2(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof TextView) {
                    return (TextView) child;
                } else if (child instanceof ViewGroup) {
                    TextView result = findTextView2((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }


}
