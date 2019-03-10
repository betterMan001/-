package com.ly.a316.ly_meetingroommanagement.chooseOffice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.clownqiang.rectcircleprogressbutton.RectCircleProgressButton;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.Adapter.Zhanshi_MyAdapter;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.daoImp.DeviceDaoImp;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.utils.CardItemTouchHelperCallback;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.utils.CardLayoutManager;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.dao.OnSwipeListener;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.object.HuiyiInformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZhanshiHuiActivity extends AppCompatActivity {

    @BindView(R.id.zhanshi_recycleview)
    RecyclerView zhanshiRecycleview;
    @BindView(R.id.hy_infor_toolbar)
    Toolbar addShike_toolbar;
    List<HuiyiInformation> list_meet = new ArrayList<>();
    List<HuiyiInformation> list_old = new ArrayList<>();
    Zhanshi_MyAdapter zhanshiMyAdapter;
    @BindView(R.id.btn_rect_circle)
    RectCircleProgressButton rectCircleProgressButton;


    DeviceDaoImp deviceDaoImp  = new DeviceDaoImp(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhanshi_hui);
        ButterKnife.bind(this);
        addShike_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list_meet = (List<HuiyiInformation>) getIntent().getSerializableExtra("list_meet");
        init();
        rectCircleProgressButton.setAnimationRectButtonListener(new RectCircleProgressButton.AnimationStatusListener() {
            @Override
            public void startLoading(int status) {
                deviceDaoImp.subbmitHuiyi(2);
                Toast.makeText(ZhanshiHuiActivity.this, "更新数据中", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void resetView(int status) {
                Toast.makeText(ZhanshiHuiActivity.this, "更新完毕", Toast.LENGTH_SHORT).show();
            }
        });

    }

   public void success_back(List<HuiyiInformation> list_meettr ){
       list_meet.clear();
       list_meet.addAll(list_meettr);
       zhanshiMyAdapter.notifyDataSetChanged();
       rectCircleProgressButton.resetButtonView();
    }
    void init() {
        zhanshiRecycleview.setItemAnimator(new DefaultItemAnimator());
        zhanshiMyAdapter = new Zhanshi_MyAdapter(this, list_meet);
        zhanshiRecycleview.setAdapter(zhanshiMyAdapter);
        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(zhanshiRecycleview.getAdapter(), list_meet);
        cardCallback.setOnSwipedListener(new OnSwipeListener() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, Object o, int direction) {
                list_old.add((HuiyiInformation) o);
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(ZhanshiHuiActivity.this, "这是最后一个会议室了", Toast.LENGTH_LONG).show();
                zhanshiRecycleview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list_meet.clear();
                        list_meet.addAll(list_old);
                        zhanshiMyAdapter.notifyDataSetChanged();
                        list_old.clear();
                    }
                }, 1500);

            }
        });
        zhanshiMyAdapter.setOnitemClick(new Zhanshi_MyAdapter.OnitemClick() {
            @Override
            public void click(HuiyiInformation huiyiInformation, int position) {
                Intent intent = new Intent(ZhanshiHuiActivity.this, HuiyiXiang_Activity.class);
                intent.putExtra("classs", huiyiInformation);
                startActivity(intent);
            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(zhanshiRecycleview, touchHelper);
        zhanshiRecycleview.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(zhanshiRecycleview);
    }
}
