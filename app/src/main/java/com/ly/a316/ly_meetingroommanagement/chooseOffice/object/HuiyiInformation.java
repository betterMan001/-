package com.ly.a316.ly_meetingroommanagement.chooseOffice.object;

import java.io.Serializable;

/**
 * 作者：余智强
 * 2019/1/23
 */
public class HuiyiInformation implements Serializable{
    String mId;
    String mRoomId;
    String mName;
    String mAddress;
    String mState;
    String mNowState;
    String mNumber;
    String mDetail;
    String mLongitude;
    String mLatitude;
    String mType;
    String mDevice;
    String mEndTime;
    String mStarttime;
    String mLockTime;
    String mLockEmployee;
    String mImageUrl;
    String rate;//会议室使用情况


    public HuiyiInformation() {

    }


    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmRoomId() {
        return mRoomId;
    }

    public void setmRoomId(String mRoomId) {
        this.mRoomId = mRoomId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmNowState() {
        return mNowState;
    }

    public void setmNowState(String mNowState) {
        this.mNowState = mNowState;
    }

    public String getmNumber() {
        return mNumber;
    }

    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }

    public String getmDetail() {
        return mDetail;
    }

    public void setmDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmDevice() {
        return mDevice;
    }

    public void setmDevice(String mDevice) {
        this.mDevice = mDevice;
    }

    public String getmEndTime() {
        return mEndTime;
    }

    public void setmEndTime(String mEndTime) {
        this.mEndTime = mEndTime;
    }

    public String getmStarttime() {
        return mStarttime;
    }

    public void setmStarttime(String mStarttime) {
        this.mStarttime = mStarttime;
    }

    public String getmLockTime() {
        return mLockTime;
    }

    public void setmLockTime(String mLockTime) {
        this.mLockTime = mLockTime;
    }

    public String getmLockEmployee() {
        return mLockEmployee;
    }

    public void setmLockEmployee(String mLockEmployee) {
        this.mLockEmployee = mLockEmployee;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}
