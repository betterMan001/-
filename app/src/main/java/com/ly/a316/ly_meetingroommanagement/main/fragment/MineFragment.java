package com.ly.a316.ly_meetingroommanagement.main.fragment;


import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.anlia.photofactory.factory.PhotoFactory;
import com.anlia.photofactory.permission.PermissionAlwaysDenied;
import com.anlia.photofactory.result.ResultData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ly.a316.ly_meetingroommanagement.MyApplication;
import com.ly.a316.ly_meetingroommanagement.R;
import com.ly.a316.ly_meetingroommanagement.login.activities.ForgetPWDOneActivity;
import com.ly.a316.ly_meetingroommanagement.login.activities.WelcomeActivity;
import com.ly.a316.ly_meetingroommanagement.login.services.UploadServiceImp;
import com.ly.a316.ly_meetingroommanagement.main.MainActivity;
import com.ly.a316.ly_meetingroommanagement.main.activites.ChangeNameActivity;
import com.ly.a316.ly_meetingroommanagement.main.activites.StatisticalActivity;
import com.ly.a316.ly_meetingroommanagement.main.services.imp.ModifyInformationServiceImp;
import com.ly.a316.ly_meetingroommanagement.utils.CleanCacheUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.anlia.photofactory.factory.PhotoFactory.ERROR_CROP_DATA;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.head_view)
    RoundedImageView headView;
    @BindView(R.id.switch_out)
    ImageView switchOut;
    @BindView(R.id.user_name)
    TextView userName;
    //裁剪用工厂
    private PhotoFactory photoFactory;
    private static final String TAG = "MineFragment:";
    private Bitmap headImage;
    private String headName = "headImage.jpg";
    private String filePath;
    public String phoneURL;
    //修改名称要用的属性
    public static String newName="";
    public static String type="1";
    //标记开关的状态
    boolean isSwitch = true;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fr_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        initFactory();

        return view;

    }

    private void initFactory() {
        photoFactory = new PhotoFactory(getActivity(), Environment.getExternalStorageDirectory() + "/" + "DCIM", headName);
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

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView(){
        //1.加载头像

    RequestOptions requestOptions=new RequestOptions()
            .placeholder(R.drawable.beaty_head001)
            .error(R.drawable.beaty_head001);
    Glide
            .with(this)
            .load(MyApplication.getImageURL())
            .apply(requestOptions)
            .into(headView);
    //2.加载用户名称
    userName.setText(MyApplication.getUserName());
}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void loginOut() {
        //1.清空本地存储的数据
        MyApplication.editor.remove("token");
        MyApplication.editor.commit();
        //2.退出云信账号
        NIMClient.getService(AuthService.class).logout();
        //3.跳转至欢迎界面
        Intent intent = new Intent(MyApplication.getContext(), WelcomeActivity.class);
        //4.将loginActivity放入新的task
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //5.移除极光推送的别名
        ((MainActivity) getActivity()).onTagAliasAction(false);
        //6.跳转
        MyApplication.getContext().startActivity(intent);
        getActivity().finish();
    }

    @OnClick({R.id.change_password_ll, R.id.change_head_ll, R.id.change_phone_ll, R.id.change_name_ll, R.id.message_item, R.id.notify_item, R.id.my_clean, R.id.my_exit_ll,R.id.schedue_state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_password_ll:
                ForgetPWDOneActivity.start(getActivity());
                break;
            case R.id.change_head_ll:
                uploadHeadImage();
                break;
            case R.id.change_phone_ll:
                break;
            case R.id.change_name_ll:
                changeName();
                break;
            case R.id.message_item:
                break;
            case R.id.notify_item:
                break;
            case R.id.my_clean:
                clean_dialog();
                break;
            case R.id.my_exit_ll:
                loginOut();
                break;
            case R.id.schedue_state:
                StatisticalActivity.start(getActivity());
                break;
        }
    }

    private void changeName() {
        ChangeNameActivity.start(getActivity());
    }


    @Override
    public void onResume() {
        super.onResume();
       //
        if("2".equals(MineFragment.type)==true){
            type="1";
            //显示修改的名字
            userName.setText(newName);
            //向后台提交修改
            new ModifyInformationServiceImp(MineFragment.this).changeNickname(MyApplication.getId(),newName);
        }
    }
    private void uploadHeadImage() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popupwindow, null);
        TextView btnCamera = view.findViewById(R.id.btn_take_photo);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        //将要加载的布局绑定到popupWindow
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        //要加载到的视图
        View parent = LayoutInflater.from(getActivity()).inflate(R.layout.activity_sign_up_last, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getActivity().getWindow().setAttributes(params);
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
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
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
                        Uri uri = data.addScaleCompress(720, 720).GetUri();
                        filePath = getRealFilePath(getActivity(), uri);
                        headImage = data.addScaleCompress(720, 720).GetBitmap();
                        //这里访问接口更新头像
                        headView.setImageBitmap(headImage);
                        getFileURl();
                    }

                    @Override
                    public void onError(String error) {
                        switch (error) {
                            case ERROR_CROP_DATA:
                                Toast.makeText(getActivity(), "data为空", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void clean_dialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(R.drawable.laba001);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否清除缓存?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        CleanCacheUtil.clearAllCache(getContext());
                        Toast.makeText(getContext(), "缓存已清理完成", Toast.LENGTH_SHORT).show();
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();

    }

    private void getFileURl() {
        //1.将headImge存到本地
        File photo_file = new File(filePath);//生成该路径的文件
        new UploadServiceImp(MineFragment.this).uploadFile(photo_file, "2");
    }

    public void uploadBack() {
        //1.吧本地的头像url也更新了
        MyApplication.setImageURL(phoneURL);
        //2.发送修改头像信息到后台服务器
        new ModifyInformationServiceImp(this).changeProfile(MyApplication.getId(), phoneURL);
    }

    @OnClick(R.id.switch_out)
    public void onViewClicked() {

        if (isSwitch == true) {
            isSwitch = false;
            switchOut.setImageResource(R.drawable.kaiguan_kai001);
        } else {
            isSwitch = true;
            switchOut.setImageResource(R.drawable.kaiguan_guan001);
        }
    }
}
