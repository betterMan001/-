package com.ly.a316.ly_meetingroommanagement.FacePack;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arcsoft.facedetection.AFD_FSDKEngine;
import com.arcsoft.facedetection.AFD_FSDKError;
import com.arcsoft.facedetection.AFD_FSDKFace;
import com.arcsoft.facedetection.AFD_FSDKVersion;
import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;

import com.google.gson.JsonObject;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.guo.android_extend.image.ImageConverter;
import com.guo.android_extend.widget.ExtImageView;
import com.guo.android_extend.widget.HListView;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements SurfaceHolder.Callback {
    private final String TAG = "zjc";
    private final static int MSG_CODE = 0x1000;
    private final static int MSG_EVENT_REG = 0x1001;
    private final static int MSG_EVENT_NO_FACE = 0x1002;
    private final static int MSG_EVENT_NO_FEATURE = 0x1003;
    private final static int MSG_EVENT_FD_ERROR = 0x1004;
    private final static int MSG_EVENT_FR_ERROR = 0x1005;
    private final static int MSG_EVENT_IMG_ERROR = 0x1006;
    private UIHandler mUIHandler;//在线程更新视图的方法
    // Intent data.
    private String mFilePath;//人脸图片的文件路径

    private SurfaceView mSurfaceView;//用来显示照片
    private SurfaceHolder mSurfaceHolder;
    private Bitmap mBitmap;
    private Rect src = new Rect();//这个是将人脸加个框框标记出来
    private Rect dst = new Rect();//将src圈出的人脸按比例缩小给显示出来
    private Thread view;
    private EditText mEditText;
    private ExtImageView mExtImageView;
    private HListView mHListView;
    private RegisterViewAdapter mRegisterViewAdapter;
    private AFR_FSDKFace mAFR_FSDKFace;//人脸特征信息

    byte[] face_information;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x23) {
                Toast.makeText(RegisterActivity.this, "网络访问失败", Toast.LENGTH_SHORT).show();
            }
            if (msg.what == 0x24) {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register);
        //initial data.
        Log.i(TAG, "来到注册页面");
        if (!getIntentData(getIntent().getExtras())) {
            Log.e(TAG, "getIntentData fail!");
            this.finish();
        }
        mRegisterViewAdapter = new RegisterViewAdapter(this);//适配器
        mHListView = (HListView) findViewById(R.id.hlistView);//注册人脸后，页面下方会显示注册过的每个人脸
        mHListView.setAdapter(mRegisterViewAdapter);
        mHListView.setOnItemClickListener(mRegisterViewAdapter);//下方人脸的信息，点击后判断是否删除

        mUIHandler = new UIHandler();
        mBitmap = MyApplication.decodeImage(mFilePath);//将图片转换成可以识别的bitmap
        src.set(0, 0, mBitmap.getWidth(), mBitmap.getHeight());//检测到人脸后，给人脸加个方框标记出来
        mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);//整个屏幕实时显示人脸
        mSurfaceView.getHolder().addCallback(this);
        view = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mSurfaceHolder == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                //*********这一步是为了判断图片是不是jpg格式**************
                    /*
                    手机从摄像头采集的预览数据一般都是NV21
                    NV21 的存储格式是，以4 X 4 图片为例子
                    占用内存为 4 X 4 X 3 / 2 = 24 个字节
                     */
                //mBitmap是将图片转换成bitmap的结果
                byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight() * 3 / 2];
                try {
                    ImageConverter convert = new ImageConverter();
                    //这里可以将图片转换成jpg格式
                    convert.initial(mBitmap.getWidth(), mBitmap.getHeight(), ImageConverter.CP_PAF_NV21);//将图片转换成另外一个格式
                    if (convert.convert(mBitmap, data)) {
                        Log.d(TAG, "convert ok!");
                    }
                    convert.destroy();
                } catch (Exception e) {
                    e.printStackTrace();
                    Message reg = Message.obtain();
                    reg.what = MSG_CODE;
                    reg.arg1 = MSG_EVENT_IMG_ERROR;//图片格式不对的错误
                    reg.obj = e.getMessage();
                    mUIHandler.sendMessage(reg);
                }
                //*******************************************************************************

                //***********判断FD初始化情况* Fd是人脸检测大致的检测是不是有一张人脸****************************************************
                // 这个类具体实现了人脸检测的功能
                AFD_FSDKEngine engine = new AFD_FSDKEngine();
                //这个类用来保存版本信息
                AFD_FSDKVersion version = new AFD_FSDKVersion();
                //所有人脸信息
                List<AFD_FSDKFace> result = new ArrayList<AFD_FSDKFace>();
                //这个类用来保存函数执行的错误信息
                AFD_FSDKError err = engine.AFD_FSDK_InitialFaceEngine(FaceDB.appid, FaceDB.fd_key, AFD_FSDKEngine.AFD_OPF_0_HIGHER_EXT, 16, 5);
                Log.d(TAG, "AFD_FSDK_InitialFaceEngine = " + err.getCode());
                if (err.getCode() != AFD_FSDKError.MOK) {
                    Message reg = Message.obtain();
                    reg.what = MSG_CODE;
                    reg.arg1 = MSG_EVENT_FD_ERROR;//FD初始化失败
                    reg.arg2 = err.getCode();
                    mUIHandler.sendMessage(reg);

                }
                //******************************************************************************


                //这个为获取SDK版本信息
                err = engine.AFD_FSDK_GetVersion(version);
                Log.d(TAG, "AFD_FSDK_GetVersion =" + version.toString() + ", " + err.getCode());
                ////输入的 data 数据为 NV21 格式（如 Camera 里 NV21 格式的 preview 数据），其中 height 不能为奇数，人脸检测返回结果保存在 result
                err = engine.AFD_FSDK_StillImageFaceDetection(data, mBitmap.getWidth(), mBitmap.getHeight(), AFD_FSDKEngine.CP_PAF_NV21, result);

                // AFD_FSDK_StillImageFaceDetection =0<1
                Log.d(TAG, "AFD_FSDK_StillImageFaceDetection =" + err.getCode() + "<" + result.size());

                //*************************将图片按比例缩小放到dst这个矩形里面********************************************************
                while (mSurfaceHolder != null) {
                    Canvas canvas = mSurfaceHolder.lockCanvas();//通过lockCanvas()方法获取Canvas对象
                    // 在子线程中使用Canvas对象进行绘制
                    /**
                     * lockCanvas() 方法获得的Canvas对象仍然是上次绘制的对象，由于我们是不断进行绘制，但是每次得到的Canvas对象都是第一次创建的Canvas对象。
                     */
                    if (canvas != null) {
                        Paint mPaint = new Paint();
                        boolean fit_horizontal = canvas.getWidth() / (float) src.width() < canvas.getHeight() / (float) src.height() ? true : false;
                        float scale = 1.0f;
                        if (fit_horizontal) {
                            scale = canvas.getWidth() / (float) src.width();
                            dst.left = 0;
                            dst.top = (canvas.getHeight() - (int) (src.height() * scale)) / 2;
                            dst.right = dst.left + canvas.getWidth();
                            dst.bottom = dst.top + (int) (src.height() * scale);
                        } else {
                            scale = canvas.getHeight() / (float) src.height();
                            dst.left = (canvas.getWidth() - (int) (src.width() * scale)) / 2;
                            dst.top = 0;
                            dst.right = dst.left + (int) (src.width() * scale);
                            dst.bottom = dst.top + canvas.getHeight();
                        }
                        canvas.drawBitmap(mBitmap, src, dst, mPaint);
                        canvas.save();
                        canvas.scale((float) dst.width() / (float) src.width(), (float) dst.height() / (float) src.height());
                        canvas.translate(dst.left / scale, dst.top / scale);
                        for (AFD_FSDKFace face : result) {
                            mPaint.setColor(Color.RED);
                            mPaint.setStrokeWidth(10.0f);
                            mPaint.setStyle(Paint.Style.STROKE);
                            canvas.drawRect(face.getRect(), mPaint);
                        }
                        canvas.restore();
                        //unlockCanvasAndPost方法将画布内容进行提交
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                        break;
                    }
                }
                //***************************判断FR初始化情况   AFR_FSDKVersion提取特征函数 是人脸比对*****************************************************
                if (!result.isEmpty()) {
                    AFR_FSDKVersion version1 = new AFR_FSDKVersion();
                    AFR_FSDKEngine engine1 = new AFR_FSDKEngine();
                    AFR_FSDKFace result1 = new AFR_FSDKFace();//人脸特征信息
                    AFR_FSDKError error1 = engine1.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
                    Log.d("com.arcsoft", "AFR_FSDK_InitialEngine = " + error1.getCode());
                    if (error1.getCode() != AFD_FSDKError.MOK) {
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_FR_ERROR;//FR初始化失败
                        reg.arg2 = error1.getCode();
                        mUIHandler.sendMessage(reg);
                    }
                    //*******************************************************************************************************************************
                    // 函数功能为获取引擎版本信息
                    error1 = engine1.AFR_FSDK_GetVersion(version1);
                    Log.d("com.arcsoft", "FR=" + version.toString() + "," + error1.getCode()); //(210, 178 - 478, 446), degree = 1　780, 2208 - 1942, 3370
                    /**
                     * AFR_FSDK_ExtractFRFeature
                     * 输入的 data 数据为 NV21 格式（如 Camera 里 NV21 格式的 preview 数据）；人脸坐标一般使用人脸检测返回的 Rect 传入；人脸角度请按照人脸检测引擎返回的值传入。
                     * 函数功能为检测输入图像中的人脸特征信息，输出结果保存在AFR_FSDKFace feature
                     */
                    error1 = engine1.AFR_FSDK_ExtractFRFeature(data, mBitmap.getWidth(), mBitmap.getHeight(), AFR_FSDKEngine.CP_PAF_NV21, new Rect(result.get(0).getRect()), result.get(0).getDegree(), result1);
                    Log.d("com.arcsoft", "Face=" + result1.getFeatureData()[0] + "," + result1.getFeatureData()[1] + "," + result1.getFeatureData()[2] + "," + error1.getCode());
                    if (error1.getCode() == error1.MOK) {
                        mAFR_FSDKFace = result1.clone();
                        face_information = mAFR_FSDKFace.getFeatureData();//获取人脸特征信息
                        int width = result.get(0).getRect().width();//得到刚才检测的第一张脸
                        int height = result.get(0).getRect().height();
                        Bitmap face_bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        Canvas face_canvas = new Canvas(face_bitmap);
                        face_canvas.drawBitmap(mBitmap, result.get(0).getRect(), new Rect(0, 0, width, height), null);
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_REG;
                        reg.obj = face_bitmap;
                        mUIHandler.sendMessage(reg);
                    } else {
                        Message reg = Message.obtain();
                        reg.what = MSG_CODE;
                        reg.arg1 = MSG_EVENT_NO_FEATURE;
                        mUIHandler.sendMessage(reg);
                    }
                    error1 = engine1.AFR_FSDK_UninitialEngine();//释放
                    Log.d("com.arcsoft", "AFR_FSDK_UninitialEngine : " + error1.getCode());
                } else {
                    Message reg = Message.obtain();
                    reg.what = MSG_CODE;
                    reg.arg1 = MSG_EVENT_NO_FACE;
                    mUIHandler.sendMessage(reg);
                }
                err = engine.AFD_FSDK_UninitialFaceEngine();
                Log.d(TAG, "AFD_FSDK_UninitialFaceEngine =" + err.getCode());
            }
        });
        view.start();

    }

    /**
     * @param bundle
     * @note bundle data :
     * String imagePath
     */
    private boolean getIntentData(Bundle bundle) {
        try {
            mFilePath = bundle.getString("imagePath");
            if (mFilePath == null || mFilePath.isEmpty()) {
                return false;
            }
            Log.i(TAG, "getIntentData:" + mFilePath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mSurfaceHolder = null;
        try {
            view.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class UIHandler extends android.os.Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE) {
                if (msg.arg1 == MSG_EVENT_REG) {
                    LayoutInflater inflater = LayoutInflater.from(RegisterActivity.this);
                    View layout = inflater.inflate(R.layout.dialog_register, null);
                    mEditText = (EditText) layout.findViewById(R.id.editview);
                    mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(16)});//限制最长字数
                    mExtImageView = (ExtImageView) layout.findViewById(R.id.extimageview);
                    mExtImageView.setImageBitmap((Bitmap) msg.obj);
                    final Bitmap face = (Bitmap) msg.obj;
                    // TODO: 2019/2/18 人脸注册
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("请输入注册名字")
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setView(layout)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ((MyApplication) RegisterActivity.this.getApplicationContext()).mFaceDB.addFace(mEditText.getText().toString(), mAFR_FSDKFace, face);
                                    mRegisterViewAdapter.notifyDataSetChanged();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            OkHttpClient okHttpClient = new OkHttpClient();
                                            FormBody formBody = new FormBody.Builder()
                                                    .add("phone", "18248612936")
                                                    .add("face", new String(face_information))
                                                    .build();
                                            final Request request = new Request.Builder().url(Net.schedule_face).post(formBody).build();
                                            //step 4： 建立联系 创建Call对象
                                            okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    handler.sendEmptyMessage(0x23);
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    String rebody = response.body().string();
                                                    try {
                                                        JSONObject jsonObject = new JSONObject(rebody);
                                                        if (jsonObject.getString("result").equals("success"))
                                                        {
                                                            handler.sendEmptyMessage(0x24);
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                        }
                                    }).start();
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else if (msg.arg1 == MSG_EVENT_NO_FEATURE) {
                    Toast.makeText(RegisterActivity.this, "人脸特征无法检测，请换一张图片", Toast.LENGTH_SHORT).show();
                } else if (msg.arg1 == MSG_EVENT_NO_FACE) {
                    Toast.makeText(RegisterActivity.this, "没有检测到人脸，请换一张图片", Toast.LENGTH_SHORT).show();
                } else if (msg.arg1 == MSG_EVENT_FD_ERROR) {
                    Toast.makeText(RegisterActivity.this, "FD初始化失败，错误码：" + msg.arg2, Toast.LENGTH_SHORT).show();
                } else if (msg.arg1 == MSG_EVENT_FR_ERROR) {
                    Toast.makeText(RegisterActivity.this, "FR初始化失败，错误码：" + msg.arg2, Toast.LENGTH_SHORT).show();
                } else if (msg.arg1 == MSG_EVENT_IMG_ERROR) {
                    Toast.makeText(RegisterActivity.this, "图像格式错误，：" + msg.obj, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    class Holder {
        ExtImageView siv;
        TextView tv;
    }

    class RegisterViewAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {
        Context mContext;
        LayoutInflater mLInflater;

        public RegisterViewAdapter(Context c) {
            mContext = c;
            mLInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return ((MyApplication) mContext.getApplicationContext()).mFaceDB.mRegister.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView != null) {
                holder = (Holder) convertView.getTag();
            } else {
                convertView = mLInflater.inflate(R.layout.item_sample, null);
                holder = new Holder();
                holder.siv = (ExtImageView) convertView.findViewById(R.id.imageView1);
                holder.tv = (TextView) convertView.findViewById(R.id.textView1);
                convertView.setTag(holder);
            }

            if (!((MyApplication) mContext.getApplicationContext()).mFaceDB.mRegister.isEmpty()) {
                FaceDB.FaceRegist face = ((MyApplication) mContext.getApplicationContext()).mFaceDB.mRegister.get(position);
                holder.tv.setText(face.mName);
                String keyPath = face.mFaceList.keySet().iterator().next();
                holder.siv.setImageBitmap(BitmapFactory.decodeFile(keyPath));
                holder.siv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                //holder.siv.setImageResource(R.mipmap.ic_launcher);
                convertView.setWillNotDraw(false);
            }

            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("onItemClick", "onItemClick = " + position + "pos=" + mHListView.getScroll());
            final String name = ((MyApplication) mContext.getApplicationContext()).mFaceDB.mRegister.get(position).mName;
            final int count = ((MyApplication) mContext.getApplicationContext()).mFaceDB.mRegister.get(position).mFaceList.size();
            final Map<String, AFR_FSDKFace> face = ((MyApplication) mContext.getApplicationContext()).mFaceDB.mRegister.get(position).mFaceList;
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("删除注册名:" + name)
                    .setMessage("包含:" + count + "个注册人脸特征信息")
                    .setView(new ListView(mContext))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ((MyApplication) mContext.getApplicationContext()).mFaceDB.delete(name);
                            mRegisterViewAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
    }
}
