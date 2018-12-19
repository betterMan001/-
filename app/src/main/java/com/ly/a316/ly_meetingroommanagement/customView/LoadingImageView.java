package com.ly.a316.ly_meetingroommanagement.customView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import android.os.Handler;
import android.widget.Toast;

import com.ly.a316.ly_meetingroommanagement.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.LogRecord;

import static com.makeramen.roundedimageview.RoundedDrawable.DEFAULT_BORDER_COLOR;

/**
 * 作者：余智强
 * 2018/12/12
 */
@SuppressLint("AppCompatCustomView")
public class LoadingImageView extends ImageView {

    private static final int GET_DATA_SUCCESSS=1;
    private static  final int NETWORK_ERROR=2;
    private static final int SERVER_ERROR=3;

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 1;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    private final RectF mDrawableRect = new RectF();
    private final RectF mBorderRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private final Paint mBorderPaint = new Paint();

    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mDrawableRadius;
    private float mBorderRadius;

    private boolean mReady;
    private boolean mSetupPending;


    public LoadingImageView(Context context) {
        super(context);
    }

    public LoadingImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public LoadingImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        super.setScaleType(SCALE_TYPE);



        mReady = true;

        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    Handler handler =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_DATA_SUCCESSS:
                    //显示网络图片
                    Bitmap bitmap = (Bitmap) msg.obj;
                    setImageBitmap(bitmap);
                    break;
                case NETWORK_ERROR:
                    Toast.makeText(getContext(), "网络连接错误", Toast.LENGTH_SHORT).show();
                    break;
                case SERVER_ERROR:
                    Toast.makeText(getContext(), "服务器发生错误", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }  private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (mBitmap == null) {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        mBorderRect.set(0, 0, getWidth(), getHeight());
        mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2, (mBorderRect.width() - mBorderWidth) / 2);

        mDrawableRect.set(mBorderWidth, mBorderWidth, mBorderRect.width() - mBorderWidth, mBorderRect.height() - mBorderWidth);
        mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width() / 2);

        updateShaderMatrix();
        invalidate();
    }
    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = mDrawableRect.height() / (float) mBitmapHeight;
            dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        } else {
            scale = mDrawableRect.width() / (float) mBitmapWidth;
            dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth, (int) (dy + 0.5f) + mBorderWidth);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }
    //设置网络图片
    public  void setImageURL(final String url){
        //开启一个线程
        new Thread(){
            @Override
            public void run() {
                //super.run();
                try {
                    //将传过来的路径转换成URL
                    URL url1=new URL(url);
                    ///获取网络连接
                    try {
                        HttpURLConnection connection=(HttpURLConnection)url1.openConnection();
                        //使用get方法访问网络
                        connection.setRequestMethod("GET");
                        //超时未10秒
                        connection.setConnectTimeout(10000);
                        //获取返回码
                        int code=connection.getResponseCode();
                        if(code==200){
                            InputStream inputStream=connection.getInputStream();
                            Bitmap bitmap= BitmapFactory.decodeStream(inputStream);

                            //使用工厂化吧网络输入流生产bitmap
                            Message message=Message.obtain();
                            message.obj=bitmap;
                            message.what=GET_DATA_SUCCESSS;
                            handler.sendMessage(message);
                            inputStream.close();
                        }else{
                            //服务器错误

                            handler.sendEmptyMessage(SERVER_ERROR);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        //网络连接错误
                        handler.sendEmptyMessage(NETWORK_ERROR);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
