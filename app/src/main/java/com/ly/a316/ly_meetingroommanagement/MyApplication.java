package com.ly.a316.ly_meetingroommanagement;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;

import com.ly.a316.ly_meetingroommanagement.FacePack.FaceDB;
import com.ly.a316.ly_meetingroommanagement.activites.MainActivity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

import java.util.ArrayList;
import java.util.List;

/*
Date:2018/12/4
Time:17:40
auther:xwd
*/
public class MyApplication extends Application {

    //上下文环境
    private static Context context;
    //登录相关信息
    private static String id = "";
    private static String token = "";
    //本地保存数据
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static List<Activity> activityList = new ArrayList<>();

    //初始化加载人脸识别引擎
    private final String TAG = this.getClass().toString();
    public FaceDB mFaceDB;
    Uri mImage;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        context = getApplicationContext();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
//        //初始化面部识别
//        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
//        mImage = null;
        //初始化云信
        NIMClient.init(this, loginInfo(), options());
        if (NIMUtil.isMainProcess(this)) {
            // 在主进程中初始化UI组件，判断所属进程方法请参见demo源码。
            initUiKit();
        }
    }

    //添加活动
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //关闭所有活动
    public static void finishAllActivities() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }

    public static Context getContext() {
        return context;
    }

    public static String getId() {

        return id;
    }

    public static void setId(String id) {
        MyApplication.id = id;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        MyApplication.token = token;
    }

    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
            //打印得check target Image:2592X4608
            Log.d("zjc", "图片的宽度和高度：check target Image:" + temp.getWidth() + "X" + temp.getHeight());
            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        return null;
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.app_icon;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
//        String sdkPath = getAppCacheDir(context) + "/nim"; // 可以不设置，那么将采用默认路径
//        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
//        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        options.thumbnailSize =width;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String s, String s1, SessionTypeEnum sessionTypeEnum) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionTypeEnum, String s) {
                return null;
            }


        };
        return options;
    }

    /**
     * 配置 APP 保存图片/语音/文件/log等数据的目录
     * 这里示例用SD卡的应用扩展存储目录
     */
//    static String getAppCacheDir(Context context) {
//        String storageRootPath = null;
//        try {
//            // SD卡应用扩展存储区(APP卸载后，该目录下被清除，用户也可以在设置界面中手动清除)，请根据APP对数据缓存的重要性及生命周期来决定是否采用此缓存目录.
//            // 该存储区在API 19以上不需要写权限，即可配置 <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" android:maxSdkVersion="18"/>
//            if (context.getExternalCacheDir() != null) {
//                storageRootPath = context.getExternalCacheDir().getCanonicalPath();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (TextUtils.isEmpty(storageRootPath)) {
//            // SD卡应用公共存储区(APP卸载后，该目录不会被清除，下载安装APP后，缓存数据依然可以被加载。SDK默认使用此目录)，该存储区域需要写权限!
//            storageRootPath = Environment.getExternalStorageDirectory() + "/" + DemoCache.getContext().getPackageName();
//        }
//
//        return storageRootPath;
//    }
    private void initUiKit() {
        // 初始化
        NimUIKit.init(this);

//        // 可选定制项
//        // 注册定位信息提供者类（可选）,如果需要发送地理位置消息，必须提供。
//        // demo中使用高德地图实现了该提供者，开发者可以根据自身需求，选用高德，百度，google等任意第三方地图和定位SDK。
//        NimUIKit.setLocationProvider(new NimDemoLocationProvider());
//
//        // 会话窗口的定制: 示例代码可详见demo源码中的SessionHelper类。
        // 1.注册自定义消息附件解析器（可选）
        // 2.注册各种扩展消息类型的显示ViewHolder（可选）
        // 3.设置会话中点击事件响应处理（一般需要）
//        SessionHelper.init();
//
//        // 通讯录列表定制：示例代码可详见demo源码中的ContactHelper类。
//        // 1.定制通讯录列表中点击事响应处理（一般需要，UIKit 提供默认实现为点击进入聊天界面)
//        ContactHelper.init();
//
//        // 在线状态定制初始化。
//        NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }
}
