package com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.adapter.MyAdapter;
import com.ly.a316.ly_meetingroommanagement.scheduleHuiHome_two.object.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：余智强
 * 2019/2/28
 */
public class MyDialog extends DialogFragment {
    View view;
    @BindView(R.id.griview_result)
    GridView griviewResult;
    @BindView(R.id.griview_choose)
    GridView griviewChoose;
    Unbinder unbinder;
    private List<Item> list = new ArrayList<>();
    private List<Item> ch_list = new ArrayList<>();

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
        view = inflater.inflate(R.layout.dialogment_yangshi, container);
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
            params.width = dp2px(getContext(), 310);

            //设置dialog高度
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            /*} else {
                params.height = dp2px(getContext(), 310);
            }*/
            window.setBackgroundDrawableResource(R.drawable.dialog_back);
            //设置dialog动画
            //   window.setWindowAnimations(R.style.EnterExitAnimation);

            window.setAttributes(params);
        }
        /**
         * 设置点空白处是否无响应
         */
        setCancelable(false);

    }

    MyAdapter myAdapterr;

    private void initview() {
        myAdapterr = new MyAdapter(getContext(), ch_list);
        //入场动画
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.gride_in);
        griviewResult.setAnimation(animation);
        griviewResult.setAdapter(myAdapterr);
        Item item = new Item("时间点", R.drawable.chacha);
        list.add(item);
        item = new Item("时间断", R.drawable.chacha);
        list.add(item);
        item = new Item("会议室地点", R.drawable.chacha);
        list.add(item);
        item = new Item("会议室类型", R.drawable.chacha);
        list.add(item);
        item = new Item("参会人数", R.drawable.chacha);
        list.add(item);
        item = new Item("设备类型", R.drawable.chacha);
        list.add(item);
        MyAdapter myAdapter = new MyAdapter(getContext(), list);
        griviewChoose.setAdapter(myAdapter);


        myAdapter.setOnclick(new MyAdapter.Onclick() {
            @Override
            public void text_click(View view, String neirong, ImageView imageView) {
                moveTo(view, 180);
                move_back(imageView,180);
                Item item1 = new Item(neirong, R.drawable.chacha);
                ch_list.add(item1);
                myAdapterr.notifyDataSetChanged();
            }

            @Override
            public void img_click(View vieww,int position) {
               /// ch_list.remove(position);
                move_back(vieww,180);
            }
        });


    }

    void moveTo(View objView, float angle) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(objView, "translationX", 0f, (float) Math.cos(angle) * 150);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(objView, "translationY", 0f, (float) Math.sin(angle) * 150);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX, translationY);
        animatorSet.setDuration(800).start();
    }
    void move_back(View objView, float angle){
        ObjectAnimator translationX = ObjectAnimator.ofFloat(objView, "translationX", (float) Math.cos(angle) * 150,0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(objView, "translationY", (float) Math.sin(angle) * 150,0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationX, translationY);
        animatorSet.setDuration(800).start();
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);

    }

    void print(String neirong) {
        Log.i("zjc", neirong);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.three_sure, R.id.three_quxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.three_sure:
                this.dismiss();
                break;
            case R.id.three_quxiao:
                this.dismiss();
                break;
        }
    }
}








