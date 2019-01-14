package com.ly.a316.ly_meetingroommanagement.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.classes.TabEntity;
import com.ly.a316.ly_meetingroommanagement.customView.BottomBarLayout;
import com.ly.a316.ly_meetingroommanagement.main.fragment.CalendarFragment;
import com.ly.a316.ly_meetingroommanagement.main.fragment.ContactListFragment;
import com.ly.a316.ly_meetingroommanagement.main.fragment.ConversationListFragment;
import com.ly.a316.ly_meetingroommanagement.main.fragment.MineFragment;
import com.ly.a316.ly_meetingroommanagement.nim.DemoCache;
import com.ly.a316.ly_meetingroommanagement.nim.helper.SessionHelper;
import com.ly.a316.ly_meetingroommanagement.nim.helper.SystemMessageUnreadManager;
import com.ly.a316.ly_meetingroommanagement.nim.helper.TeamCreateHelper;
import com.ly.a316.ly_meetingroommanagement.nim.reminder.ReminderManager;
import com.ly.a316.ly_meetingroommanagement.nim.user_info.UserPreferences;
import com.ly.a316.ly_meetingroommanagement.utils.PopupMenuUtil;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.contact.selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.common.ToastHelper;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.support.permission.MPermission;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionDenied;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionGranted;
import com.netease.nim.uikit.support.permission.annotation.OnMPermissionNeverAskAgain;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  描述：主活动
 *  作者：余智强、徐文铎
 *  创建时间：2018 12/4 13：27
*/
public class MainActivity extends UI {
    @BindView(R.id.bottom_nav)
     BottomBarLayout bottomBarLayout;
    Fragment contactListFragment,conversationListFragment,fr_calendar, fr_mine;
    private FragmentManager fManager;
    //与状态栏同高的View
    private View statusBarView;
    private static final String TAG = "MainActivity";
    private List<TabEntity> tabEntityList;
    private String[] tabText = {"消息","日程","工作","通讯录","我的"};

    private int[] normalIcon ={R.drawable.message_press, R.drawable.huiyiricheng2,R.drawable.bg,R.drawable.contact_list_normal,R.drawable.me};
    private int[] selectIcon = {R.drawable.messagenormal,R.drawable.huiyicheng,R.drawable.bg,R.drawable.contact_list_press,R.drawable.me2};

    private int normalTextColor = Color.parseColor("#999999");
    private int selectTextColor = Color.parseColor("#fa6e51");
    private static final String EXTRA_APP_QUIT = "APP_QUIT";
    private static final int REQUEST_CODE_NORMAL = 1;
    private static final int REQUEST_CODE_ADVANCED = 2;
    private static final int BASIC_PERMISSION_REQUEST_CODE = 100;
    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };
    //请求危险权限
    private static final String[] BASIC_PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            //暂时不需要定位的权限
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION
    };
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
        //初始化云信相关东西
        initNim();
         /*
       初始化显示第一个页面
        */
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fr_calendar = new CalendarFragment();
        fTransaction.add(R.id.ac_main_frameLayout, fr_calendar);
        fTransaction.commit();
        ImmersionBar.with(MainActivity.this).reset().statusBarColor(R.color.classical_blue).fitsSystemWindows(true).init();
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
                        ImmersionBar.with(MainActivity.this).reset().statusBarColor(R.color.classical_blue).fitsSystemWindows(true).init();
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
                        ImmersionBar.with(MainActivity.this).reset().statusBarColor(R.color.classical_blue).fitsSystemWindows(true).init();
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
                        ImmersionBar.with(MainActivity.this).reset().statusBarColor(R.color.classical_blue).fitsSystemWindows(true).init();
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
                        ImmersionBar.with(MainActivity.this).reset().init();
                        break;
                }
            }
        });
    }

     public static final void start(Context context){
        Intent intent=new Intent();
        intent.setClass(context,MainActivity.class);
        context.startActivity(intent);
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
    private boolean parseIntent() {

        Intent intent = getIntent();

        if (intent.hasExtra(NimIntent.EXTRA_NOTIFY_CONTENT)) {
            IMMessage message = (IMMessage) intent.getSerializableExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            intent.removeExtra(NimIntent.EXTRA_NOTIFY_CONTENT);
            switch (message.getSessionType()) {
                case P2P:
                    SessionHelper.startP2PSession(this, message.getSessionId());
                    break;
                case Team:
                    SessionHelper.startTeamSession(this, message.getSessionId());
                    break;
            }

            return true;
        }

//        if (intent.hasExtra(AVChatActivity.INTENT_ACTION_AVCHAT) && AVChatProfile.getInstance().isAVChatting()) {
//            intent.removeExtra(AVChatActivity.INTENT_ACTION_AVCHAT);
//            Intent localIntent = new Intent();
//            localIntent.setClass(this, AVChatActivity.class);
//            startActivity(localIntent);
//            return true;
//        }
//
//        String account = intent.getStringExtra(AVChatExtras.EXTRA_ACCOUNT);
//        if (intent.hasExtra(AVChatExtras.EXTRA_FROM_NOTIFICATION) && !TextUtils.isEmpty(account)) {
//            intent.removeExtra(AVChatExtras.EXTRA_FROM_NOTIFICATION);
//            SessionHelper.startP2PSession(this, account);
//            return true;
//        }

        return false;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        parseIntent();
    }
    private void nimLogin(String phone) {
        final String account = phone;
        //云信的密码是账号的后六位
        int length=account.length();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=length-6;i<=length-1;i++)
            stringBuilder.append(account.charAt(i));

        String token = stringBuilder.toString();
        LoginInfo info = new LoginInfo(account, token); // config...
        RequestCallback<LoginInfo> callback =
                new RequestCallback<LoginInfo>() {
                    // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        Log.d(TAG, "onSuccess: 登录成功！");

                        DemoCache.setAccount(account);

                        //自己管理不用云信的
//                        saveLoginInfo(account, token);

                        // 初始化消息提醒配置
                        initNotificationConfig();
                    }

                    @Override
                    public void onFailed(int i) {
                        Log.d(TAG, "onFailed:登录失败了！");
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        Log.d(TAG, "onException: 登录异常！");
                    }
                };
        NimUIKit.login(info, callback);
    }
    private void initNim(){
        //登录运行账号，因为此app在登录过账号后就可以直接跳转此活动，所以云信的登录要设置在这里
        //1.获取phone
        final String phone= MyApplication.getId();
        nimLogin(phone);
        //注册/注销系统消息未读数变化
        registerSystemMessageObservers(true);
        //请求权限提示
        requestBasicPermission();
    }
    private void requestBasicPermission() {
        MPermission.printMPermissionResult(true, this, BASIC_PERMISSIONS);
        MPermission.with(MainActivity.this)
                .setRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(BASIC_PERMISSIONS)
                .request();
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
    private void initNotificationConfig() {
        // 初始化消息提醒（先默认开启）
        NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
        // 加载状态栏配置
        StatusBarNotificationConfig statusBarNotificationConfig = UserPreferences.getStatusConfig();
        if (statusBarNotificationConfig == null) {
            statusBarNotificationConfig = DemoCache.getNotificationConfig();
            UserPreferences.setStatusConfig(statusBarNotificationConfig);
        }
        // 更新配置
        NIMClient.updateStatusBarNotificationConfig(statusBarNotificationConfig);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_NORMAL) {
            final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
            if (selected != null && !selected.isEmpty()) {
                TeamCreateHelper.createNormalTeam(MainActivity.this, selected, false, null);
            } else {
                ToastHelper.showToast(MainActivity.this, "请选择至少一个联系人！");
            }
        } else if (requestCode == REQUEST_CODE_ADVANCED) {
            final ArrayList<String> selected = data.getStringArrayListExtra(ContactSelectActivity.RESULT_DATA);
            TeamCreateHelper.createAdvancedTeam(MainActivity.this, selected);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        try {
            ToastHelper.showToast(this, "授权成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        try {
            ToastHelper.showToast(this, "未全部授权，部分功能可能无法正常运行！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        MPermission.printMPermissionResult(false, this, BASIC_PERMISSIONS);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
        registerSystemMessageObservers(false);
    }
}
