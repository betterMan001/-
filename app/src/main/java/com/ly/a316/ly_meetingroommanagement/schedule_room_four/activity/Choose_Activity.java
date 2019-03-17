package com.ly.a316.ly_meetingroommanagement.schedule_room_four.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.schedule_room_four.customview.MyDragGrideLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Choose_Activity extends AppCompatActivity {

    @BindView(R.id.gridLayout1)
    MyDragGrideLayout gridLayout1;
    @BindView(R.id.gridLayout2)
    MyDragGrideLayout gridLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__three);
        ButterKnife.bind(this);

        initview();
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
        items2.add("会议类型");
        items2.add("会议地点");
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


    List<String> list_choose = new ArrayList<>();


    void shan(String neirong) {
        list_choose.clear();
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
                Intent intent = new Intent(this, Schedule_Activity_four.class);
                intent.putExtra("listdetail", (Serializable) list_choose);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }
                break;
        }
    }


    @OnClick(R.id.finish_img)
    public void onViewClicked() {
        finish();
    }
}
