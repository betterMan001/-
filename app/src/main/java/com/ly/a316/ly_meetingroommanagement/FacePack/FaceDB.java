package com.ly.a316.ly_meetingroommanagement.FacePack;

import android.graphics.Bitmap;
import android.util.Log;

import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.java.ExtInputStream;
import com.guo.android_extend.java.ExtOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：余智强
 * 2018/12/7
 */
public class FaceDB {

    private final String TAG = "zjc";

    //这些全是你申请的时候才有的
    public static String appid = "GQ2faBW2dWRpKR4eUyTkMwY9zvKoa7fN2pGTo5SA9xJi";
    public static String ft_key = "6RbyJfHCKHZJF1hKycV3L1TSyhBQuF7XmAxWHkn5iPLe";
    public static String fd_key = "6RbyJfHCKHZJF1hKycV3L1Ta96SaCGYaAUM8XaJzRDU7";
    public static String fr_key = "6RbyJfHCKHZJF1hKycV3L1U4nhVCaABiA4Cw5As9EBCV";
    public static String age_key = "6RbyJfHCKHZJF1hKycV3L1UK7W1ZweZQEiiUAYWdePSb";
    public static String gender_key = "6RbyJfHCKHZJF1hKycV3L1USGuGhuAbGfCSw6P9MXjFP";

    String mDBPath;
    boolean mUpgrade;
    /**
     * 这个集合保存了所有的人脸注册信息
     *
     */
    public List<FaceRegist> mRegister;
    AFR_FSDKEngine mFREngine;
    AFR_FSDKVersion mFRVersion;

    class FaceRegist {
        String mName;//人的名字
        Map<String, AFR_FSDKFace> mFaceList;//这是将人脸的信息和人的名字相匹配

        public FaceRegist(String mName) {
            this.mName = mName;
            mFaceList = new LinkedHashMap<>();//LinkHashMap是一个有序的HashMap
        }
    }

    /**
     * 定义一个构造函数 初始化引擎
     */
    public FaceDB(String path) {
        mDBPath = path;
        mRegister = new ArrayList<>();
        mFRVersion = new AFR_FSDKVersion();
        mUpgrade = false;
        mFREngine = new AFR_FSDKEngine();//初始化引擎类

        AFR_FSDKError error = mFREngine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
        if (error.getCode() != AFR_FSDKError.MOK) {
            Log.e(TAG, "初始化引擎失败的原因：AFR_FSDK_InitialEngine fail! error code :" + error.getCode());
        } else {
            mFREngine.AFR_FSDK_GetVersion(mFRVersion);
            //版本信息：AFR_FSDK_GetVersion=1.2.0.42
            Log.d(TAG, "版本信息：AFR_FSDK_GetVersion=" + mFRVersion.toString());
        }
    }

    /**
     * 定义析构函数释放引擎占用的系统资源
     */
    public void destory() {
        if (mFREngine != null) {
            mFREngine.AFR_FSDK_UninitialEngine();
        }
    }

    /**
     * 保存人脸信息
     */
    public boolean savaInfo() {
        try {
            FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt");
            Log.i(TAG, "人脸信息保存的位置：" + mDBPath + "/face.txt");//mDBpath/storage/emulated/0/Android/data/com.arcsoft.sdk_demo/cache
            ExtOutputStream bos = new ExtOutputStream(fs);
            bos.writeString(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel());//保存版本信息
            Log.i(TAG, "saveInfo执行了，保存的信息有：mFRVersion.toString()： " + mFRVersion.toString() + "  mFRVersion.getFeatureLevel()： " + mFRVersion.getFeatureLevel());
            bos.close();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 注册人脸 将人脸和个人信息集合在一起
     * 可以判断有没有这个文件
     *
     * @return
     */
    private boolean loadInfo() {
        if (!mRegister.isEmpty()) {
            return false;
        }
        try {
            //从这个文件找看看有没有这张脸
            FileInputStream fs = new FileInputStream(mDBPath + "/face.txt");
            ExtInputStream bos = new ExtInputStream(fs);
            String version_saved = bos.readString();
            if (version_saved.equals(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel())) {
                mUpgrade = true;
            }
            if (version_saved != null) {
                for (String name = bos.readString(); name != null; name = bos.readString()) {
                    if (new File(mDBPath + "/" + name + ".data").exists()) {
                        //如果这张脸存在的话就只改变这张脸的信息
                        mRegister.add(new FaceRegist(new String(name)));
                    }
                }
            }
            bos.close();
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 使用loadFaces从文件中读取人脸
     *
     * @return
     */
    public boolean loadFaces() {
        if (loadInfo()) {
            Log.i(TAG, "打印全部人脸信息");
            try {
                for (FaceRegist face : mRegister) {
                    Log.d(TAG, "人脸名字:" + face.mName + "'s face feature data.");
                    FileInputStream fs = new FileInputStream(mDBPath + "/" + face.mName + ".data");
                    ExtInputStream bos = new ExtInputStream(fs);
                    AFR_FSDKFace afr = null;
                    do {
                        if (afr != null) {
                            if (mUpgrade) {
                                //upgrade data.
                            }
                            String keyFile = bos.readString();
                            face.mFaceList.put(keyFile, afr);
                        }
                        afr = new AFR_FSDKFace();
                    } while (bos.readBytes(afr.getFeatureData()));
                    bos.close();
                    fs.close();
                    Log.d(TAG, "人脸信息库的总人脸数目：load name: size = " + face.mFaceList.size());
                }
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 实现添加人脸
     *
     * @param name
     * @param face
     * @param faceicon
     */
    //注册的人的名字 人脸特征信息 识别的人脸的bitmap图片
    public void addFace(String name, AFR_FSDKFace face, Bitmap faceicon) {
        try {
            // save face
            String keyPath = mDBPath + "/" + System.nanoTime() + ".jpg";
            File keyFile = new File(keyPath);
            OutputStream stream = new FileOutputStream(keyFile);
            if (faceicon.compress(Bitmap.CompressFormat.JPEG, 80, stream)) {
                Log.d(TAG, "saved face bitmap to jpg!");
            }
            stream.close();
            //check if already registered.
            boolean add = true;
            for (FaceRegist frface : mRegister) {
                if (frface.mName.equals(name)) {
                    frface.mFaceList.put(keyPath, face);
                    add = false;
                    break;
                }
            }
            if (add) { // not registered.
                FaceRegist frface = new FaceRegist(name);
                frface.mFaceList.put(keyPath, face);
                mRegister.add(frface);
            }

            if (savaInfo()) {
                //update all names
                FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
                ExtOutputStream bos = new ExtOutputStream(fs);
                for (FaceRegist frface : mRegister) {
                    bos.writeString(frface.mName);
                }
                bos.close();
                fs.close();

                //save new feature
                fs = new FileOutputStream(mDBPath + "/" + name + ".data", true);
                bos = new ExtOutputStream(fs);
                bos.writeBytes(face.getFeatureData());
                bos.writeString(keyPath);
                bos.close();
                fs.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //删除
    public boolean delete(String name) {
        try {
            boolean find = false;
            for (FaceRegist frface : mRegister) {
                if (frface.mName.equals(name)) {
                    File delfile = new File(mDBPath + "/" + name + ".data");
                    if (delfile.exists()) {
                        delfile.delete();
                    }
                    mRegister.remove(frface);
                    find = true;
                    break;
                }
            }
            if (find) {
                if (savaInfo()) {
                    //update all names
                    FileOutputStream fs = new FileOutputStream(mDBPath + "/face.txt", true);
                    ExtOutputStream bos = new ExtOutputStream(fs);
                    for (FaceRegist frface : mRegister) {
                        bos.writeString(frface.mName);
                    }
                    bos.close();
                    fs.close();
                }
            }
            return find;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean upgrade() {
        return false;
    }
}
