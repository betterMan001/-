package com.ly.a316.ly_meetingroommanagement.endActivity.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.util.Log;

import com.google.gson.JsonObject;
import com.ly.a316.ly_meetingroommanagement.chooseOffice.customview.LoadingDialog;
import com.ly.a316.ly_meetingroommanagement.endActivity.activity.End_Activity;
import com.ly.a316.ly_meetingroommanagement.utils.Net;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.netease.nim.uikit.common.util.file.FileUtil.getMimeType;

/**
 * 作者：余智强
 * 2019/2/16
 */
public class TransDoucument {
    private List<String> filepath_list;
    private Context context;
    String  wangzhi;
    End_Activity end_activity;

    public TransDoucument(List<String> filepath_list, Context context,End_Activity end_activity) {
        this.filepath_list = filepath_list;
        this.end_activity = end_activity;
        this.context = context;
    }
  android.os.Handler handler = new android.os.Handler(){
      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);
          if(msg.what==0x21){
              end_activity.success_back("文件上传成功",wangzhi);
          }
      }
  };


    public void transDocument() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(getRequest(Net.transFile, filepath_list));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String dsa = response.body().string();
                JSONObject jsonObject= null;
                try {
                    jsonObject = new JSONObject(dsa);
                    wangzhi = jsonObject.getString("result");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0x21);
            }
        });

    }

    static String filenames;

    private static RequestBody getRequestBody(List<String> fileNames) {
        //创建MultipartBody.Builder，用于添加请求的数据
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < fileNames.size(); i++) { //对文件进行遍历
            File file = new File(fileNames.get(i)); //生成文件
            //根据文件的后缀名，获得文件类型
            String fileType = getMimeType(file.getName());
            if (isCNChar(file.getName())) {
                String fileTyle = file.getName().substring(file.getName().lastIndexOf("."), file.getName().length());
                filenames = System.currentTimeMillis() + "." + fileTyle;
            } else {
                filenames = file.getName();
            }

            builder.addFormDataPart( //给Builder添加上传的文件
                    "profile",  //请求的名字
                    filenames, //文件的文字，服务器端用来解析的
                    RequestBody.create(MediaType.parse(fileType), file) //创建RequestBody，把上传的文件放入
            );
        }
        return builder.build(); //根据Builder创建请求
    }

    private static Request getRequest(String url, List<String> fileNames) {
        Request.Builder builder = new Request.Builder();
        builder.url(url)
                .post(getRequestBody(fileNames));
        return builder.build();
    }

    /**
     * 判断字符串中是否含有中文
     */
    public static boolean isCNChar(String s) {
        boolean booleanValue = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 128) {
                booleanValue = true;
                break;
            }
        }
        return booleanValue;
    }


}
