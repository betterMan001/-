package com.ly.a316.ly_meetingroommanagement;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import com.ly.a316.ly_meetingroommanagement.FacePack.FaceDB;

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
    private static String id="";
    private static String token="";
    //本地保存数据
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static List<Activity> activityList=new ArrayList<>();

    //初始化加载人脸识别引擎
    private final String TAG=this.getClass().toString();
    public FaceDB mFaceDB;
    Uri mImage;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        context=getApplicationContext();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        editor=pref.edit();

        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
        mImage = null;
    }
    //添加活动
    public static void addActivity(Activity activity){
        activityList.add(activity);
    }
    //关闭所有活动
    public static void finishAllActivities(){
        for(Activity activity:activityList){
            if(!activity.isFinishing()){
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

    public static Bitmap decodeImage(String path){
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
