package com.ly.a316.ly_meetingroommanagement.activites.chooseOffice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.activites.MainActivity;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;
import com.netease.nim.uikit.common.util.C;

import java.sql.ParameterMetaData;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteconditionActivity extends AppCompatActivity {
    RecyclerView writeChooseRecycleview;
    Button btn_que;
    List<String> list = new ArrayList<>();
    AlertDialog parkIdsdialog;
    CardAdapter cardAdapter;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecondition);
        list.add("参会人数");
        list.add("设备需求");
        list.add("时间");
        list.add("地点");
        init();
    }

    void init() {
        View bottomView = View.inflate(WriteconditionActivity.this, R.layout.activity_writecondition, null);//填充ListView布局
        writeChooseRecycleview = (RecyclerView) bottomView.findViewById(R.id.write_choose_recycleview);
        btn_que = bottomView.findViewById(R.id.queding);
        final int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
        writeChooseRecycleview.setLayoutManager(new LinearLayoutManager(this));
        writeChooseRecycleview.setNestedScrollingEnabled(false);//禁止滑动
        writeChooseRecycleview.setFocusable(false);
        cardAdapter = new CardAdapter(list, this);
        writeChooseRecycleview.setAdapter(cardAdapter);
        writeChooseRecycleview.addItemDecoration(new ItemOffsetDecoration(spacing));
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom);
        writeChooseRecycleview.setLayoutAnimation(controller);
        writeChooseRecycleview.scheduleLayoutAnimation();
        parkIdsdialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setView(bottomView)//在这里把写好的这个listview的布局加载dialog中
                .create();
        parkIdsdialog.show();
        parkIdsdialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        btn_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parkIdsdialog.dismiss();
            }
        });

        cardAdapter.setOnclickItem(new CardAdapter.OnclickItem() {
            @SuppressLint("WrongConstant")
            @Override
            public void OnItem_shebei_Click(int position, RecyclerView.ViewHolder viewHolder) {
                if (((CardAdapter.EquipmentHolder) viewHolder).item_choose_she.getVisibility() == 8) {
                    ((CardAdapter.EquipmentHolder) viewHolder).item_choose_she.setVisibility(View.VISIBLE);
                } else {
                    ((CardAdapter.EquipmentHolder) viewHolder).item_choose_she.setVisibility(View.GONE);
                }
            }

            @Override
            public void OnItem_ren_Click(RecyclerView.ViewHolder viewHolder) {


            }

            @Override
            public void OnItem_time_Click(RecyclerView.ViewHolder viewHolder, int whoclick) {
                if (whoclick == 1){//代表点了时刻
                    TimePicker timePicker = new TimePicker(WriteconditionActivity.this);
                    timePicker.setOnChangeListener(new TimePicker.OnChangeListener() {
                        @Override
                        public void onChange(int hour, int munite) {

                        }
                    });
                    ((CardAdapter.TimeViewHolder)viewHolder).item_choose_shikeview.addView(timePicker);

                }else if (whoclick == 2){//带表点击了时间段

                }
            }
        });
    }
    public void showTost(String bodystring){
        if(toast == null){
            toast = Toast.makeText(WriteconditionActivity.this,bodystring,Toast.LENGTH_SHORT);
        }else{
            toast.setText(bodystring);
        }
        toast.show();
    }
}
