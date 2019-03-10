package com.ly.a316.ly_meetingroommanagement.schedule_room_four.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.schedule_room_four.customview.MyDragGrideLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：余智强
 * 2019/3/2
 */
public class MyDialogFragment_four extends DialogFragment {
    View view;
    @BindView(R.id.gridLayout1)
    MyDragGrideLayout gridLayout1;
    @BindView(R.id.gridLayout2)
    MyDragGrideLayout gridLayout2;
    Unbinder unbinder;


    /**
     * 第一步
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        print("onAttach被执行");
    }

    /**
     * 第二部
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        print("onCreate执行");


    }

    /**
     * 第三部
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        print("onCreateview被执行");
        view = inflater.inflate(R.layout.activity_schedule__three, container, false);

        unbinder = ButterKnife.bind(this, view);

        initParams();
        initview();
        return view;
    }

    //onCreateView执行完立马就是onViewCreate 比如执行动画初始化
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    /**
     * 第四部onStart
     */
    @Override
    public void onStart() {
        super.onStart();
    }

    private void initParams() {
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = (float) 1.00;//设置昏暗度

            //设置dialog显示位置
            params.gravity = Gravity.CENTER;

            //设置dialog宽度
            params.width = WindowManager.LayoutParams.MATCH_PARENT;

            //设置dialog高度
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            /*} else {
                params.height = dp2px(getContext(), 310);
            }*/
            window.setBackgroundDrawableResource(R.drawable.dialog_back);
            //设置dialog动画
         //   window.setWindowAnimations(R.style.PopupAnimation);

            window.setAttributes(params);
        }
        /**
         * 设置点空白处是否无响应
         */
        setCancelable(false);

    }


    private void initview() {
        gridLayout1.setCanDrag(true);//设置可以拖动
        gridLayout1.setShow_cha(true);
        List<String> items = new ArrayList<>();

        gridLayout1.setItems(items);
        List<String> items2 = new ArrayList<>();
        items2.add("时间点");
        items2.add("时间段");
        items2.add("参会人数");
        items2.add("会议时长");
        items2.add("设备需求");
        items2.add("会议室类型");
        items2.add("会议室地点");
        gridLayout2.setItems(items2);
        gridLayout2.setShow_cha(false);
        gridLayout1.setOnDragItemClickListener(new MyDragGrideLayout.OnDragItemClickListener() {
            @Override
            public void onDragItemClick(RelativeLayout tv, TextView vi) {
                gridLayout1.removeView(tv);
                shan(vi.getText().toString().trim());
                gridLayout2.addGridItem(vi.getText().toString());
            }
        });
        gridLayout2.setOnDragItemClickListener(new MyDragGrideLayout.OnDragItemClickListener() {
            @Override
            public void onDragItemClick(RelativeLayout tv, TextView vi) {
                gridLayout2.removeView(tv);
                list_choose.add(vi.getText().toString().trim());
                gridLayout1.addGridItem(vi.getText().toString());
            }
        });

    }


    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);

    }

    void print(String neirong) {
        Log.i("zjc", neirong);
    }

    List<String> list_choose = new ArrayList<>();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    void shan(String neirong) {

        for (int i = 0; i < list_choose.size(); i++) {
            if (list_choose.get(i).toString().equals(neirong)) {

                list_choose.remove(i);

            }
        }


    }

    @OnClick({R.id.sure_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sure_four:
                diaFragment_interface.success(list_choose);
                dismiss();
                break;

        }
    }


    public interface DiaFragment_interface{
        void success(List<String> list);
    }
    DiaFragment_interface diaFragment_interface;

    public void setDiaFragment_interface(DiaFragment_interface diaFragment_interface) {
        this.diaFragment_interface = diaFragment_interface;
    }

}