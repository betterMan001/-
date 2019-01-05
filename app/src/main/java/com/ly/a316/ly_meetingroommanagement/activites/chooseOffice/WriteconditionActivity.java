package com.ly.a316.ly_meetingroommanagement.activites.chooseOffice;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.Adapter.Calendar_Adapter;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.customView.DatePicker;
import com.ly.a316.ly_meetingroommanagement.customView.TimePicker;
import com.netease.nim.uikit.business.robot.parser.elements.group.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WriteconditionActivity extends AppCompatActivity {
    RecyclerView writeChooseRecycleview;
    Button btn_que;
    List<String> list = new ArrayList<>();
    List<String> equiment_list = new ArrayList<>();//存放设备名称
    List<String> place_list = new ArrayList<>();//存放地点
    AlertDialog parkIdsdialog;
    CardAdapter cardAdapter;
    Toast toast;
    public  static Map<Integer,String> map = new HashMap<>();//设备
    public  static  Map<Integer,String> mapwhere = new HashMap<>();//地点
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writecondition);
        list.add("参会人数");
        list.add("设备需求");
        list.add("时间");
        list.add("地点");

        equiment_list.add("空调");
        equiment_list.add("投影仪");
        equiment_list.add("音响");
        equiment_list.add("饮水机");
        equiment_list.add("电脑");
        equiment_list.add("桌子");
        equiment_list.add("凳子");

        place_list.add("3A");
        place_list.add("2B");
        place_list.add("1C");
        place_list.add("1B");
        place_list.add("1A");
        place_list.add("2A");
        place_list.add("2C");
        init();
}


    void init() {
        View bottomView = View.inflate(WriteconditionActivity.this, R.layout.activity_writecondition, null);//填充ListView布局
        writeChooseRecycleview = (RecyclerView) bottomView.findViewById(R.id.write_choose_recycleview);
        btn_que = bottomView.findViewById(R.id.queding);
        final int spacing = getResources().getDimensionPixelOffset(R.dimen.default_spacing_small);
        writeChooseRecycleview.setLayoutManager(new MyManager(this));
        cardAdapter = new CardAdapter(list, this,equiment_list,place_list);
        writeChooseRecycleview.setAdapter(cardAdapter);
        writeChooseRecycleview.addItemDecoration(new ItemOffsetDecoration(spacing));
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_bottom);
        writeChooseRecycleview.setLayoutAnimation(controller);
        writeChooseRecycleview.scheduleLayoutAnimation();
        parkIdsdialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setView(bottomView)//在这里把写好的这个listview的布局加载dialog中.setCancelable(false);
                .setCancelable(false)
                .create();
        parkIdsdialog.show();
        parkIdsdialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        btn_que.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交
                RecyclerView.LayoutManager manager = writeChooseRecycleview.getLayoutManager();
                View vieww = manager.findViewByPosition(0);
                CardAdapter.PeopleViewHolder holder = (CardAdapter.PeopleViewHolder) writeChooseRecycleview.getChildViewHolder(vieww);
                showTost( holder.one.getText().toString());


               for(int i=0;i<equiment_list.size();i++){
                   if(map.get(i)!=null){
                       Log.i("zjc",map.get(i));
                   }
               }
               Log.i("zjc",map.size()+"");
                for(int i=0;i<place_list.size();i++){
                    if(mapwhere.get(i)!=null){
                        Log.i("zjc",mapwhere.get(i));
                    }
                }
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

            @SuppressLint("WrongConstant")
            @Override
            public void OnItem_time_Click(RecyclerView.ViewHolder viewHolder, int whoclick) {
                if (whoclick == 1) {//代表点了时刻
                    if (((CardAdapter.TimeViewHolder) viewHolder).item_choose_shikeview.getVisibility() == 8) {
                        ((CardAdapter.TimeViewHolder) viewHolder).item_choose_shikeview.setVisibility(View.VISIBLE);
                    } else {
                        ((CardAdapter.TimeViewHolder) viewHolder).item_choose_shijainduan.setVisibility(View.GONE);

                    }
                } else if (whoclick == 2) {//带表点击了时间段
                    if (((CardAdapter.TimeViewHolder) viewHolder).item_choose_shikeview.getVisibility() == 8) {
                        ((CardAdapter.TimeViewHolder) viewHolder).item_choose_shikeview.setVisibility(View.VISIBLE);
                    }
                    if (((CardAdapter.TimeViewHolder) viewHolder).item_choose_shijainduan.getVisibility() == 8) {
                        ((CardAdapter.TimeViewHolder) viewHolder).item_choose_shijainduan.setVisibility(View.VISIBLE);
                    }

                }
            }
        });
    }

    public void showTost(String bodystring) {
        if (toast == null) {
            toast = Toast.makeText(WriteconditionActivity.this, bodystring, Toast.LENGTH_SHORT);
        } else {
            toast.setText(bodystring);
        }
        toast.show();
    }
}
