package com.ly.a316.ly_meetingroommanagement.main.activites;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.meetting.activity.OrderDetailMeetingActivity;
import com.netease.nim.uikit.common.ToastHelper;

import org.json.JSONException;
import org.json.JSONObject;

import cn.simonlee.xcodescanner.core.CameraScanner;
import cn.simonlee.xcodescanner.core.GraphicDecoder;
import cn.simonlee.xcodescanner.core.NewCameraScanner;
import cn.simonlee.xcodescanner.core.OldCameraScanner;
import cn.simonlee.xcodescanner.core.ZBarDecoder;
import cn.simonlee.xcodescanner.view.AdjustTextureView;

/**
 * 扫码页面
 *
 * @author Simon Lee
 * @e-mail jmlixiaomeng@163.com
 * @github https://github.com/Simon-Leeeeeeeee/XCodeScanner
 */
public class ScanActivity extends AppCompatActivity implements CameraScanner.CameraListener, TextureView.SurfaceTextureListener, GraphicDecoder.DecodeListener, View.OnClickListener {

    private AdjustTextureView mTextureView;
    private View mScannerFrameView;

    private CameraScanner mCameraScanner;
    protected GraphicDecoder mGraphicDecoder;

    protected String TAG = "XCodeScanner";
    private Button mButton_Flash;
    private int[] mCodeType;
    //中断标记
    Boolean flag=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);


        mTextureView = findViewById(R.id.textureview);
        mTextureView.setSurfaceTextureListener(this);

        mScannerFrameView = findViewById(R.id.scannerframe);

        mButton_Flash = findViewById(R.id.btn_flash);
        mButton_Flash.setOnClickListener(this);

        Intent intent = getIntent();
        mCodeType = intent.getIntArrayExtra("codeType");

        /*
        * 注意，SDK21的设备是可以使用NewCameraScanner的，但是可能存在对新API支持不够的情况，比如红米Note3（双网通Android5.0.2）
        * 开发者可自行配置使用规则，比如针对某设备型号过滤，或者针对某SDK版本过滤
        * */
        if (intent.getBooleanExtra("newAPI", false) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCameraScanner = new NewCameraScanner(this);
        } else {
            mCameraScanner = new OldCameraScanner(this);
        }
    }

    @Override
    protected void onRestart() {
        if (mTextureView.isAvailable()) {
            //部分机型转到后台不会走onSurfaceTextureDestroyed()，因此isAvailable()一直为true，转到前台后不会再调用onSurfaceTextureAvailable()
            //因此需要手动开启相机
            mCameraScanner.setPreviewTexture(mTextureView.getSurfaceTexture());
            mCameraScanner.setPreviewSize(mTextureView.getWidth(), mTextureView.getHeight());
            mCameraScanner.openCamera(this.getApplicationContext());
        }
        super.onRestart();
    }

    @Override
    protected void onPause() {
        mCameraScanner.closeCamera();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mCameraScanner.setGraphicDecoder(null);
        if (mGraphicDecoder != null) {
            mGraphicDecoder.setDecodeListener(null);
            mGraphicDecoder.detach();
        }
        mCameraScanner.detach();
        super.onDestroy();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureAvailable() width = " + width + " , height = " + height);
        mCameraScanner.setPreviewTexture(surface);
        mCameraScanner.setPreviewSize(width, height);
        mCameraScanner.openCamera(this);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureSizeChanged() width = " + width + " , height = " + height);
        // TODO 当View大小发生变化时，要进行调整。
//        mTextureView.setImageFrameMatrix();
//        mCameraScanner.setPreviewSize(width, height);
//        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.e(TAG, getClass().getName() + ".onSurfaceTextureDestroyed()");
        return true;
    }

    @Override// 每有一帧画面，都会回调一次此方法
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    public void openCameraSuccess(int frameWidth, int frameHeight, int frameDegree) {
        Log.e(TAG, getClass().getName() + ".openCameraSuccess() frameWidth = " + frameWidth + " , frameHeight = " + frameHeight + " , frameDegree = " + frameDegree);
        mTextureView.setImageFrameMatrix(frameWidth, frameHeight, frameDegree);
        if (mGraphicDecoder == null) {
            mGraphicDecoder = new ZBarDecoder(this, mCodeType);//使用带参构造方法可指定条码识别的格式
        }
        //该区域坐标为相对于父容器的左上角顶点。
        //TODO 应考虑TextureView与ScannerFrameView的Margin与padding的情况
        mCameraScanner.setFrameRect(mScannerFrameView.getLeft(), mScannerFrameView.getTop(), mScannerFrameView.getRight(), mScannerFrameView.getBottom());
        mCameraScanner.setGraphicDecoder(mGraphicDecoder);
    }

    @Override
    public void openCameraError() {
        Toast.makeText(this,  "出错了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noCameraPermission() {
        Toast.makeText(this,  "没有权限", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cameraDisconnected() {
        Toast.makeText(this,  "断开了连接", Toast.LENGTH_SHORT).show();
    }

    int mBrightnessCount = 0;

    @Override
    public void cameraBrightnessChanged(int brightness) {
        if (brightness <= 50) {
            if (mBrightnessCount < 0) {
                mBrightnessCount = 1;
            } else {
                mBrightnessCount++;
            }
        } else {
            if (mBrightnessCount > 0) {
                mBrightnessCount = -1;
            } else {
                mBrightnessCount--;
            }
        }
        if (mBrightnessCount > 4) {//连续5帧亮度低于50，显示闪光灯开关
            mButton_Flash.setVisibility(View.VISIBLE);
        } else if(mBrightnessCount < -4 && !mCameraScanner.isFlashOpened()){//连续5帧亮度不低于50，且闪光灯未开启，隐藏闪光灯开关
            mButton_Flash.setVisibility(View.GONE);
        }
    }

    int mCount = 0;
    String mResult = null;

    @Override
    public void decodeComplete(String result, int type, int quality, int requestCode) {
        if (result == null) return;
        if (result.equals(mResult)) {
            if (++mCount > 3) {//连续四次相同则显示结果（主要过滤脏数据，也可以根据条码类型自定义规则）
                /*正式使用中不弹该Toast*/
//                if (quality < 10) {
//                    Toast.makeText(this,"[类型" + type + "/精度00" + quality + "]" + result,Toast.LENGTH_SHORT).show();
//                } else if (quality < 100) {
//                    Toast.makeText(this,"[类型" + type + "/精度0" + quality + "]" + result,Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this,"[类型" + type + "/精度" + quality + "]" + result,Toast.LENGTH_SHORT).show();
//                }
                if(flag==false){
                    //扫码预订会议室
                    orderMeeting(result);
                }

            }

        } else {
            mCount = 1;
            mResult = result;
        }
        Log.d(TAG, getClass().getName() + ".decodeComplete() -> " + mResult);
    }
   private void orderMeeting(String result)  {
        //1.先分辨扫的码是不是预定会议室的

       try {
           //1.2先判断不是JSONObject
           JSONObject jsonObject=new JSONObject(result);
           if(jsonObject ==null){
             return;
           }
           //1.3再判断是不是我们的jsonObject
           String roomId=jsonObject.getString("roomId");
           String address=jsonObject.getString("address");
           if(roomId==null){
             return;
           }
           //1.4传至给预定会议界面准备跳转
           else{
               flag=true;
               ScanActivity.this.finish();
               OrderDetailMeetingActivity.start(this,"","",0,address,roomId);
           }
       } catch (JSONException e) {
           e.printStackTrace();
       }

   }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_flash: {
                if (v.isSelected()) {
                    ((Button) v).setText("开启闪光灯");
                    v.setSelected(false);
                    mCameraScanner.closeFlash();
                } else {
                    ((Button) v).setText("关闭闪光灯");
                    v.setSelected(true);
                    mCameraScanner.openFlash();
                }
                break;
            }
        }
    }

}
