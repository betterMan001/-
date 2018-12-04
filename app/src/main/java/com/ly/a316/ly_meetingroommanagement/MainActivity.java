package com.ly.a316.ly_meetingroommanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;

import com.ly.a316.ly_meetingroommanagement.Class.TabEntity;
import com.ly.a316.ly_meetingroommanagement.Customview.BottomBarLayout;
import com.ly.a316.ly_meetingroommanagement.Fragment.Fr_calendar;
import com.ly.a316.ly_meetingroommanagement.Fragment.Fr_mine;
import com.ly.a316.ly_meetingroommanagement.Utils.PopupMenuUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 描述：主活动
 * 作者：余智强
 * 创建时间：2018 12/4 13：27
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_nav)
    BottomBarLayout bottomBarLayout;
    Fragment fr_calendar, fr_mine;
    private FragmentManager fManager;

    private List<TabEntity> tabEntityList;
    private String[] tabText = {"日程", "工作", "我的"};

    private int[] normalIcon = {R.drawable.calendar, R.drawable.bg, R.drawable.me};
    private int[] selectIcon = {R.drawable.calender2, R.drawable.bg, R.drawable.me2};

    private int normalTextColor = Color.parseColor("#999999");
    private int selectTextColor = Color.parseColor("#fa6e51");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fManager = getSupportFragmentManager();
        tabEntityList = new ArrayList<>();
        initview();

        bottomBarLayout.setNormalTextColor(normalTextColor);
        bottomBarLayout.setSelectTextColor(selectTextColor);
        bottomBarLayout.setTabList(tabEntityList);


        bottomBarLayout.setOnItemClickListener(new BottomBarLayout.OnItemClickListener() {
            @Override
            public void onItemCLick(int position, View v) {
                FragmentTransaction fTransaction = fManager.beginTransaction();
                hideAllfragment(fTransaction);
                switch (position) {
                    case 0:
                        /**
                         *   动态改角标
                         *   TextView number = (TextView) v.findViewById(R.id.tv_count);
                         number.setVisibility(View.GONE);
                         number.setText("12");
                         */
                        if (fr_calendar == null) {
                            fr_calendar = new Fr_calendar();
                            fTransaction.add(R.id.ac_main_frameLayout, fr_calendar);
                        } else {
                            fTransaction.show(fr_calendar);
                        }
                        fTransaction.commit();
                        break;
                    case 1:
                        PopupMenuUtil.getInstance()._show(MainActivity.this, v);
                        break;

                    case 2:
                        if (fr_mine == null) {
                            fr_mine = new Fr_mine();
                            fTransaction.add(R.id.ac_main_frameLayout, fr_mine);
                        } else {
                            fTransaction.show(fr_mine);
                        }
                        fTransaction.commit();
                        break;
                }
            }
        });
    }

    void initview() {
        for (int i = 0; i < tabText.length; i++) {
            TabEntity item = new TabEntity();
            item.setText(tabText[i]);
            item.setNormalIconId(normalIcon[i]);
            item.setSelectIconId(selectIcon[i]);
            item.setShowPoint(false);
            item.setNewsCount(0);
            tabEntityList.add(item);
        }
    }


    //隐藏所有fragemnt
    private void hideAllfragment(FragmentTransaction fragmentTransaction) {
        if (fr_calendar != null) {
            fragmentTransaction.hide(fr_calendar);
        }

        if (fr_mine != null) {
            fragmentTransaction.hide(fr_mine);
        }
    }

}
