package com.ly.a316.ly_meetingroommanagement.schedule_room_three.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.schedule_room_three.customview.ChannelView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：余智强
 * 2019/3/1
 */
public class MyDialogment extends DialogFragment implements ChannelView.OnChannelListener {
    View view;
    Context context;
    @BindView(R.id.channelView)
    ChannelView channelView;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.schedule_room, container);
        unbinder = ButterKnife.bind(this, view);
        initParams();
        init();
        return view;

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

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);

    }

    void init() {

        String[] myChannel = {};
        String[] recommendChannel0 = {"时间点", "时间段", "会议室类型", "设备类型", "参会人数", "会议时长"};

        Map<String, String[]> channels = new LinkedHashMap<>();
        channels.put("已选条件", myChannel);
        channels.put("可选条件", recommendChannel0);

        channelView.setChannels(channels);

        channelView.setChannelNormalBackground(R.drawable.bg_channel_normal);
        channelView.setChannelSelectedBackground(R.drawable.bg_channel_selected);
        channelView.setChannelFocusedBackground(R.drawable.bg_channel_focused);
        channelView.setOnChannelItemClickListener(this);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void channelItemClick(int itemId, String channel) {

    }

    @Override
    public void channelFinish(List<String> channels) {

    }

    @OnClick({R.id.two_sure, R.id.two_quxiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.two_sure:

                dismiss();
                break;
            case R.id.two_quxiao:
                dismiss();
                break;
        }
    }

}
