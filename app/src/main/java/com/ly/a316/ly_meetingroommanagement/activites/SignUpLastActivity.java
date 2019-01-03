package com.ly.a316.ly_meetingroommanagement.activites;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.anlia.photofactory.factory.PhotoFactory;
import com.anlia.photofactory.permission.PermissionAlwaysDenied;
import com.anlia.photofactory.result.ResultData;
import com.guo.android_extend.cache.BitmapMonitor;
import com.gyf.barlibrary.ImmersionBar;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.services.SignUpServiceImp;
import com.ly.a316.ly_meetingroommanagement.services.UploadServiceImp;
import com.ly.a316.ly_meetingroommanagement.utils.PointConst;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.anlia.photofactory.factory.PhotoFactory.ERROR_CROP_DATA;

public class SignUpLastActivity extends BaseActivity {

    @BindView(R.id.act_sign_up_name)
    EditText actSignUpName;
    @BindView(R.id.act_sign_up_head)
    RoundedImageView actSignUpHead;
    //裁剪用工厂
    private PhotoFactory photoFactory;
    private static final String TAG = "SignUpLastActivity:";
    private Bitmap headImage;
    private String headName="headImage.jpg";
    public String phoneURL;
    private String phoneNumber;
    private String password;
    private String nickName;
    private String imageUrl;
    private String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏沉浸效果
        ImmersionBar.with(this).reset().fitsSystemWindows(true).statusBarColor("#00A7FF").init();
        setContentView(R.layout.activity_sign_up_last);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        photoFactory = new PhotoFactory(this, Environment.getExternalStorageDirectory() + "/" + "DCIM", headName);
        //申请相关权限
        PhotoFactory.setPermissionAlwaysDeniedAction(new PermissionAlwaysDenied.Action() {
            @Override
            public void onAction(Context context, List<String> permissions, final PermissionAlwaysDenied.Executor executor) {
                List<String> permissionNames = PhotoFactory.transformPermissionText(context, permissions);
                String permissionText = TextUtils.join("权限\n", permissionNames);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("权限说明");
                builder.setMessage("您禁止了以下权限的动态申请：\n\n" + permissionText + "权限\n\n是否去应用权限管理中手动授权呢？");
                builder.setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.toSetting();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });

    }

    public static final void start(Context context, String phoneNumber, String password) {
        Intent intent = new Intent();
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("password", password);
        intent.setClass(context, SignUpLastActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @OnClick({R.id.act_sign_up_last_back_ll, R.id.act_sign_up_head, R.id.act_sign_up_last_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //退回到上一个界面
            case R.id.act_sign_up_last_back_ll:
                finish();
                break;
            //点击选择头像
            case R.id.act_sign_up_head:
                uploadHeadImage();
                break;
            //提交账号信息
            case R.id.act_sign_up_last_bt:
                register();
                break;
        }
    }

    private void register() {
        //1.先判断昵称是否已经正确输入
        nickName = actSignUpName.getText().toString();
        if ("".equals(nickName) || nickName == null) {
            subThreadToast(PointConst.NO_EMPTY_NICK_NAME);
        } else {
            //2.获得账号和密码
            Intent intent = getIntent();
             phoneNumber = intent.getExtras().getString("phoneNumber", "");
             password = intent.getExtras().getString("password", "");
             getFileURl();
        }
    }
    private void getFileURl(){
        //1.将headImge存到本地
            File photo_file=new File(filePath);//生成该路径的文件
            new UploadServiceImp(SignUpLastActivity.this).uploadFile(photo_file);
    }
    private String getImagUrl() {
        //测试默认返回本地头像
        return MyApplication.getImageURL();
    }

    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCamera=view.findViewById(R.id.btn_take_photo);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        //将要加载的布局绑定到popupWindow
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        //要加载到的视图
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_sign_up_last, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 0.5f;
        this.getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                SignUpLastActivity.this.getWindow().setAttributes(params);
            }
        });
        //打开摄像头
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoFactory.FromCamera()
                        .AddOutPutExtra()
                        .StartForResult(new PhotoFactory.OnResultListener() {
                            @Override
                            public void onCancel() {
                                Log.e(TAG, "取消从相册选择");
                            }

                            @Override
                            public void onSuccess(ResultData resultData) {
                                dealSelectPhoto(resultData);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
            }
        });
        //打开相册
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFactory.FromGallery()
                        .StartForResult(new PhotoFactory.OnResultListener() {
                            @Override
                            public void onCancel() {
                                Log.e(TAG, "取消从相册选择");
                            }

                            @Override
                            public void onSuccess(ResultData resultData) {
                                dealSelectPhoto(resultData);
//                            Uri uri = resultData.GetUri();
//                            imgPhoto.setImageURI(uri);
                            }

                            @Override
                            public void onError(String error) {

                            }

                        });
                popupWindow.dismiss();
            }
        });
        //取消操作
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void dealSelectPhoto(ResultData resultData) {
        Uri uri = resultData
                .setExceptionListener(new ResultData.OnExceptionListener() {
                    @Override
                    public void onCatch(String error, Exception e) {
                        Toast.makeText(SignUpLastActivity.this, error, Toast.LENGTH_SHORT).show();
                        return;
                    }
                })
                .GetUri();
        photoFactory.FromCrop(uri)
                .AddAspectX(1)
                .AddAspectY(1)
                .StartForResult(new PhotoFactory.OnResultListener() {
                    @Override
                    public void onCancel() {
                        Log.e(TAG, "取消裁剪");
                    }

                    @Override
                    public void onSuccess(ResultData data) {
                            Uri uri= data.addScaleCompress(720,720).GetUri();
                            filePath=getRealFilePath(SignUpLastActivity.this,uri);

                        headImage=data.addScaleCompress(720,720).GetBitmap();
                        actSignUpHead.setImageBitmap(headImage);
                       // dealCropPhoto();
                    }

                    @Override
                    public void onError(String error) {
                        switch (error) {
                            case ERROR_CROP_DATA:
                                Toast.makeText(SignUpLastActivity.this, "data为空", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public  void uploadBack(){

        //4.发送注册信息到后台服务器
        new SignUpServiceImp(this).signUp(phoneNumber,nickName,phoneURL,password);
    }
    //对注册的结果进行反馈
    public void callBack(String re) {
        //对注册结果进行不同的反馈
        final String result = re;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //0不是合法的号码
                if ("0".equals(result)) {
                    subThreadToast("对不起，该号码不是员工的号码");
                } else if ("1".equals(result)) {
                    //注册成功
                    subThreadToast("注册成功！");
                } else {
                    //号码已经注册过了
                    subThreadToast("该号码已经被注册！");
                }
                finish();
            }
        });
    }
}
