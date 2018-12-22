package com.ly.a316.ly_meetingroommanagement.activites;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.classes.TabEntity;
import com.ly.a316.ly_meetingroommanagement.customView.BottomBarLayout;
import com.ly.a316.ly_meetingroommanagement.fragments.CalendarFragment;
import com.ly.a316.ly_meetingroommanagement.fragments.ContactListFragment;
import com.ly.a316.ly_meetingroommanagement.fragments.ConversationListFragment;
import com.ly.a316.ly_meetingroommanagement.fragments.MineFragment;
import com.ly.a316.ly_meetingroommanagement.nim.helper.SystemMessageUnreadManager;
import com.ly.a316.ly_meetingroommanagement.nim.reminder.ReminderManager;
import com.ly.a316.ly_meetingroommanagement.utils.PopupMenuUtil;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  描述：主活动
 *  作者：余智强
 *  创建时间：2018 12/4 13：27
*/
public class MainActivity extends UI {
    @BindView(R.id.bottom_nav)
     BottomBarLayout bottomBarLayout;
    Fragment contactListFragment,conversationListFragment,fr_calendar, fr_mine;
    private FragmentManager fManager;

    private List<TabEntity> tabEntityList;
    private String[] tabText = {"消息","日程","工作","通讯录","我的"};

    private int[] normalIcon ={R.drawable.message_press, R.drawable.huiyiricheng2,R.drawable.bg,R.drawable.contact_list_normal,R.drawable.me};
    private int[] selectIcon = {R.drawable.messagenormal,R.drawable.huiyicheng,R.drawable.bg,R.drawable.contact_list_press,R.drawable.me2};

    private int normalTextColor = Color.parseColor("#999999");
    private int selectTextColor = Color.parseColor("#fa6e51");
    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fManager = getSupportFragmentManager();
        tabEntityList = new ArrayList<>();
        initview();
        ImmersionBar.with(this).init();
        bottomBarLayout.setNormalTextColor(normalTextColor);
        bottomBarLayout.setSelectTextColor(selectTextColor);
        bottomBarLayout.setTabList(tabEntityList);
        //初始化云信相关东西
       initNim();
         /*
       初始化显示第一个页面
        */
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fr_calendar = new CalendarFragment();
        fTransaction.add(R.id.ac_main_frameLayout, fr_calendar);
        fTransaction.commit();



        bottomBarLayout.setOnItemClickListener(new BottomBarLayout.OnItemClickListener(){
            @Override
            public void onItemCLick(int position,View v) {
                FragmentTransaction fTransaction = fManager.beginTransaction();
                hideAllfragment(fTransaction);
                switch (position) {
                    //1.会话界面
                    case 0:
                        if (conversationListFragment == null) {
                            conversationListFragment = new ConversationListFragment();
                            fTransaction.add(R.id.ac_main_frameLayout,conversationListFragment);
                        } else {
                            fTransaction.show( conversationListFragment);
                        }
                        fTransaction.commit();
                        break;
                    //2.日历界面
                    case 1:
                        /**
                         *   动态改角标
                         *   TextView number = (TextView) v.findViewById(R.id.tv_count);
                             number.setVisibility(View.GONE);
                             number.setText("12");
                         */
                        if (fr_calendar == null) {
                            fr_calendar = new CalendarFragment();
                            fTransaction.add(R.id.ac_main_frameLayout, fr_calendar);
                        } else {
                            fTransaction.show(fr_calendar);
                        }
                        fTransaction.commit();
                        break;
                    //3.工作界面
                    case 2:
                        PopupMenuUtil.getInstance()._show(MainActivity.this, v);
                        break;
                    //4.通讯录界面
                    case 3:
                        if (contactListFragment == null) {
                            contactListFragment = new ContactListFragment();
                            fTransaction.add(R.id.ac_main_frameLayout,contactListFragment);
                        } else {
                            fTransaction.show(contactListFragment);
                        }
                        fTransaction.commit();
                        break;
                    //5.我的界面
                    case 4:
                        if (fr_mine == null) {
                            fr_mine = new MineFragment();
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
    void initview(){
        for (int i=0;i<tabText.length;i++){
            TabEntity item = new TabEntity();
            item.setText(tabText[i]);
            item.setNormalIconId(normalIcon[i]);
            item.setSelectIconId(selectIcon[i]);
            item.setShowPoint(false);
            item.setNewsCount(0);
            tabEntityList.add(item);
        }
    }

private void initNim(){
        //注册/注销系统消息未读数变化
    registerSystemMessageObservers(true);
}
    /**
     * 注册/注销系统消息未读数变化
     */
    private void registerSystemMessageObservers(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver, register);
    }
    //隐藏所有fragemnt
    private void hideAllfragment(FragmentTransaction fragmentTransaction) {
        if (conversationListFragment != null) {
            fragmentTransaction.hide(conversationListFragment);
        }

        if (contactListFragment != null) {
            fragmentTransaction.hide(contactListFragment);
        }

        if (fr_mine != null) {
            fragmentTransaction.hide(fr_mine);
        }
        if (fr_calendar != null) {
            fragmentTransaction.hide(fr_calendar);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        registerSystemMessageObservers(false);
    }
}
