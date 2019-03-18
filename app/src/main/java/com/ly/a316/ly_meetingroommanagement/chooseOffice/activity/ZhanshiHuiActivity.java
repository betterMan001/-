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
import android.widget.AdapterView;
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
import com.necer.calendar.WeekCalendar;
import com.necer.entity.NDate;
import com.necer.listener.OnWeekSelectListener;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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


    String s_year, s_month, s_day;
    String didian = "";
    int type = -1;
    int ren_xiao = -1, ren_da;
    String panduan(int timee) {
        String times;
        if (timee / 10 == 0) {
            times = "0" + timee;
        } else {
            times = String.valueOf(timee);
        }
        return times;
    }
    List<HuiyiInformation> list_hui_linghsi = new ArrayList<>();
    @BindView(R.id.zhan_spinner1)
    NiceSpinner spinner1;
    @BindView(R.id.zhan_spinner2)
    NiceSpinner spinner2;
    @BindView(R.id.zhan_spinner3)
    NiceSpinner spinner3;
    @BindView(R.id.zhan_all_weekCalendar)
    WeekCalendar allWeekCalendar;
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
        list_old.addAll(list_meet);
        init();
        initview();
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
               // list_old.add((HuiyiInformation) o);
            }

            @Override
            public void onSwipedClear() {
                Toast.makeText(ZhanshiHuiActivity.this, "这是最后一个会议室了", Toast.LENGTH_LONG).show();
                zhanshiRecycleview.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list_meet.clear();
                        list_meet.addAll(list_hui_linghsi);
                        zhanshiMyAdapter.notifyDataSetChanged();
                      //  list_old.clear();
                    }
                }, 1500);

            }
        });
        zhanshiMyAdapter.setOnitemClick(new Zhanshi_MyAdapter.OnitemClick() {
            @Override
            public void click(HuiyiInformation huiyiInformation, int position) {
                Intent intent = new Intent(ZhanshiHuiActivity.this, HuiyiXiang_Activity.class);
                intent.putExtra("classs", huiyiInformation);
                intent.putExtra("dates", s_year + "-" + s_month + "-" + s_day);//时间
                startActivity(intent);
            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(zhanshiRecycleview, touchHelper);
        zhanshiRecycleview.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(zhanshiRecycleview);

        allWeekCalendar.setOnWeekSelectListener(new OnWeekSelectListener() {
            @Override
            public void onWeekSelect(NDate date, boolean isClick) {
                s_year = panduan(date.localDate.getYear());
                s_month = panduan(date.localDate.getMonthOfYear());
                s_day = panduan(date.localDate.getDayOfMonth());
            }
        });
    }

    void initview() {
        List<String> dataset = new LinkedList<>(Arrays.asList("全部人数", "0-20", "20-50", "50以上"));
        spinner1.attachDataSource(dataset);  //设置下拉列表框的下拉选项样式
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    ren_xiao = 0;
                    ren_da = 20;
                } else if (position == 2) {
                    ren_xiao = 20;
                    ren_da = 50;
                } else if (position == 3) {
                    ren_xiao = 50;
                    ren_da = 10000;
                } else {
                    ren_xiao = -1;
                }
                shaixuan();
                list_meet.clear();
                list_meet.addAll(list_hui_linghsi);
                zhanshiMyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> dataset_didian = new LinkedList<>(Arrays.asList("全部地点", "2A", "3C", "A栋", "B栋", "C栋", "早10", "行健楼"));
        spinner2.attachDataSource(dataset_didian);  //设置下拉列表框的下拉选项样式
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    didian = "";
                } else {
                    didian = dataset_didian.get(position);
                }

                shaixuan();
                list_meet.clear();
                list_meet.addAll(list_hui_linghsi);
                zhanshiMyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> dataset_type = new LinkedList<>(Arrays.asList("全部", "接见式", "董事会式", "U型", "回型", "剧院", "课桌", "围桌", "T型", "多功能厅", "大礼堂式", "国会式"));
        spinner3.attachDataSource(dataset_type);  //设置下拉列表框的下拉选项样式
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    type = -1;
                } else {
                    type = position;
                }
                shaixuan();
                list_meet.clear();
                list_meet.addAll(list_hui_linghsi);
                zhanshiMyAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    void shaixuan() {
        list_hui_linghsi.clear();
        for (int i = 0; i < list_old.size(); i++) {
            if (didian.equals("")) {
                if (type == -1) {
                    if (ren_xiao == -1) {
                        //默认显示全部会议室.
                        list_hui_linghsi.add(list_old.get(i));
                    } else {
                        //只符合人数条件
                        if (Integer.valueOf(list_old.get(i).getmNumber()) >= ren_xiao && Integer.valueOf(list_old.get(i).getmNumber()) <= ren_da) {
                            list_hui_linghsi.add(list_old.get(i));
                        }
                    }
                } else {
                    //符合类型条件
                    if (ren_xiao == -1) {
                        //符合类型 不符合人数
                        if (list_old.get(i).getmType().equals(String.valueOf(type))) {
                            list_hui_linghsi.add(list_old.get(i));
                        }
                    } else {
                        //符合类型 符合人数
                        if (Integer.valueOf(list_old.get(i).getmNumber()) >= ren_xiao && Integer.valueOf(list_old.get(i).getmNumber()) <= ren_da) {
                            if (list_old.get(i).getmType().equals(String.valueOf(type))) {
                                list_hui_linghsi.add(list_old.get(i));
                            }
                        }

                    }
                }
            } else {
                if (type == -1) {
                    if (ren_xiao == -1) {
                        //只符合地点
                        if (list_old.get(i).getmAddress().equals(didian)) {
                            list_hui_linghsi.add(list_old.get(i));
                        }
                    } else {
                        //符合地点 + 人数
                        if (list_old.get(i).getmAddress().equals(didian)) {
                            if (Integer.valueOf(list_old.get(i).getmNumber()) >= ren_xiao && Integer.valueOf(list_old.get(i).getmNumber()) <= ren_da) {
                                list_hui_linghsi.add(list_old.get(i));
                            }
                        }
                    }
                } else {
                    if (ren_xiao == -1) {
                        //符合地点 + 类型
                        if (list_old.get(i).getmAddress().equals(didian)) {
                            if (list_old.get(i).getmType().equals(String.valueOf(type))) {
                                list_hui_linghsi.add(list_old.get(i));
                            }
                        }
                    } else {
                        //符合地点 + 人数+类型
                        if (list_old.get(i).getmAddress().equals(didian)) {
                            if (Integer.valueOf(list_old.get(i).getmNumber()) >= ren_xiao && Integer.valueOf(list_old.get(i).getmNumber()) <= ren_da) {
                                if (list_old.get(i).getmType().equals(String.valueOf(type))) {
                                    list_hui_linghsi.add(list_old.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
